package _controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import util.ContextApp;

@ManagedBean(name = "principalView")
@RequestScoped
public class PrincipalView implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8708864562354226535L;
	private boolean result;
	private String nextPage;

	public PrincipalView() throws Exception {
	}
	
	@SuppressWarnings("unchecked")
	public boolean validaAcesso(String pag) throws Exception
	{
		/*
		 * **************************************************************
		 * * VERIFICA SE USUÁRIO LOGADO TEM ACESSO A PÁGINA SELECIONADA
		 * **************************************************************
		 */
		result = true;
		Map<Object, String> usuarioAcesso = new HashMap<Object, String>();
		usuarioAcesso = (Map<Object, String>)ContextApp.getSession().getAttribute("usuarioAcesso");
		// -- SE NÃO EXISTIR A PÁGINA O ACESSO DEVE SER BLOQUEADO, RETIRANDO A OPÇÃO DO MENU.
		if (usuarioAcesso.get(pag)==null) {
			/*
			 * ***********************************************************************************************
			 * * MANTENDO O CONTROLE DE ACESSO INATIVO ATÉ QUE O CADASTRO DE TODAS AS PÁGINAS SEJA CONCLUÍDO
			 * ***********************************************************************************************
			 */
			//result=false;
			result=true;
		} 
		
		return result;

	}

	
	public String desconectaUsuario() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		session.invalidate();
		
		return "/index.xhtml?faces-redirect=true";
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	
	
}
