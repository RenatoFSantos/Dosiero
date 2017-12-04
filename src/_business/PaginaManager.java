package _business;

import java.util.ArrayList;
import java.util.List;

import _dao.impl.PaginaDAO;
import _dao.impl.PerfilPaginaDAO;
import _dao.interfaces.IPaginaDAO;
import _dao.interfaces.IPerfilPaginaDAO;
import _dao.util.HibernateUtil;
import _model.vo.Modulo;
import _model.vo.Pagina;
import _model.vo.Perfil;
import _model.vo.PerfilPagina;
import exception.ControllerException;
import exception.DaoException;

public class PaginaManager {

	private IPaginaDAO paginaDAO = new PaginaDAO();
	private IPerfilPaginaDAO perfilPaginaDAO = new PerfilPaginaDAO();

	public PaginaManager() {
		HibernateUtil.getSession();
	}
	
	public void salvar(Pagina pagina) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			paginaDAO.save(pagina);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void deletar(Pagina pagina) throws ControllerException
	{

		try
		{
			HibernateUtil.beginTransaction();
			paginaDAO.delete(pagina);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public List<Pagina> listaPaginas() throws ControllerException {

		List<Pagina> paginas = new ArrayList<Pagina>();

		try
		{
		// --- ROTINA PARA BUSCAR TODOS OS REGISTROS DO OBJETO
			paginas = paginaDAO.allPaginas();
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
			HibernateUtil.closeSession();
		}
		
		return paginas;
	}

	public List<Pagina> listaPaginasPerfil(Perfil filtro) throws ControllerException {

		List<Pagina> paginas = new ArrayList<Pagina>();

		try
		{
		// --- ROTINA PARA BUSCAR TODAS AS PÁGINAS E OS STATUS DE ACESSO RELATIVOS AO RELACIONAMENTO COM PERFIL
			paginas = paginaDAO.allPaginasPerfil(filtro);
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
			HibernateUtil.closeSession();
		}
		
		return paginas;
	}

	public List<Pagina> pesquisa(Pagina filtro) throws ControllerException {

		List<Pagina> paginas = new ArrayList<Pagina>();

		try
		{
		// --- ROTINA PARA BUSCAR UM OBJETO ESPECÍFICO
			paginas = paginaDAO.pesquisaPagina(filtro);
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
			HibernateUtil.closeSession();
		}
		
		return paginas;
	}
	
	public List<Pagina> pesquisaPorModulo(Modulo filtro) throws ControllerException {

		List<Pagina> paginas = new ArrayList<Pagina>();

		try {
			// --- ROTINA PARA BUSCAR UMA LISTA DE OBJETO DE UM DETERMINADO FILTRO
			paginas = paginaDAO.pesquisaPaginaPorModulo(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return paginas;
	}
	
	public List<PerfilPagina> pesquisaPorPerfil(Perfil filtro) throws ControllerException {

		List<PerfilPagina> paginas = new ArrayList<PerfilPagina>();

		try {
			// --- ROTINA PARA BUSCAR UMA LISTA DE OBJETO DE UM DETERMINADO FILTRO
			paginas = perfilPaginaDAO.pesquisaPaginaPorPerfil(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return paginas;
	}


}
