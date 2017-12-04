package _dao.impl;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.IConfiguracaoDAO;
import _dao.util.HibernateUtil;
import _model.vo.Parametro;
import exception.DaoException;

public class ConfiguracaoDAO extends GenericDAOImpl<Parametro, Integer> implements IConfiguracaoDAO {

	public ConfiguracaoDAO() 
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
	
	public Parametro allParametros() throws DaoException {
		Parametro parametro = new Parametro();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Parametro res1 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			parametro = findOne(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return parametro;
	}
}
