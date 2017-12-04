package _business;

import java.util.ArrayList;
import java.util.List;

import _dao.impl.UnidadeDAO;
import _dao.interfaces.IUnidadeDAO;
import _dao.util.HibernateUtil;
import _model.vo.Unidade;
import exception.ControllerException;
import exception.DaoException;

public class UnidadeManager {

	private IUnidadeDAO unidadeDAO = new UnidadeDAO();

	public UnidadeManager() {
		HibernateUtil.getSession();
	}

	public void salvar(Unidade unidade) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			unidadeDAO.save(unidade);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void deletar(Unidade unidade) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			unidadeDAO.delete(unidade);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {

			HibernateUtil.closeSession();
		}

	}

	public List<Unidade> listaUnidades() throws ControllerException {

		List<Unidade> unidades = new ArrayList<Unidade>();

		try {
			unidades = unidadeDAO.allUnidades();

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return unidades;
	}

	public List<Unidade> pesquisa(Unidade filtro) throws ControllerException {

		List<Unidade> unidades = new ArrayList<Unidade>();

		try {
			unidades = unidadeDAO.pesquisaUnidade(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return unidades;
	}
	
	public Unidade buscaUnidadePorId(Unidade filtro) throws ControllerException {

		Unidade unidade = new Unidade();

		try {
			unidade = unidadeDAO.findByID(Unidade.class, filtro.getId());

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return unidade;
	}

}
