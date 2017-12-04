package _dao.interfaces;

import java.util.List;

import _model.vo.Acervo;
import _model.vo.Documento;
import _model.vo.Tipo;
import exception.DaoException;

public interface IDocumentoDAO extends IGenericDAO<Documento, Integer> 
{
	List<Documento> allDocumentos() throws DaoException;

	List<Documento> allDocumentosAcervo(Acervo filtro) throws DaoException;

	List<Documento> pesquisaDocumento(Documento filtro) throws DaoException;

	List<Documento> allDocumentosPorTipo(Tipo filtro) throws DaoException;

	Integer ultimoID() throws DaoException;
	
	Documento pesquisaDocumentoPorCodBarra(String filtro) throws DaoException;

}
