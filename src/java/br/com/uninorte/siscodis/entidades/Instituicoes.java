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
public class Instituicoes implements Serializable {

    @Id
    @GeneratedValue
    private Integer idInstituicoes;
    @Column(nullable = false, unique = true, length = 60)
    private String nome;
    @Column(nullable = false, unique = true, length = 30)
    private String sigla;

    public Integer getIdInstituicoes() {
        return idInstituicoes;
    }

    public void setIdInstituicoes(Integer idInstituicoes) {
        this.idInstituicoes = idInstituicoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.idInstituicoes != null ? this.idInstituicoes.hashCode() : 0);
        hash = 53 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 53 * hash + (this.sigla != null ? this.sigla.hashCode() : 0);
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
        final Instituicoes other = (Instituicoes) obj;
        if (this.idInstituicoes != other.idInstituicoes && (this.idInstituicoes == null || !this.idInstituicoes.equals(other.idInstituicoes))) {
            return false;
        }
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if ((this.sigla == null) ? (other.sigla != null) : !this.sigla.equals(other.sigla)) {
            return false;
        }
        return true;
    }
}
