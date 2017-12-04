package _controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import _business.ConfiguracaoManager;
import _model.vo.Parametro;
import util.Bematech;
import util.ContextApp;

@ManagedBean(name="configuracaoView")
@RequestScoped
public class ConfiguracaoView implements Serializable {

	private static final long serialVersionUID = 1L;
	private Parametro parametro;
	private HashMap<String, String> impressoras = new HashMap<String, String>();
	
	public String retorna() throws Exception
	{

		return "/pages/principal/principal.xhtml";
	}

	
	public ConfiguracaoView() {
		
	}
	
	public String configuracao() throws Exception {
		listaImpressoras();
		return "/pages/configuracao/config.xhtml";
	}
	
	public void listaImpressoras() throws Exception {
		List<String> lista = new ArrayList<String>();
		lista = Bematech.retornaImpressoras();
		for (String impr : lista) {
			impressoras.put(impr, impr);
		}
		// --- Recuperando a impressora padrão
		parametro = new Parametro();
		if(ContextApp.getSession().getAttribute("parametroSistema")!=null) {
			parametro = (Parametro)ContextApp.getSession().getAttribute("parametroSistema");
		}
	}

	public void salvar() throws Exception {
		
		// --- Colocando na sessão a impressora do Boleto
		ConfiguracaoManager configuracaoManager = new ConfiguracaoManager();
		configuracaoManager.salvar(parametro);
		// --- Atualizando variável de sessão
		ContextApp.getSession().setAttribute("parametroSistema", parametro);
		
		ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Configuração salva com sucesso!", null));
	}

	/*
	 * ************************************************************
	 * GET's and SET's
	 * ************************************************************
	*/

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public HashMap<String, String> getImpressoras() {
		return impressoras;
	}

	public void setImpressoras(HashMap<String, String> impressoras) {
		this.impressoras = impressoras;
	}
	
}
