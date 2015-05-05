/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.PeriodoDAO;
import br.com.uninorte.siscodis.dao.TurmasDAO;
import br.com.uninorte.siscodis.dao.PeriodosDaSalaDAO;
import br.com.uninorte.siscodis.dao.TurnosDoCursoDAO;
import br.com.uninorte.siscodis.entidades.Periodos;
import br.com.uninorte.siscodis.entidades.Turmas;
import br.com.uninorte.siscodis.entidades.TurnosDoCurso;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Rennan Francisco
 */
@ManagedBean
@SessionScoped
public class FinalizaSemestreBean implements Serializable {

    private Turmas turma = new Turmas();
    private List<Turmas> turmas = new ArrayList<Turmas>();
    private List<TurnosDoCurso> turnosBD = new ArrayList<TurnosDoCurso>();
    private int v = 0;

    public List<TurnosDoCurso> getTurnosBD() {
        turnosBD = new TurnosDoCursoDAO().pesquisaTurnoPorCurso(turma.getCurso().getIdCursos());
        return turnosBD;
    }

    public void setTurnosBD(List<TurnosDoCurso> turnosBD) {
        this.turnosBD = turnosBD;
    }

    public Turmas getTurma() {
        return turma;
    }

    public void setTurma(Turmas turma) {
        this.turma = turma;
    }

    public List<Turmas> getTurmas() {
        turmas = new TurmasDAO().ListaTodos();
        return turmas;
    }

    public void setTurmas(List<Turmas> turmas) {
        this.turmas = turmas;
    }

    public String fimSemetre() {
        TurmasDAO td = new TurmasDAO();
        PeriodosDaSalaDAO pds = new PeriodosDaSalaDAO();
        PeriodoDAO pd = new PeriodoDAO();
        Periodos p;
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            int temp = 0;
            for (Turmas t : turmas) {
                temp = t.getPeriodo().getPeriodo();
                p = pd.verificaPeriodo((temp + 1), t.getCurso().getIdCursos());
                if (p == null) {
                    if ((temp+1) > t.getCurso().getQtdPeriodos()) {
                        t.setStatus("Formada");
                    }
                }else{
                    t.setPeriodo(p);
                }
                t.setSala(null);
                td.alterar(t);
            }
            pds.truncar();
            turmas = null;
            v = 1;
            msg.getExternalContext().redirect("/SisCoDis/servicos/finalizarSemestre/index.xhtml");
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao Finalizar o Semestre", null));
        }
        return null;
    }

    public String excluiTurmas() {
        TurmasDAO td = new TurmasDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            for (Turmas t : turmas) {
                if (t.getStatus().equals("Formada")) {
                    td.excluir(t);
                }
            }
            turmas = null;
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "As Turmas formadas foram excluídas com sucesso", null));
            msg.getExternalContext().getFlash().setKeepMessages(true);
            msg.getExternalContext().redirect("/SisCoDis/servicos/finalizarSemestre/finalizaSemestre.xhtml");
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao excluir as Turmas", null));
        }
        return null;
    }

    public String listaTurnos() {
        String msg = "";
        getTurnosBD();
        int qtd = turnosBD.size();
        if (qtd >= 2) {
            Iterator i = turnosBD.iterator();
            while (i.hasNext()) {
                TurnosDoCurso t = (TurnosDoCurso) i.next();
                if (i.hasNext()) {
                    msg += t.getTurno().getNome() + ", ";
                } else {
                    msg += t.getTurno().getNome();
                }

            }
            return "Integral (" + msg + ")";
        } else {
            for (TurnosDoCurso t : turnosBD) {
                msg += t.getTurno().getNome();
            }
            return msg;
        }
    }

    public String listaSala() {
        if (turma.getSala() == null) {
            return "Turma sem Sala";
        } else {
            return turma.getSala().getNumero() + " - " + turma.getSala().getBloco().getNome();
        }
    }

    public String verificaFormada() {
        FacesContext msg = FacesContext.getCurrentInstance();
        int cont = 0;
        String ret = null;
        getTurmas();
        try {
            for (Turmas t : turmas) {
                if (t.getStatus().equals("Formada")) {
                    cont++;
                }
            }
            if (cont == 0) {
                msg.getExternalContext().redirect("/SisCoDis/servicos/finalizarSemestre/finalizaSemestre.xhtml");
            } else {
                msg.getExternalContext().redirect("/SisCoDis/servicos/finalizarSemestre/turmasFormadas.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(FinalizaSemestreBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public String verificaV() {
        FacesContext msg = FacesContext.getCurrentInstance();
        if (v == 1) {
            v = 0;
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "O Semestre foi finalizado com sucesso", null));
        }
        return null;
    }
}
