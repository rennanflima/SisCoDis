/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.dao;

import br.com.uninorte.siscodis.entidades.Equipamentos;
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
public class EquipamentosDAO {
 
    public void salvar(Equipamentos eq) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.save(eq);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }
    }

    public void excluir(Equipamentos eq) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(eq);
        t.commit();
        s.close();

    }

    public void alterar(Equipamentos eq) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.update(eq);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }

    }

    public List<Equipamentos> ListaTodos() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Equipamentos");
        List<Equipamentos> lista = q.list();
        //s.close();
        return lista;
    }

    public Equipamentos pesquisaPorId(Integer id) {
        Session s = HibernateUtil.getSession();
        return (Equipamentos) s.get(Equipamentos.class, id);
    }
}
