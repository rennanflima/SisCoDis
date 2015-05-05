/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.dao;

import br.com.uninorte.siscodis.entidades.PeriodosDaSala;
import br.com.uninorte.siscodis.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author rennan
 */
public class PeriodosDaSalaDAO {

    public void salvar(PeriodosDaSala tds) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.save(tds);
        t.commit();
        s.close();
    }

    public void excluir(PeriodosDaSala tds) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(tds);
        t.commit();
        s.close();
    }

    public void alterar(PeriodosDaSala tds) {
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.update(tds);
        t.commit();
        s.close();

    }

    public List<PeriodosDaSala> ListaTodos() {
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from PeriodosDaSala");
        List<PeriodosDaSala> lista = q.list();
        s.close();
        return lista;
    }

    public PeriodosDaSala pesquisaPorId(Integer id) {
        Session s = HibernateUtil.getSession();
        return (PeriodosDaSala) s.load(PeriodosDaSala.class, id);
    }

    public List<PeriodosDaSala> pesquisaTurnoPorSala(Integer id) {//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from PeriodosDaSala tds where tds.sala.id = :salas order by tds.turno.horarioInicio");
        q.setParameter("salas", id);
        return q.list();
    }
    
    public void truncar(){
        Session s = HibernateUtil.getSession();
        Query q = s.createSQLQuery("TRUNCATE PeriodosDaSala");
        q.executeUpdate();
        s.close();
    }
    
}
