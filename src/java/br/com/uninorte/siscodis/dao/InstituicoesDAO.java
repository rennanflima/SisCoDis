/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.dao;

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
public class InstituicoesDAO {

     public void salvar(Instituicoes inst) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.save(inst);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }
    }

    public void excluir(Instituicoes inst) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(inst);
        t.commit();
        s.close();

    }

    public void alterar(Instituicoes inst) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.update(inst);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }

    }

    public List<Instituicoes> ListaTodos() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Instituicoes");
        List<Instituicoes> lista = q.list();
        s.close();
        return lista;
    }

    public Instituicoes pesquisaPorId(Integer id) {
        Session s = HibernateUtil.getSession();
        return (Instituicoes) s.load(Instituicoes.class, id);
    }
}
