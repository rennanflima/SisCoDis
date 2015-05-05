/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.dao;

import br.com.uninorte.siscodis.entidades.Cursos;
import br.com.uninorte.siscodis.entidades.Instituicoes;
import br.com.uninorte.siscodis.util.HibernateUtil;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rennan Francisco
 */
public class CursosDAO {

     public void salvar(Cursos curso) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.save(curso);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }
    }

    public void excluir(Cursos curso) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(curso);
        t.commit();
        s.close();

    }

    public void alterar(Cursos curso) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.update(curso);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }

    }

    public List<Cursos> ListaTodos() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Cursos");
        List<Cursos> lista = q.list();
        s.close();
        return lista;
    }

    public Long contaTotalCursos(){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("select count(*) from Cursos");
        return (Long) q.uniqueResult();
    }
    
    public List<Cursos> pesquisaCursos(String nome, Integer id) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Cursos c where c.nome like :nome and c.instituicao.id = :instituicoes");
        q.setParameter("nome", nome+"%");
        q.setParameter("instituicoes", id);
        q.setMaxResults(5);
        return q.list();
    }
    
    public List<Cursos> pesquisaTodosCursos(String nome) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Cursos c where c.nome like :nome");
        q.setParameter("nome", nome+"%");
        q.setMaxResults(5);
        return q.list();
    }
    
    public Cursos pesquisaPorId(Integer id) {
        Session s = HibernateUtil.getSession();
        return (Cursos) s.get(Cursos.class, id);
    }

    public List<Cursos> pesquisaCursosPorInstituicao(Integer id) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Cursos c where c.instituicao.id = :instituicoes order by c.nome");
        q.setParameter("instituicoes", id);
        return q.list();
    }
    

}
