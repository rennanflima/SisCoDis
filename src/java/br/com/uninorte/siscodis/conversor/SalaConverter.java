/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.conversor;

import br.com.uninorte.siscodis.dao.SalasDAO;
import br.com.uninorte.siscodis.entidades.Salas;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rennan Francisco
 */
@FacesConverter(value = "salaConverter")
public class SalaConverter implements Converter {

    SalasDAO sd = new SalasDAO();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            Salas s = sd.pesquisaPorId(Integer.parseInt(value));
            return s;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Salas) {
            Salas s = (Salas) value;
            return String.valueOf(s.getIdSalas());
        }
        return "";
    }
}
