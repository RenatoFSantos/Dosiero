package _dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.IModuloDAO;
import _dao.util.HibernateUtil;
import _model.vo.Cliente;
import _model.vo.Modulo;
import exception.DaoException;

public class ModuloDAO extends GenericDAOImpl<Modulo, Integer> implements IModuloDAO {

	public ModuloDAO() 
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

	public List<Modulo> allModulos() throws DaoException {
		List<Modulo> modulos = new ArrayList<Modulo>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Modulo res1 ");
			sql.append("where res1.modu_in_delecao = 0 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			modulos = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return modulos;
	}

	public List<Modulo> pesquisaModulo(Modulo filtro) throws DaoException {
		List<Modulo> filtromodulos = new ArrayList<Modulo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Modulo res1 ");
			sql.append("where upper(res1.modu_nm_modulo) like :nome ");
			sql.append("and res1.modu_in_delecao=0 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("nome", "%" + filtro.getModu_nm_modulo().toUpperCase() + "%");
			filtromodulos = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtromodulos;
	}

	public List<Modulo> pesquisaModuloPorCliente(Cliente filtro_cliente) throws DaoException {
		List<Modulo> filtromodulos = new ArrayList<Modulo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1.objModulo from ClienteModulo res1 ");
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
}
