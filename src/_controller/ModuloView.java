package _controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import _business.CommonsManager;
import _business.ModuloManager;
import _business.PaginaManager;
import _model.vo.Modulo;
import _model.vo.Pagina;
import exception.ControllerException;
import exception.DaoException;
import util.ContextApp;

@ManagedBean(name="moduloView")
@RequestScoped
public class ModuloView implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1482441658383091400L;
	private Modulo modulo ;
	private Modulo filtro ;
	private Modulo moduloSel = null;
	private List<Modulo> modulos;
	private CommonsManager commonsManager;
	private List<Pagina> selecionados = new ArrayList<Pagina>();
	
	public ModuloView() throws Exception {
		commonsManager = new CommonsManager();
		
		modulo = new Modulo();
		filtro = new Modulo();
	}

	public String retorna() throws Exception
	{

		return "/pages/principal/principal.xhtml";
	}
	
	public String listaModulos() throws Exception
	{
		ModuloManager moduloManager = new ModuloManager();
		modulos = moduloManager.listaModulos();
		return "/pages/modulo/cadmodulo.xhtml";
	}
	
	public List<Modulo> pesquisar() throws Exception
	{
		ModuloManager moduloManager = new ModuloManager();
		modulos = moduloManager.pesquisa(filtro);
		return modulos;
	}	

	public String inserir() throws Exception
	{
		modulo = new Modulo();
		return "/pages/modulo/cadmodulodetalhe.xhtml";
	}
	
	public String editar() throws Exception
	{
		modulo = moduloSel;
		buscaPaginas();
		return "/pages/modulo/cadmodulodetalhe.xhtml";
	}

	public String excluir() throws Exception
	{
	
		String mensagem = "";
		Integer cont=0;
    	/* 
    	 * **********************************
    	 * VERIFICANDO OS PÁGINAS ASSOCIADAS
    	 * **********************************
    	 */
		if (moduloSel.getPaginas()!=null && moduloSel.getPaginas().size()>0) {
			mensagem="Existem páginas relacionadas a este módulo. Desvincule antes de excluir! Veja algumas das Páginas: ";
			cont=0;
			for (Pagina pagina : moduloSel.getPaginas()) {
				 mensagem+=pagina.getPagi_nm_pagina() + ", ";
				 cont++;
				 if(cont>9) break;
			}
			mensagem = mensagem.substring(0, mensagem.length()-2) + ".";
			
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
		} else {
	    	/* 
	    	 * **********************************
	    	 * EXCLUINDO MÓDULO SELECIONADO
	    	 * **********************************
	    	 */
			moduloSel.setModu_in_delecao(1);
			ModuloManager moduloManager = new ModuloManager();
			moduloManager.salvar(moduloSel);
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", null));
		}
	
		listaModulos();
	
		return "/pages/modulo/cadmodulo.xhtml";
	}
	
	private List<Pagina> buscaPaginas() {
		PaginaManager paginaManager = new PaginaManager();
		try {
			selecionados = paginaManager.pesquisaPorModulo(this.modulo);
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return selecionados;
	}	

	public void salvar() throws Exception
	{
		try {
			if (validaForm()) {
				ModuloManager moduloManager = new ModuloManager();
				moduloManager.salvar(modulo);
				modulo = new Modulo();
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

	
	// --- GETs e SETs
	
	public Modulo getModulo() {
		return modulo;
	}
	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}
	public Modulo getFiltro() {
		return filtro;
	}
	public void setFiltro(Modulo filtro) {
		this.filtro = filtro;
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
	public CommonsManager getCommonsManager() {
		return commonsManager;
	}

	public List<Pagina> getSelecionados() {
		return selecionados;
	}

	public void setSelecionados(List<Pagina> selecionados) {
		this.selecionados = selecionados;
	}

}
