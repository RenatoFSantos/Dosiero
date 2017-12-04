package _dao.interfaces;

import java.util.List;

import _model.vo.Acervo;
import _model.vo.Descritor;
import exception.DaoException;

public interface IDescritorDAO extends IGenericDAO<Descritor, Integer> 
{
	List<Descritor> allDescritors() throws DaoException;

	List<Descritor> pesquisaDescritor(Descritor filtro) throws DaoException;

	List<Descritor> pesquisaDescritorPorAcervo(Acervo filtro) throws DaoException;
}
