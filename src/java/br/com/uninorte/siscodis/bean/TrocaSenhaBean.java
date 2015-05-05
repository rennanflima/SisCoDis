/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.FuncionariosDAO;
import br.com.uninorte.siscodis.dao.UsersDAO;
import br.com.uninorte.siscodis.entidades.Funcionarios;
import br.com.uninorte.siscodis.entidades.Usuarios;
import br.com.uninorte.siscodis.util.GeraSenha;
import br.com.uninorte.siscodis.util.MailEnviaAuth;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author rennan
 */
@ManagedBean
@SessionScoped
public class TrocaSenhaBean implements Serializable {

    private String login;
    private Integer mat;
    private String senha;
    private String oldSenha;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getMat() {
        return mat;
    }

    public void setMat(Integer mat) {
        this.mat = mat;
    }

    public String getOldSenha() {
        return oldSenha;
    }

    public void setOldSenha(String oldSenha) {
        this.oldSenha = oldSenha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String alteraSenha() {
        UsersDAO ud = new UsersDAO();
        String temp;
        Usuarios user;
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            user = ud.pesquisaPorId(new UsuarioBean().getUsuario().getLogin());
            temp = new GeraSenha().ecripta(oldSenha);
            if (temp.equals(user.getSenha())) {
                user.setSenha(new GeraSenha().ecripta(senha));
                ud.alterar(user);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Sua senha foi alterada com sucesso.", null));
            } else {
                throw new IllegalArgumentException();
            }
            RequestContext.getCurrentInstance().execute("redefinir.hide()");
        } catch (IllegalArgumentException ex) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Sua senha antiga não corresponde a que está cadastrada", null));
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao alterar sua senha", null));
        }
        return null;
    }

    public String esqueciSenha() {
        MailEnviaAuth enviaEmail = new MailEnviaAuth();
        String pass = new GeraSenha().geraSenha();
        String senhaCript = new GeraSenha().ecripta(pass);
        UsersDAO ud = new UsersDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        boolean verificaEnvio;
        try {
            Funcionarios f = new FuncionariosDAO().pesquisaPorMat(mat);
            if (f != null) {
                if (login.equals(f.getUser().getLogin())) {
                    Usuarios user = ud.pesquisaPorId(login);
                    user.setSenha(senhaCript);
                    ud.alterar(user);
                    verificaEnvio = enviaEmail.enviaCad(f.getEmail(), "Recuperação de Senha", f.getUser().getLogin(), pass);
                    if (verificaEnvio) {
                        msg.addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "E-mail enviado com sucesso.", null));
                    } else {
                        msg.addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Ocorreu um erro ao enviar o e-mail", null));
                    }
                } else {
                    msg.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "O login informado não corresponde ao que foi cadastrado", null));
                }
            } else {
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Não foi encontrado(a) nenhum(a) funcionário(a) cadastrado(a) com essa matrícula", null));
            }
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao recuperar sua senha", null));
        }
        return null;
    }

    public String limpaRecupera() {
        login = null;
        mat = null;
        return null;
    }

    public String limpar() {
        senha = null;
        return null;
    }

    public String mensagens(String msg){
        if(msg.equals("org.springframework.security.web.authentication.session.SessionAuthenticationException: Maximum sessions of 1 for this principal exceeded")){
            return "Execedido o número máximo de sessões para este usuário";
        } else{
            return "Usuário inexistente ou senha inválida";
        }
    }
}
