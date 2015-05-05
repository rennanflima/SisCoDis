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

/**
 *
 * @author Rennan Francisco
 */
@Entity
public class Turnos implements Serializable {

    @Id
    @GeneratedValue
    private Integer idTurnos;
    @Column(unique = true, nullable = false, length = 40)
    private String nome;

    public Integer getIdTurnos() {
        return idTurnos;
    }

    public void setIdTurnos(Integer idTurnos) {
        this.idTurnos = idTurnos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.idTurnos != null ? this.idTurnos.hashCode() : 0);
        hash = 89 * hash + (this.nome != null ? this.nome.hashCode() : 0);
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
        final Turnos other = (Turnos) obj;
        if (this.idTurnos != other.idTurnos && (this.idTurnos == null || !this.idTurnos.equals(other.idTurnos))) {
            return false;
        }
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        return true;
    }
}
