/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.conversor;

import br.com.uninorte.siscodis.dao.CursosDAO;
import br.com.uninorte.siscodis.entidades.Cursos;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rennan Francisco
 */
@FacesConverter(value = "cursoConverter")
public class CursoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            CursosDAO cd = new CursosDAO();
            Cursos c = cd.pesquisaPorId(Integer.parseInt(value));
            return c;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Cursos) {
            Cursos c = (Cursos) value;
            return String.valueOf(c.getIdCursos());
        }
        return "";
    }
}
