package _business;

import java.util.ArrayList;
import java.util.List;

import _dao.impl.PerfilDAO;
import _dao.impl.PerfilPaginaDAO;
import _dao.interfaces.IPerfilDAO;
import _dao.interfaces.IPerfilPaginaDAO;
import _dao.util.HibernateUtil;
import _model.vo.AcervoDescritor;
import _model.vo.Perfil;
import _model.vo.PerfilPagina;
import exception.ControllerException;
import exception.DaoException;

public class PerfilManager {

	private IPerfilDAO perfilDAO = new PerfilDAO();
	private IPerfilPaginaDAO perfilPaginaDAO = new PerfilPaginaDAO();

	public PerfilManager() {
		HibernateUtil.getSession();
	}

	public void salvar(Perfil perfil) throws ControllerException {


		try {
			HibernateUtil.beginTransaction();
			
			perfilDAO.save(perfil);
			
			// --- EXCLUINDO TODAS AS PAGINAS RELACIONADAS AO PERFIL
			// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			excluiPaginasPorPerfil(perfil);
			// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------

			// --- VERIFICA SE EXISTE NOVAS PAGINAS RELACIONADAS PARA GRAVAR E SE ESTE PERFIL ESTÁ ATIVO
			// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			if (perfil.getPerfilPaginas() != null && perfil.getPerfilPaginas().size() > 0 && perfil.getPerf_in_delecao() == 0)
			{
				PerfilPagina novoItem;
				for (PerfilPagina item : perfil.getPerfilPaginas())
				{
					novoItem = new PerfilPagina();
					novoItem.setObjPagina(item.getObjPagina());
					novoItem.setObjPerfil(item.getObjPerfil());
					novoItem.setPepa_in_acesso(item.getPepa_in_acesso());
					perfilPaginaDAO.save(novoItem);
				}
			}

			HibernateUtil.commitTransaction();
		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void deletar(Perfil perfil) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			perfilDAO.delete(perfil);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {

			HibernateUtil.closeSession();
		}

	}

	public List<Perfil> listaPerfils() throws ControllerException {

		List<Perfil> perfils = new ArrayList<Perfil>();

		try {
			// --- Rotina para buscar todos os objetos do banco
			perfils = perfilDAO.allPerfils();

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return perfils;
	}

	public List<Perfil> pesquisa(Perfil filtro) throws ControllerException {

		List<Perfil> perfils = new ArrayList<Perfil>();

		try {
			// --- Rotina para buscar todos os usuários do banco
			perfils = perfilDAO.pesquisaPerfil(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return perfils;
	}

	public void excluirPagina(PerfilPagina filtro) throws ControllerException {
		try {
			// --- Rotina para buscar todos os usuários do banco
			perfilPaginaDAO.delete(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public boolean excluiPaginasPorPerfil(Perfil filtro) throws ControllerException {
		boolean result = true;
		try {
			// --- ROTINA PARA EXCLUIR AS PÁGINAS ATRELADAS AO PERFIL
			List<PerfilPagina> lista = new ArrayList<PerfilPagina>();
			lista = perfilPaginaDAO.pesquisaPaginaPorPerfil(filtro);
			if (lista != null && lista.size()>0) {
				for (PerfilPagina perfPag : lista) {
					perfilPaginaDAO.delete(perfPag);
				}
			}
		} catch (DaoException e) {
			result = false;
			throw new ControllerException(e);
		}
		return result;
	}

}
