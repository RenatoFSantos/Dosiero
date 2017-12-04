package _dao.interfaces;

import java.util.List;

import _model.vo.Categoria;
import _model.vo.CategoriaTipo;
import _model.vo.Tipo;
import exception.DaoException;

public interface ICategoriaTipoDAO extends IGenericDAO<CategoriaTipo, Integer> 
{
	List<CategoriaTipo> allCategoriaTipos() throws DaoException;

	List<CategoriaTipo> pesquisaCategoriaTipoPorCategoria(Categoria filtro) throws DaoException;
	
	List<CategoriaTipo> pesquisaCategoriaTipoPorTipo(Tipo filtro) throws DaoException;

	List<CategoriaTipo> pesquisaCategoriaTipo(CategoriaTipo filtro) throws DaoException;

	CategoriaTipo validaCategoriaTipo(CategoriaTipo filtro) throws DaoException;

}
