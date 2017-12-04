package _dao.interfaces;

import java.util.List;

import _model.vo.Modulo;
import _model.vo.Pagina;
import _model.vo.Perfil;
import _model.vo.PerfilPagina;
import exception.DaoException;

public interface IPaginaDAO extends IGenericDAO<Pagina, Integer> 
{
	List<Pagina> allPaginas() throws DaoException;

	List<Pagina> allPaginasPerfil(Perfil filtro) throws DaoException;

	List<Pagina> pesquisaPagina(Pagina filtro) throws DaoException;

	List<Pagina> pesquisaPaginaPorModulo(Modulo filtro) throws DaoException;

}
