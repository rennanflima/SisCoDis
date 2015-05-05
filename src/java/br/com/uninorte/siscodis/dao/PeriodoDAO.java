/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.dao;

import br.com.uninorte.siscodis.entidades.Periodos;
import br.com.uninorte.siscodis.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rennan Francisco
 */
public class PeriodoDAO {

    public void salvar(Periodos per) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.save(per);
            t.commit();
        } catch (Exception ex) {
            throw new Exception();
        } finally {
            s.close();
        }
    }

    public void excluir(Periodos per) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(per);
        t.commit();
        s.close();

    }

    public void alterar(Periodos per) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.update(per);
            t.commit();
        } catch (Exception ex) {
            throw new Exception();
        } finally {
            s.close();
        }

    }

    public List<Periodos> ListaTodos() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Periodos");
        List<Periodos> lista = q.list();
        s.close();
        return lista;
    }

    public Periodos pesquisaPorId(Integer id) {
        Session s = HibernateUtil.getSession();
        return (Periodos) s.get(Periodos.class, id);
    }

    public Periodos verificaPeriodo(Integer per, Integer curso) {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Periodos p where p.periodo = :per and p.curso.id = :curso");
        q.setParameter("per", per);
        q.setParameter("curso", curso);
        return (Periodos) q.uniqueResult();
    }

    public List<Periodos> pesquisaPeriodosPorCursos(Integer id) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Periodos p where p.curso.id = :cursos order by p.periodo");
        q.setParameter("cursos", id);
        return q.list();
    }
}
