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
public class Blocos implements Serializable {

    @Id
    @GeneratedValue
    private Integer idBlocos;
    @Column(nullable = false, unique = true, length = 35)
    private String nome;
    private Integer qtdAndares;
    private boolean subsolo;

    public boolean isSubsolo() {
        return subsolo;
    }

    public void setSubsolo(boolean subsolo) {
        this.subsolo = subsolo;
    }

    public Integer getIdBlocos() {
        return idBlocos;
    }

    public void setIdBlocos(Integer idBlocos) {
        this.idBlocos = idBlocos;
    }

    public Integer getQtdAndares() {
        return qtdAndares;
    }

    public void setQtdAndares(Integer qtdAndares) {
        this.qtdAndares = qtdAndares;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.idBlocos != null ? this.idBlocos.hashCode() : 0);
        hash = 89 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 89 * hash + (this.qtdAndares != null ? this.qtdAndares.hashCode() : 0);
        hash = 89 * hash + (this.subsolo ? 1 : 0);
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
        final Blocos other = (Blocos) obj;
        if (this.idBlocos != other.idBlocos && (this.idBlocos == null || !this.idBlocos.equals(other.idBlocos))) {
            return false;
        }
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if (this.qtdAndares != other.qtdAndares && (this.qtdAndares == null || !this.qtdAndares.equals(other.qtdAndares))) {
            return false;
        }
        if (this.subsolo != other.subsolo) {
            return false;
        }
        return true;
    }
}
