package _dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import _dao.GenericDAOImpl;
import _dao.interfaces.IClasseDAO;
import _dao.util.HibernateUtil;
import _model.dto.ClasseDTO;
import _model.vo.Classe;
import exception.DaoException;

public class ClasseDAO extends GenericDAOImpl<Classe, Integer> implements IClasseDAO {
	
	public ClasseDAO() 
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

	public List<Classe> allClasses() throws DaoException {
		List<Classe> classes = new ArrayList<Classe>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Classe res1 ");
			sql.append("where res1.clas_in_delecao<1 ");

			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			classes = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return classes;
	}

	public List<Classe> pesquisaClasse(Classe filtro) throws DaoException {
		List<Classe> filtroclasses = new ArrayList<Classe>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Classe res1 ");
			sql.append("where upper(res1.clas_ds_nome) like :nome ");
			sql.append("and res1.clas_in_delecao<1 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("nome", "%" + filtro.getClas_ds_nome().toUpperCase() + "%");
			filtroclasses = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtroclasses;
	}

	public List<Classe> pesquisaClassesPai() throws DaoException {
		List<Classe> filtroclasses = new ArrayList<Classe>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Classe res1 ");
			sql.append("where res1.objClasse is null ");
			sql.append("and res1.clas_in_delecao<1 ");
			Query query = HibernateUtil.getSession().createQuery(sql.toString());
			filtroclasses = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtroclasses;
	}

	public List<Classe> pesquisaClassesPrincipais() throws DaoException {
		List<Classe> filtroclasses = new ArrayList<Classe>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Classe res1 ");
			sql.append("where res1.objClasse.id=1 ");
			sql.append("and res1.clas_in_delecao<1 ");
			Query query = HibernateUtil.getSession().createQuery(sql.toString());
			filtroclasses = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtroclasses;
	}

	public List<Classe> temFilhas(Classe pai) throws DaoException {
		List<Classe> filtroclasses = new ArrayList<Classe>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Classe res1 ");
			sql.append("where res1.objClasse.id=:pai ");
			sql.append("and res1.clas_in_delecao<1 ");
			Query query = HibernateUtil.getSession().createQuery(sql.toString());
			query.setParameter("pai", pai.getId());
			filtroclasses = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtroclasses;
	}

	public Classe carrega(Integer id) throws DaoException {
		Classe objClasse = new Classe();

		try {

			objClasse = findByID(Classe.class, id);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return objClasse;
	}
	
	public Classe carregaClasseComAcervo(Classe objClasse) throws DaoException {
		Classe obj = new Classe();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Classe res1 join fetch res1.acervos ");
			sql.append("where res1.id=:filtro ");
			sql.append("and res1.clas_in_delecao<1 ");
			Query query = HibernateUtil.getSession().createQuery(sql.toString());
			query.setParameter("filtro", objClasse.getId());
			obj = findOne(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return obj;
	}

	public Classe carregaClassePorCodigo(Classe objClasse) throws DaoException {
		Classe obj = new Classe();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Classe res1 ");
			sql.append("where res1.clas_cd_classe = :filtro ");
			sql.append("and res1.clas_in_delecao<1 ");
			Query query = HibernateUtil.getSession().createQuery(sql.toString());
			query.setParameter("filtro", objClasse.getClas_cd_classe());
			obj = findOne(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return obj;
	}

	
	public List<ClasseDTO> buscaDadosRelatorioClasse(Classe filtro) throws DaoException {
		List resultQuery;
		List<ClasseDTO> listaClasses = new ArrayList<ClasseDTO>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("with recursive hierarquia_classe (cdto_sq_id, cdto_cd_classe, cdto_ds_nome, cdto_tx_fasecorrente, cdto_nr_fasecorrente, cdto_tx_faseintermediaria, cdto_tx_destinacaofinal, cdto_tx_observacao) as");
			sql.append(" (");
			sql.append(" select clas_sq_id, clas_cd_classe, clas_ds_nome, clas_tx_fasecorrente, clas_nr_fasecorrente, clas_tx_faseintermediaria, clas_tx_destinacaofinal, clas_tx_observacao from classe where clas_cd_pai=:codclassepai and clas_in_delecao<1");
			sql.append(" UNION ALL");
			sql.append(" select classe.clas_sq_id as classeID,");
			sql.append(" 	classe.clas_cd_classe as classeCod,");
			sql.append(" 	classe.clas_ds_nome as classeNome,");
			sql.append(" 	classe.clas_tx_fasecorrente as classeFC,");
			sql.append("	classe.clas_nr_fasecorrente as classeFCN,");
			sql.append("	classe.clas_tx_faseintermediaria as classeFI,");
			sql.append("	classe.clas_tx_destinacaofinal as classeDF,");
			sql.append("	classe.clas_tx_observacao as classeOBS");
			sql.append("	from classe INNER JOIN hierarquia_classe ON classe.clas_cd_pai = hierarquia_classe.cdto_sq_id");
			sql.append(" where classe.clas_in_delecao<1");
			sql.append(" )");
			sql.append(" select");
			sql.append(" (select clas_cd_classe as CodPai from classe where clas_sq_id=:codclassepai) cdto_cd_pai,");
			sql.append(" (select clas_ds_nome as NomePai from classe where clas_sq_id=:codclassepai) cdto_nm_pai,");
			sql.append(" cdto_sq_id, cdto_cd_classe, cdto_ds_nome, cdto_tx_fasecorrente, cdto_nr_fasecorrente, cdto_tx_faseintermediaria, cdto_tx_destinacaofinal, cdto_tx_observacao from hierarquia_classe");
			sql.append(" order by cdto_cd_pai, cdto_cd_classe;");
//			Query query = HibernateUtil.getSession().createSQLQuery(sql.toString())
//			.setParameter("codclassepai", filtro.getId());
//			resultQuery = query.list();
			resultQuery = HibernateUtil.getSession().createSQLQuery(sql.toString())
			.setParameter("codclassepai", filtro.getId())
			.setResultTransformer(Transformers.aliasToBean(ClasseDTO.class))
			.list();

			for (Iterator iterator = resultQuery.iterator(); iterator.hasNext();) {
				ClasseDTO objClasse = (ClasseDTO) iterator.next();
				listaClasses.add(objClasse);
			}

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return listaClasses;
	}
}


