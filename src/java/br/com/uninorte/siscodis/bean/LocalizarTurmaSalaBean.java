/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.CursosDAO;
import br.com.uninorte.siscodis.dao.PeriodoDAO;
import br.com.uninorte.siscodis.dao.SalasDAO;
import br.com.uninorte.siscodis.dao.TurmasDAO;
import br.com.uninorte.siscodis.dao.TurnosDoCursoDAO;
import br.com.uninorte.siscodis.entidades.Cursos;
import br.com.uninorte.siscodis.entidades.Periodos;
import br.com.uninorte.siscodis.entidades.Salas;
import br.com.uninorte.siscodis.entidades.Turmas;
import br.com.uninorte.siscodis.entidades.TurnosDoCurso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author rennan
 */
@ManagedBean
@SessionScoped
public class LocalizarTurmaSalaBean implements Serializable {

    private String opcao;
    private String codTurma;
    private Integer periodo;
    private Salas sala = new Salas();
    private Cursos curso = new Cursos();
    private List<Turmas> turmas = new ArrayList<Turmas>();
    private Turmas turma = new Turmas();
    private List<Periodos> peridoCurso = new ArrayList<Periodos>();

    public Cursos getCurso() {
        return curso;
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
    }

    public Salas getSala() {
        return sala;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }

    public List<Periodos> getPeridoCurso() {
        peridoCurso = null;
        if (curso.getIdCursos() != null) {
            peridoCurso = new PeriodoDAO().pesquisaPeriodosPorCursos(curso.getIdCursos());
        }
        return peridoCurso;
    }

    public void setPeridoCurso(List<Periodos> peridoCurso) {
        this.peridoCurso = peridoCurso;
    }

    public Turmas getTurma() {
        return turma;
    }

    public void setTurma(Turmas turma) {
        this.turma = turma;
    }

    public List<Cursos> completeCursos(String num) {
        return new CursosDAO().pesquisaTodosCursos(num);
    }

    public List<Salas> completeSalas(String num) {
        return new SalasDAO().pesquisaSalas(num);
    }

    public List<Turmas> getTurmas() {
        TurmasDAO td = new TurmasDAO();
        if (opcao == null) {
            turmas = new TurmasDAO().listaTurmasComSala();
        } else {
            if (opcao.equals("turmaS")) {
                turmas = td.consultaPorTurmaComSala(codTurma);
            } else if (opcao.equals("cursoper")) {
                if (periodo != null) {
                    turmas = td.pesquisaTurmasPorCursoPeriodoComSala(curso.getIdCursos(), periodo);
                } else {
                    turmas = td.pesquisaTurmasPorCursoComSala(curso.getIdCursos());
                }
            } else if (opcao.equals("salaB")) {
                turmas = td.pesquisaTurmasPorSala(sala.getIdSalas());
            }
        }
        return turmas;
    }

    public void setTurmas(List<Turmas> turmas) {
        this.turmas = turmas;
    }

    public String getCodTurma() {
        return codTurma;
    }

    public void setCodTurma(String codTurma) {
        this.codTurma = codTurma;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public String getOpcao() {
        return opcao;
    }

    public void setOpcao(String opcao) {
        this.opcao = opcao;
    }

    public String limpaLista() {
        turmas = null;
        turma = new Turmas();
        return null;
    }

    public String limpar() {
        opcao = null;
        codTurma = null;
        curso = new Cursos();
        periodo = null;
        sala = new Salas();
        turma = new Turmas();
        return null;
    }

    public String listaSala() {
        if (turma.getSala() == null) {
            return "Turma sem Sala";
        } else {
            return turma.getSala().getNumero() + " (" + turma.getSala().getBloco().getNome() + ")";
        }
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

    public String listaTurnosCurso(Cursos c) {
        String msg = "";
        List<TurnosDoCurso> turnosBD = new TurnosDoCursoDAO().pesquisaTurnoPorCurso(c.getIdCursos());
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
            return "(" + msg + ")";
        } else {
            for (TurnosDoCurso t : turnosBD) {
                msg += "(" + t.getTurno().getNome() + ")";;
            }
            return msg;
        }
    }
}
