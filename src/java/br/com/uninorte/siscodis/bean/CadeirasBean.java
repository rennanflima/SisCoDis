/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.CadeirasDAO;
import br.com.uninorte.siscodis.dao.SalasDAO;
import br.com.uninorte.siscodis.entidades.Cadeiras;
import br.com.uninorte.siscodis.entidades.Salas;
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
public class CadeirasBean implements Serializable{

    private Cadeiras cadeira = new Cadeiras();
    private List<Cadeiras> cadeiras = new ArrayList<Cadeiras>();
    private List<Salas> salas = new ArrayList<Salas>();

    @PostConstruct
    public void construct() {
	cadeiras = new CadeirasDAO().ListaTodos();
    }
    
    public List<Salas> getSalas() {
        return salas;
    }

    public void setSalas(List<Salas> salas) {
        this.salas = salas;
    }

    public Cadeiras getCadeira() {
        return cadeira;
    }

    public void setCadeira(Cadeiras cadeira) {
        this.cadeira = cadeira;
    }

    public List<Cadeiras> getCadeiras() {
        return cadeiras;
    }

    public void setCadeiras(List<Cadeiras> cadeiras) {
        this.cadeiras = cadeiras;
    }

    public String salvar() {
        CadeirasDAO cD = new CadeirasDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            if (cadeira.getIdCadeiras() == null) {
                cD.salvar(cadeira);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "A cadeira " + cadeira.getDescricao() + " foi inserida com sucesso.", null));
            } else {
                cD.alterar(cadeira);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "A cadeira " + cadeira.getDescricao() + " foi alterada com sucesso.", null));
                RequestContext.getCurrentInstance().execute("inserir.hide()");
            }
            cadeira = new Cadeiras();
        } catch (SQLIntegrityConstraintViolationException ex) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Já existe uma Cadeira cadastrada com essa descrição!", null));
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao inserir a cadeira " + cadeira.getDescricao(), null));
        }
        construct();
        return null;
    }

    public String excluir() {
        CadeirasDAO cD = new CadeirasDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            salas = new SalasDAO().pesquisaSalasPorCadeira(cadeira.getIdCadeiras());
            if (salas.isEmpty()) {
                cD.excluir(cadeira);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "A cadeira " + cadeira.getDescricao() + " foi excluída com sucesso.", null));
                cadeira = new Cadeiras();
            } else {
                 msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "A cadeira " + cadeira.getDescricao() + " possui dependências com a tabela salas. É necessário corrigí-las.", null));
            }
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao excluir a cadeira " + cadeira.getDescricao(), null));
        }
        construct();
        return null;
    }

    public String limpar() {
        cadeira = new Cadeiras();
        return null;
    }
}
