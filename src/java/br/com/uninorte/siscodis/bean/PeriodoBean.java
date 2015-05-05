/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.CursosDAO;
import br.com.uninorte.siscodis.dao.PeriodoDAO;
import br.com.uninorte.siscodis.dao.TurmasDAO;
import br.com.uninorte.siscodis.entidades.Cursos;
import br.com.uninorte.siscodis.entidades.Periodos;
import br.com.uninorte.siscodis.entidades.Turmas;
import java.io.Serializable;
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
public class PeriodoBean implements Serializable {

    private Periodos periodo = new Periodos();
    private List<Periodos> periodos = new ArrayList<Periodos>();
    private Integer idInstituicoes;
    private Cursos curso = new Cursos();

    @PostConstruct
    public void construct() {
        periodos = new PeriodoDAO().ListaTodos();
    }

    public Cursos getCurso() {
        if (periodo.getIdPeriodos() == null) {
            return curso;
        } else {
            return periodo.getCurso();
        }
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
    }

    public Periodos getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodos periodo) {
        this.periodo = periodo;
    }

    public Integer getIdInstituicoes() {
        if (periodo.getIdPeriodos() == null) {
            return idInstituicoes;
        } else {
            Integer temp = periodo.getCurso().getInstituicao().getIdInstituicoes();
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

    public Periodos getTurno() {
        return periodo;
    }

    public void setTurno(Periodos periodo) {
        this.periodo = periodo;
    }

    public List<Periodos> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<Periodos> periodos) {
        this.periodos = periodos;
    }

    public List<Cursos> completeCursos(String nome) {
        return new CursosDAO().pesquisaCursos(nome, idInstituicoes);
    }

    public String salvar() {
        PeriodoDAO pd = new PeriodoDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            int op = periodo.getHorarioFinal().compareTo(periodo.getHorarioInicio());
            Periodos per = new PeriodoDAO().verificaPeriodo(periodo.getPeriodo(), curso.getIdCursos());
            periodo.setCurso(curso);
            if (op <= 0) {
                throw new IllegalArgumentException("A Hora Final deve ser maior do que Hora Inicial.");
            } else if (periodo.getPeriodo() > curso.getQtdPeriodos()) {
                    throw new IllegalArgumentException("O 'Período' não deve ser maior do que " + curso.getQtdPeriodos());
            } else if (periodo.getPeriodo() <= 0) {
                throw new IllegalArgumentException("O 'Período' não deve ser menor ou igual a 0");
            } else {
                if (periodo.getIdPeriodos() == null) {
                    if (per != null) {
                        throw new IllegalArgumentException("O equipamento " + periodo.getPeriodo() + " já foi adicionado no curso " + periodo.getCurso().getNome() + ".");
                    } else {
                        pd.salvar(periodo);
                        msg.addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "O período '" + periodo.getPeriodo() + "' foi inserido com sucesso no curso " + periodo.getCurso().getNome() + ".", null));
                    }
                } else {
                    if (per != null) {
                        throw new IllegalArgumentException("O equipamento " + periodo.getPeriodo() + " já foi adicionado no curso " + periodo.getCurso().getNome() + ".");
                    } else {
                        pd.alterar(periodo);
                        msg.addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "O período '" + periodo.getPeriodo() + "' foi alterado com sucesso no curso " + periodo.getCurso().getNome() + ".", null));
                        RequestContext.getCurrentInstance().execute("inserir.hide()");
                    }
                }
            }
            limpar();
        } catch (IllegalArgumentException e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    e.getMessage(), null));
            System.out.println("Erro: "+e);
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao inserir o período " + periodo.getPeriodo() + " no curso " + periodo.getCurso().getNome() + ".", null));
            System.out.println("Erro periodo: " + e.getMessage());
        }
        construct();
        return null;
    }

    public String excluir() {
        PeriodoDAO pd = new PeriodoDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            List<Turmas> t = new TurmasDAO().pesquisaTurmasPorPeriodo(periodo.getIdPeriodos());
            if (t.isEmpty()) {
                pd.excluir(periodo);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "O período " + periodo.getPeriodo() + " foi excluído com sucesso do curso " + periodo.getCurso().getNome() + ".", null));
                limpar();
            } else {
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "O período " + periodo.getPeriodo() + " possui dependências com a tabela turmas. É necessário corrigí-las.", null));
            }
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao excluir o periodo " + periodo.getPeriodo() + " do curso " + periodo.getCurso().getNome() + ".", null));
        }
        construct();
        return null;
    }

    public String limpar() {
        periodo = new Periodos();
        idInstituicoes = null;
        curso = new Cursos();
        return null;
    }
}
