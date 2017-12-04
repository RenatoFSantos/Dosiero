package _business;

import java.util.ArrayList;
import java.util.List;

import _dao.impl.CategoriaDAO;
import _dao.interfaces.ICategoriaDAO;
import _dao.util.HibernateUtil;
import _model.vo.Categoria;
import exception.ControllerException;
import exception.DaoException;

public class CategoriaManager {

	private ICategoriaDAO categoriaDAO = new CategoriaDAO();

	public CategoriaManager() {
		HibernateUtil.getSession();
	}

	public void salvar(Categoria categoria) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			categoriaDAO.save(categoria);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void deletar(Categoria categoria) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			categoriaDAO.delete(categoria);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {

			HibernateUtil.closeSession();
		}

	}

	public List<Categoria> listaCategorias() throws ControllerException {

		List<Categoria> categorias = new ArrayList<Categoria>();

		try {
			// --- Rotina para buscar todos os objetos do banco
			categorias = categoriaDAO.allCategorias();

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return categorias;
	}

	public List<Categoria> pesquisa(Categoria filtro) throws ControllerException {

		List<Categoria> categorias = new ArrayList<Categoria>();

		try {
			// --- Rotina para buscar todos os usuários do banco
			categorias = categoriaDAO.pesquisaCategoria(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return categorias;
	}
}
