package _dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import _dao.GenericDAOImpl;
import _dao.interfaces.IAcervoDAO;
import _dao.util.HibernateUtil;
import _model.vo.Acervo;
import _model.vo.Classe;
import _model.vo.Cliente;
import _model.vo.Descritor;
import _model.vo.Tipo;
import _model.vo.Unidade;
import exception.DaoException;

public class AcervoDAO extends GenericDAOImpl<Acervo, Integer> implements IAcervoDAO {

	public AcervoDAO() 
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

	public List<Acervo> allAcervos() throws DaoException {
		List<Acervo> acervos = new ArrayList<Acervo>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Acervo res1 ");
			sql.append("where res1.acer_in_delecao = 0 ");
			sql.append("order by res1.id ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			acervos = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return acervos;
	}

	public List<Acervo> pesquisaAcervo(Acervo filtro) throws DaoException {
		List<Acervo> filtroacervos = new ArrayList<Acervo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Acervo res1 ");
			sql.append("where upper(res1.acer_ds_assunto) like :nome ");
			sql.append("and res1.acer_in_delecao=0 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("nome", "%" + filtro.getAcer_ds_assunto().toUpperCase() + "%");
			filtroacervos = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtroacervos;
	}

	public List<Acervo> pesquisaAssunto(Acervo filtro) throws DaoException {
		List<Acervo> filtroacervos = new ArrayList<Acervo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Acervo res1 ");
			sql.append("where upper(res1.acer_ds_assunto) = :nome ");
			sql.append("and res1.acer_in_delecao=0 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("nome", filtro.getAcer_ds_assunto().toUpperCase());
			filtroacervos = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtroacervos;
	}
	
	public List<Acervo> listaAcervoPorClasse(Classe filtro) throws DaoException {
		List<Acervo> lista = new ArrayList<Acervo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Acervo res1 ");
			sql.append("where res1.objClasse.id = :cod ");
			sql.append("and res1.acer_in_delecao<1 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("cod", filtro.getId());
			lista = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}

	
	public List<Acervo> listaAcervoPorDescritor(Descritor filtro) throws DaoException {
		List<Acervo> lista = new ArrayList<Acervo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1.objAcervo from AcervoDescritor res1 ");
			sql.append("where res1.objDescritor.id = :cod ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("cod", filtro.getId());
			lista = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}

	public List<Acervo> listaAcervoPorCliente(Cliente filtro) throws DaoException {
		List<Acervo> lista = new ArrayList<Acervo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Acervo res1 ");
			sql.append("where res1.objCliente.id = :cod ");
			sql.append("and res1.acer_in_delecao<1 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("cod", filtro.getId());
			lista = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}

	public List<Acervo> listaAcervoPorUnidade(Unidade unidade, Date dataIni, Date dataFim) throws DaoException {
		List<Acervo> lista = new ArrayList<Acervo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Acervo res1 ");
			sql.append("where res1.objUnidade.id = :cod ");
			sql.append("and res1.acer_dt_finalvigencia>= :dat_ini ");
			sql.append("and res1.acer_dt_finalvigencia<= :dat_fim ");
			sql.append("and res1.acer_in_delecao<1 ");
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

	public List<Acervo> listaAcervoPorUnidadeOrdemLocal(Unidade unidade, Date dataIni, Date dataFim) throws DaoException {
		List<Acervo> lista = new ArrayList<Acervo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Acervo res1 ");
			sql.append("where res1.objUnidade.id = :cod ");
			sql.append("and res1.acer_dt_finalvigencia>= :dat_ini ");
			sql.append("and res1.acer_dt_finalvigencia<= :dat_fim ");
			sql.append("and res1.acer_in_delecao<1 ");
			sql.append("order by res1.acer_ds_localarquivo ");
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
	
	public List<Acervo> listaAcervoPorClasse(Cliente cliente, Classe classe, Date dataIni, Date dataFim) throws DaoException {
		List<Acervo> lista = new ArrayList<Acervo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Acervo res1 ");
			sql.append("where res1.objCliente.id = :codcli ");
			if (classe!=null && classe.getId()!=null) sql.append("and res1.objClasse.id = :codclas ");
			sql.append("and res1.acer_dt_inclusao>= :dat_ini ");
			sql.append("and res1.acer_dt_inclusao<= :dat_fim ");
			sql.append("and res1.acer_in_delecao<1 ");
			sql.append("order by res1.acer_ds_assunto ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codcli", cliente.getId());
			if (classe!=null && classe.getId()!=null) query.setParameter("codclas", classe.getId());
			query.setParameter("dat_ini", dataIni);
			query.setParameter("dat_fim", dataFim);
			lista = findMany(query);
			
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}

	public List<Acervo> listaAcervoMovimentacao(Cliente cliente, Unidade unidade, Classe classe, Date dataIni, Date dataFim) throws DaoException {
		List<Acervo> lista = new ArrayList<Acervo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Acervo res1 ");
			sql.append("where res1.objCliente.id = :codcli ");
			if (unidade!=null && unidade.getId()!=null) sql.append("and res1.objUnidade.id = :codund ");
			if (classe!=null && classe.getId()!=null) sql.append("and res1.objClasse.id = :codclas ");
			sql.append("and (res1.acer_dt_realmvt_fc_fi>= :dat_ini or res1.acer_dt_realmvt_fi_df>= :dat_ini) ");
			sql.append("and (res1.acer_dt_realmvt_fc_fi<= :dat_fim or res1.acer_dt_realmvt_fi_df<= :dat_fim) ");
			sql.append("and res1.acer_in_automovimentacao = TRUE ");
			sql.append("and res1.acer_in_delecao<1 ");
			sql.append("order by res1.objUnidade.unid_sg_sigla, res1.objClasse.clas_cd_classe ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codcli", cliente.getId());
			query.setParameter("codund", unidade.getId());
			if (classe!=null && classe.getId()!=null) query.setParameter("codclas", classe.getId());
			query.setParameter("dat_ini", dataIni);
			query.setParameter("dat_fim", dataFim);
			lista = findMany(query);
			
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}
	
	public List<Acervo> listaAcervoPorDigitacao(Cliente cliente, Unidade unidade, Classe classe, Date dataIni, Date dataFim) throws DaoException {
		List<Acervo> lista = new ArrayList<Acervo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Acervo res1 ");
			sql.append("where res1.objCliente.id = :codcli ");
			if (unidade!=null && unidade.getId()!=null) sql.append("and res1.objUnidade.id = :codund ");
			if (classe!=null && classe.getId()!=null) sql.append("and res1.objClasse.id = :codclas ");
			sql.append("and res1.acer_dt_inclusao>= :dat_ini ");
			sql.append("and res1.acer_dt_inclusao<= :dat_fim ");
			sql.append("and res1.acer_in_delecao<1 ");
			sql.append("order by res1.objClasse.clas_cd_classe ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codcli", cliente.getId());
			query.setParameter("codund", unidade.getId());
			if (classe!=null && classe.getId()!=null) query.setParameter("codclas", classe.getId());
			query.setParameter("dat_ini", dataIni);
			query.setParameter("dat_fim", dataFim);
			lista = findMany(query);
			
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}

	public List<Acervo> listaAcervoPorDataInclusao(Cliente cliente, Date dataIni, Date dataFim) throws DaoException {
		List<Acervo> lista = new ArrayList<Acervo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Acervo res1 ");
			sql.append("where res1.objCliente.id = :codcli ");
			sql.append("and res1.acer_dt_inclusao>= :dat_ini ");
			sql.append("and res1.acer_dt_inclusao<= :dat_fim ");
			sql.append("and res1.acer_in_delecao<1 ");
			sql.append("order by res1.id ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codcli", cliente.getId());
			query.setParameter("dat_ini", dataIni);
			query.setParameter("dat_fim", dataFim);
			lista = findMany(query);
			
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}

	public List<Acervo> listaAcervoPorCodigoAcervo(Cliente cliente, Integer codIni, Integer codFim) throws DaoException {
		List<Acervo> lista = new ArrayList<Acervo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Acervo res1 ");
			sql.append("where res1.objCliente.id = :codcli ");
			sql.append("and res1.id>= :cod_ini ");
			sql.append("and res1.id<= :cod_fim ");
			sql.append("and res1.acer_in_delecao<1 ");
			sql.append("order by res1.id ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codcli", cliente.getId());
			query.setParameter("cod_ini", codIni);
			query.setParameter("cod_fim", codFim);
			lista = findMany(query);
			
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}

	public Integer ultimoID() throws DaoException {
		Integer maxCod = 0;

		try {
			maxCod = (Integer) HibernateUtil.getSession().createQuery("select max(id) from Acervo").uniqueResult();
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return maxCod;
	}
	
	public Acervo pesquisaAcervoPorCodBarra(String filtro) throws DaoException {
		Acervo result = new Acervo();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Acervo as res1 ");
			sql.append("where acer_nr_codbarras = :codfiltro ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro);
			result = findOne(query);
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return result;

	}

	public List<Acervo> pesquisaAcervosPorTipo(Tipo filtro) throws DaoException {
		List<Acervo> lista = new ArrayList<Acervo>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Acervo res1 ");
			sql.append("where res1.objTipo.id = :codTipo ");
			sql.append("and res1.acer_in_delecao<1 ");
			sql.append("order by res1.id ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codTipo", filtro.getId());
			lista = findMany(query);
			
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return lista;
	}


}
