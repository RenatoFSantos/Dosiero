package _dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.IOcorrenciaDAO;
import _dao.util.HibernateUtil;
import _model.vo.Acervo;
import _model.vo.Ocorrencia;
import _model.vo.Usuario;
import exception.DaoException;

public class OcorrenciaDAO extends GenericDAOImpl<Ocorrencia, Integer> implements IOcorrenciaDAO {

	public OcorrenciaDAO() 
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

	public List<Ocorrencia> allOcorrencias() throws DaoException {
		List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Ocorrencia res1 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			ocorrencias = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return ocorrencias;
	}

	public List<Ocorrencia> pesquisaOcorrencia(Ocorrencia filtro) throws DaoException {
		
		List<Ocorrencia> filtroocorrencias = new ArrayList<Ocorrencia>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Ocorrencia res1 ");
			sql.append("where upper(res1.ocor_tx_ocorrencia) like :nome ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("nome", "%" + filtro.getOcor_tx_ocorrencia().toUpperCase() + "%");
			filtroocorrencias = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtroocorrencias;
	}

	public List<Ocorrencia> pesquisaOcorrenciaPorUsuario(Usuario filtro) throws DaoException {
		
		List<Ocorrencia> filtroocorrencias = new ArrayList<Ocorrencia>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Ocorrencia res1 ");
			sql.append("where res1.objUsuarioInclusao.id = :cod ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("cod", filtro.getId());
			filtroocorrencias = findMany(query);
			
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtroocorrencias;
	}

	public List<Ocorrencia> pesquisaOcorrenciaPorAcervo(Acervo filtro) throws DaoException {
		
		List<Ocorrencia> filtroocorrencias = new ArrayList<Ocorrencia>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Ocorrencia res1 ");
			sql.append("where res1.objAcervo.id = :cod ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("cod", filtro.getId());
			filtroocorrencias = findMany(query);
			
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtroocorrencias;
	}

}
