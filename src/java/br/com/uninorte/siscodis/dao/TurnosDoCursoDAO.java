/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.dao;

import br.com.uninorte.siscodis.entidades.TurnosDoCurso;
import br.com.uninorte.siscodis.util.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Rennan Francisco
 */
public class TurnosDoCursoDAO {

    public void salvar (TurnosDoCurso tdt){
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.save(tdt);
        t.commit();
        s.close();
    }

    public void excluir(TurnosDoCurso tdt){
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.delete(tdt);
        t.commit();
        s.close();
        
    }
    public void alterar(TurnosDoCurso tdt){
        Session s = HibernateUtil.getSession();
        Transaction t = s.beginTransaction();
        s.update(tdt);
        t.commit();
        s.close();
        
    }
     
    public List<TurnosDoCurso> ListaTodos(){
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from TurnosDoCurso");
        List<TurnosDoCurso> lista = q.list();
        s.close();
        return lista;
    }
    
    public TurnosDoCurso pesquisaPorId(Long id){
        Session s = HibernateUtil.getSession();
        return (TurnosDoCurso) s.load(TurnosDoCurso.class, id);
    }    
    

    public List<TurnosDoCurso> pesquisaTurnoPorCurso (Integer id){//join
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("from TurnosDoCurso tdt where tdt.curso.id = :cursos order by tdt.turno.id");
        q.setParameter("cursos", id);
        return q.list();
    } 

}
