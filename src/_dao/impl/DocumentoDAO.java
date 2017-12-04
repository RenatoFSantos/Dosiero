package _dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.IDocumentoDAO;
import _dao.util.HibernateUtil;
import _model.vo.Acervo;
import _model.vo.Documento;
import _model.vo.Tipo;
import exception.DaoException;

public class DocumentoDAO extends GenericDAOImpl<Documento, Integer> implements IDocumentoDAO {

	public DocumentoDAO() 
	{
		try
		{
			HibernateUtil.getInstance();
		}
		catch (DaoException e) 
		{
			// Colocar o log para funcionar depois
		}
	}

	public List<Documento> allDocumentos() throws DaoException {
		List<Documento> paginas = new ArrayList<Documento>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Documento res1 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			paginas = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return paginas;
	}

	public List<Documento> allDocumentosAcervo(Acervo filtro) throws DaoException {
		List<Documento> paginas = new ArrayList<Documento>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Documento as res1 ");
			sql.append("where objAcervo.id = :codfiltro ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro.getId());
			paginas = findMany(query);
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return paginas;
	}
	
	public List<Documento> pesquisaDocumento(Documento filtro) throws DaoException {
		List<Documento> filtropaginas = new ArrayList<Documento>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Documento res1 ");
			sql.append("where upper(res1.docu_nm_documento) like :nome ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("nome", "%" + filtro.getDocu_nm_documento().toUpperCase() + "%");
			filtropaginas = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtropaginas;
	}

	public List<Documento> allDocumentosPorTipo(Tipo filtro) throws DaoException {
		List<Documento> paginas = new ArrayList<Documento>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Documento as res1 ");
			sql.append("where objTipo.id = :codfiltro ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro.getId());
			paginas = findMany(query);
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return paginas;
	}

	public Integer ultimoID() throws DaoException {
		Integer maxCod = 0;

		try {
			maxCod = (Integer) HibernateUtil.getSession().createQuery("select max(id) from Documento").uniqueResult();
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return maxCod;
	}

	public Documento pesquisaDocumentoPorCodBarra(String filtro) throws DaoException {
		Documento result = new Documento();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Documento as res1 ");
			sql.append("where docu_nr_codbarras = :codfiltro ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro);
			result = findOne(query);
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return result;

	}
}
