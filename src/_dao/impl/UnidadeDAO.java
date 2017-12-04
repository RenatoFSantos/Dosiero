package _dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.IUnidadeDAO;
import _dao.util.HibernateUtil;
import _model.vo.Unidade;
import exception.DaoException;

public class UnidadeDAO extends GenericDAOImpl<Unidade, Integer> implements IUnidadeDAO {

	public UnidadeDAO() 
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

	public List<Unidade> allUnidades() throws DaoException {
		List<Unidade> unidades = new ArrayList<Unidade>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select us from Unidade us ");
			sql.append("where us.unid_in_delecao<1 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			unidades = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return unidades;
	}

	public List<Unidade> pesquisaUnidade(Unidade filtro) throws DaoException {
		List<Unidade> filtrounidades = new ArrayList<Unidade>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select us from Unidade us ");
			sql.append("where upper(us.unid_nm_unidade) like :nome ");
			sql.append("and us.unid_in_delecao=0 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("nome", "%" + filtro.getUnid_nm_unidade().toUpperCase() + "%");
			filtrounidades = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtrounidades;
	}

}
