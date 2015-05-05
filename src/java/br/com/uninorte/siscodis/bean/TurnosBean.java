/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.TurnosDAO;
import br.com.uninorte.siscodis.dao.TurnosDoCursoDAO;
import br.com.uninorte.siscodis.entidades.Turnos;
import br.com.uninorte.siscodis.entidades.TurnosDoCurso;
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
public class TurnosBean implements Serializable {

    private Turnos turno = new Turnos();
    private List<Turnos> turnos = new ArrayList<Turnos>();
    private List<TurnosDoCurso> tdt = new ArrayList<TurnosDoCurso>();

    @PostConstruct
    public void construct() {
        turnos = new TurnosDAO().ListaTodos();
        
    }

    public Turnos getTurno() {
        return turno;
    }

    public void setTurno(Turnos turno) {
        this.turno = turno;
    }

    public List<Turnos> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turnos> turnos) {
        this.turnos = turnos;
    }

    public String salvar() {
        TurnosDAO td = new TurnosDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            if (turno.getIdTurnos() == null) {
                td.salvar(turno);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "O turno '" + turno.getNome() + "' foi inserido com sucesso.", null));
            } else {
                td.alterar(turno);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "O turno '" + turno.getNome() + "' foi alterado com sucesso.", null));
                RequestContext.getCurrentInstance().execute("inserir.hide()");
            }
            turno = new Turnos();
        } catch (SQLIntegrityConstraintViolationException ex) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Já existe um Turno cadastrado com esse nome!", null));
        } catch (IllegalArgumentException e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "A Hora Final deve ser maior do que Hora Inicial.", null));
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao inserir o turno " + turno.getNome(), null));
        }
        construct();
        return null;
    }

    public String excluir() {
        TurnosDAO td = new TurnosDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            tdt = new TurnosDoCursoDAO().pesquisaTurnoPorCurso(turno.getIdTurnos());
            if (tdt.isEmpty()) {
                td.excluir(turno);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "O turno " + turno.getNome() + " foi excluído com sucesso.", null));
                turno = new Turnos();
            } else {
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "O turno " + turno.getNome() + " possui dependências com a tabela turmas. É necessário corrigí-las.", null));
            }
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao excluir o turno " + turno.getNome(), null));
        }
        construct();
        return null;
    }

    public String limpar() {
        turno = new Turnos();
        return null;

    }
}
