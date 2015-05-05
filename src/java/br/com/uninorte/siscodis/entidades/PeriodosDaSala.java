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
public class PeriodosDaSala implements Serializable {

    @Id
    @GeneratedValue
    private Integer idPeriodosDaSala;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Salas sala = new Salas();
    @ManyToOne
    @JoinColumn(nullable = false)
    private Periodos periodo = new Periodos();

    public Integer getIdPeriodosDaSala() {
        return idPeriodosDaSala;
    }

    public void setIdPeriodosDaSala(Integer idPeriodosDaSala) {
        this.idPeriodosDaSala = idPeriodosDaSala;
    }

    public Salas getSala() {
        return sala;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }

    public Periodos getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodos periodo) {
        this.periodo = periodo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.idPeriodosDaSala != null ? this.idPeriodosDaSala.hashCode() : 0);
        hash = 97 * hash + (this.sala != null ? this.sala.hashCode() : 0);
        hash = 97 * hash + (this.periodo != null ? this.periodo.hashCode() : 0);
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
        final PeriodosDaSala other = (PeriodosDaSala) obj;
        if (this.idPeriodosDaSala != other.idPeriodosDaSala && (this.idPeriodosDaSala == null || !this.idPeriodosDaSala.equals(other.idPeriodosDaSala))) {
            return false;
        }
        if (this.sala != other.sala && (this.sala == null || !this.sala.equals(other.sala))) {
            return false;
        }
        if (this.periodo != other.periodo && (this.periodo == null || !this.periodo.equals(other.periodo))) {
            return false;
        }
        return true;
    }
}
