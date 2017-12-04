package _dao.interfaces;

import java.util.Date;
import java.util.List;

import _model.vo.Boleto;
import _model.vo.Emprestimo;
import _model.vo.Unidade;
import _model.vo.Usuario;
import exception.DaoException;

public interface IEmprestimoDAO extends IGenericDAO<Emprestimo, Integer> 
{
	List<Emprestimo> allEmprestimos() throws DaoException;

	Emprestimo pesquisaEmprestimo(Emprestimo filtro) throws DaoException;
	
	List<Emprestimo> pesquisaEmprestimoPorBoleto(Boleto filtro) throws DaoException;

	List<Emprestimo> buscaEmprestimosEmAberto(Usuario filtro) throws DaoException;
	
	List<Emprestimo> buscaEmprestimosPorCodBarras(String filtro) throws DaoException;

	List<Emprestimo> buscaEmprestimosPorBoleto(Boleto filtro) throws DaoException;
	
	List<Emprestimo> listaEmprestimoPorUnidade(Unidade unidade, Date data_ini, Date data_fim) throws DaoException;
}
