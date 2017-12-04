package _controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import _business.AcervoManager;
import _business.CommonsManager;
import _business.DescritorManager;
import _model.vo.Acervo;
import _model.vo.AcervoDescritor;
import _model.vo.Descritor;
import exception.DaoException;
import util.ContextApp;

@ManagedBean(name="descritorView")
@RequestScoped
public class DescritorView implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1482441658383091400L;
	private Descritor descritor ;
	private Descritor filtro ;
	private Descritor descritorSel = null;
	private List<Descritor> descritors;
	private CommonsManager commonsManager;
	
	public DescritorView() throws Exception {
		commonsManager = new CommonsManager();
		
		descritor = new Descritor();
		filtro = new Descritor();
	}

	public String retorna() throws Exception
	{

		return "/pages/principal/principal.xhtml";
	}
	
	public String listaDescritors() throws Exception
	{
		DescritorManager descritorManager = new DescritorManager();
		descritors = descritorManager.listaDescritors();
		return "/pages/descritor/caddescritor.xhtml";
	}
	
	public List<Descritor> pesquisar() throws Exception
	{
		DescritorManager descritorManager = new DescritorManager();
		descritors = descritorManager.pesquisa(filtro);
		return descritors;
	}	

	public String inserir() throws Exception
	{
		descritor = new Descritor();
		return "/pages/descritor/caddescritordetalhe.xhtml";
	}
	
	public String editar() throws Exception
	{
		descritor = descritorSel;

		return "/pages/descritor/caddescritordetalhe.xhtml";
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
		AcervoManager acervoManager = new AcervoManager();
		List<Acervo> acervoAssociados  = new ArrayList<Acervo>();
		acervoAssociados = acervoManager.listaAcervoPorDescritor(descritorSel);
		if (acervoAssociados!=null && acervoAssociados.size()>0) {
			mensagem="Existem acervos relacionadas a este descritor. Desvincule antes de excluir!" ;
			ContextApp.getContext().addMessage("message_erro", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
		} else {
	    	/* 
	    	 * *********************************
	    	 * EXCLUINDO O DESCRITOR SELECIONADO
	    	 * *********************************
	    	 */
			DescritorManager descritorManager = new DescritorManager();
			descritorManager.deletar(descritorSel);
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", null));
		}
		
		listaDescritors();
	
		return "/pages/descritor/caddescritor.xhtml";
	}
	
	public void salvar() throws Exception
	{
		try {
			if (validaForm()) {
			DescritorManager descritorManager = new DescritorManager();
			descritor.setDesc_sg_descritor(descritor.getDesc_sg_descritor().toUpperCase());
			descritorManager.salvar(descritor);
			descritor = new Descritor();
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
	
	public Descritor getDescritor() {
		return descritor;
	}
	public void setDescritor(Descritor descritor) {
		this.descritor = descritor;
	}
	public Descritor getFiltro() {
		return filtro;
	}
	public void setFiltro(Descritor filtro) {
		this.filtro = filtro;
	}
	public Descritor getDescritorSel() {
		return descritorSel;
	}
	public void setDescritorSel(Descritor descritorSel) {
		this.descritorSel = descritorSel;
	}
	public List<Descritor> getDescritors() {
		return descritors;
	}
	public void setDescritors(List<Descritor> descritors) {
		this.descritors = descritors;
	}
	public CommonsManager getCommonsManager() {
		return commonsManager;
	}

	
	
	
}
