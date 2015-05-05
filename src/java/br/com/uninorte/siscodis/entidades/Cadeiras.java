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
public class Cadeiras implements Serializable {

    @Id
    @GeneratedValue
    private Integer idCadeiras;
    @Column(nullable = false, length = 40)
    private String descricao;
    @Column(length = 40)
    private String proprietario;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdCadeiras() {
        return idCadeiras;
    }

    public void setIdCadeiras(Integer idCadeiras) {
        this.idCadeiras = idCadeiras;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.idCadeiras != null ? this.idCadeiras.hashCode() : 0);
        hash = 89 * hash + (this.descricao != null ? this.descricao.hashCode() : 0);
        hash = 89 * hash + (this.proprietario != null ? this.proprietario.hashCode() : 0);
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
        final Cadeiras other = (Cadeiras) obj;
        if (this.idCadeiras != other.idCadeiras && (this.idCadeiras == null || !this.idCadeiras.equals(other.idCadeiras))) {
            return false;
        }
        if ((this.descricao == null) ? (other.descricao != null) : !this.descricao.equals(other.descricao)) {
            return false;
        }
        if ((this.proprietario == null) ? (other.proprietario != null) : !this.proprietario.equals(other.proprietario)) {
            return false;
        }
        return true;
    }
}
