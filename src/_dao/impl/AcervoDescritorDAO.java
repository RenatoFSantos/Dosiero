package _dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.IAcervoDescritorDAO;
import _dao.util.HibernateUtil;
import _model.vo.Acervo;
import _model.vo.AcervoDescritor;
import _model.vo.Cliente;
import _model.vo.ClienteModulo;
import exception.DaoException;

public class AcervoDescritorDAO extends GenericDAOImpl<AcervoDescritor, Integer> implements IAcervoDescritorDAO {

	public AcervoDescritorDAO() 
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

	public List<AcervoDescritor> allAcervoDescritors() throws DaoException {
		List<AcervoDescritor> clientemodulos = new ArrayList<AcervoDescritor>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from AcervoDescritor res1 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			clientemodulos = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return clientemodulos;
	}

	public List<AcervoDescritor> pesquisaAcervoDescritorPorAcervo(Acervo filtro) throws DaoException {
		List<AcervoDescritor> filtrodescritors = new ArrayList<AcervoDescritor>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from AcervoDescritor res1 ");
			sql.append("where res1.objAcervo.id = :codfiltro ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro.getId());
			filtrodescritors = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtrodescritors;
	}

	public boolean excluiDescritoresPorAcervo(Acervo filtro) throws DaoException {
		boolean res = true;

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("delete from AcervoDescritor res1 ");
			sql.append("where res1.objAcervo.id = :codfiltro ");
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
}
