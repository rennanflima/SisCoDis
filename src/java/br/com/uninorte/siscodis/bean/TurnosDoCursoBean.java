/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.TurnosDoCursoDAO;
import br.com.uninorte.siscodis.entidades.Cursos;
import br.com.uninorte.siscodis.entidades.Turnos;
import br.com.uninorte.siscodis.entidades.TurnosDoCurso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Rennan Francisco
 */
@ManagedBean
@ViewScoped
public class TurnosDoCursoBean implements Serializable{

    private TurnosDoCurso tDoCurso = new TurnosDoCurso();
    private List<TurnosDoCurso> tDoCursos = new ArrayList<TurnosDoCurso>();
    private Integer idCurso;
    private Integer idTurno;

    public Integer getIdTurma() {
        return idCurso;
    }

    public void setIdTurma(Integer idTurma) {
        this.idCurso = idTurma;
    }

    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public TurnosDoCurso gettDaTurma() {
        return tDoCurso;
    }

    public void settDaTurma(TurnosDoCurso tDoCurso) {
        this.tDoCurso = tDoCurso;
    }

    public List<TurnosDoCurso> gettDaTurmas(Integer id) {
        tDoCursos = new TurnosDoCursoDAO().pesquisaTurnoPorCurso(id);
        return tDoCursos;
    }

    public void settDaTurmas(List<TurnosDoCurso> tDaTurmas) {
        this.tDoCursos = tDaTurmas;
    }

    public String salvar(Turnos turno, Cursos cr) throws Exception {
        TurnosDoCursoDAO tdtd = new TurnosDoCursoDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            tDoCurso.setCurso(cr);
            tDoCurso.setTurno(turno);
            tdtd.salvar(tDoCurso);
            tDoCurso = new TurnosDoCurso();
        } catch (Exception e) {
            throw new InterruptedException();
        }
        return null;
    }

    public String alterar(TurnosDoCurso tdt) throws Exception {
        TurnosDoCursoDAO tdtd = new TurnosDoCursoDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            tdtd.alterar(tdt);
        } catch (Exception e) {
            throw new InterruptedException();
        }
        return null;
    }
  
    public String excluir(TurnosDoCurso td) throws Exception {
        TurnosDoCursoDAO tdtd = new TurnosDoCursoDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            tdtd.excluir(td);
            tDoCurso = new TurnosDoCurso();
        } catch (Exception e) {
            throw new InterruptedException();
        }
        return null;
    }

    public String limpar() {
        tDoCurso = new TurnosDoCurso();
        return null;
    }
}
