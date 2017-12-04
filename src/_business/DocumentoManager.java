package _business;

import java.util.ArrayList;
import java.util.List;

import _dao.impl.DocumentoDAO;
import _dao.interfaces.IDocumentoDAO;
import _dao.util.HibernateUtil;
import _model.vo.Acervo;
import _model.vo.Documento;
import _model.vo.Tipo;
import exception.ControllerException;
import exception.DaoException;

public class DocumentoManager {

	private IDocumentoDAO documentoDAO = new DocumentoDAO();

	public DocumentoManager() {
	}
	
	public void salvar(Documento documento) throws ControllerException {
		HibernateUtil.getSession();
		try {
			HibernateUtil.beginTransaction();
			documentoDAO.save(documento);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void deletar(Documento documento) throws ControllerException
	{
		HibernateUtil.getSession();

		try
		{
			HibernateUtil.beginTransaction();
			documentoDAO.delete(documento);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public List<Documento> listaDocumentos() throws ControllerException {
		HibernateUtil.getSession();

		List<Documento> documentos = new ArrayList<Documento>();

		try
		{
		// --- ROTINA PARA BUSCAR TODOS OS REGISTROS DO OBJETO
			documentos = documentoDAO.allDocumentos();
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
			HibernateUtil.closeSession();
		}
		
		return documentos;
	}

	public List<Documento> listaDocumentosAcervo(Acervo filtro) throws ControllerException {
		HibernateUtil.getSession();

		List<Documento> documentos = new ArrayList<Documento>();

		try
		{
		// --- ROTINA PARA BUSCAR TODOS DOCUMENTOS RELACIONADOS AO ACERVO
			documentos = documentoDAO.allDocumentosAcervo(filtro);
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
 			HibernateUtil.closeSession();
		}
		
		return documentos;
	}

	public List<Documento> pesquisa(Documento filtro) throws ControllerException {
		HibernateUtil.getSession();

		List<Documento> documentos = new ArrayList<Documento>();

		try
		{
		// --- ROTINA PARA BUSCAR UM OBJETO ESPECÍFICO
			documentos = documentoDAO.pesquisaDocumento(filtro);
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
			HibernateUtil.closeSession();
		}
		
		return documentos;
	}

	public List<Documento> listaDocumentosPorTipo(Tipo filtro) throws ControllerException {
		HibernateUtil.getSession();

		List<Documento> documentos = new ArrayList<Documento>();

		try
		{
		// --- ROTINA PARA BUSCAR TODOS DOCUMENTOS RELACIONADOS AO TIPO
			documentos = documentoDAO.allDocumentosPorTipo(filtro);
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
 			HibernateUtil.closeSession();
		}
		
		return documentos;
	}

	public Integer proximoID() throws ControllerException {
		HibernateUtil.getSession();

		Integer proxDoc = 0;

		try
		{
		// --- ROTINA PARA BUSCAR O ÚLTIMO NÚMERO DE DOCUMENTO CADASTRADO
			proxDoc = documentoDAO.ultimoID();
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
 			HibernateUtil.closeSession();
		}
		
		return proxDoc;
	}
	
	public Documento pesquisaDocumentoPorCodBarra(String filtro) throws ControllerException {
		HibernateUtil.getSession();

		Documento documento = new Documento();

		try
		{
		// --- ROTINA PARA BUSCAR TODOS DOCUMENTOS RELACIONADOS AO TIPO
			documento = documentoDAO.pesquisaDocumentoPorCodBarra(filtro);
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
 			HibernateUtil.closeSession();
		}
		
		return documento;
	}
	

}
