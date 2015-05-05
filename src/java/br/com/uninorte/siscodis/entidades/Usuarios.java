/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author rennan
 */
@Entity
public class Usuarios implements Serializable {

    @Id
    @Column(unique = true, length = 30)
    private String usuario;
    @Column(nullable = false, length = 45)
    private String senha;
    @Column(nullable = false, length = 60)
    private String autorizacao;

    public String getAutorizacao() {
        return autorizacao;
    }

    public void setAutorizacao(String autorizacao) {
        this.autorizacao = autorizacao;
    }

    public String getLogin() {
        return usuario;
    }

    public void setLogin(String login) {
        this.usuario = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
