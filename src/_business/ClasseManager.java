package _business;

import java.util.ArrayList;
import java.util.List;

import _dao.impl.ClasseDAO;
import _dao.interfaces.IClasseDAO;
import _dao.util.HibernateUtil;
import _model.dto.ClasseDTO;
import _model.vo.Classe;
import exception.ControllerException;
import exception.DaoException;

public class ClasseManager {
	private IClasseDAO classeDAO = new ClasseDAO();

	public ClasseManager() {
		HibernateUtil.getSession();
	}

	public void salvar(Classe classe) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			classeDAO.save(classe);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void merge(Classe classe) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			classeDAO.merge(classe);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}
	
	public void deletar(Classe classe) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			classeDAO.delete(classe);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {

			HibernateUtil.closeSession();
		}

	}

	public List<Classe> listaClasses() throws ControllerException {

		List<Classe> classes = new ArrayList<Classe>();

		try {
			// --- Rotina para buscar todos os objetos do banco
			classes = classeDAO.allClasses();

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return classes;
	}

	public List<Classe> listaClassesPai() throws ControllerException {

		List<Classe> classes = new ArrayList<Classe>();

		try {
			/*
			 * ********************************************
			 * BUSCA AS CLASSES PAIS 
			 * ********************************************
			*/
			classes = classeDAO.pesquisaClassesPai();
		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return classes;
	}

	public List<Classe> listaClassesPrincipais() throws ControllerException {

		List<Classe> classes = new ArrayList<Classe>();

		try {
			/*
			 * ********************************************
			 * BUSCA AS CLASSES PRINCIPAIS
			 * ********************************************
			*/
			classes = classeDAO.pesquisaClassesPrincipais();
		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return classes;
	}
	
	public Classe carrega(Integer id) throws ControllerException {

		Classe objClasse = new Classe();

		try {
			objClasse = classeDAO.carrega(id);
		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return objClasse;
	}

	public List<Classe> temFilhas(Classe pai) throws ControllerException {

		List<Classe> classes = new ArrayList<Classe>();

		try {
			classes = classeDAO.temFilhas(pai);
		} catch (DaoException e) {
			throw new ControllerException(e);
		}

		return classes;
	}	
	
	public List<Classe> pesquisa(Classe filtro) throws ControllerException {

		List<Classe> classes = new ArrayList<Classe>();

		try {
			// --- Rotina para buscar as classes pelo filtro
			classes = classeDAO.pesquisaClasse(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return classes;
	}
	
	public Classe carregaClasseComAcervo(Classe filtro) throws ControllerException {

		Classe obj = new Classe();

		try {
			/* 
			 * *******************************************
			 * CARREGA OS ACERVOS RELACIONADOS A CLASSE 
			 * *******************************************
			 */
			obj = classeDAO.carregaClasseComAcervo(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return obj;
	}

	
	public List<ClasseDTO> buscaDadosRelatorioClasse(Classe filtro) throws ControllerException {

		List<ClasseDTO> classes = new ArrayList<ClasseDTO>();

		try {
			/*
			 * ********************************************
			 * BUSCA AS CLASSES PARA O RELATORIO
			 * ********************************************
			*/
			classes = classeDAO.buscaDadosRelatorioClasse(filtro);
		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return classes;
	}
	
	public Classe buscaClassePorCodigo(Classe filtro) throws ControllerException {
		Classe obj = new Classe();

		try {
			/* 
			 * **********************************************
			 * CARREGA A CLASSE PELO CÓDIGO DE CLASSIFICAÇÃO 
			 * **********************************************
			 */
			obj = classeDAO.carregaClassePorCodigo(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return obj;		
	}

}
