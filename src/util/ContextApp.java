package util;


import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ContextApp
{
	public static FacesContext getContext()
	{
		FacesContext context = FacesContext.getCurrentInstance();

		return context;
	}

	public static HttpSession getSession()
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		HttpSession session = request.getSession();

		return session;
	}

}
