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
public class EquipamentoDaSala implements Serializable {

    @Id
    @GeneratedValue
    private Integer idEquipamentoDaSala;
    @Column(length = 15)
    private String situacao;
    private String obsSituacaoE;
    private Integer qtd;
    private String descricao;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Salas sala = new Salas();
    @ManyToOne
    @JoinColumn(nullable = false)
    private Equipamentos equipamento = new Equipamentos();

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public Equipamentos getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamentos equipamento) {
        this.equipamento = equipamento;
    }

    public Integer getIdEquipamentoDaSala() {
        return idEquipamentoDaSala;
    }

    public void setIdEquipamentoDaSala(Integer idEquipamentoDaSala) {
        this.idEquipamentoDaSala = idEquipamentoDaSala;
    }

    public Salas getSala() {
        return sala;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getObsSituacaoE() {
        return obsSituacaoE;
    }

    public void setObsSituacaoE(String obsSituacaoE) {
        this.obsSituacaoE = obsSituacaoE;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.idEquipamentoDaSala != null ? this.idEquipamentoDaSala.hashCode() : 0);
        hash = 67 * hash + (this.situacao != null ? this.situacao.hashCode() : 0);
        hash = 67 * hash + (this.obsSituacaoE != null ? this.obsSituacaoE.hashCode() : 0);
        hash = 67 * hash + (this.qtd != null ? this.qtd.hashCode() : 0);
        hash = 67 * hash + (this.descricao != null ? this.descricao.hashCode() : 0);
        hash = 67 * hash + (this.sala != null ? this.sala.hashCode() : 0);
        hash = 67 * hash + (this.equipamento != null ? this.equipamento.hashCode() : 0);
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
        final EquipamentoDaSala other = (EquipamentoDaSala) obj;
        if (this.idEquipamentoDaSala != other.idEquipamentoDaSala && (this.idEquipamentoDaSala == null || !this.idEquipamentoDaSala.equals(other.idEquipamentoDaSala))) {
            return false;
        }
        if ((this.situacao == null) ? (other.situacao != null) : !this.situacao.equals(other.situacao)) {
            return false;
        }
        if ((this.obsSituacaoE == null) ? (other.obsSituacaoE != null) : !this.obsSituacaoE.equals(other.obsSituacaoE)) {
            return false;
        }
        if (this.qtd != other.qtd && (this.qtd == null || !this.qtd.equals(other.qtd))) {
            return false;
        }
        if ((this.descricao == null) ? (other.descricao != null) : !this.descricao.equals(other.descricao)) {
            return false;
        }
        if (this.sala != other.sala && (this.sala == null || !this.sala.equals(other.sala))) {
            return false;
        }
        if (this.equipamento != other.equipamento && (this.equipamento == null || !this.equipamento.equals(other.equipamento))) {
            return false;
        }
        return true;
    }
}
