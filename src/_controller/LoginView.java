package _controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import _business.ConfiguracaoManager;
import _business.UsuarioManager;
import _model.vo.Parametro;
import _model.vo.Usuario;
import util.ContextApp;

@ManagedBean(name = "loginView")
@RequestScoped
public class LoginView
{
	private Usuario usuario = new Usuario();
	
	public String homePage() {
		return "/pages/principal/principal";
	}

	public String quemsomos() {
		return "/pages/visitante/quemsomos";
	}
	
	public String produtos() {
		return "/pages/visitante/produtos";
	}

	public String contato() {
		return "/pages/visitante/contato";
	}

	public String ajuda() {
		return "/pages/visitante/ajuda";
	}
	
	public String portaria() {
		return "/pages/visitante/portaria1224";
	}

	public String login() {
		return "/index";
	}
	
	public String validaUsuario() throws Exception
	{
		UsuarioManager usuarioManager = new UsuarioManager();
		Map<Object, String> usuarioAcesso = new HashMap<Object, String>();

		Usuario usua = usuarioManager.findByLoginPwd(usuario);

		if (usua != null)
		{
			/*
			 * ***************************************************************************************
			 * * FAZ A CARGA DE TODAS AS PÁGINAS E SEUS NÍVEIS DE ACESSO QUE ESTE USUÁRIO TEM DIREITO
			 * * COLOCANDO O RESULTADO NA SESSÃO PARA PESQUISA NA PRINCIPALVIEW.JAVA
			 * ***************************************************************************************
			 */
			usuarioAcesso=(Map<Object, String>) usuarioManager.acessoUsuario(usua.getObjCliente().getId(), usua.getId());
			
			/*
			 * **************************************************
			 * * CARREGA VARIÁVEIS NA SESSÃO:
			 * * USUÁRIO LOGADO E DIREITOS DE ACESSO AS PÁGINAS
			 * **************************************************
			 */
			ContextApp.getSession().setAttribute("usuariologado", usua);
			ContextApp.getSession().setAttribute("usuarioAcesso",  usuarioAcesso);

			/*
			 * **************************************************
			 * * PARÂMETROS DO SISTEMA
			 * **************************************************
			 */
			Parametro parametro = new Parametro();
			ConfiguracaoManager configuracaoManager = new ConfiguracaoManager();
			parametro = configuracaoManager.listaParametros();
			ContextApp.getSession().setAttribute("parametroSistema", parametro);
			
			return "/pages/principal/principal";

		}
		else
		{
			ContextApp.getContext().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario inválido", null));
			return "";
		}

	}
	
	/*
	 * **************************************************
	 * GET's e SET's
	 * **************************************************
	 */
	
	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}

}
