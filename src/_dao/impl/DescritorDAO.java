package _dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.IDescritorDAO;
import _dao.util.HibernateUtil;
import _model.vo.Acervo;
import _model.vo.Descritor;
import _model.vo.Modulo;
import exception.DaoException;

public class DescritorDAO extends GenericDAOImpl<Descritor, Integer> implements IDescritorDAO {

	public DescritorDAO() 
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

	public List<Descritor> allDescritors() throws DaoException {
		List<Descritor> descritors = new ArrayList<Descritor>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Descritor res1 ");
			sql.append("where res1.desc_in_delecao = 0 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			descritors = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return descritors;
	}

	public List<Descritor> pesquisaDescritor(Descritor filtro) throws DaoException {
		List<Descritor> filtrodescritors = new ArrayList<Descritor>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Descritor res1 ");
			sql.append("where upper(res1.desc_nm_descritor) like :nome ");
			sql.append("and res1.desc_in_delecao=0 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("nome", "%" + filtro.getDesc_nm_descritor().toUpperCase() + "%");
			filtrodescritors = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtrodescritors;
	}

	
	public List<Descritor> pesquisaDescritorPorAcervo(Acervo filtro) throws DaoException {
		List<Descritor> filtrodescritors = new ArrayList<Descritor>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1.objDescritor from AcervoDescritor res1 ");
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
}
