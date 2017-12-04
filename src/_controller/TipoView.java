package _controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import _business.AcervoManager;
import _business.CategoriaTipoManager;
import _business.TipoManager;
import _model.vo.Acervo;
import _model.vo.CategoriaTipo;
import _model.vo.Tipo;
import exception.DaoException;
import util.ContextApp;

@ManagedBean(name="tipoView")
@RequestScoped
public class TipoView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Tipo tipo ;
	private Tipo filtro ;
	private Tipo tipoSel = null;
	private List<Tipo> tipos;
	private TipoManager tipoManager;
	private AcervoManager acervoManager;
	private CategoriaTipoManager categoriaTipoManager;
	
	public TipoView() throws Exception {
		tipoManager = new TipoManager();
		tipos = tipoManager.listaTipos();		
		tipo = new Tipo();
		filtro = new Tipo();
	}

	public String retorna() throws Exception
	{

		return "/pages/principal/principal.xhtml";
	}
	
	public String listaTipos() throws Exception
	{
		tipoManager = new TipoManager();
		tipos = tipoManager.listaTipos();
		return "/pages/tipo/cadtipo.xhtml";
	}
	
	public List<Tipo> pesquisar() throws Exception
	{
		TipoManager tipoManager = new TipoManager();
		tipos = tipoManager.pesquisa(filtro);
		return tipos;
	}	

	public String inserir() throws Exception
	{
		tipo = new Tipo();
		return "/pages/tipo/cadtipodetalhe.xhtml";
	}
	
	public String editar() throws Exception
	{
		tipo = tipoSel;

		return "/pages/tipo/cadtipodetalhe.xhtml";
	}

	public String excluir() throws Exception
	{
	
		String mensagem = "";
		Integer cont=0;
    	/* 
    	 * ************************************
    	 * VERIFICANDO OS ACERVOS ASSOCIADOS
    	 * ************************************
    	 */
		
		acervoManager = new AcervoManager();
		List<Acervo> acervoAssociados  = new ArrayList<Acervo>();
		acervoAssociados = acervoManager.listaAcervosPorTipo(tipoSel);
		if (acervoAssociados!=null && acervoAssociados.size()>0) {
			mensagem="Existem acervos relacionadas a esta tipo. Desvincule antes de excluir!" ;
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
		} else {
			categoriaTipoManager = new CategoriaTipoManager();
			List<CategoriaTipo> categoriaTipos = new ArrayList<CategoriaTipo>();
			categoriaTipos = categoriaTipoManager.pesquisaPorTipo(tipoSel);
			if (categoriaTipos!=null && categoriaTipos.size()>0) {
				mensagem="Existem parâmetros de empréstimo vinculados a este tipo. Desvincule antes de excluir!" ;
				ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
			} else {
		    	/* 
		    	 * *********************************
		    	 * EXCLUINDO O TIPO SELECIONADO
		    	 * *********************************
		    	 */
				TipoManager tipoManager = new TipoManager();
				tipoManager.deletar(tipoSel);
				ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", null));
			}
		}
		
		listaTipos();
	
		return "/pages/tipo/cadtipo.xhtml";
	}
	
	public void salvar() throws Exception
	{
		try {
			if (validaForm()) {
			TipoManager tipoManager = new TipoManager();
			tipoManager.salvar(tipo);
			tipo = new Tipo();
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
	
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public Tipo getFiltro() {
		return filtro;
	}
	public void setFiltro(Tipo filtro) {
		this.filtro = filtro;
	}
	public Tipo getTipoSel() {
		return tipoSel;
	}
	public void setTipoSel(Tipo tipoSel) {
		this.tipoSel = tipoSel;
	}
	public List<Tipo> getTipos() {
		return tipos;
	}
	public void setTipos(List<Tipo> tipos) {
		this.tipos = tipos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((tipoSel == null) ? 0 : tipoSel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoView other = (TipoView) obj;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (tipoSel == null) {
			if (other.tipoSel != null)
				return false;
		} else if (!tipoSel.equals(other.tipoSel))
			return false;
		return true;
	}
	
	
	
}
