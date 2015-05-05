/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.dao;

import br.com.uninorte.siscodis.entidades.Salas;
import br.com.uninorte.siscodis.util.HibernateUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rennan Francisco
 */
public class SalasDAO {

    public void salvar(Salas sala) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.save(sala);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }
    }

    public void excluir(Salas sala) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(sala);
        t.commit();
        s.close();

    }

    public void alterar(Salas sala) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.update(sala);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }

    }

    public List<Salas> ListaTodos() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Salas");
        List<Salas> lista = q.list();
        s.close();
        return lista;
    }

    public Salas pesquisaPorId(Integer id) {
        Session s = HibernateUtil.getSession();
        return (Salas) s.get(Salas.class, id);
    }

    public List<Salas> pesquisaSalas(String num) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Salas s where s.numero like :num");
        q.setParameter("num", num + "%");
        q.setMaxResults(5);
        return q.list();
    }

    public List<Salas> pesquisaSalasPorId(Integer id) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Salas s where s.id = :id order by s.numero");
        q.setParameter("id", id);
        return q.list();
    }

    public List<Salas> pesquisaSalasPorBloco(Integer id) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Salas s where s.bloco.id = :bloco order by s.numero");
        q.setParameter("bloco", id);
        return q.list();
    }

    public List<Salas> pesquisaSalasPorCadeira(Integer id) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Salas s where s.cadeira.id = :cadeira order by s.numero");
        q.setParameter("cadeira", id);
        return q.list();
    }

    public List<Salas> pesquisaSalasPorEquipamentos(Integer id) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("select s.* from Salas s inner join EquipamentoDaSala eqs where eqs.sala.id = :s.sala and eqs.equipamento.id = :equip order by s.numero");
        q.setParameter("equip", id);
        return q.list();
        /*Select S.* 
         *from salas S inner join equipamentodasala T 
         *where t.sala_idSalas = s.idSalas and equipamento_idEquipamentos = 1 
         *order by S.numero*/
    }

    public List<Salas> pesquisaSalasEquipDefeituoso() {
        Session s = HibernateUtil.getSession();
        Query q = s.createSQLQuery(
                "SELECT s.* FROM salas s INNER JOIN equipamentodasala eqs ON"
                + " eqs.sala_idSalas = s.idSalas AND eqs.Situacao = 'Defeito' GROUP BY s.numero")
                .addEntity(Salas.class);
        return q.list();
    }
    
    public Long contaSalasBloqueadas(){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("select count(*) from Salas s where s.situacao = 'Bloqueada'");
        return (Long) q.uniqueResult();
    }
    
    public Long contaSalasLiberadas(){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("select count(*) from Salas s where s.situacao = 'Liberada'");
        return (Long) q.uniqueResult();
    }
    
    public Long contaTotalSalas(){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("select count(*) from Salas");
        return (Long) q.uniqueResult();
    }
    
    public List<Salas> pesquisaSalasPorCapacidade(Integer qtd, Date hi, Date hf) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createSQLQuery(
                "SELECT t.* "
                + "FROM(Select s.*, a.idPeriodosDaSala "
                + "FROM Salas s LEFT OUTER JOIN PeriodosDaSala a "
                + "ON s.idSalas = a.sala_idSalas LEFT OUTER JOIN Periodos p ON p.idPeriodos = a.periodo_idPeriodos "
                + "AND (CAST('" + hi + "' AS time) < p.horarioInicio  and CAST('" + hf + "' AS time) < p.horarioInicio) OR "
                + "(CAST('" + hi + "' AS time) > p.horarioInicio and CAST('" + hf + "' AS time) > p.horarioFinal) GROUP BY s.numero) t "
                + "WHERE t.capacidade >= :qtd and t.situacao = 'Liberada' and t.idPeriodosDaSala is null "
                + "ORDER BY t.numero")
                
                //Tolerância de 15min para reservar
                /*"SELECT t.* "
                + "FROM(Select s.*, a.idPeriodosDaSala "
                + "FROM Salas s LEFT OUTER JOIN PeriodosDaSala a "
                + "ON s.idSalas = a.sala_idSalas LEFT OUTER JOIN Periodos p ON p.idPeriodos = a.periodo_idPeriodos "
                + "AND (CAST('" + hi + "' AS time) < p.horarioInicio  and ADDTIME('" + hf + "','00:15:00') < p.horarioInicio) OR "
                + "(CAST('" + hi + "' AS time) > p.horarioInicio and CAST('" + hf + "' AS time) > ADDTIME('00:15:00', p.horarioFinal)) ) t "
                + "WHERE t.capacidade >= :qtd and t.situacao = 'Liberada' and t.idPeriodosDaSala is null "
                + "ORDER BY t.numero"*/
                
                .addEntity(Salas.class)
                .setParameter("qtd", qtd);
        return q.list();
    }

    public ResultSet salasBlocosPorAndarResultSet(Connection conn) {
        ResultSet rs = null;
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(
                    "SELECT "
                    + "blocos.`nome` AS blocos_nome, "
                    + "salas.`andar` AS salas_andar, "
                    + "salas.`capacidade` AS salas_capacidade, "
                    + "salas.`numero` AS salas_numero, "
                    + "salas.`obsSituacao` AS salas_obsSituacao, "
                    + "salas.`situacao` AS salas_situacao "
                    + "FROM "
                    + "`blocos` blocos INNER JOIN `salas` salas ON blocos.`idBlocos` = salas.`bloco_idBlocos` "
                    + "ORDER BY "
                    + "blocos.`nome` ASC, "
                    + "salas.`andar` ASC, "
                    + "salas.`numero` ASC");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
    
    public ResultSet salasBlocoResultSet(Connection conn, Integer idBlocos) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT "
                    + "blocos.`nome` AS blocos_nome, "
                    + "salas.`andar` AS salas_andar, "
                    + "salas.`capacidade` AS salas_capacidade, "
                    + "salas.`numero` AS salas_numero, "
                    + "salas.`obsSituacao` AS salas_obsSituacao, "
                    + "salas.`situacao` AS salas_situacao "
                    + "FROM "
                    + "`blocos` blocos INNER JOIN `salas` salas ON blocos.`idBlocos` = salas.`bloco_idBlocos`"
                    + "AND blocos.`idBlocos` = ? "
                    + "ORDER BY "
                    + "blocos.`nome` ASC, "
                    + "salas.`andar` ASC, "
                    + "salas.`numero` ASC");
            ps.setInt(1, idBlocos);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
    
    public ResultSet salasLiberadasResultSet(Connection conn) {
        ResultSet rs = null;
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(
                    "SELECT "
                    + "blocos.`nome` AS blocos_nome, "
                    + "salas.`andar` AS salas_andar, "
                    + "salas.`capacidade` AS salas_capacidade, "
                    + "salas.`numero` AS salas_numero, "
                    + "salas.`obsSituacao` AS salas_obsSituacao, "
                    + "salas.`situacao` AS salas_situacao "
                    + "FROM "
                    + "`blocos` blocos INNER JOIN `salas` salas ON blocos.`idBlocos` = salas.`bloco_idBlocos`"
                    + "AND salas.`situacao` = 'Liberada' "
                    + "ORDER BY "
                    + "blocos.`nome` ASC, "
                    + "salas.`andar` ASC, "
                    + "salas.`numero` ASC");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
    
    public ResultSet salasBloqueadasResultSet(Connection conn) {
        ResultSet rs = null;
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(
                    "SELECT "
                    + "blocos.`nome` AS blocos_nome, "
                    + "salas.`andar` AS salas_andar, "
                    + "salas.`capacidade` AS salas_capacidade, "
                    + "salas.`numero` AS salas_numero, "
                    + "salas.`obsSituacao` AS salas_obsSituacao, "
                    + "salas.`situacao` AS salas_situacao "
                    + "FROM "
                    + "`blocos` blocos INNER JOIN `salas` salas ON blocos.`idBlocos` = salas.`bloco_idBlocos`"
                    + "AND salas.`situacao` = 'Bloqueada' "
                    + "ORDER BY "
                    + "blocos.`nome` ASC, "
                    + "salas.`andar` ASC, "
                    + "salas.`numero` ASC");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
    
    public ResultSet salasEquipDefeituososResultSet(Connection conn) {
        ResultSet rs = null;
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(
                    "SELECT "
                    + "blocos.`nome` AS blocos_nome, "
                    + "salas.`andar` AS salas_andar, "
                    + "salas.`capacidade` AS salas_capacidade, "
                    + "salas.`numero` AS salas_numero, "
                    + "salas.`obsSituacao` AS salas_obsSituacao, "
                    + "salas.`situacao` AS salas_situacao "
                    + "FROM "
                    + "`blocos` blocos INNER JOIN `salas` salas ON blocos.`idBlocos` = salas.`bloco_idBlocos`"
                    + "INNER JOIN `equipamentodasala` eqs ON "
                    + "eqs.`sala_idSalas` = salas.`idSalas` AND eqs.`situacao` = 'Defeito' GROUP BY salas.`numero` "
                    + "ORDER BY "
                    + "blocos.`nome` ASC, "
                    + "salas.`andar` ASC, "
                    + "salas.`numero` ASC");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
    
    public ResultSet salasSemEquipResultSet(Connection conn) {
        ResultSet rs = null;
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(
                    "Select s.bloco, s.andar, s.capacidade, s.numero, s.obsSituacao, s.situacao "
                    + "FROM(SELECT "
                    + "blocos.`nome` AS bloco, "
                    + "salas.`andar` AS andar, "
                    + "salas.`capacidade` AS capacidade, "
                    + "salas.`numero` AS numero, "
                    + "salas.`obsSituacao` AS obsSituacao, "
                    + "eqs.`sala_idSalas` AS id, "
                    + "salas.`situacao` AS situacao "
                    + "FROM "
                    + "`blocos` blocos INNER JOIN `salas` salas ON blocos.`idBlocos` = salas.`bloco_idBlocos` "
                    + "LEFT OUTER JOIN `equipamentodasala` eqs ON "
                    + "eqs.`sala_idSalas` = salas.`idSalas` "
                    + "GROUP BY "
                    + "salas.`numero`) s "
                    + "WHERE s.id IS NULL "
                    + "ORDER BY "
                    + "s.`bloco` ASC, "
                    + "s.`andar` ASC, "
                    + "s.`numero` ASC");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
}
