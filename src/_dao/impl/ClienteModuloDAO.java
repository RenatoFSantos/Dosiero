package _dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.IClienteModuloDAO;
import _dao.util.HibernateUtil;
import _model.vo.Cliente;
import _model.vo.ClienteModulo;
import _model.vo.Modulo;
import exception.DaoException;

public class ClienteModuloDAO extends GenericDAOImpl<ClienteModulo, Integer> implements IClienteModuloDAO {

	public ClienteModuloDAO() 
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

	public List<ClienteModulo> allClienteModulos() throws DaoException {
		List<ClienteModulo> clientemodulos = new ArrayList<ClienteModulo>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from ClienteModulo res1 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			clientemodulos = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return clientemodulos;
	}

	public List<ClienteModulo> pesquisaClienteModuloPorCliente(Cliente filtro_cliente) throws DaoException {
		List<ClienteModulo> filtromodulos = new ArrayList<ClienteModulo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from ClienteModulo res1 ");
			sql.append("where res1.objCliente.id = :codcli ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codcli", filtro_cliente.getId());
			filtromodulos = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtromodulos;
	}
	
	public boolean excluiModulosDoCliente(Cliente filtro_cliente) throws DaoException {
		boolean res = true;

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("delete from ClienteModulo res1 ");
			sql.append("where res1.objCliente.id = :codcli ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codcli", filtro_cliente.getId());
			query.executeUpdate();

		} catch (Exception e) {
			res=false;
			throw new DaoException(e);
		}

		return res;
	}
}
