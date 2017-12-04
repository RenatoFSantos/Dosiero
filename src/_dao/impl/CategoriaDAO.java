package _dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.ICategoriaDAO;
import _dao.util.HibernateUtil;
import _model.vo.Categoria;
import exception.DaoException;

public class CategoriaDAO extends GenericDAOImpl<Categoria, Integer> implements ICategoriaDAO {

	public CategoriaDAO() 
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

	public List<Categoria> allCategorias() throws DaoException {
		List<Categoria> categorias = new ArrayList<Categoria>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Categoria res1 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			categorias = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return categorias;
	}

	public List<Categoria> pesquisaCategoria(Categoria filtro) throws DaoException {
		List<Categoria> filtrocategorias = new ArrayList<Categoria>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Categoria res1 ");
			sql.append("where upper(res1.cate_nm_categoria) like :nome ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("nome", "%" + filtro.getCate_nm_categoria().toUpperCase() + "%");
			filtrocategorias = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtrocategorias;
	}

}
