package br.com.uninorte.siscodis.util;



import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class SessionUtil {
    private static ResourceBundle bundle = ResourceBundle.getBundle("messages",
			 FacesContext.getCurrentInstance().getViewRoot().getLocale());
    
    public static void addErrorMessage(String msg) { 
		msg = bundle.getString(msg);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, facesMsg);
    }

    public static void addSuccessMessage(String msg) {
    	msg = bundle.getString(msg);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage("successInfo", facesMsg);
    }
}
