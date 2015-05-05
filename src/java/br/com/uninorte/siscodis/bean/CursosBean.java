/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.CursosDAO;
import br.com.uninorte.siscodis.dao.InstituicoesDAO;
import br.com.uninorte.siscodis.dao.TurmasDAO;
import br.com.uninorte.siscodis.dao.TurnosDAO;
import br.com.uninorte.siscodis.dao.TurnosDoCursoDAO;
import br.com.uninorte.siscodis.entidades.Cursos;
import br.com.uninorte.siscodis.entidades.Instituicoes;
import br.com.uninorte.siscodis.entidades.Turmas;
import br.com.uninorte.siscodis.entidades.Turnos;
import br.com.uninorte.siscodis.entidades.TurnosDoCurso;
import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
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
public class CursosBean implements Serializable {

    private Cursos curso = new Cursos();
    private List<Cursos> cursos = new ArrayList<Cursos>();
    private List<Turmas> turmas = new ArrayList<Turmas>();
    private Integer idInstituicoes;
    private List<String> turnos = new ArrayList<String>();
    private List<TurnosDoCurso> turnosBD = new ArrayList<TurnosDoCurso>();

    @PostConstruct
    public void construct() {
        cursos = new CursosDAO().ListaTodos();
    }

    public List<TurnosDoCurso> getTurnosBD() {
        turnosBD = new TurnosDoCursoDAO().pesquisaTurnoPorCurso(curso.getIdCursos());
        return turnosBD;
    }

    public void setTurnosBD(List<TurnosDoCurso> turnosBD) {
        this.turnosBD = turnosBD;
    }

    public List<String> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<String> turnos) {
        this.turnos = turnos;
    }

    public List<Turmas> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turmas> turmas) {
        this.turmas = turmas;
    }

    public Integer getIdInstituicoes() {
        idInstituicoes = curso.getInstituicao().getIdInstituicoes();
        return idInstituicoes;
    }

    public void setIdInstituicoes(Integer idInstituicoes) {
        this.idInstituicoes = idInstituicoes;
    }

    public Cursos getCurso() {
        return curso;
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
    }

    public List<Cursos> getCursos() {
        return cursos;
    }

    public void setCursos(List<Cursos> cursos) {
        this.cursos = cursos;
    }

    public String carregaListaTurnos() {
        getTurnosBD();
        for (TurnosDoCurso tbd : turnosBD) {
            turnos.add(tbd.getTurno().getIdTurnos().toString());
        }
        return null;
    }

    public String salvar() {
        CursosDAO cd = new CursosDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        TurnosDoCursoBean tdcb = new TurnosDoCursoBean();
        try {
            Instituicoes i = new InstituicoesDAO().pesquisaPorId(idInstituicoes);
            curso.setInstituicao(i);
            if (curso.getQtdPeriodos() <= 0) {
                curso.setQtdPeriodos(null);
                throw new IllegalArgumentException("A 'Quantidade de Períodos' não deve ser menor ou igual a 0");
            } else if (curso.getCodigo() <= 0) {
                curso.setCodigo(null);
                throw new IllegalArgumentException("O 'Código' não deve ser menor ou igual a 0");
            } else {
                if (curso.getIdCursos() == null) {
                    cd.salvar(curso);
                    for (String tur : turnos) {
                        Turnos t = new TurnosDAO().pesquisaPorId(Integer.parseInt(tur));
                        tdcb.salvar(t, curso);
                    }
                    msg.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "O curso " + curso.getNome() + " foi inserido com sucesso.", null));
                } else {
                    List<TurnosDoCurso> tdt = getTurnosBD();
                    for (TurnosDoCurso tde : tdt) {
                        tdcb.excluir(tde);
                    }
                    cd.alterar(curso);
                    for (String tur : turnos) {
                        Turnos t = new TurnosDAO().pesquisaPorId(Integer.parseInt(tur));
                        tdcb.salvar(t, curso);
                    }
                    msg.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "O curso " + curso.getNome() + " foi alterado com sucesso.", null));
                    RequestContext.getCurrentInstance().execute("inserir.hide()");
                }
            }
            limpar();
        } catch (IllegalArgumentException e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    e.getMessage(), null));
        } catch (SQLIntegrityConstraintViolationException ex) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Já existe um Curso cadastrado com esse código!", null));
        } catch (InterruptedException e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao adicionar o(s) turno(s) no curso " + curso.getNome(), null));
        } catch (InstantiationException e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao alterar o(s) turno(s) no curso " + curso.getNome(), null));
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao inserir o curso " + curso.getNome(), null));
        }
        construct();
        return null;
    }

    public String excluir() {
        CursosDAO cd = new CursosDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        TurnosDoCursoBean tdcb = new TurnosDoCursoBean();
        try {
            turmas = new TurmasDAO().pesquisaTurmasPorCurso(curso.getIdCursos());
            if (turmas.isEmpty()) {
                List<TurnosDoCurso> tdt = getTurnosBD();
                for (TurnosDoCurso tde : tdt) {
                    tdcb.excluir(tde);
                }
                cd.excluir(curso);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "O curso " + curso.getNome() + " foi excluído com sucesso.", null));
                curso = new Cursos();
            } else {
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "o curso " + curso.getNome() + " possui dependências com a tabela turmas. É necessário corrigí-las.", null));
            }
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao excluir o curso " + curso.getNome(), null));
        }
        construct();
        return null;
    }

    public String limpar() {
        curso = new Cursos();
        idInstituicoes = null;
        turnos = new ArrayList<String>();
        turnosBD = new ArrayList<TurnosDoCurso>();
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

    public String listaTurnosData(Cursos c) {
        String msg = "";
        List<TurnosDoCurso> listaT = new TurnosDoCursoDAO().pesquisaTurnoPorCurso(c.getIdCursos());
        int qtd = listaT.size();
        if (qtd >= 2) {
            Iterator i = listaT.iterator();
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
            for (TurnosDoCurso t : listaT) {
                msg += t.getTurno().getNome();
            }
            return msg;
        }
    }
}
