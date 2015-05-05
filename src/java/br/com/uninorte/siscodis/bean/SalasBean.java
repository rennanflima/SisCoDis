/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.BlocosDAO;
import br.com.uninorte.siscodis.dao.CadeirasDAO;
import br.com.uninorte.siscodis.dao.EquipamentoDaSalaDAO;
import br.com.uninorte.siscodis.dao.SalasDAO;
import br.com.uninorte.siscodis.dao.TurmasDAO;
import br.com.uninorte.siscodis.entidades.Blocos;
import br.com.uninorte.siscodis.entidades.Cadeiras;
import br.com.uninorte.siscodis.entidades.EquipamentoDaSala;
import br.com.uninorte.siscodis.entidades.Salas;
import br.com.uninorte.siscodis.entidades.Turmas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rennan Francisco
 */
@ManagedBean
@ViewScoped
public class SalasBean implements Serializable {

    private Salas sala = new Salas();
    private List<Salas> salas = new ArrayList<Salas>();
    private List<Turmas> turmas = new ArrayList<Turmas>();
    private List<EquipamentoDaSala> eq = new ArrayList<EquipamentoDaSala>();
    private Integer idBlocos;
    private Integer idCadeiras;
    private String proprietario;
    private List<EquipamentoDaSala> eqsDaSala = new ArrayList<EquipamentoDaSala>();
    private List<Blocos> blocos = new ArrayList<Blocos>();
    private Integer andar;
    private List<SelectItem> pisos = new ArrayList<SelectItem>();
    private int cont = 0;

    @PostConstruct
    public void construct() {
        salas = new SalasDAO().ListaTodos();
    }

    public List<SelectItem> getPisos() {
        SelectItem piso = new SelectItem();
        if (idBlocos != null) {
            Blocos bloco = new BlocosDAO().pesquisaPorId(idBlocos);
            for (int i = 1; i <= bloco.getQtdAndares(); i++) {
                if (bloco.isSubsolo()) {
                    if (i == 1) {
                        piso.setLabel("Subsolo");
                        piso.setValue(1);
                        pisos.add(piso);
                    } else if (i == 2) {
                        piso.setLabel("Térreo");
                        piso.setValue(2);
                        pisos.add(piso);
                    } else {
                        piso.setLabel((i - 2) + "º");
                        piso.setValue(i);
                        pisos.add(piso);
                    }
                    piso = new SelectItem();
                } else {
                    if (i == 1) {
                        piso.setLabel("Térreo");
                        piso.setValue(1);
                        pisos.add(piso);

                    } else {
                        piso.setLabel((i - 1) + "º");
                        piso.setValue(i);
                        pisos.add(piso);
                    }
                    piso = new SelectItem();
                }
            }
        }
        return pisos;
    }

    public void setPisos(List<SelectItem> pisos) {
        this.pisos = pisos;
    }

    public Integer getAndar() {
        if (sala.getIdSalas() != null && cont == 1) {
            andar = retornaValorAndar(sala.getAndar(), sala.getBloco());
        }
        return andar;
    }

    public void setAndar(Integer andar) {
        this.andar = andar;
    }

    public List<Blocos> getBlocos() {
        blocos = new BlocosDAO().listaTodosCombo();
        return blocos;
    }

    public void setBlocos(List<Blocos> blocos) {
        this.blocos = blocos;
    }

    public List<EquipamentoDaSala> getEqsDaSala() {
        eqsDaSala = new EquipamentoDaSalaDAO().consultaPorSala(sala.getIdSalas());
        return eqsDaSala;
    }

    public void setEqsDaSala(List<EquipamentoDaSala> eqsDaSala) {
        this.eqsDaSala = eqsDaSala;
    }

    public String getProprietario() {
        if (idCadeiras == null) {
            proprietario = "";
        } else {
            proprietario = new CadeirasDAO().pesquisaPorId(idCadeiras).getProprietario();
        }
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public List<Turmas> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turmas> turmas) {
        this.turmas = turmas;
    }

    public Integer getIdCadeiras() {
        idCadeiras = sala.getCadeira().getIdCadeiras();
        return idCadeiras;
    }

    public void setIdCadeiras(Integer idCadeiras) {
        this.idCadeiras = idCadeiras;
    }

    public Integer getIdBlocos() {
        idBlocos = sala.getBloco().getIdBlocos();
        return idBlocos;
    }

    public void setIdBlocos(Integer idBlocos) {
        this.idBlocos = idBlocos;
    }

    public Salas getSala() {
        return sala;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }

    public List<Salas> getSalas() {
        return salas;
    }

    public void setSalas(List<Salas> salas) {
        this.salas = salas;
    }

