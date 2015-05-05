/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.entidades;

import java.io.Serializable;
import javax.persistence.Column;
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
public class Turmas implements Serializable {

    @Id
    @GeneratedValue
    private Integer idTurmas;
    @Column(nullable = false, unique = true, length = 20)
    private String codigo;
    private Integer qtdAlunos;
    @Column(length = 20)
    private String status;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Periodos periodo = new Periodos();
    @ManyToOne
    @JoinColumn(nullable = false)
    private Cursos curso = new Cursos();
    @ManyToOne
    @JoinColumn(nullable = true)
    private Salas sala = new Salas();

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cursos getCurso() {
        return curso;
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
    }

    public Integer getIdTurmas() {
        return idTurmas;
    }

    public void setIdTurmas(Integer idTurmas) {
        this.idTurmas = idTurmas;
    }

    public Integer getQtdAlunos() {
        return qtdAlunos;
    }

    public void setQtdAlunos(Integer qtdAlunos) {
        this.qtdAlunos = qtdAlunos;
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
        hash = 23 * hash + (this.idTurmas != null ? this.idTurmas.hashCode() : 0);
        hash = 23 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
        hash = 23 * hash + (this.qtdAlunos != null ? this.qtdAlunos.hashCode() : 0);
        hash = 23 * hash + (this.status != null ? this.status.hashCode() : 0);
        hash = 23 * hash + (this.periodo != null ? this.periodo.hashCode() : 0);
        hash = 23 * hash + (this.curso != null ? this.curso.hashCode() : 0);
        hash = 23 * hash + (this.sala != null ? this.sala.hashCode() : 0);
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
        final Turmas other = (Turmas) obj;
        if (this.idTurmas != other.idTurmas && (this.idTurmas == null || !this.idTurmas.equals(other.idTurmas))) {
            return false;
        }
        if ((this.codigo == null) ? (other.codigo != null) : !this.codigo.equals(other.codigo)) {
            return false;
        }
        if (this.qtdAlunos != other.qtdAlunos && (this.qtdAlunos == null || !this.qtdAlunos.equals(other.qtdAlunos))) {
            return false;
        }
        if ((this.status == null) ? (other.status != null) : !this.status.equals(other.status)) {
            return false;
        }
        if (this.periodo != other.periodo && (this.periodo == null || !this.periodo.equals(other.periodo))) {
            return false;
        }
        if (this.curso != other.curso && (this.curso == null || !this.curso.equals(other.curso))) {
            return false;
        }
        if (this.sala != other.sala && (this.sala == null || !this.sala.equals(other.sala))) {
            return false;
        }
        return true;
    }
}