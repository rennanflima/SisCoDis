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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rennan Francisco
 */
@ManagedBean
@ViewScoped
public class FuncionariosBean implements Serializable {

    private Funcionarios funcionario = new Funcionarios();
    private List<Funcionarios> funcionarios = new ArrayList<Funcionarios>();
    private String login;
    private String permissao;

    @PostConstruct
    public void construct() {
        funcionarios = new FuncionariosDAO().ListaTodos();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPermissao() {
        permissao = funcionario.getUser().getAutorizacao();
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    public Funcionarios getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionarios usuario) {
        this.funcionario = usuario;
    }

    public List<Funcionarios> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionarios> usuarios) {
        this.funcionarios = usuarios;
    }

    public String salvar() {
        FuncionariosDAO fd = new FuncionariosDAO();
        MailEnviaAuth enviaEmail = new MailEnviaAuth();
        Usuarios us = new Usuarios();
        String pass = new GeraSenha().geraSenha();
        String senhaCript = new GeraSenha().ecripta(pass);
        UsersDAO ud = new UsersDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        boolean verificaEnvio;
        try {
            if (funcionario.getIdFuncionarios() == null) {
                us.setSenha(senhaCript);
                us.setLogin(login);
                us.setAutorizacao(permissao);
                ud.salvar(us);
                funcionario.setUser(us);
                fd.salvar(funcionario);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "O funcionário " + funcionario.getNome() + " foi inserido com sucesso.", null));
                verificaEnvio = enviaEmail.enviaCad(funcionario.getEmail(), "Acesso ao SisCoDis", funcionario.getUser().getLogin(), pass);
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
                Usuarios user = ud.pesquisaPorId(funcionario.getUser().getLogin());
                user.setAutorizacao(permissao);
                ud.alterar(user);
                fd.alterar(funcionario);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "O funcionário " + funcionario.getNome() + " foi alterado com sucesso.", null));
                RequestContext.getCurrentInstance().execute("inserir.hide()");
            }
            limpar();
        } catch (SQLIntegrityConstraintViolationException ex) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    ex.getMessage(), null));
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao inserir o funcionário " + funcionario.getNome(), null));
        }
        construct();
        return null;
    }

    public String excluir() {
        FuncionariosDAO fd = new FuncionariosDAO();
        UsersDAO ud = new UsersDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            Usuarios user = ud.pesquisaPorId(funcionario.getUser().getLogin());
            fd.excluir(funcionario);
            ud.excluir(user);
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "O funcionário " + funcionario.getNome() + " foi excluído com sucesso.", null));
            funcionario = new Funcionarios();
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao excluir o funcionário " + funcionario.getNome(), null));
        }
        construct();
        return null;
    }

    public String limpar() {
        funcionario = new Funcionarios();
        login = " ";
        permissao = " ";
        return null;
    }

    public String listaPermissao() {
        if (funcionario.getUser().getAutorizacao() == null) {
            return "Sem Nível de Permissão";
        }
        if (funcionario.getUser().getAutorizacao().equals("ROLE_GER")) {
            return "Administrador";
        } else if (funcionario.getUser().getAutorizacao().equals("ROLE_SICP")) {
            return "SICP";
        } else {
            return "APOIO";
        }
    }
}
