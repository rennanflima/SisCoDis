/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.CursosDAO;
import br.com.uninorte.siscodis.dao.InstituicoesDAO;
import br.com.uninorte.siscodis.entidades.Cursos;
import br.com.uninorte.siscodis.entidades.Instituicoes;
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
public class InstituicoesBean implements Serializable{

    private Instituicoes insti = new Instituicoes();
    private List<Instituicoes> instituicoes = new ArrayList<Instituicoes>();
    private List<Cursos> cursos = new ArrayList<Cursos>();
    
    @PostConstruct
    public void construct() {
	instituicoes = new InstituicoesDAO().ListaTodos();
    }

    public List<Cursos> getCursos() {
        return cursos;
    }

    public void setCursos(List<Cursos> cursos) {
        this.cursos = cursos;
    }

    public Instituicoes getInsti() {
        return insti;
    }

    public void setInsti(Instituicoes insti) {
        this.insti = insti;
    }

    public List<Instituicoes> getInstituicoes() {
        return instituicoes;
    }

    public void setInstituicoes(List<Instituicoes> instituicoes) {
        this.instituicoes = instituicoes;
    }

    public String salvar() {
        InstituicoesDAO instiD = new InstituicoesDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            if (insti.getIdInstituicoes() == null) {
                instiD.salvar(insti);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "A instituição " + insti.getSigla() + " foi inserida com sucesso.", null));
            } else {
                instiD.alterar(insti);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "A instituição " + insti.getSigla() + " foi alterada com sucesso.", null));
                RequestContext.getCurrentInstance().execute("inserir.hide()");

            }
            insti = new Instituicoes();
        } catch (SQLIntegrityConstraintViolationException ex) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Já existe uma Instituição cadastrada com esse nome ou sigla!", null));
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao inserir a instituição " + insti.getSigla(), null));
        }
        construct();
        return null;
    }

    public String excluir() {
        InstituicoesDAO instiD = new InstituicoesDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            cursos = new CursosDAO().pesquisaCursosPorInstituicao(insti.getIdInstituicoes());
            if (cursos.isEmpty()) {
                instiD.excluir(insti);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "A instituição " + insti.getNome() + " foi excluída com sucesso.", null));
                insti = new Instituicoes();
            } else{
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "A instituição " + insti.getNome() + " possui dependências com a tabela cursos. É necessário corrigí-las.", null));
            }
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao excluir a instituição " + insti.getSigla(), null));
        }
        construct();
        return null;
    }

    public String limpar() {
        insti = new Instituicoes();
        return null;
    }

}
