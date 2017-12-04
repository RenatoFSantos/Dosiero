package _dao.interfaces;

import java.util.List;

import _model.vo.Perfil;
import _model.vo.PerfilPagina;
import exception.DaoException;

public interface IPerfilPaginaDAO extends IGenericDAO<PerfilPagina, Integer> 
{
	List<PerfilPagina> allPerfilPaginas() throws DaoException;

	List<PerfilPagina> pesquisaPerfilPaginaPorPerfil(Perfil filtro) throws DaoException;
		
	boolean excluiPaginasPorPerfil(Perfil filtro) throws DaoException;

	List<PerfilPagina> pesquisaPaginaPorPerfil(Perfil filtro) throws DaoException;

}
