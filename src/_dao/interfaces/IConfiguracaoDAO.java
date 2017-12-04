package _dao.interfaces;

import _model.vo.Parametro;
import exception.DaoException;

public interface IConfiguracaoDAO extends IGenericDAO<Parametro, Integer> 
{
	Parametro allParametros() throws DaoException;
}
