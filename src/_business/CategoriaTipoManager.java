package _business;

import java.util.ArrayList;
import java.util.List;

import _dao.impl.CategoriaTipoDAO;
import _dao.interfaces.ICategoriaTipoDAO;
import _dao.util.HibernateUtil;
import _model.vo.CategoriaTipo;
import _model.vo.Tipo;
import exception.ControllerException;
import exception.DaoException;

public class CategoriaTipoManager {

	private ICategoriaTipoDAO categoriaTipoDAO = new CategoriaTipoDAO();

	public CategoriaTipoManager() {
		HibernateUtil.getSession();
	}

	public void salvar(CategoriaTipo categoriaTipo) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			categoriaTipoDAO.save(categoriaTipo);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void deletar(CategoriaTipo categoriaTipo) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			categoriaTipoDAO.delete(categoriaTipo);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {

			HibernateUtil.closeSession();
		}

	}

	public List<CategoriaTipo> listaCategoriaTipos() throws ControllerException {

		List<CategoriaTipo> categoriaTipos = new ArrayList<CategoriaTipo>();

		try {
			// --- Rotina para buscar todos os objetos do banco
			categoriaTipos = categoriaTipoDAO.allCategoriaTipos();

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return categoriaTipos;
	}

	public List<CategoriaTipo> pesquisa(CategoriaTipo filtro) throws ControllerException {

		List<CategoriaTipo> categoriaTipos = new ArrayList<CategoriaTipo>();

		try {
			// --- Rotina para buscar todos os usuários do banco
			categoriaTipos = categoriaTipoDAO.pesquisaCategoriaTipo(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return categoriaTipos;
	}
	
	public CategoriaTipo validaCategoriaTipo(CategoriaTipo filtro) throws ControllerException {

		CategoriaTipo categoriaTipo = new CategoriaTipo();

		try {
			
			categoriaTipo = categoriaTipoDAO.validaCategoriaTipo(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return categoriaTipo;
	}

	public List<CategoriaTipo> pesquisaPorTipo(Tipo filtro) throws ControllerException {

		List<CategoriaTipo> categoriaTipos = new ArrayList<CategoriaTipo>();

		try {
			// --- Rotina para buscar todos os usuários do banco
			categoriaTipos = categoriaTipoDAO.pesquisaCategoriaTipoPorTipo(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return categoriaTipos;
	}

}
