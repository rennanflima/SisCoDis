/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.EquipamentoDaSalaDAO;
import br.com.uninorte.siscodis.dao.SalasDAO;
import br.com.uninorte.siscodis.dao.TurmasDAO;
import br.com.uninorte.siscodis.entidades.EquipamentoDaSala;
import br.com.uninorte.siscodis.entidades.Salas;
import br.com.uninorte.siscodis.entidades.Turmas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author rennan
 */
@ManagedBean
@SessionScoped
public class BloqueiaDesbloqueiaSalaBean implements Serializable {

    private Salas sala = new Salas();
    private List<Salas> salas = new ArrayList<Salas>();
    private List<EquipamentoDaSala> eqsDaSala = new ArrayList<EquipamentoDaSala>();
    private boolean bloqueia = true;
    private boolean desbloqueia = false;
    
    public boolean isBloqueia() {
        return bloqueia;
    }

    public void setBloqueia(boolean bloqueia) {
        this.bloqueia = bloqueia;
    }

    public boolean isDesbloqueia() {
        return desbloqueia;
    }

    public void setDesbloqueia(boolean desbloqueia) {
        this.desbloqueia = desbloqueia;
    }

    public List<EquipamentoDaSala> getEqsDaSala() {
        eqsDaSala = new EquipamentoDaSalaDAO().consultaPorSala(sala.getIdSalas());
        return eqsDaSala;
    }

    public void setEqsDaSala(List<EquipamentoDaSala> eqsDaSala) {
        this.eqsDaSala = eqsDaSala;
    }

    public Salas getSala() {
        return sala;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }

    public List<Salas> getSalas() {
        salas = new SalasDAO().ListaTodos();
        return salas;
    }

    public void setSalas(List<Salas> salas) {
        this.salas = salas;
    }

    public String verificaSituacao() {
        if (sala.getSituacao().equals("Liberada")) {
            setBloqueia(true);
            setDesbloqueia(false);
        } else {
            setBloqueia(false);
            setDesbloqueia(true);
        }
        return null;
    }

    public String salvaBloqueio() {
        SalasDAO sd = new SalasDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            List<Turmas> t = new TurmasDAO().pesquisaTurmasPorSala(sala.getIdSalas());
            if (t.isEmpty()) {
                sala.setSituacao("Bloqueada");
                sd.alterar(sala);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "A sala " + sala.getNumero() + " foi " + sala.getSituacao() + " com sucesso.", null));
                msg.getExternalContext().getFlash().setKeepMessages(true);
                msg.getExternalContext().redirect("/SisCoDis/servicos/bloqueioDesbloqueioSala/");
            }else{
                msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao Bloquear a sala " + sala.getNumero() + " possui dependências com a tabela turmas. É necessário corrigí-las.", null));
                sala.setObsSituacao("");
            }
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao " + sala.getSituacao() + " a sala " + sala.getNumero(), null));
        }
        return null;
    }

    public String salvaDesbloqueio() {
        SalasDAO sd = new SalasDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            sala.setSituacao("Liberada");
            sala.setObsSituacao(null);
            sd.alterar(sala);
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "A sala " + sala.getNumero() + " foi " + sala.getSituacao() + " com sucesso.", null));
            msg.getExternalContext().getFlash().setKeepMessages(true);
            msg.getExternalContext().redirect("/SisCoDis/servicos/bloqueioDesbloqueioSala/");
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao " + sala.getSituacao() + " a sala " + sala.getNumero(), null));
        }
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
}
