package _dao.interfaces;

import java.util.List;

import _model.vo.Acervo;
import _model.vo.AcervoDescritor;
import exception.DaoException;

public interface IAcervoDescritorDAO extends IGenericDAO<AcervoDescritor, Integer> 
{
	List<AcervoDescritor> allAcervoDescritors() throws DaoException;

	List<AcervoDescritor> pesquisaAcervoDescritorPorAcervo(Acervo filtro) throws DaoException;
		
	boolean excluiDescritoresPorAcervo(Acervo filtro) throws DaoException;

}
