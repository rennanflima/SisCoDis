/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Rennan Francisco
 */
@Entity
public class Periodos implements Serializable {

    @Id
    @GeneratedValue
    private Integer idPeriodos;
    private Integer periodo;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date horarioInicio;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date horarioFinal;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Cursos curso = new Cursos();

    public Integer getIdPeriodos() {
        return idPeriodos;
    }

    public void setIdPeriodos(Integer idPeriodos) {
        this.idPeriodos = idPeriodos;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Date getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(Date horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public Date getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(Date horarioFinal) {
        this.horarioFinal = horarioFinal;
    }

    public Cursos getCurso() {
        return curso;
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.idPeriodos != null ? this.idPeriodos.hashCode() : 0);
        hash = 47 * hash + (this.periodo != null ? this.periodo.hashCode() : 0);
        hash = 47 * hash + (this.horarioInicio != null ? this.horarioInicio.hashCode() : 0);
        hash = 47 * hash + (this.horarioFinal != null ? this.horarioFinal.hashCode() : 0);
        hash = 47 * hash + (this.curso != null ? this.curso.hashCode() : 0);
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
        final Periodos other = (Periodos) obj;
        if (this.idPeriodos != other.idPeriodos && (this.idPeriodos == null || !this.idPeriodos.equals(other.idPeriodos))) {
            return false;
        }
        if (this.periodo != other.periodo && (this.periodo == null || !this.periodo.equals(other.periodo))) {
            return false;
        }
        if (this.horarioInicio != other.horarioInicio && (this.horarioInicio == null || !this.horarioInicio.equals(other.horarioInicio))) {
            return false;
        }
        if (this.horarioFinal != other.horarioFinal && (this.horarioFinal == null || !this.horarioFinal.equals(other.horarioFinal))) {
            return false;
        }
        if (this.curso != other.curso && (this.curso == null || !this.curso.equals(other.curso))) {
            return false;
        }
        return true;
    }
}
