package _dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.IPerfilDAO;
import _dao.util.HibernateUtil;
import _model.vo.Perfil;
import exception.DaoException;

public class PerfilDAO extends GenericDAOImpl<Perfil, Integer> implements IPerfilDAO {

	public PerfilDAO() 
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

	public List<Perfil> allPerfils() throws DaoException {
		List<Perfil> perfils = new ArrayList<Perfil>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Perfil res1 ");
			sql.append("where res1.perf_in_delecao = 0 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			perfils = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return perfils;
	}

	public List<Perfil> pesquisaPerfil(Perfil filtro) throws DaoException {
		List<Perfil> filtroperfils = new ArrayList<Perfil>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Perfil res1 ");
			sql.append("where upper(res1.perf_nm_perfil) like :nome ");
			sql.append("and res1.perf_in_delecao=0 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("nome", "%" + filtro.getPerf_nm_perfil().toUpperCase() + "%");
			filtroperfils = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtroperfils;
	}

}
