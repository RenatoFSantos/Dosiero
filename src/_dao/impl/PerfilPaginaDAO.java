package _dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.IPerfilPaginaDAO;
import _dao.util.HibernateUtil;
import _model.vo.Perfil;
import _model.vo.PerfilPagina;
import exception.DaoException;

public class PerfilPaginaDAO extends GenericDAOImpl<PerfilPagina, Integer> implements IPerfilPaginaDAO {

	public PerfilPaginaDAO() 
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

	public List<PerfilPagina> allPerfilPaginas() throws DaoException {
		List<PerfilPagina> clientemodulos = new ArrayList<PerfilPagina>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from PerfilPagina res1 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			clientemodulos = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return clientemodulos;
	}

	public List<PerfilPagina> pesquisaPerfilPaginaPorPerfil(Perfil filtro) throws DaoException {
		List<PerfilPagina> filtropaginas = new ArrayList<PerfilPagina>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from PerfilPagina res1 ");
			sql.append("where res1.objPerfil.id = :codfiltro ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro.getId());
			filtropaginas = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtropaginas;
	}

	public boolean excluiPaginasPorPerfil(Perfil filtro) throws DaoException {
		boolean res = true;

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("delete from PerfilPagina res1 ");
			sql.append("where res1.objPerfil.id = :codfiltro ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro.getId());
			query.executeUpdate();

		} catch (Exception e) {
			res=false;
			throw new DaoException(e);
		}

		return res;
	}
	

	public List<PerfilPagina> pesquisaPaginaPorPerfil(Perfil filtro) throws DaoException {
		List<PerfilPagina> filtropaginas = new ArrayList<PerfilPagina>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from PerfilPagina res1 ");
			sql.append("where res1.objPerfil.id = :codfiltro ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro.getId());
			filtropaginas = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtropaginas;
	}
}
