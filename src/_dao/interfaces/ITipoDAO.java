package _dao.interfaces;

import java.util.List;

import _model.vo.Tipo;
import exception.DaoException;

public interface ITipoDAO extends IGenericDAO<Tipo, Integer> 
{
	List<Tipo> allTipos() throws DaoException;

	List<Tipo> pesquisaTipo(Tipo filtro) throws DaoException;

}
