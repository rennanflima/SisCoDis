/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.dao;

import br.com.uninorte.siscodis.entidades.Cadeiras;
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
public class CadeirasDAO {

    public void salvar(Cadeiras cad) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.save(cad);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }
    }

    public void excluir(Cadeiras cad) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(cad);
        t.commit();
        s.close();

    }

    public void alterar(Cadeiras cad) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.update(cad);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }

    }

    public List<Cadeiras> ListaTodos() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Cadeiras");
        List<Cadeiras> lista = q.list();
        s.close();
        return lista;
    }

    public Cadeiras pesquisaPorId(Integer id) {
        Session s = HibernateUtil.getSession();
        return (Cadeiras) s.load(Cadeiras.class, id);
    }
}
