package _dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.IEmprestimoDAO;
import _dao.util.HibernateUtil;
import _model.vo.Acervo;
import _model.vo.Boleto;
import _model.vo.Emprestimo;
import _model.vo.Unidade;
import _model.vo.Usuario;
import exception.DaoException;

public class EmprestimoDAO extends GenericDAOImpl<Emprestimo, Integer> implements IEmprestimoDAO {

	public EmprestimoDAO() 
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

	public List<Emprestimo> allEmprestimos() throws DaoException {
		List<Emprestimo> clientemodulos = new ArrayList<Emprestimo>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Emprestimo res1 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			clientemodulos = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return clientemodulos;
	}

	public List<Emprestimo> pesquisaEmprestimoPorBoleto(Boleto filtro) throws DaoException {
		List<Emprestimo> lista = new ArrayList<Emprestimo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Emprestimo res1 ");
			sql.append("where res1.objBoleto.id = :codfiltro ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro.getId());
			lista = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}
	
	public Emprestimo pesquisaEmprestimo(Emprestimo filtro) throws DaoException {
		Emprestimo obj = new Emprestimo();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Emprestimo res1 ");
			sql.append("where res1.id = :codfiltro ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro.getId());
			obj = findOne(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return obj;
	}

	public List<Emprestimo> buscaEmprestimosEmAberto(Usuario filtro) throws DaoException {
		List<Emprestimo> lista = new ArrayList<Emprestimo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Emprestimo res1 ");
			sql.append("where res1.objUsuarioEmprestimo.id = :codfiltro ");
			sql.append("and res1.empr_dt_real_devolucao is null");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro.getId());
			lista = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}

	public List<Emprestimo> buscaEmprestimosPorCodBarras(String filtro) throws DaoException {
		List<Emprestimo> lista = new ArrayList<Emprestimo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Emprestimo res1 ");
			sql.append("where res1.objDocumento.docu_nr_codbarras = :codfiltro ");
			sql.append("and res1.empr_dt_real_devolucao is null");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro);
			lista = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}

	public List<Emprestimo> buscaEmprestimosPorBoleto(Boleto filtro) throws DaoException {
		List<Emprestimo> lista = new ArrayList<Emprestimo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Emprestimo res1 ");
			sql.append("where res1.objBoleto.id = :codfiltro ");
			sql.append("and res1.empr_dt_real_devolucao is null");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro.getId());
			lista = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}

	public List<Emprestimo> listaEmprestimoPorUnidade(Unidade unidade, Date dataIni, Date dataFim) throws DaoException {
		List<Emprestimo> lista = new ArrayList<Emprestimo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Emprestimo res1 ");
			sql.append("where res1.objAcervo.objUnidade.id = :cod ");
			sql.append("and res1.empr_dt_emprestimo>= :dat_ini ");
			sql.append("and res1.empr_dt_emprestimo<= :dat_fim ");
			sql.append("order by res1.objUsuarioCadastro.usua_nm_usuario, res1.empr_dt_emprestimo ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("cod", unidade.getId());
			query.setParameter("dat_ini", dataIni);
			query.setParameter("dat_fim", dataFim);
			lista = findMany(query);
			
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}

}
