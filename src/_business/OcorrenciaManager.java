package _business;

import java.util.ArrayList;
import java.util.List;

import _dao.impl.OcorrenciaDAO;
import _dao.interfaces.IOcorrenciaDAO;
import _dao.util.HibernateUtil;
import _model.vo.Acervo;
import _model.vo.Ocorrencia;
import _model.vo.Usuario;
import exception.ControllerException;
import exception.DaoException;

public class OcorrenciaManager {

	private IOcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO();

	public OcorrenciaManager() {
	}

	public void salvar(Ocorrencia ocorrencia) throws ControllerException {
		HibernateUtil.getSession();

		try {
			HibernateUtil.beginTransaction();
			ocorrenciaDAO.save(ocorrencia);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void deletar(Ocorrencia ocorrencia) throws ControllerException {
		HibernateUtil.getSession();

		try {
			HibernateUtil.beginTransaction();
			ocorrenciaDAO.delete(ocorrencia);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {

			HibernateUtil.closeSession();
		}

	}

	public List<Ocorrencia> listaOcorrencias() throws ControllerException {
		HibernateUtil.getSession();

		List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();

		try {
			// --- Rotina para buscar todos os objetos do banco
			ocorrencias = ocorrenciaDAO.allOcorrencias();

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return ocorrencias;
	}

	public List<Ocorrencia> pesquisa(Ocorrencia filtro) throws ControllerException {
		HibernateUtil.getSession();

		List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();

		try {
			// --- Rotina para buscar todos os usuários do banco
			ocorrencias = ocorrenciaDAO.pesquisaOcorrencia(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return ocorrencias;
	}

	public List<Ocorrencia> listaOcorrenciasPorUsuario(Usuario filtro) throws ControllerException {
		HibernateUtil.getSession();

		List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();

		try {
			// --- Rotina para buscar todos os usuários do banco
			ocorrencias = ocorrenciaDAO.pesquisaOcorrenciaPorUsuario(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return ocorrencias;
	}

	public List<Ocorrencia> listaOcorrenciasPorAcervo(Acervo filtro) throws ControllerException {
		HibernateUtil.getSession();

		List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();

		try {
			// --- Rotina para buscar todos os usuários do banco
			ocorrencias = ocorrenciaDAO.pesquisaOcorrenciaPorAcervo(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return ocorrencias;
	}
}
