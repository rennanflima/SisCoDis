/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Rennan Francisco
 */
@Entity
public class TurnosDoCurso implements Serializable {

    @Id
    @GeneratedValue
    private Long idTurnosDoCurso;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Cursos curso = new Cursos();
    @ManyToOne
    @JoinColumn(nullable = false)
    private Turnos turno = new Turnos();

    public Long getIdTurnosDoCurso() {
        return idTurnosDoCurso;
    }

    public void setIdTurnosDoCurso(Long idTurnosDoCurso) {
        this.idTurnosDoCurso = idTurnosDoCurso;
    }

    public Cursos getCurso() {
        return curso;
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
    }

    public Turnos getTurno() {
        return turno;
    }

    public void setTurno(Turnos turno) {
        this.turno = turno;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.idTurnosDoCurso != null ? this.idTurnosDoCurso.hashCode() : 0);
        hash = 79 * hash + (this.curso != null ? this.curso.hashCode() : 0);
        hash = 79 * hash + (this.turno != null ? this.turno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TurnosDoCurso other = (TurnosDoCurso) obj;
        if (this.idTurnosDoCurso != other.idTurnosDoCurso && (this.idTurnosDoCurso == null || !this.idTurnosDoCurso.equals(other.idTurnosDoCurso))) {
            return false;
        }
        if (this.curso != other.curso && (this.curso == null || !this.curso.equals(other.curso))) {
            return false;
        }
        if (this.turno != other.turno && (this.turno == null || !this.turno.equals(other.turno))) {
            return false;
        }
        return true;
    }
}
