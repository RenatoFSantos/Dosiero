package _dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.IBoletoDAO;
import _dao.util.HibernateUtil;
import _model.vo.Acervo;
import _model.vo.Boleto;
import _model.vo.Tipo;
import exception.DaoException;

public class BoletoDAO extends GenericDAOImpl<Boleto, Integer> implements IBoletoDAO {

	public BoletoDAO() 
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

	public List<Boleto> allBoletos() throws DaoException {
		List<Boleto> paginas = new ArrayList<Boleto>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Boleto res1 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			paginas = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return paginas;
	}


	public Integer ultimoID() throws DaoException {
		Integer maxCod = 0;

		try {
			maxCod = (Integer) HibernateUtil.getSession().createQuery("select max(id) from Boleto").uniqueResult();
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return maxCod;
	}

}
