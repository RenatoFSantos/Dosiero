package _controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import _business.CategoriaManager;
import _business.UsuarioManager;
import _model.vo.Categoria;
import _model.vo.Usuario;
import exception.DaoException;
import util.ContextApp;

@ManagedBean(name="categoriaView")
@RequestScoped
public class CategoriaView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Categoria categoria ;
	private Categoria filtro ;
	private Categoria categoriaSel = null;
	private List<Categoria> categorias;
	private CategoriaManager categoriaManager;
	private UsuarioManager usuarioManager;
	
	public CategoriaView() throws Exception {
		categoriaManager = new CategoriaManager();
		categorias = categoriaManager.listaCategorias();
		categoria = new Categoria();
		filtro = new Categoria();
	}

	public String retorna() throws Exception
	{

		return "/pages/principal/principal.xhtml";
	}
	
	public String listaCategorias() throws Exception
	{
		CategoriaManager categoriaManager = new CategoriaManager();
		categorias = categoriaManager.listaCategorias();
		return "/pages/categoria/cadcategoria.xhtml";
	}
	
	public List<Categoria> pesquisar() throws Exception
	{
		CategoriaManager categoriaManager = new CategoriaManager();
		categorias = categoriaManager.pesquisa(filtro);
		return categorias;
	}	

	public String inserir() throws Exception
	{
		categoria = new Categoria();
		return "/pages/categoria/cadcategoriadetalhe.xhtml";
	}
	
	public String editar() throws Exception
	{
		categoria = categoriaSel;

		return "/pages/categoria/cadcategoriadetalhe.xhtml";
	}

	public String excluir() throws Exception
	{
	
		String mensagem = "";
		Integer cont=0;
    	/* 
    	 * **********************************
    	 * VERIFICANDO OS USUARIOS ASSOCIADOS
    	 * **********************************
    	 */
		usuarioManager = new UsuarioManager();
		List<Usuario> usuarioAssociados  = new ArrayList<Usuario>();
		usuarioAssociados = usuarioManager.listaUsuarioPorCategoria(categoriaSel);
		if (usuarioAssociados!=null && usuarioAssociados.size()>0) {
			mensagem="Existem usuários relacionadas a esta categoria. Desvincule antes de excluir!" ;
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
		} else {
	    	/* 
	    	 * *********************************
	    	 * EXCLUINDO A CATEGORIA SELECIONADA
	    	 * *********************************
	    	 */
			CategoriaManager categoriaManager = new CategoriaManager();
			categoriaManager.deletar(categoriaSel);
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", null));
		}
		
		listaCategorias();
	
		return "/pages/categoria/cadcategoria.xhtml";
	}
	
	public void salvar() throws Exception
	{
		try {
			if (validaForm()) {
			CategoriaManager categoriaManager = new CategoriaManager();
			categoriaManager.salvar(categoria);
			categoria = new Categoria();
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new DaoException(e);
		}
		
	}
	
	private boolean validaForm() throws Exception {
		boolean result = true;
		
		return result;
	}

	// --- GETs e SETs
	
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public Categoria getFiltro() {
		return filtro;
	}
	public void setFiltro(Categoria filtro) {
		this.filtro = filtro;
	}
	public Categoria getCategoriaSel() {
		return categoriaSel;
	}
	public void setCategoriaSel(Categoria categoriaSel) {
		this.categoriaSel = categoriaSel;
	}
	public List<Categoria> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
}