    public String salvar() {
        SalasDAO sD = new SalasDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            Cadeiras cd = new CadeirasDAO().pesquisaPorId(idCadeiras);
            Blocos bl = new BlocosDAO().pesquisaPorId(idBlocos);
            sala.setBloco(bl);
            sala.setCadeira(cd);
            sala.setAndar(verificaAndar(andar, bl));
            if (sala.getSituacao().equals("Liberada")) {
                sala.setObsSituacao(null);
            }
            if (sala.getNumero().equals("0")) {
                sala.setNumero(null);
                throw new IllegalArgumentException("O 'Número' não deve ser igual a 0");
            } else if (sala.getCapacidade() <= 0) {
                sala.setCapacidade(null);
                throw new IllegalArgumentException("A 'Capacidade' não deve ser menor ou igual a 0");
            } else {
                if (sala.getIdSalas() == null) {
                    sD.salvar(sala);
                    msg.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "A sala " + sala.getNumero() + " foi inserida com sucesso.", null));
                } else {
                    sD.alterar(sala);
                    msg.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "A sala " + sala.getNumero() + " foi alterada com sucesso.", null));
                    RequestContext.getCurrentInstance().execute("inserir.hide()");
                }
            }
            limpar();
        } catch (InterruptedException ex) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao adicionar o(s) equipamento(s) na sala " + sala.getNumero(), null));
        } catch (IllegalArgumentException ex) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ex.getMessage(), null));
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao inserir a sala " + sala.getNumero(), null));
        }
        construct();
        return null;
    }

    public String excluir() {
        SalasDAO sD = new SalasDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        EquipamentoDaSalaBean edsb = new EquipamentoDaSalaBean();
        try {
            turmas = new TurmasDAO().pesquisaTurmasPorSala(sala.getIdSalas());
            eq = new EquipamentoDaSalaDAO().consultaPorSala(sala.getIdSalas());
            if (turmas.isEmpty()) {
                if (!eq.isEmpty()) {
                    for (EquipamentoDaSala tde : eq) {
                        edsb.excluirSala(tde);
                    }
                }
                sD.excluir(sala);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "A sala " + sala.getNumero() + " foi excluída com sucesso.", null));
                limpar();
            } else {
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "A sala " + sala.getNumero() + " possui dependências com a tabela turmas e associações com equipamentos. É necessário corrigí-las.", null));
            }
        } catch (InterruptedException e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao excluir o(s) equipamento(s) da sala " + sala.getNumero(), null));
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao excluir a sala " + sala.getNumero(), null));
        }
        construct();
        return null;
    }

    public String limpar() {
        sala = new Salas();
        idBlocos = null;
        idCadeiras = null;
        andar = null;
        pisos = new ArrayList<SelectItem>();
        return null;
    }

    public String verificaStatus(Salas s) {
        if (s != null) {
            List<EquipamentoDaSala> eqs = new EquipamentoDaSalaDAO().consultaPorSala(s.getIdSalas());
            Integer cont = 0;
            for (EquipamentoDaSala e : eqs) {
                if (e.getSituacao().equals("Defeito")) {
                    cont++;
                }
            }
            if (s.getSituacao().equals("Liberada")) {
                if (cont >= 1) {
                    return "alerta.png";
                } else {
                    return "ativado.png";
                }
            } else {
                return "desativado.png";
            }
        }
        return null;
    }

    public String verificaAndar(Integer temp, Blocos b) {
        if (b.isSubsolo()) {
            if (temp == 1) {
                return "Subsolo";
            } else if (temp == 2) {
                return "Térreo";
            } else {
                return (temp-2) + "º";
            }
        } else {
            if (temp == 1) {
                return "Térreo";
            } else {
                return (temp-1) + "º";
            }
        }
    }

    public String limpaCombo() {
        pisos = new ArrayList<SelectItem>();
        return null;
    }

    public Integer retornaValorAndar(String temp, Blocos b) {
        pisos = new ArrayList<SelectItem>();
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(temp);
        String s = "";
        System.out.println("Temp: " + temp);
        Integer op = 0;
        cont++;
        if (b.isSubsolo()) {
            if (temp.equals("Subsolo")) {
                return 1;
            } else if (temp.equals("Térreo")) {
                return 2;
            } else {
                while (m.find()) {
                    s+=m.group();
                }
                op = Integer.parseInt(s.toString());
                return (op+2);
            }
        } else {
            if (temp.equals("Térreo")) {
                return 1;
            } else {
                while (m.find()) {
                    s+=m.group();
                }
                op = Integer.parseInt(s.toString());
                return (op+1);
            }
        }
    }
    
    public String conta(){
        cont = 0;
        cont++;
        return null;
    }
}
