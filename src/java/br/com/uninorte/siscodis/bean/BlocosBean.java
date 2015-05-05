/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.BlocosDAO;
import br.com.uninorte.siscodis.dao.SalasDAO;
import br.com.uninorte.siscodis.entidades.Blocos;
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
public class BlocosBean implements Serializable{

    private Blocos bloco = new Blocos();
    private List<Blocos> blocos = new ArrayList<Blocos>();
    private List<Salas> salas = new ArrayList<Salas>();

    @PostConstruct
    public void construct() {
	blocos = new BlocosDAO().ListaTodos();
    }
    
    public List<Salas> getSalas() {
        return salas;
    }

    public void setSalas(List<Salas> salas) {
        this.salas = salas;
    }

    public Blocos getBloco() {
        return bloco;
    }

    public void setBloco(Blocos bloco) {
        this.bloco = bloco;
    }

    public List<Blocos> getBlocos() {
        return blocos;
    }

    public void setBlocos(List<Blocos> blocos) {
        this.blocos = blocos;
    }

    public String salvar() {
        BlocosDAO bD = new BlocosDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            if (bloco.getQtdAndares() <= 0) {
                bloco.setQtdAndares(null);
                throw new IllegalArgumentException();
            } else {
                if (bloco.getIdBlocos() == null) {
                    bD.salvar(bloco);
                    msg.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "O bloco " + bloco.getNome() + " foi inserido com sucesso.", null));
                } else {
                    bD.alterar(bloco);
                    msg.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "O bloco " + bloco.getNome() + " foi alterado com sucesso.", null));
                    RequestContext.getCurrentInstance().execute("inserir.hide()");
                }
            }
            bloco = new Blocos();
        } catch (IllegalArgumentException e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "O 'Quantidade de Andares' não deve ser menor ou igual a 0", null));
        } catch (SQLIntegrityConstraintViolationException ex) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Já existe um Bloco cadastrado com esse nome!", null));
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao inserir o bloco " + bloco.getNome(), null));
        }
        construct();
        return null;
    }

    public String excluir() {
        BlocosDAO bD = new BlocosDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            salas = new SalasDAO().pesquisaSalasPorBloco(bloco.getIdBlocos());
            if (salas.isEmpty()) {
                bD.excluir(bloco);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "O bloco " + bloco.getNome() + " foi excluído com sucesso.", null));
                bloco = new Blocos();
            } else {
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "O bloco " + bloco.getNome() + " possui dependências com a tabela salas. É necessário corrigí-las.", null));
            }
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao excluir o bloco " + bloco.getNome(), null));
        }
        construct();
        return null;
    }

    public String limpar() {
        bloco = new Blocos();
        return null;
    }
}
