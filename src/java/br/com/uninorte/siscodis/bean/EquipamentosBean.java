/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.EquipamentoDaSalaDAO;
import br.com.uninorte.siscodis.dao.EquipamentosDAO;
import br.com.uninorte.siscodis.entidades.EquipamentoDaSala;
import br.com.uninorte.siscodis.entidades.Equipamentos;
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
public class EquipamentosBean implements Serializable{

    private Equipamentos equipamento = new Equipamentos();
    private List<Equipamentos> equipamentos = new ArrayList<Equipamentos>();
    private List<EquipamentoDaSala> eqs = new ArrayList<EquipamentoDaSala>();

    @PostConstruct
    public void construct() {
	equipamentos = new EquipamentosDAO().ListaTodos();
    }
    
    public List<EquipamentoDaSala> getEqs() {
        return eqs;
    }

    public void setEqs(List<EquipamentoDaSala> eqs) {
        this.eqs = eqs;
    }

    public Equipamentos getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamentos equipamento) {
        this.equipamento = equipamento;
    }

    public List<Equipamentos> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<Equipamentos> equipamentos) {
        this.equipamentos = equipamentos;
    }

    public String salvar() {
        EquipamentosDAO eD = new EquipamentosDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            if (equipamento.getIdEquipamentos() == null) {
                eD.salvar(equipamento);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "O equipamento " + equipamento.getNome() + " foi inserido com sucesso.", null));
            } else {
                eD.alterar(equipamento);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "O equipamento " + equipamento.getNome() + " foi alterado com sucesso.", null));
                RequestContext.getCurrentInstance().execute("inserir.hide()");
            }
            equipamento = new Equipamentos();
        } catch (SQLIntegrityConstraintViolationException ex) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Já existe um Equipamento cadastrado com esse nome!", null));
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao inserir o equipamento " + equipamento.getNome(), null));
        }
        construct();
        return null;
    }

    public String excluir() {
        EquipamentosDAO eD = new EquipamentosDAO();
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            eqs = new EquipamentoDaSalaDAO().pesquisaEqSPorEquipamento(equipamento.getIdEquipamentos());
            if (eqs.isEmpty()) {
                eD.excluir(equipamento);
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "O equipamento " + equipamento.getNome() + " foi excluído com sucesso.", null));
                equipamento = new Equipamentos();
            } else {
                msg.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "O equipamento " + equipamento.getNome() + " possui dependências com a tabela salas. É necessário corrigí-las.", null));
            }
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao excluir o equipamento " + equipamento.getNome(), null));
        }
        construct();
        return null;
    }

    public String limpar() {
        equipamento = new Equipamentos();
        return null;
    }
}
