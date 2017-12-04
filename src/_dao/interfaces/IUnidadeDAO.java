package _dao.interfaces;

import java.util.List;

import _model.vo.Unidade;
import exception.DaoException;

public interface IUnidadeDAO extends IGenericDAO<Unidade, Integer> 
{
	List<Unidade> allUnidades() throws DaoException;

	List<Unidade> pesquisaUnidade(Unidade filtro) throws DaoException;

}
