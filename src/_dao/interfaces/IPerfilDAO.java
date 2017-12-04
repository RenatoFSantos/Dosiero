package _dao.interfaces;

import java.util.List;

import _model.vo.Perfil;
import exception.DaoException;

public interface IPerfilDAO extends IGenericDAO<Perfil, Integer> 
{
	List<Perfil> allPerfils() throws DaoException;

	List<Perfil> pesquisaPerfil(Perfil filtro) throws DaoException;

}
