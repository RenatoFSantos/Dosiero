package _dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.ITipoDAO;
import _dao.util.HibernateUtil;
import _model.vo.Tipo;
import exception.DaoException;

public class TipoDAO extends GenericDAOImpl<Tipo, Integer> implements ITipoDAO {

	public TipoDAO() 
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

	public List<Tipo> allTipos() throws DaoException {
		List<Tipo> tipos = new ArrayList<Tipo>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Tipo res1 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			tipos = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return tipos;
	}

	public List<Tipo> pesquisaTipo(Tipo filtro) throws DaoException {
		List<Tipo> filtrotipos = new ArrayList<Tipo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Tipo res1 ");
			sql.append("where upper(res1.tipo_ds_tipo) like :nome ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("nome", "%" + filtro.getTipo_ds_tipo().toUpperCase() + "%");
			filtrotipos = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtrotipos;
	}

}
