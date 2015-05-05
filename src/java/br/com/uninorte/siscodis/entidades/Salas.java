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
public class Salas implements Serializable {

    @Id
    @GeneratedValue
    private Integer idSalas;
    @Column(nullable = false, unique = true, length = 20)
    private String numero;
    private Integer capacidade;
    @Column(nullable = false, length = 40)
    private String andar;
    @Column(length = 15)
    private String situacao;
    private String obsSituacao;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Blocos bloco = new Blocos();
    @ManyToOne
    @JoinColumn(nullable = false)
    private Cadeiras cadeira = new Cadeiras();
    @Column(length = 30)
    private String tamanhoCad;

    public String getTamanhoCad() {
        return tamanhoCad;
    }

    public void setTamanhoCad(String tamanhoCad) {
        this.tamanhoCad = tamanhoCad;
    }

    public String getAndar() {
        return andar;
    }

    public void setAndar(String andar) {
        this.andar = andar;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public Integer getIdSalas() {
        return idSalas;
    }

    public void setIdSalas(Integer idSalas) {
        this.idSalas = idSalas;
    }

    public Cadeiras getCadeira() {
        return cadeira;
    }

    public void setCadeira(Cadeiras cadeira) {
        this.cadeira = cadeira;
    }

    public Blocos getBloco() {
        return bloco;
    }

    public void setBloco(Blocos bloco) {
        this.bloco = bloco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getObsSituacao() {
        return obsSituacao;
    }

    public void setObsSituacao(String obsSituacao) {
        this.obsSituacao = obsSituacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.idSalas != null ? this.idSalas.hashCode() : 0);
        hash = 71 * hash + (this.numero != null ? this.numero.hashCode() : 0);
        hash = 71 * hash + (this.capacidade != null ? this.capacidade.hashCode() : 0);
        hash = 71 * hash + (this.andar != null ? this.andar.hashCode() : 0);
        hash = 71 * hash + (this.situacao != null ? this.situacao.hashCode() : 0);
        hash = 71 * hash + (this.obsSituacao != null ? this.obsSituacao.hashCode() : 0);
        hash = 71 * hash + (this.bloco != null ? this.bloco.hashCode() : 0);
        hash = 71 * hash + (this.cadeira != null ? this.cadeira.hashCode() : 0);
        hash = 71 * hash + (this.tamanhoCad != null ? this.tamanhoCad.hashCode() : 0);
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
        final Salas other = (Salas) obj;
        if (this.idSalas != other.idSalas && (this.idSalas == null || !this.idSalas.equals(other.idSalas))) {
            return false;
        }
        if ((this.numero == null) ? (other.numero != null) : !this.numero.equals(other.numero)) {
            return false;
        }
        if (this.capacidade != other.capacidade && (this.capacidade == null || !this.capacidade.equals(other.capacidade))) {
            return false;
        }
        if ((this.andar == null) ? (other.andar != null) : !this.andar.equals(other.andar)) {
            return false;
        }
        if ((this.situacao == null) ? (other.situacao != null) : !this.situacao.equals(other.situacao)) {
            return false;
        }
        if ((this.obsSituacao == null) ? (other.obsSituacao != null) : !this.obsSituacao.equals(other.obsSituacao)) {
            return false;
        }
        if (this.bloco != other.bloco && (this.bloco == null || !this.bloco.equals(other.bloco))) {
            return false;
        }
        if (this.cadeira != other.cadeira && (this.cadeira == null || !this.cadeira.equals(other.cadeira))) {
            return false;
        }
        if ((this.tamanhoCad == null) ? (other.tamanhoCad != null) : !this.tamanhoCad.equals(other.tamanhoCad)) {
            return false;
        }
        return true;
    }
}
