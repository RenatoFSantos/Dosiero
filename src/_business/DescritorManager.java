package _business;

import java.util.ArrayList;
import java.util.List;

import _dao.impl.DescritorDAO;
import _dao.interfaces.IDescritorDAO;
import _dao.util.HibernateSessionRequestFilter;
import _dao.util.HibernateUtil;
import _model.vo.Acervo;
import _model.vo.Descritor;
import exception.ControllerException;
import exception.DaoException;

public class DescritorManager {

	private IDescritorDAO descritorDAO = new DescritorDAO();

	public DescritorManager() {
		
	}

	public void salvar(Descritor descritor) throws ControllerException {
		HibernateUtil.getSession();
		try {
			HibernateUtil.beginTransaction();
			descritorDAO.save(descritor);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void deletar(Descritor descritor) throws ControllerException {
		HibernateUtil.getSession();
		try {
			HibernateUtil.beginTransaction();
			descritorDAO.delete(descritor);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {

			HibernateUtil.closeSession();
		}

	}

	public List<Descritor> listaDescritors() throws ControllerException {
		HibernateUtil.getSession();
		List<Descritor> descritors = new ArrayList<Descritor>();

		try {
			// --- Rotina para buscar todos os objetos do banco
			descritors = descritorDAO.allDescritors();

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return descritors;
	}

	public List<Descritor> pesquisa(Descritor filtro) throws ControllerException {
		HibernateUtil.getSession();
		List<Descritor> descritors = new ArrayList<Descritor>();

		try {
			// --- Rotina para buscar todos os descritores homônimos
			descritors = descritorDAO.pesquisaDescritor(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return descritors;
	}

	public List<Descritor> pesquisaPorAcervo(Acervo filtro) throws ControllerException {
		HibernateUtil.getSession();
		List<Descritor> descritors = new ArrayList<Descritor>();
		try {
			// --- Rotina para buscar todos os usuários do banco
			descritors = descritorDAO.pesquisaDescritorPorAcervo(filtro);
		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}
		return descritors;
	}

}
