package _dao.interfaces;

import java.util.List;

import _model.vo.Acervo;
import _model.vo.Ocorrencia;
import _model.vo.Usuario;
import exception.DaoException;

public interface IOcorrenciaDAO extends IGenericDAO<Ocorrencia, Integer> 
{
	List<Ocorrencia> allOcorrencias() throws DaoException;

	List<Ocorrencia> pesquisaOcorrencia(Ocorrencia filtro) throws DaoException;
	
	List<Ocorrencia> pesquisaOcorrenciaPorUsuario(Usuario filtro) throws DaoException;

	List<Ocorrencia> pesquisaOcorrenciaPorAcervo(Acervo filtro) throws DaoException;;

}
