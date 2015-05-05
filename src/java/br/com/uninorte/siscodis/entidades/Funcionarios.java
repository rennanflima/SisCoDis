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
import javax.persistence.OneToOne;

/**
 *
 * @author Rennan Francisco
 */
@Entity
public class Funcionarios implements Serializable {

    @Id
    @GeneratedValue
    private Integer idFuncionarios;
    @Column(nullable = false, unique = true)
    private Integer matricula;
    @Column(nullable = false, length = 60)
    private String nome;
    @Column(length = 40)
    private String email;
    @Column(length = 30)
    private String setor;
    @Column(length = 30)
    private String cargo;
    @OneToOne
    @JoinColumn(nullable = false)
    private Usuarios user = new Usuarios();

    public Usuarios getUser() {
        return user;
    }

    public void setUser(Usuarios user) {
        this.user = user;
    }

    public Integer getIdFuncionarios() {
        return idFuncionarios;
    }

    public void setIdFuncionarios(Integer idFuncionarios) {
        this.idFuncionarios = idFuncionarios;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.idFuncionarios != null ? this.idFuncionarios.hashCode() : 0);
        hash = 23 * hash + (this.matricula != null ? this.matricula.hashCode() : 0);
        hash = 23 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 23 * hash + (this.email != null ? this.email.hashCode() : 0);
        hash = 23 * hash + (this.setor != null ? this.setor.hashCode() : 0);
        hash = 23 * hash + (this.cargo != null ? this.cargo.hashCode() : 0);
        hash = 23 * hash + (this.user != null ? this.user.hashCode() : 0);
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
        final Funcionarios other = (Funcionarios) obj;
        if (this.idFuncionarios != other.idFuncionarios && (this.idFuncionarios == null || !this.idFuncionarios.equals(other.idFuncionarios))) {
            return false;
        }
        if (this.matricula != other.matricula && (this.matricula == null || !this.matricula.equals(other.matricula))) {
            return false;
        }
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        if ((this.setor == null) ? (other.setor != null) : !this.setor.equals(other.setor)) {
            return false;
        }
        if ((this.cargo == null) ? (other.cargo != null) : !this.cargo.equals(other.cargo)) {
            return false;
        }
        if (this.user != other.user && (this.user == null || !this.user.equals(other.user))) {
            return false;
        }
        return true;
    }
}
