/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.dao;

import br.com.uninorte.siscodis.entidades.Turmas;
import br.com.uninorte.siscodis.util.HibernateUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rennan Francisco
 */
public class TurmasDAO {

    public void salvar(Turmas turma) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.save(turma);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }
    }

    public void excluir(Turmas turma) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(turma);
        t.commit();
        s.close();

    }

    public void alterar(Turmas turma) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.update(turma);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }

    }

    public List<Turmas> ListaTodos() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Turmas");
        List<Turmas> lista = q.list();
        s.close();
        return lista;
    }
    
    public List<Turmas> ListaTurmasCursando() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Turmas t where t.status = :sit");
        q.setParameter("sit", "Cursando");
        List<Turmas> lista = q.list();
        s.close();
        return lista;
    }

    public Turmas pesquisaPorId(Integer id) {
        Session s = HibernateUtil.getSession();
        return (Turmas) s.get(Turmas.class, id);
    }

    public List<Turmas> consultaPorTurma(String turma) {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Turmas t where t.codigo like :cod order by t.periodo.periodo");
        q.setParameter("cod", turma + "%");
        return q.list();
    }

    public List<Turmas> listaTurmasComSala(){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Turmas t where t.sala is not null order by t.periodo.periodo");
        List<Turmas> lista = q.list();
        s.close();
        return lista;
    }
    
    public List<Turmas> consultaPorTurmaComSala(String turma) {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Turmas t where t.codigo like :cod and t.sala is not null order by t.periodo.periodo");
        q.setParameter("cod", turma + "%");
        return q.list();
    }
    
    public List<Turmas> pesquisaTurmasPorCursoPeriodoComSala(Integer id, Integer per) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Turmas t where t.curso.id = :curso and t.periodo.id = :per and and t.sala is not null order by t.periodo.periodo");
        q.setParameter("curso", id);
        q.setParameter("per", per);
        return q.list();
    }
    
    public List<Turmas> pesquisaTurmasPorCursoComSala(Integer id) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Turmas t where t.curso.id = :curso and t.sala is not null order by t.periodo.periodo");
        q.setParameter("curso", id);
        return q.list();
    }
    
    public List<Turmas> pesquisaTurmasPorCurso(Integer id) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Turmas t where t.curso.id = :curso and t.status = :sit order by t.periodo.periodo");
        q.setParameter("curso", id);
        q.setParameter("sit", "Cursando");
        return q.list();
    }

    public List<Turmas> pesquisaTurmasPorPeriodo(Integer id) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Turmas t where t.periodo.id = :per order by t.periodo.periodo");
        q.setParameter("per", id);
        return q.list();
    }

    public Long contaTotalTurmas(){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("select count(*) from Turmas");
        return (Long) q.uniqueResult();
    }
    
    public Long contaTurmasUltimoPer(){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("select count(*) from Turmas t where t.periodo.periodo = t.curso.qtdPeriodos");
        return (Long) q.uniqueResult();
    }
    
    public Long contaTurmasNovas(){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("select count(*) from Turmas t where t.periodo.periodo = 1");
        return (Long) q.uniqueResult();
    }
    
    public List<Turmas> pesquisaTurmasPorCursoPeriodo(Integer id, Integer per) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Turmas t where t.curso.id = :curso and t.periodo.id = :per order by t.periodo.periodo");
        q.setParameter("curso", id);
        q.setParameter("per", per);
        return q.list();
    }

    public List<Turmas> pesquisaTurmasPorSala(Integer id) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Turmas t where t.sala.id = :sala order by t.periodo.periodo");
        q.setParameter("sala", id);
        return q.list();
    }
    
    public List<Turmas> pesquisaTurmasPorCursoDistri(Integer id, Integer idt) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Turmas t where t.curso.id = :curso  and t.id != :turma order by t.periodo.periodo");
        q.setParameter("curso", id);
        q.setParameter("turma", idt);
        return q.list();
    }

    public ResultSet distribuicaoResultSet(Connection conn) {
        ResultSet rs = null;
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(
                    "SELECT "
                        + "instituicoes.`sigla` AS instituicoes_sigla, "
                        + "cursos.`codigo` AS cursos_codigo, "
                        + "cursos.`nome` AS cursos_nome, "
                        + "periodos.`periodo` AS periodos_periodo, "
                        + "salas.`numero` AS salas_numero, "
                        + "blocos.`nome` AS blocos_nome, "
                        + "turmas.`codigo` AS turmas_codigo, "
                        + "turmas.`qtdAlunos` AS turmas_qtdAlunos "
                    + "FROM "
                        + "`instituicoes` instituicoes INNER JOIN `cursos` cursos ON instituicoes.`idInstituicoes` = cursos.`instituicao_idInstituicoes` "
                        + "INNER JOIN `turmas` turmas ON cursos.`idCursos` = turmas.`curso_idCursos` AND turmas.`status` = 'Cursando' " 
                        + "INNER JOIN `periodos` periodos ON cursos.`idCursos` = periodos.`curso_idCursos` "
                        + "AND periodos.`idPeriodos` = turmas.`periodo_idPeriodos` "
                        + "INNER JOIN `salas` salas ON turmas.`sala_idSalas` = salas.`idSalas` "
                        + "INNER JOIN `blocos` blocos ON salas.`bloco_idBlocos` = blocos.`idBlocos` "
                    + "ORDER BY "
                        + "instituicoes.`sigla` ASC, "
                        + "cursos.`nome` ASC, "
                        + "periodos.`periodo` ASC");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public ResultSet turmasSemSalasResultSet(Connection conn) {
        ResultSet rs = null;
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(
                    "SELECT "
                        + "instituicoes.`sigla` AS instituicoes_sigla, "
                        + "cursos.`codigo` AS cursos_codigo, "
                        + "cursos.`nome` AS cursos_nome, "
                        + "periodos.`periodo` AS periodos_periodo, "
                        + "turmas.`codigo` AS turmas_codigo, "
                        + "turmas.`qtdAlunos` AS turmas_qtdAlunos "
                    + "FROM "
                        + "`instituicoes` instituicoes INNER JOIN `cursos` cursos ON instituicoes.`idInstituicoes` = cursos.`instituicao_idInstituicoes` "
                        + "INNER JOIN `turmas` turmas ON cursos.`idCursos` = turmas.`curso_idCursos` AND turmas.`status` = 'Cursando' AND turmas.`sala_idSalas` IS NULL "
                        + "INNER JOIN `periodos` periodos ON cursos.`idCursos` = periodos.`curso_idCursos` "
                        + "AND periodos.`idPeriodos` = turmas.`periodo_idPeriodos` "
                    + "ORDER BY "
                        + "instituicoes.`sigla` ASC, "
                        + "cursos.`nome` ASC, "
                        + "periodos.`periodo` ASC");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
}
