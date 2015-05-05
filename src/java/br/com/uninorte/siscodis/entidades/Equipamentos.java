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
public class Equipamentos implements Serializable {

    @Id
    @GeneratedValue
    private Integer idEquipamentos;
    @Column(nullable = false, length = 50)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String equipamento) {
        this.nome = equipamento;
    }

    public Integer getIdEquipamentos() {
        return idEquipamentos;
    }

    public void setIdEquipamentos(Integer idEquipamentos) {
        this.idEquipamentos = idEquipamentos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.idEquipamentos != null ? this.idEquipamentos.hashCode() : 0);
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
        final Equipamentos other = (Equipamentos) obj;
        if (this.idEquipamentos != other.idEquipamentos && (this.idEquipamentos == null || !this.idEquipamentos.equals(other.idEquipamentos))) {
            return false;
        }
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        return true;
    }
}
