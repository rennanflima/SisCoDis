/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.dao;

import br.com.uninorte.siscodis.entidades.Usuarios;
import br.com.uninorte.siscodis.util.HibernateUtil;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author rennan
 */
public class UsersDAO {
    public void salvar(Usuarios user) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.save(user);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException("Já existe um Usuário cadastrado com esse login!");
        } finally {
            s.close();
        }
    }

    public void excluir(Usuarios user) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(user);
        t.commit();
        s.close();

    }

    public void alterar(Usuarios user) throws Exception {
        Session s = HibernateUtil.getSession();
        try {
            Transaction t = s.beginTransaction();
            s.update(user);
            t.commit();
        } catch (Exception ex) {
            throw new SQLIntegrityConstraintViolationException();
        } finally {
            s.close();
        }

    }

    public List<Usuarios> ListaTodos() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from Users");
        List<Usuarios> lista = q.list();
        s.close();
        return lista;
    }

    public Usuarios pesquisaPorId(String id) {
        Session s = HibernateUtil.getSession();
        return (Usuarios) s.load(Usuarios.class, id);
    }
}
