/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.dao;

import br.com.uninorte.siscodis.entidades.EquipamentoDaSala;
import br.com.uninorte.siscodis.util.HibernateUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rennan Francisco
 */
public class EquipamentoDaSalaDAO {

    public void salvar(EquipamentoDaSala eqs) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.save(eqs);
            t.commit();
        } catch (Exception ex) {
            throw new Exception();
        } finally {
            s.close();
        }
    }

    public void excluir(EquipamentoDaSala eqs) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(eqs);
        t.commit();
        s.close();

    }

    public void alterar(EquipamentoDaSala eqs) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.update(eqs);
            t.commit();
        } catch (Exception ex) {
            throw new Exception();
        } finally {
            s.close();
        }
    }

    public List<EquipamentoDaSala> ListaTodos() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from EquipamentoDaSala");
        List<EquipamentoDaSala> lista = q.list();
        s.close();
        return lista;
    }

    public Long contaEquipamentosDefeituosos(){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("select count(*) from EquipamentoDaSala eqS where eqS.situacao = 'Defeito'");
        return (Long) q.uniqueResult();
    }
    
    public EquipamentoDaSala pesquisaPorId(Integer id) {
        Session s = HibernateUtil.getSession();
        return (EquipamentoDaSala) s.load(EquipamentoDaSala.class, id);
    }

    public List<EquipamentoDaSala> consultaPorSala(Integer id) {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from EquipamentoDaSala eqS where eqS.sala.idSalas = :id order by eqS.equipamento.nome");
        q.setParameter("id", id);
        return q.list();
    }

    public List<EquipamentoDaSala> pesquisaEqSPorEquipamento(Integer id) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("select eqs from EquipamentoDaSala eqs where eqs.equipamento.id = :equipamento group by eqs.sala.idSalas order by eqs.equipamento.nome");
        q.setParameter("equipamento", id);
        return q.list();
    }
    
    public ResultSet salasPorEquipamentosTodosResultSet(Connection conn) {
        ResultSet rs = null;
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(
                   "SELECT "
                        + "salas.`andar` AS salas_andar, "
                        + "salas.`capacidade` AS salas_capacidade, "
                        + "salas.`numero` AS salas_numero, "
                        + "salas.`obsSituacao` AS salas_obsSituacao, "
                        + "salas.`situacao` AS salas_situacao, "
                        + "blocos.`nome` AS blocos_nome, "
                        + "equipamentos.`nome` AS equipamentos_nome "
                   + "FROM "
                        + "`salas` salas INNER JOIN `equipamentodasala` equipamentodasala ON salas.`idSalas` = equipamentodasala.`sala_idSalas` "
                        + "INNER JOIN `blocos` blocos ON salas.`bloco_idBlocos` = blocos.`idBlocos` "
                        + "INNER JOIN `equipamentos` equipamentos ON equipamentodasala.`equipamento_idEquipamentos` = equipamentos.`idEquipamentos` "     
                   + "ORDER BY "
                        + "equipamentos.`nome` ASC, "
                        + "blocos.`nome` ASC, "  
                        + "salas.`andar` ASC, "     
                        + "salas.`numero` ASC");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
    
    public ResultSet salasPorEquipamentosResultSet(Connection conn, Integer idEquipamentos) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                   "SELECT "
                        + "salas.`andar` AS salas_andar, "
                        + "salas.`capacidade` AS salas_capacidade, "
                        + "salas.`numero` AS salas_numero, "
                        + "salas.`obsSituacao` AS salas_obsSituacao, "
                        + "salas.`situacao` AS salas_situacao, "
                        + "blocos.`nome` AS blocos_nome, "
                        + "equipamentos.`nome` AS equipamentos_nome "
                   + "FROM "
                        + "`salas` salas INNER JOIN `equipamentodasala` equipamentodasala ON salas.`idSalas` = equipamentodasala.`sala_idSalas` "
                        + "INNER JOIN `blocos` blocos ON salas.`bloco_idBlocos` = blocos.`idBlocos` "
                        + "INNER JOIN `equipamentos` equipamentos ON equipamentodasala.`equipamento_idEquipamentos` = equipamentos.`idEquipamentos` "
                        + "AND equipamentodasala.`equipamento_idEquipamentos` = ? "
                   + "ORDER BY "
                        + "equipamentos.`nome` ASC, "
                        + "blocos.`nome` ASC, "  
                        + "salas.`andar` ASC, "     
                        + "salas.`numero` ASC");
            ps.setInt(1, idEquipamentos);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
}
