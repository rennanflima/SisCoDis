/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.dao;

import br.com.uninorte.siscodis.entidades.Blocos;
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
public class BlocosDAO {

   public void salvar(Blocos bloco) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.save(bloco);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }
    }

    public void excluir(Blocos bloco) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(bloco);
        t.commit();
        s.close();

    }

    public void alterar(Blocos bloco) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.update(bloco);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }

    }

    public List<Blocos> ListaTodos() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Blocos");
        List<Blocos> lista = q.list();
        s.close();
        return lista;
    }

    public Long contaTotalBlocos(){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("select count(*) from Blocos");
        return (Long) q.uniqueResult();
    }
    
    public List<Blocos> listaTodosCombo() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Blocos b order by b.nome");
        List<Blocos> lista = q.list();
        s.close();
        return lista;
    }
    
    public Blocos pesquisaPorId(Integer id) {
        Session s = HibernateUtil.getSession();
        return (Blocos) s.load(Blocos.class, id);
    }
}
