/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.EquipamentoDaSalaDAO;
import br.com.uninorte.siscodis.dao.SalasDAO;
import br.com.uninorte.siscodis.entidades.EquipamentoDaSala;
import br.com.uninorte.siscodis.entidades.Salas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Rennan Francisco
 */
@ManagedBean
@ViewScoped
public class TipoEquipamentoBean implements Serializable {

    private Integer idEquipamento;
    private Salas sala = new Salas();
    private EquipamentoDaSala eqSala = new EquipamentoDaSala();
    private List<EquipamentoDaSala> eqs = new ArrayList<EquipamentoDaSala>();
    private List<EquipamentoDaSala> eqsDaSala = new ArrayList<EquipamentoDaSala>();

    @PostConstruct
    public void construct() {
        if (idEquipamento != null) {
            eqs = new EquipamentoDaSalaDAO().pesquisaEqSPorEquipamento(idEquipamento);
        }
    }

    public List<EquipamentoDaSala> getEqsDaSala() {
        eqsDaSala = new EquipamentoDaSalaDAO().consultaPorSala(sala.getIdSalas());
        return eqsDaSala;
    }

    public void setEqsDaSala(List<EquipamentoDaSala> eqsDaSala) {
        this.eqsDaSala = eqsDaSala;
    }
    
    public EquipamentoDaSala getEqSala() {
        return eqSala;
    }

    public void setEqSala(EquipamentoDaSala eqSala) {
        this.eqSala = eqSala;
    }

    public List<EquipamentoDaSala> getEqs() {
        return eqs;
    }

    public void setEqs(List<EquipamentoDaSala> eqs) {
        this.eqs = eqs;
    }

    public Integer getIdEquipamento() {
        return idEquipamento;
    }

    public void setIdEquipamento(Integer idEquipamento) {
        this.idEquipamento = idEquipamento;
    }

    public Salas getSala() {
        if (eqSala.getSala().getIdSalas() != null) {
            sala = new SalasDAO().pesquisaPorId(eqSala.getSala().getIdSalas());
        }
        return sala;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }

    public List<EquipamentoDaSala> completeEquipamento() {
        return new EquipamentoDaSalaDAO().ListaTodos();
    }

    public String limparLista() {
        idEquipamento = null;
        eqSala = new EquipamentoDaSala();
        eqs = null;
        sala = new Salas();
        construct();
        return null;
    }

    public String carregaLista() {
        construct();
        return null;
    }
}
