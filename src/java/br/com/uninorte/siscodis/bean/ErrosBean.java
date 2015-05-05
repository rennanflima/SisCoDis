/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.uninorte.siscodis.bean;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Rennan Francisco
 */
@ManagedBean
@ViewScoped
public class ErrosBean implements Serializable{
    
    public String redirecionaErros() {
        FacesContext msg = FacesContext.getCurrentInstance();
        try {
            msg.getExternalContext().redirect("/SisCoDis/publico/temp.xhtml");
        } catch (Exception e) {
            msg.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Ocorreu um erro ao redirecionar o usuário", null));
        }
        return null;
    }
}
