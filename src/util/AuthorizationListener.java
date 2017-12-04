package util;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import _model.vo.Usuario;

public class AuthorizationListener implements PhaseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8388022673182649244L;
	private String nextPage;

	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		String currentPage = facesContext.getViewRoot().getViewId();
		
		boolean isLoginPage = (currentPage.lastIndexOf("index.xhtml") > -1);
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		Object currentUser = session.getAttribute("usuariologado");
		
		if (!isLoginPage && currentUser == null) {
			NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
			nh.handleNavigation(facesContext, null, "loginPage");
			try {
				facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/index.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	public void beforePhase(PhaseEvent event) {
	}
	
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
