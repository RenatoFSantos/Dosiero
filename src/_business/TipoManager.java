package _business;

import java.util.ArrayList;
import java.util.List;

import _dao.impl.TipoDAO;
import _dao.interfaces.ITipoDAO;
import _dao.util.HibernateUtil;
import _model.vo.Tipo;
import exception.ControllerException;
import exception.DaoException;

public class TipoManager {

	private ITipoDAO tipoDAO = new TipoDAO();

	public TipoManager() {
		HibernateUtil.getSession();
	}

	public void salvar(Tipo tipo) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			tipoDAO.save(tipo);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void deletar(Tipo tipo) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			tipoDAO.delete(tipo);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {

			HibernateUtil.closeSession();
		}

	}

	public List<Tipo> listaTipos() throws ControllerException {

		List<Tipo> tipos = new ArrayList<Tipo>();

		try {
			// --- Rotina para buscar todos os objetos do banco
			tipos = tipoDAO.allTipos();

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return tipos;
	}

	public List<Tipo> pesquisa(Tipo filtro) throws ControllerException {

		List<Tipo> tipos = new ArrayList<Tipo>();

		try {
			// --- Rotina filtra os objetos do banco
			tipos = tipoDAO.pesquisaTipo(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return tipos;
	}
}
