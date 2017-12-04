package _business;

import java.util.ArrayList;
import java.util.List;

import _dao.impl.ModuloDAO;
import _dao.interfaces.IModuloDAO;
import _dao.util.HibernateUtil;
import _model.vo.Cliente;
import _model.vo.Modulo;
import exception.ControllerException;
import exception.DaoException;

public class ModuloManager {

	private IModuloDAO moduloDAO = new ModuloDAO();

	public ModuloManager() {
		HibernateUtil.getSession();
	}

	public void salvar(Modulo modulo) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			
			moduloDAO.save(modulo);
			
			HibernateUtil.commitTransaction();
		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void deletar(Modulo modulo) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			moduloDAO.delete(modulo);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {

			HibernateUtil.closeSession();
		}

	}

	public List<Modulo> listaModulos() throws ControllerException {

		List<Modulo> modulos = new ArrayList<Modulo>();

		try {
			// --- Rotina para buscar todos os objetos do banco
			modulos = moduloDAO.allModulos();

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return modulos;
	}

	public List<Modulo> pesquisa(Modulo filtro) throws ControllerException {

		List<Modulo> modulos = new ArrayList<Modulo>();

		try {
			// --- ROTINA QUE BUSCA OS MÓDULOS SELECIONADOS
			modulos = moduloDAO.pesquisaModulo(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return modulos;
	}

	public List<Modulo> pesquisaPorCliente(Cliente filtro_cliente) throws ControllerException {

		List<Modulo> modulos = new ArrayList<Modulo>();

		try {
			// --- Rotina para buscar todos os usuários do banco
			modulos = moduloDAO.pesquisaModuloPorCliente(filtro_cliente);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return modulos;
	}
	
}
