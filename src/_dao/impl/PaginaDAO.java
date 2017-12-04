package _dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.IPaginaDAO;
import _dao.util.HibernateUtil;
import _model.vo.Modulo;
import _model.vo.Pagina;
import _model.vo.Perfil;
import exception.DaoException;

public class PaginaDAO extends GenericDAOImpl<Pagina, Integer> implements IPaginaDAO {

	public PaginaDAO() 
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

	public List<Pagina> allPaginas() throws DaoException {
		List<Pagina> paginas = new ArrayList<Pagina>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Pagina res1 ");
			sql.append("where res1.pagi_in_delecao = 0 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			paginas = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return paginas;
	}

	public List<Pagina> allPaginasPerfil(Perfil filtro) throws DaoException {
		List<Pagina> paginas = new ArrayList<Pagina>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1.pagi_nm_pagina, "
					+ "res2.pepa_in_acesso_total, "
					+ "res2.pepa_in_acesso_leitura, "
					+ "res2.pepa_in_acesso_escrita, "
					+ "res2.pepa_in_acesso_exclusao "
					+ "from Pagina as res1 left outer join fetch res1.PerfilPagina as res2 with res2.objPerfil.id = :codfiltro");
			sql.append("where res1.pagi_in_delecao = 0 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro.getId());
			paginas = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return paginas;
	}
	
	public List<Pagina> pesquisaPagina(Pagina filtro) throws DaoException {
		List<Pagina> filtropaginas = new ArrayList<Pagina>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Pagina res1 ");
			sql.append("where upper(res1.pagi_nm_pagina) like :nome ");
			sql.append("and res1.pagi_in_delecao=0 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("nome", "%" + filtro.getPagi_nm_pagina().toUpperCase() + "%");
			filtropaginas = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtropaginas;
	}

	public List<Pagina> pesquisaPaginaPorModulo(Modulo filtro) throws DaoException {
		List<Pagina> filtropaginas = new ArrayList<Pagina>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1.objPagina from ModuloPagina res1 ");
			sql.append("where res1.objModulo.id = :codfiltro ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro.getId());
			filtropaginas = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtropaginas;
	}

}
