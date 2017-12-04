package _dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.ICategoriaTipoDAO;
import _dao.util.HibernateUtil;
import _model.vo.Categoria;
import _model.vo.CategoriaTipo;
import _model.vo.Tipo;
import exception.DaoException;

public class CategoriaTipoDAO extends GenericDAOImpl<CategoriaTipo, Integer> implements ICategoriaTipoDAO {

	public CategoriaTipoDAO() 
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

	public List<CategoriaTipo> allCategoriaTipos() throws DaoException {
		List<CategoriaTipo> clientemodulos = new ArrayList<CategoriaTipo>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from CategoriaTipo res1 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			clientemodulos = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return clientemodulos;
	}

	public List<CategoriaTipo> pesquisaCategoriaTipoPorCategoria(Categoria filtro) throws DaoException {
		List<CategoriaTipo> lista = new ArrayList<CategoriaTipo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from CategoriaTipo res1 ");
			sql.append("where res1.objCategoria.id = :codfiltro ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro.getId());
			lista = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}
	
	public List<CategoriaTipo> pesquisaCategoriaTipoPorTipo(Tipo filtro) throws DaoException {
		List<CategoriaTipo> lista = new ArrayList<CategoriaTipo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from CategoriaTipo res1 ");
			sql.append("where res1.objTipo.id = :codfiltro ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro.getId());
			lista = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}
	
	public List<CategoriaTipo> pesquisaCategoriaTipo(CategoriaTipo filtro) throws DaoException {
		List<CategoriaTipo> lista = new ArrayList<CategoriaTipo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from CategoriaTipo res1 ");
			sql.append("where res1.id = :codfiltro ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro.getId());
			lista = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}

	public CategoriaTipo validaCategoriaTipo(CategoriaTipo filtro) throws DaoException {
		CategoriaTipo obj = new CategoriaTipo();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from CategoriaTipo res1 ");
			sql.append("where res1.objCategoria.id = :codfiltro_1 ");
			sql.append("and res1.objTipo.id = :codfiltro_2 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro_1", filtro.getObjCategoria().getId());
			query.setParameter("codfiltro_2", filtro.getObjTipo().getId());
			obj = findOne(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return obj;
	}

}
