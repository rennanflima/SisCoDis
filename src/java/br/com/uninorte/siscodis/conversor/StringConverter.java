/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rennan Francisco
 */
@FacesConverter ("stringConverter")
public class StringConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value == null || value.length() == 0){
            return null;
        }
        return value;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if(value == null){
            return "";
        }
        return value.toString();
    }
}
