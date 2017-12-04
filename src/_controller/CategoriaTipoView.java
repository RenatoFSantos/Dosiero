package _controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import _business.CategoriaTipoManager;
import _business.UsuarioManager;
import _model.vo.CategoriaTipo;
import exception.DaoException;
import util.ContextApp;

@ManagedBean(name="categoriaTipoView")
@RequestScoped
public class CategoriaTipoView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CategoriaTipo categoriaTipo ;
	private CategoriaTipo filtro ;
	private CategoriaTipo categoriaTipoSel = null;
	private List<CategoriaTipo> categoriaTipos;
	private CategoriaTipoManager categoriaTipoManager;
	private String modo = "";
	
	public CategoriaTipoView() throws Exception {
		categoriaTipoManager = new CategoriaTipoManager();
		categoriaTipo = new CategoriaTipo();
		filtro = new CategoriaTipo();
	}

	public String retorna() throws Exception
	{

		return "/pages/principal/principal.xhtml";
	}
	
	public String listaCategoriaTipos() throws Exception
	{
		CategoriaTipoManager categoriaTipoManager = new CategoriaTipoManager();
		categoriaTipos = categoriaTipoManager.listaCategoriaTipos();
		return "/pages/categoria_tipo/cadcategoriatipo.xhtml";
	}
	
	public List<CategoriaTipo> pesquisar() throws Exception
	{
		CategoriaTipoManager categoriaTipoManager = new CategoriaTipoManager();
		categoriaTipos = categoriaTipoManager.pesquisa(filtro);
		return categoriaTipos;
	}	

	public String inserir() throws Exception
	{
		categoriaTipo = new CategoriaTipo();
		this.modo="I";
		return "/pages/categoria_tipo/adicionarcategoriatipo.xhtml";
	}
	
	public String editar() throws Exception
	{
		categoriaTipo = categoriaTipoSel;
		this.modo="E";
		return "/pages/categoria_tipo/editarcategoriatipo.xhtml";
	}

	public String excluir() throws Exception
	{
	
    	/* 
    	 * **************************************
    	 * EXCLUINDO A CATEGORIA_TIPO SELECIONADA
    	 * **************************************
    	 */
		this.modo="E";
		CategoriaTipoManager categoriaTipoManager = new CategoriaTipoManager();
		categoriaTipoManager.deletar(categoriaTipoSel);
		ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", null));
		
		listaCategoriaTipos();
	
		return "/pages/categoria_tipo/cadcategoriatipo.xhtml";
	}
	
	public String salvar() throws Exception
	{
		try {
			if (validaForm()) {
				CategoriaTipoManager categoriaTipoManager = new CategoriaTipoManager();
				categoriaTipoManager.salvar(categoriaTipo);
				categoriaTipo = new CategoriaTipo();
				this.modo="I";
				ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new DaoException(e);
		}
		return "/pages/categoria_tipo/adicionarcategoriatipo.xhtml";
	}
	
	private boolean validaForm() throws Exception {
		boolean result = true;
		
		
		if(this.categoriaTipo!=null && modo.equals("I")) {
			CategoriaTipo obj = new CategoriaTipo();
			CategoriaTipoManager categoriaTipoManager = new CategoriaTipoManager();
			obj = categoriaTipoManager.validaCategoriaTipo(this.categoriaTipo);
			if(obj!=null) {
				ContextApp.getContext().addMessage("message_erro",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro já existente. Verifique!", null));
			}

		}
		
		if (ContextApp.getContext().getMessages().hasNext()) {
			result = false;
		}

		return result;
	}

	// --- GETs e SETs
	
	public CategoriaTipo getCategoriaTipo() {
		return categoriaTipo;
	}
	public void setCategoriaTipo(CategoriaTipo categoriaTipo) {
		this.categoriaTipo = categoriaTipo;
	}
	public CategoriaTipo getFiltro() {
		return filtro;
	}
	public void setFiltro(CategoriaTipo filtro) {
		this.filtro = filtro;
	}
	public CategoriaTipo getCategoriaTipoSel() {
		return categoriaTipoSel;
	}
	public void setCategoriaTipoSel(CategoriaTipo categoriaTipoSel) {
		this.categoriaTipoSel = categoriaTipoSel;
	}
	public List<CategoriaTipo> getCategoriaTipos() {
		return categoriaTipos;
	}
	public void setCategoriaTipos(List<CategoriaTipo> categoriaTipos) {
		this.categoriaTipos = categoriaTipos;
	}
	public String getModo() {
		return modo;
	}
	public void setModo(String modo) {
		this.modo = modo;
	}
}
