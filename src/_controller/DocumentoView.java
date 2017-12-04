package _controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import _business.CommonsManager;
import _business.DocumentoManager;
import _model.vo.Acervo;
import _model.vo.Documento;
import _model.vo.Tipo;
import _model.vo.Usuario;
import exception.DaoException;
import util.ContextApp;


@ManagedBean(name="documentoView")
@ViewScoped
public class DocumentoView implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9033941256961069020L;
	private Documento documento ;
	private Documento filtroDocumento;
	private Documento documentoSel = null;
	private List<Documento> documentos;
	private CommonsManager commonsManager;
	private String fmtEtq;
	private String fmtRlt;
	private Tipo tipoSel;
	
	public DocumentoView() throws Exception 
	{
		commonsManager = new CommonsManager();
		documento = new Documento();
		filtroDocumento = new Documento();
		tipoSel = new Tipo();
	}

	public String retorna() throws Exception
	{

		return "/pages/principal/principal.xhtml";
	}
	

	public boolean validaFiltroRelatorio() {
		return true;
	}
	
	public String geraNovoCodBarras(Integer proxID) {
		String result = "";
		Usuario usuariologado = (Usuario)ContextApp.getSession().getAttribute("usuariologado");
		String codBrasil = "789";
		String codCliente = String.format("%03d", usuariologado.getObjCliente().getId());
		String codDocumento = String.format("%06d", proxID);
		result =  codBrasil+codCliente+codDocumento;
		return result;
	}

	public void selecionarDocumento() {
		documento=documentoSel;
	}
	
	public void listaDocumentos() throws Exception
	{
		DocumentoManager documentoManager = new DocumentoManager();
		documentos = documentoManager.listaDocumentos();
		RequestContext.getCurrentInstance().execute("PF('popUpCodBarrasDocumento').show()");
	}

	public String listaDocumentosAcervo(Acervo filtro) throws Exception
	{
		DocumentoManager documentoManager = new DocumentoManager();

		documentos = documentoManager.listaDocumentosAcervo(filtro);

		return "/pages/documento/caddocumento.xhtml";
	}
	
	public List<Documento> pesquisar() throws Exception
	{
		DocumentoManager documentoManager = new DocumentoManager();

		documentos = documentoManager.pesquisa(filtroDocumento);

		return documentos;
	}


	public String inserir() throws Exception
	{
		documento = new Documento();
		return "/pages/documento/caddocumentodetalhe.xhtml";
	}
	
	public String editar() throws Exception
	{
		documento = documentoSel;

		return "/pages/documento/caddocumentodetalhe.xhtml";
	}

	public String excluir() throws Exception
	{
    	/* 
    	 * **********************************
    	 * EXCLUINDO DOCUMENTO SELECIONADO
    	 * **********************************
    	 */
		DocumentoManager documentoManager = new DocumentoManager();
		documentoManager.deletar(documentoSel);
		ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro excluído com sucesso!", null));

		listaDocumentosAcervo(documentoSel.getObjAcervo());

		return "/pages/documento/caddocumento.xhtml";
	}
	
	public String salvar() throws Exception
	{
		
		try {
			if (validaForm()) {
			DocumentoManager documentoManager = new DocumentoManager();
			// --- Gerando novo código de barras do documento
			Integer proxId = documentoManager.proximoID() + 1;
			documento.setDocu_nr_codbarras(geraNovoCodBarras(proxId));
			// _______________________________________________________
			documentoManager.salvar(documento);
			documento = new Documento();
			ContextApp.getContext().addMessage("message_info", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro salvo com sucesso!", null));
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new DaoException(e);
		}
		
		return null;
		
	}
	
	private boolean validaForm() throws Exception {
		boolean result = true;
		
		return result;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public List<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}

	public Documento getFiltroDocumento() {
		return filtroDocumento;
	}

	public void setFiltroDocumento(Documento filtroDocumento) {
		this.filtroDocumento = filtroDocumento;
	}

	public CommonsManager getCommonsManager() {
		return commonsManager;
	}

	public Documento getDocumentoSel() {
		return documentoSel;
	}

	public void setDocumentoSel(Documento documentoSel) {
		this.documentoSel = documentoSel;
	}

	public String getFmtEtq() {
		return fmtEtq;
	}

	public void setFmtEtq(String fmtEtq) {
		this.fmtEtq = fmtEtq;
	}

	public String getFmtRlt() {
		return fmtRlt;
	}

	public void setFmtRlt(String fmtRlt) {
		this.fmtRlt = fmtRlt;
	}

	public Tipo getTipoSel() {
		return tipoSel;
	}

	public void setTipoSel(Tipo tipoSel) {
		this.tipoSel = tipoSel;
	}
	
	
	
}
