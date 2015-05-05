/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.dao;

import br.com.uninorte.siscodis.entidades.Turnos;
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
public class TurnosDAO {

     public void salvar(Turnos turno) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.save(turno);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }
    }

    public void excluir(Turnos turno) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(turno);
        t.commit();
        s.close();

    }

    public void alterar(Turnos turno) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.update(turno);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }

    }

    public List<Turnos> ListaTodos() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Turnos t order by t.id");
        List<Turnos> lista = q.list();
        s.close();
        return lista;
    }

    public Turnos pesquisaPorId(Integer id) {
        Session s = HibernateUtil.getSession();
        return (Turnos) s.load(Turnos.class, id);
    }    
}
