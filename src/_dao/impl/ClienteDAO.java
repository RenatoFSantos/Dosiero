package _dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.IClienteDAO;
import _dao.util.HibernateUtil;
import _model.vo.Cliente;
import exception.DaoException;

public class ClienteDAO extends GenericDAOImpl<Cliente, Integer> implements IClienteDAO {

	public ClienteDAO() 
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

	public List<Cliente> allClientes() throws DaoException {
		List<Cliente> clientes = new ArrayList<Cliente>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select us from Cliente us ");
			sql.append("where us.clie_in_delecao = 0 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			clientes = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return clientes;
	}

	public List<Cliente> pesquisaCliente(Cliente filtro) throws DaoException {
		List<Cliente> filtroclientes = new ArrayList<Cliente>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select us from Cliente us ");
			sql.append("where upper(us.clie_nm_fantasia) like :nome ");
			sql.append("and us.clie_in_delecao=0 ");
			Query query = HibernateUtil.getSession().createQuery(sql.toString());
			query.setParameter("nome", "%" + filtro.getClie_nm_fantasia().toUpperCase() + "%");
			filtroclientes = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtroclientes;
	}

}
