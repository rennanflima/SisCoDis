/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.CursosDAO;
import br.com.uninorte.siscodis.dao.EquipamentoDaSalaDAO;
import br.com.uninorte.siscodis.dao.TurmasDAO;
import br.com.uninorte.siscodis.dao.PeriodosDaSalaDAO;
import br.com.uninorte.siscodis.dao.SalasDAO;
import br.com.uninorte.siscodis.dao.TurnosDoCursoDAO;
import br.com.uninorte.siscodis.entidades.Cursos;
import br.com.uninorte.siscodis.entidades.EquipamentoDaSala;
import br.com.uninorte.siscodis.entidades.Salas;
import br.com.uninorte.siscodis.entidades.Turmas;
import br.com.uninorte.siscodis.entidades.PeriodosDaSala;
import br.com.uninorte.siscodis.entidades.TurnosDoCurso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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
public class DistribuicaoSalaBean implements Serializable {

    private Turmas turma = new Turmas();
    private Turmas turmaCurso = new Turmas();
    private Salas sala = new Salas();
    private Integer idInstituicoes;
    private Cursos curso = new Cursos();
    private List<Salas> salasTurma = new ArrayList<Salas>();
    private List<Turmas> turmasCurso = new ArrayList<Turmas>();
    private List<Turmas> turmas = new ArrayList<Turmas>();
    private List<EquipamentoDaSala> eqsDaSala = new ArrayList<EquipamentoDaSala>();

    public List<Turmas> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turmas> turmas) {
        this.turmas = turmas;
    }

    public Integer getIdInstituicoes() {
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

    public List<EquipamentoDaSala> getEqsDaSala() {
        eqsDaSala = new EquipamentoDaSalaDAO().consultaPorSala(sala.getIdSalas());
        return eqsDaSala;
    }

    public void setEqsDaSala(List<EquipamentoDaSala> eqsDaSala) {
        this.eqsDaSala = eqsDaSala;
    }

    public Turmas getTurmaCurso() {
        return turmaCurso;
    }

    public void setTurmaCurso(Turmas turmaCurso) {
        this.turmaCurso = turmaCurso;
    }

    public Salas getSala() {
        return sala;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }

    public List<Salas> getSalasTurma() {
        salasTurma = new SalasDAO().pesquisaSalasPorCapacidade(turma.getQtdAlunos(), turma.getPeriodo().getHorarioInicio(), turma.getPeriodo().getHorarioFinal());
        return salasTurma;
    }

    public void setSalasTurma(List<Salas> salasTurma) {
        this.salasTurma = salasTurma;
    }

    public Turmas getTurma() {
        return turma;
    }

    public void setTurma(Turmas turma) {
        this.turma = turma;
    }

    public List<Turmas> getTurmasCurso() {
        turmasCurso = new TurmasDAO().pesquisaTurmasPorCursoDistri(turma.getCurso().getIdCursos(), turma.getIdTurmas());
        return turmasCurso;
    }

    public void setTurmasCurso(List<Turmas> turmasCurso) {
        this.turmasCurso = turmasCurso;
    }

    public List<Cursos> completeCursos(String nome) {
        return new CursosDAO().pesquisaCursos(nome, idInstituicoes);
    }

    public String listaSala() {
        if (turma.getSala() == null) {
            return "Turma sem Sala";
        } else {
            return turma.getSala().getNumero() + " (" + turma.getSala().getBloco().getNome() + ")";
        }
    }

    public String listaSalaCurso() {
        if (turmaCurso.getSala() == null) {
            return "Turma sem Sala";
        } else {
            return turmaCurso.getSala().getNumero() + " (" + turmaCurso.getSala().getBloco().getNome() + ")";
        }
    }

    public String mudaDetalhaTurma() {
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            msg.getExternalContext().redirect("/SisCoDis/servicos/distribuicaoSalas/detalhamento.xhtml");
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao redirecionar o usuário", null));
        }
        return null;
    }

    public String mudaCancela() {
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            turma = new Turmas();
            sala = new Salas();
            turmasCurso = null;
            salasTurma = null;
            msg.getExternalContext().redirect("/SisCoDis/servicos/distribuicaoSalas");
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao redirecionar o usuário", null));
        }
        return null;
    }

    public String salvaSalaTurma() {
        TurmasDAO td = new TurmasDAO();
        PeriodosDaSala pds = new PeriodosDaSala();
        PeriodosDaSalaDAO pdsd = new PeriodosDaSalaDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        turma.setSala(sala);
        try {
            pds.setSala(sala);
            pds.setPeriodo(turma.getPeriodo());
            pdsd.salvar(pds);
            td.alterar(turma);
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "A sala " + sala.getNumero() + " foi distribuida com sucesso para a Turma " + turma.getCodigo(), null));
            msg.getExternalContext().getFlash().setKeepMessages(true);
            msg.getExternalContext().redirect("/SisCoDis/servicos/distribuicaoSalas");
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao definir a sala " + sala.getNumero() + " para a turma " + turma.getCodigo(), null));
        }
        return null;
    }

    public String limpaSala() {
        sala = new Salas();
        return null;
    }

    public String limpaAutoComplete() {
        curso = new Cursos();
        idInstituicoes = null;
        turmas = new ArrayList<Turmas>();
        return null;
    }

    public String carregaListaPrincipal() {
        if (curso.getIdCursos() != null) {
            turmas = new TurmasDAO().pesquisaTurmasPorCurso(curso.getIdCursos());
        }
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
