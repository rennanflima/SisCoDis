/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import br.com.uninorte.siscodis.dao.BlocosDAO;
import br.com.uninorte.siscodis.dao.CursosDAO;
import br.com.uninorte.siscodis.dao.EquipamentoDaSalaDAO;
import br.com.uninorte.siscodis.dao.SalasDAO;
import br.com.uninorte.siscodis.dao.TurmasDAO;
import br.com.uninorte.siscodis.entidades.EquipamentoDaSala;
import br.com.uninorte.siscodis.entidades.Salas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Rennan Francisco
 */
@ManagedBean
@ViewScoped
public class HomeBean implements Serializable{
    
    private Salas sala = new Salas();
    private List<Salas> salas = new ArrayList<Salas>();
    private List<EquipamentoDaSala> eqsDaSala = new ArrayList<EquipamentoDaSala>();
    private PieChartModel graficoSalas;
    private Long totalTurmas;
    private Long turmasNovas;
    private Long turmasUltimoPer;
    private Long totalCursos;
    private Long totalSalas;
    private Long totalBlocos;
    private Long totalEquipamentosDefeituosos;

    public PieChartModel getGraficoSalas() {
        graficoSalas = new PieChartModel();
        graficoSalas.set("Salas Bloqueadas", new SalasDAO().contaSalasBloqueadas());
        graficoSalas.set("Salas Liberadas", new SalasDAO().contaSalasLiberadas());
        return graficoSalas;
    }

    public Long getTotalEquipamentosDefeituosos() {
        totalEquipamentosDefeituosos = new EquipamentoDaSalaDAO().contaEquipamentosDefeituosos();
        return totalEquipamentosDefeituosos;
    }

    public void setTotalEquipamentosDefeituosos(Long totalEquipamentosDefeituosos) {
        this.totalEquipamentosDefeituosos = totalEquipamentosDefeituosos;
    }

    public Long getTotalSalas() {
        totalSalas = new SalasDAO().contaTotalSalas();
        return totalSalas;
    }

    public void setTotalSalas(Long totalSalas) {
        this.totalSalas = totalSalas;
    }

    public Long getTotalBlocos() {
        totalBlocos =  new BlocosDAO().contaTotalBlocos();
        return totalBlocos;
    }

    public void setTotalBlocos(Long totalBlocos) {
        this.totalBlocos = totalBlocos;
    }

    public Long getTotalCursos() {
        totalCursos = new CursosDAO().contaTotalCursos();
        return totalCursos;
    }

    public void setTotalCursos(Long totalCursos) {
        this.totalCursos = totalCursos;
    }

    public Long getTurmasNovas() {
        turmasNovas = new TurmasDAO().contaTurmasNovas();
        return turmasNovas;
    }

    public void setTurmasNovas(Long turmasNovas) {
        this.turmasNovas = turmasNovas;
    }

    public Long getTurmasUltimoPer() {
        turmasUltimoPer = new TurmasDAO().contaTurmasUltimoPer();
        return turmasUltimoPer;
    }

    public void setTurmasUltimoPer(Long turmasUltimoPer) {
        this.turmasUltimoPer = turmasUltimoPer;
    }

    public Long getTotalTurmas() {
        totalTurmas = new TurmasDAO().contaTotalTurmas();
        return totalTurmas;
    }

    public void setTotalTurmas(Long totalTurmas) {
        this.totalTurmas = totalTurmas;
    }

    public Salas getSala() {
        return sala;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }
    
    public List<EquipamentoDaSala> getEqsDaSala() {
        eqsDaSala = new EquipamentoDaSalaDAO().consultaPorSala(sala.getIdSalas());
        return eqsDaSala;
    }

    public void setEqsDaSala(List<EquipamentoDaSala> eqsDaSala) {
        this.eqsDaSala = eqsDaSala;
    }

    public List<Salas> getSalas() {
        salas = new SalasDAO().pesquisaSalasEquipDefeituoso();
        return salas;
    }

    public void setSalas(List<Salas> salas) {
        this.salas = salas;
    }

    public String verificaStatus(Salas s) {
        if (s != null) {
            List<EquipamentoDaSala> eqs = new EquipamentoDaSalaDAO().consultaPorSala(s.getIdSalas());
            Integer cont = 0;
            for (EquipamentoDaSala e : eqs) {
                if (e.getSituacao().equals("Defeito")) {
                    cont++;
                }
            }
            if (s.getSituacao().equals("Liberada")) {
                if (cont >= 1) {
                    return "alerta.png";
                } else {
                    return "ativado.png";
                }
            } else {
                return "desativado.png";
            }
        }
        return null;
    }
}
