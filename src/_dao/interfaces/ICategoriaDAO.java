package _dao.interfaces;

import java.util.List;

import _model.vo.Categoria;
import exception.DaoException;

public interface ICategoriaDAO extends IGenericDAO<Categoria, Integer> 
{
	List<Categoria> allCategorias() throws DaoException;

	List<Categoria> pesquisaCategoria(Categoria filtro) throws DaoException;

}
