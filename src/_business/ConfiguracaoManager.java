package _business;

import _dao.impl.ConfiguracaoDAO;
import _dao.interfaces.IConfiguracaoDAO;
import _dao.util.HibernateUtil;
import _model.vo.Parametro;
import exception.ControllerException;
import exception.DaoException;

public class ConfiguracaoManager {

	private IConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO();

	public ConfiguracaoManager() {
		HibernateUtil.getSession();
	}

	public void salvar(Parametro parametro) throws ControllerException {


		try {
			HibernateUtil.beginTransaction();
			
			configuracaoDAO.save(parametro);

			HibernateUtil.commitTransaction();
		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public Parametro listaParametros() throws ControllerException {

		Parametro parametro = new Parametro();

		try {
			// --- Rotina para buscar todos os objetos do banco
			parametro = configuracaoDAO.allParametros();

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return parametro;
	}

}
