package _controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import _business.CommonsManager;
import _business.ModuloManager;
import _business.PaginaManager;
import _model.vo.Modulo;
import _model.vo.Pagina;
import _model.vo.PerfilPagina;
import exception.DaoException;
import util.ContextApp;


@ManagedBean(name="paginaView")
@RequestScoped
public class PaginaView implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2511238918417337771L;
	private Pagina pagina ;
	private Pagina filtro ;
	private Pagina paginaSel = null;
	private Modulo moduloSel = null;
	private List<Pagina> paginas;
	private CommonsManager commonsManager;
	private ModuloManager moduloManager;
	private List<Modulo> modulos;
	private Modulo filtroModulo;
	
	
	
	public PaginaView() throws Exception 
	{
	
		commonsManager = new CommonsManager();
		moduloManager = new ModuloManager();
		filtroModulo = new Modulo();
		modulos = moduloManager.listaModulos();
		pagina = new Pagina();
		filtro = new Pagina();
		moduloSel = new Modulo();
		
	}

	public String retorna() throws Exception
	{

		return "/pages/principal/principal.xhtml";
	}
	
	public String listaPaginas() throws Exception
	{
		PaginaManager paginaManager = new PaginaManager();

		paginas = paginaManager.listaPaginas();

		return "/pages/pagina/cadpagina.xhtml";
	}
	
	public List<Pagina> pesquisar() throws Exception
	{
		PaginaManager paginaManager = new PaginaManager();

		paginas = paginaManager.pesquisa(filtro);

		return paginas;
	}	

	public List<Modulo> pesquisarModulos() throws Exception
	{
		ModuloManager moduloManager = new ModuloManager();
		modulos = moduloManager.pesquisa(filtroModulo);
		return modulos;
	}

	public String inserir() throws Exception
	{
		pagina = new Pagina();
		return "/pages/pagina/cadpaginadetalhe.xhtml";
	}
	
	public String editar() throws Exception
	{
		pagina = paginaSel;

		return "/pages/pagina/cadpaginadetalhe.xhtml";
	}

	public String excluir() throws Exception
	{
		String mensagem = "";
		Integer cont=0;
    	/* 
    	 * **********************************
    	 * VERIFICANDO OS PERFIS ASSOCIADOS
    	 * **********************************
    	 */
		
		if (paginaSel.getPerfilPaginas()!=null && paginaSel.getPerfilPaginas().size()>0) {
			mensagem="Existem perfis relacionadas a esta página. Desvincule antes de excluir! Veja alguns dos Perfis: ";
			cont=0;
			for (PerfilPagina perfilPagina : paginaSel.getPerfilPaginas()) {
				 mensagem+=perfilPagina.getObjPerfil().getPerf_nm_perfil() + ", ";
				 cont++;
				 if(cont>9) break;
			}
			mensagem = mensagem.substring(0, mensagem.length()-2) + ".";
			
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
		} else {
	    	/* 
	    	 * **********************************
	    	 * EXCLUINDO A PÁGINA SELECIONADA
	    	 * **********************************
	    	 */
			paginaSel.setPagi_in_delecao(1); // --- MARCANDO REGISTRO COMO EXCLUIDO
			PaginaManager paginaManager = new PaginaManager();
			paginaManager.salvar(paginaSel);
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", null));
		}
		listaPaginas();

		return "/pages/pagina/cadpagina.xhtml";
	}
	
	public String salvar() throws Exception
	{
		
		try {
			if (validaForm()) {
			PaginaManager paginaManager = new PaginaManager();
			paginaManager.salvar(pagina);
			pagina = new Pagina();
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new DaoException(e);
		}
		
		return null;
		
	}
	
	public void selecionarModulo() throws Exception
	{
		pagina.setObjModulo(moduloSel);
	}
	
	private boolean validaForm() throws Exception {
		boolean result = true;
		
		if(pagina.getObjModulo().getModu_nm_modulo()==null) {
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Módulo inexistente. Favor selecione um módulo válido!", null));
		}
		
		if (ContextApp.getContext().getMessages().hasNext())
		{
			result = false;
		}
		
		return result;
	}

	public Pagina getPagina() {
		return pagina;
	}

	public void setPagina(Pagina pagina) {
		this.pagina = pagina;
	}

	public List<Pagina> getPaginas() {
		return paginas;
	}

	public void setPaginas(List<Pagina> paginas) {
		this.paginas = paginas;
	}

	public Pagina getFiltro() {
		return filtro;
	}

	public void setFiltro(Pagina filtro) {
		this.filtro = filtro;
	}

	public CommonsManager getCommonsManager() {
		return commonsManager;
	}

	public Pagina getPaginaSel() {
		return paginaSel;
	}

	public void setPaginaSel(Pagina paginaSel) {
		this.paginaSel = paginaSel;
	}
	
	public Modulo getModuloSel() {
		return moduloSel;
	}

	public void setModuloSel(Modulo moduloSel) {
		this.moduloSel = moduloSel;
	}

	public List<Modulo> getModulos() {
		return modulos;
	}

	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}

	public Modulo getFiltroModulo() {
		return filtroModulo;
	}

	public void setFiltroModulo(Modulo filtroModulo) {
		this.filtroModulo = filtroModulo;
	}
	
	
}
