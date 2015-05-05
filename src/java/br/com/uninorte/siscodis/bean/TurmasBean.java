/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.CursosDAO;
import br.com.uninorte.siscodis.dao.PeriodoDAO;
import br.com.uninorte.siscodis.dao.TurmasDAO;
import br.com.uninorte.siscodis.dao.TurnosDoCursoDAO;
import br.com.uninorte.siscodis.entidades.Cursos;
import br.com.uninorte.siscodis.entidades.Periodos;
import br.com.uninorte.siscodis.entidades.Turmas;
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
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rennan Francisco
 */
@ManagedBean
@ViewScoped
public class TurmasBean implements Serializable {

    private Turmas turma = new Turmas();
    private List<Turmas> turmas = new ArrayList<Turmas>();
    private Integer idSalas;
    private Integer idInstituicoes;
    private Integer idPeriodos;
    private List<Periodos> peridoCurso = new ArrayList<Periodos>();
    private Cursos curso = new Cursos();

    @PostConstruct
    public void construct() {
        turmas = new TurmasDAO().ListaTurmasCursando();
    }

    public Cursos getCurso() {
        if (turma.getIdTurmas() == null) {
            return curso;
        } else {
            return turma.getCurso();
        }
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
    }

    public Integer getIdInstituicoes() {
        if (turma.getIdTurmas() == null) {
            return idInstituicoes;
        } else {
            Integer temp = turma.getCurso().getInstituicao().getIdInstituicoes();
            if (idInstituicoes == null) {
                idInstituicoes = temp;
                return idInstituicoes;
            } else if (idInstituicoes != temp) {
                return idInstituicoes;
            } else {
                return idInstituicoes;
            }
        }
    }

    public void setIdInstituicoes(Integer idInstituicoes) {
        this.idInstituicoes = idInstituicoes;
    }

    public Integer getIdPeriodos() {
        idPeriodos = turma.getPeriodo().getIdPeriodos();
        return idPeriodos;
    }

    public void setIdPeriodos(Integer idPeriodos) {
        this.idPeriodos = idPeriodos;
    }

    public List<Periodos> getPeridoCurso() {
        peridoCurso = null;
        peridoCurso = new PeriodoDAO().pesquisaPeriodosPorCursos(curso.getIdCursos());
        return peridoCurso;
    }

    public void setPeridoCurso(List<Periodos> peridoCurso) {
        this.peridoCurso = peridoCurso;
    }

    public Integer getIdSalas() {
        idSalas = turma.getSala().getIdSalas();
        return idSalas;
    }

    public void setIdSalas(Integer idSalas) {
        this.idSalas = idSalas;
    }

    public Turmas getTurma() {
        return turma;
    }

    public void setTurma(Turmas turma) {
        this.turma = turma;
    }

    public List<Turmas> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turmas> turmas) {
        this.turmas = turmas;
    }

    public List<Cursos> completeCursos(String nome) {
        return new CursosDAO().pesquisaCursos(nome, idInstituicoes);
    }

    public String salvar() {
        TurmasDAO td = new TurmasDAO();
        TurnosDoCursoBean tdtb = new TurnosDoCursoBean();
        List<TurnosDoCurso> ts;
        TurnosDoCurso temp = new TurnosDoCurso();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            turma.setCurso(curso);
            turma.setStatus("Cursando");
            turma.setSala(null);
            Periodos p = new PeriodoDAO().pesquisaPorId(idPeriodos);
            turma.setPeriodo(p);
            if (turma.getCodigo().equals("0")) {
                turma.setCodigo(null);
                throw new IllegalArgumentException("O 'Código' não deve ser igual a 0");
            } else if (turma.getQtdAlunos() <= 0) {
                turma.setQtdAlunos(null);
                throw new IllegalArgumentException("O 'Quantidade de Alunos' não deve ser menor ou igual a 0");
            } else {
                if (turma.getIdTurmas() == null) {
                    td.salvar(turma);
                    msg.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "A turma " + turma.getCodigo() + " foi inserida com sucesso.", null));
                } else {
                    td.alterar(turma);
                    msg.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "A turma " + turma.getCodigo() + " foi alterada com sucesso.", null));
                    RequestContext.getCurrentInstance().execute("inserir.hide()");
                }
            }
            limpar();
        } catch (SQLIntegrityConstraintViolationException ex) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Já existe uma Turma cadastrada com esse código!", null));
        } catch (IllegalArgumentException ex) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ex.getMessage(), null));
        } catch (InterruptedException e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao adicionar o(s) turno(s) na turma " + turma.getCodigo(), null));
        } catch (InstantiationException e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao alterar o(s) turno(s) na turma " + turma.getCodigo(), null));
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao inserir a turma " + turma.getCodigo(), null));
        }
        construct();
        return null;
    }

    public String excluir() {
        TurmasDAO td = new TurmasDAO();
        TurnosDoCursoBean tdtb = new TurnosDoCursoBean();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            td.excluir(turma);
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "A turma " + turma.getCodigo() + " foi excluída com sucesso.", null));
            limpar();
        } catch (ConstraintViolationException e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao excluir a turma " + turma.getCodigo() + ", pois a mesma possui relacionamentos", null));
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao excluir a turma " + turma.getCodigo(), null));
        }
        construct();
        return null;
    }

    public String limpar() {
        turma = new Turmas();
        idInstituicoes = null;
        idPeriodos = null;
        peridoCurso = new ArrayList<Periodos>();
        curso = new Cursos();
        return null;
    }

    public String listaSala() {
        if (turma.getSala() == null) {
            return "Turma sem Sala";
        } else {
            return turma.getSala().getNumero() + " (" + turma.getSala().getBloco().getNome() + ")";
        }
    }

    public String limpaPeriodo() {
        peridoCurso = null;
        return null;
    }
    
    public String listaTurnos() {
        String msg = "";
        List<TurnosDoCurso> turnosBD = new TurnosDoCursoDAO().pesquisaTurnoPorCurso(turma.getCurso().getIdCursos());
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
}
