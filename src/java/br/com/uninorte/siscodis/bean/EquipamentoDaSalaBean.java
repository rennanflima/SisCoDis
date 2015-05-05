/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.EquipamentoDaSalaDAO;
import br.com.uninorte.siscodis.dao.EquipamentosDAO;
import br.com.uninorte.siscodis.dao.SalasDAO;
import br.com.uninorte.siscodis.entidades.EquipamentoDaSala;
import br.com.uninorte.siscodis.entidades.Equipamentos;
import br.com.uninorte.siscodis.entidades.Salas;
import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rennan Francisco
 */
@ManagedBean
@ViewScoped
public class EquipamentoDaSalaBean implements Serializable {

    private EquipamentoDaSala eqSala = new EquipamentoDaSala();
    private List<EquipamentoDaSala> eqSalas = new ArrayList<EquipamentoDaSala>();
    private List<Equipamentos> equipamentos = new ArrayList<Equipamentos>();
    private Salas sala = new Salas();
    private Integer idEquipamento;


    @PostConstruct
    public void construct() {
        if (sala == null) {
            eqSalas = null;
        } else {
            eqSalas = new EquipamentoDaSalaDAO().consultaPorSala(sala.getIdSalas());
        }
    }

    public List<Equipamentos> getEquipamentos() {
        equipamentos = new EquipamentosDAO().ListaTodos();
        return equipamentos;
    }

    public void setEquipamentos(List<Equipamentos> equipamentos) {
        this.equipamentos = equipamentos;
    }

    public Salas getSala() {
        return sala;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }

    public Integer getIdEquipamento() {
        idEquipamento = eqSala.getEquipamento().getIdEquipamentos();
        return idEquipamento;
    }

    public void setIdEquipamento(Integer idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public EquipamentoDaSala getEqSala() {
        return eqSala;
    }

    public void setEqSala(EquipamentoDaSala eqSala) {
        this.eqSala = eqSala;
    }

    public List<EquipamentoDaSala> getEqSalas() {
        return eqSalas;
    }

    public void setEqSalas(List<EquipamentoDaSala> eqSalas) {
        this.eqSalas = eqSalas;
    }

    public List<Salas> completeSalas(String num) {
        return new SalasDAO().pesquisaSalas(num);
    }

    public String salvar() {
        EquipamentoDaSalaDAO eqd = new EquipamentoDaSalaDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            Equipamentos equipamento = new EquipamentosDAO().pesquisaPorId(idEquipamento);
            eqSala.setEquipamento(equipamento);
            eqSala.setSala(sala);
            if (eqSala.getSituacao().equals("Funcionando")) {
                eqSala.setObsSituacaoE(null);
            }
            if (eqSala.getIdEquipamentoDaSala() == null) {
                if (eqSala.getQtd() <= 0) {
                    eqSala.setQtd(null);
                    throw new IllegalArgumentException("A 'Quantidade' não deve ser menor ou igual a 0");
                } else {
                    eqd.salvar(eqSala);
                    msg.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "O equipamento " + eqSala.getEquipamento().getNome() + " foi adicionado com sucesso", null));
                }
            } else {
                eqd.alterar(eqSala);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "O equipamento " + eqSala.getEquipamento().getNome() + " foi alterado com sucesso", null));
                RequestContext.getCurrentInstance().execute("inserirEquipamento.hide()");
            }
            eqSala = new EquipamentoDaSala();
        } catch (SQLIntegrityConstraintViolationException ex) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ex.getMessage(), null));
        } catch (IllegalArgumentException ex) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ex.getMessage(), null));
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao adicionar o(s) equipamento(s) na sala " + eqSala.getSala().getNumero(), null));
            System.out.println("Msg: " + e);
        }
        construct();
        return null;
    }

    public String excluir() {
        EquipamentoDaSalaDAO eqd = new EquipamentoDaSalaDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            eqd.excluir(eqSala);
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "O equipamento " + eqSala.getEquipamento().getNome() + " foi removido com sucesso", null));
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao excluir o(s) equipamento(s) na sala " + eqSala.getSala().getNumero(), null));
        }
        construct();
        return null;
    }

    public String excluirSala(EquipamentoDaSala eq) throws Exception {
        EquipamentoDaSalaDAO eqd = new EquipamentoDaSalaDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            eqd.excluir(eq);
        } catch (Exception e) {
            throw new InterruptedException();
        }
        return null;
    }
    
    public String limpar() {
        eqSala = new EquipamentoDaSala();
        idEquipamento = null;
        return null;
    }

    public String limparLista() {
        sala = new Salas();
        eqSalas = null;
        construct();
        return null;
    }

    public boolean verificaLista() {
        if (sala.getIdSalas() != null) {
            return false;
        } else if (eqSalas.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public String carregaLista() {
        construct();
        return null;
    }
}
