package _dao.interfaces;

import java.util.Date;
import java.util.List;

import _model.vo.Acervo;
import _model.vo.Classe;
import _model.vo.Cliente;
import _model.vo.Descritor;
import _model.vo.Documento;
import _model.vo.Tipo;
import _model.vo.Unidade;
import exception.DaoException;

public interface IAcervoDAO extends IGenericDAO<Acervo, Integer> 
{
	List<Acervo> allAcervos() throws DaoException;

	List<Acervo> pesquisaAcervo(Acervo filtro) throws DaoException;
	
	List<Acervo> pesquisaAssunto(Acervo filtro) throws DaoException;

	List<Acervo> listaAcervoPorClasse(Classe filtro) throws DaoException;

	List<Acervo> listaAcervoPorDescritor(Descritor filtro) throws DaoException;

	List<Acervo> listaAcervoPorCliente(Cliente filtro) throws DaoException;

	List<Acervo> listaAcervoPorUnidade(Unidade unidade, Date dataIni, Date dataFim) throws DaoException;

	List<Acervo> listaAcervoPorUnidadeOrdemLocal(Unidade unidade, Date dataIni, Date dataFim) throws DaoException;

	List<Acervo> listaAcervoPorDigitacao(Cliente cliente, Unidade unidade, Classe classe, Date dataIni, Date dataFim)  throws DaoException;

	List<Acervo> listaAcervoPorClasse(Cliente cliente, Classe classe, Date dataIni, Date dataFim)  throws DaoException;

	List<Acervo> listaAcervoPorDataInclusao(Cliente cliente, Date dataIni, Date dataFim)  throws DaoException;

	List<Acervo> listaAcervoPorCodigoAcervo(Cliente cliente, Integer codIni, Integer codFim)  throws DaoException;

	List<Acervo> listaAcervoMovimentacao(Cliente cliente, Unidade unidade, Classe classe, Date dataIni, Date dataFim)  throws DaoException;

	Integer ultimoID() throws DaoException;

	Acervo pesquisaAcervoPorCodBarra(String filtro) throws DaoException;

	List<Acervo> pesquisaAcervosPorTipo(Tipo filtro) throws DaoException;
}
