package _controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import _business.CommonsManager;
import _business.UnidadeManager;
import _model.vo.Acervo;
import _model.vo.Unidade;
import _model.vo.Usuario;
import exception.DaoException;
import util.ContextApp;


@ManagedBean(name="unidadeView")
@RequestScoped
public class UnidadeView implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6804055097177586988L;
	private Unidade unidade ;
	private Unidade filtro ;
	private Unidade unidadeSel = null;
	private List<Unidade> unidades;
	private CommonsManager commonsManager;
	


	public UnidadeView() throws Exception 
	{
	
		commonsManager = new CommonsManager();
		
		unidade = new Unidade();
		filtro = new Unidade();
		
	}

	public String retorna() throws Exception
	{

		return "/pages/principal/principal.xhtml";
	}

	public String listaUnidades() throws Exception
	{
		UnidadeManager unidadeManager = new UnidadeManager();
		unidades = unidadeManager.listaUnidades();
		return "/pages/unidade/cadunidade.xhtml";
	}
	
	public List<Unidade> pesquisar() throws Exception
	{
		UnidadeManager unidadeManager = new UnidadeManager();
		unidades = unidadeManager.pesquisa(filtro);
		return unidades;
	}	

	public String inserir() throws Exception
	{
		unidade = new Unidade();
		return "/pages/unidade/cadunidadedetalhe.xhtml";
	}
	
	public String editar() throws Exception
	{
		unidade = unidadeSel;

		return "/pages/unidade/cadunidadedetalhe.xhtml";
	}

	public String excluir() throws Exception
	{
	
		String mensagem = "";
		Integer cont=0;
    	/* 
    	 * **********************************
    	 * VERIFICANDO OS ACERVOS ASSOCIADOS
    	 * **********************************
    	 */
		if (unidadeSel.getAcervos()!=null && unidadeSel.getAcervos().size()>0) {
			mensagem="Existem acervos relacionadas a esta unidade. Desvincule antes de excluir! Veja alguns dos Acervos: ";
			cont=0;
			for (Acervo acervo : unidadeSel.getAcervos()) {
				 mensagem+=acervo.getId() + ", ";
				 cont++;
				 if(cont>9) break;
			}
			mensagem = mensagem.substring(0, mensagem.length()-2) + ".";
			
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
		} else {
	    	/* 
	    	 * **********************************
	    	 * VERIFICANDO OS USUÁRIOS ASSOCIADOS
	    	 * **********************************
	    	 */
			if (unidadeSel.getUsuarios()!=null && unidadeSel.getUsuarios().size()>0) {
				mensagem="Existem usuários relacionadas a esta unidade. Desvincule antes de excluir! Veja alguns dos Usuários: ";
				cont=0;
				for (Usuario usuario : unidadeSel.getUsuarios()) {
					 mensagem+=usuario.getUsua_nm_usuario() + ", ";
					 cont++;
					 if(cont>9) break;
				}
				mensagem = mensagem.substring(0, mensagem.length()-2) + ".";
				
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
			} else {
		    	/* 
		    	 * **********************************
		    	 * EXCLUINDO UNIDADE SELECIONADA
		    	 * **********************************
		    	 */
				unidadeSel.setUnid_in_delecao(1);
				
				UnidadeManager unidadeManager = new UnidadeManager();
				unidadeManager.salvar(unidadeSel);
				ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", null));
			}
		}
	
		listaUnidades();
	
		return "/pages/unidade/cadunidade.xhtml";
	}
	
	public void salvar() throws Exception
	{
		try {
			if (validaForm()) {
			UnidadeManager unidadeManager = new UnidadeManager();
			unidade.setUnid_sg_sigla(unidade.getUnid_sg_sigla().toUpperCase());
			unidadeManager.salvar(unidade);
			unidade = new Unidade();
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new DaoException(e);
		}
		
	}
	
	private boolean validaForm() throws Exception {
		boolean result = true;
		
		// --- CONVERTE SIGLA EM MAIÚSCULAS
		
		return result;
	}

	
	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

	public Unidade getFiltro() {
		return filtro;
	}

	public void setFiltro(Unidade filtro) {
		this.filtro = filtro;
	}

	public CommonsManager getCommonsManager() {
		return commonsManager;
	}

	public Unidade getUnidadeSel() {
		return unidadeSel;
	}

	public void setUnidadeSel(Unidade unidadeSel) {
		this.unidadeSel = unidadeSel;
	}
	

}
