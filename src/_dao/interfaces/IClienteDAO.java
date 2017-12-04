package _dao.interfaces;

import java.util.List;

import _model.vo.Cliente;
import exception.DaoException;

public interface IClienteDAO extends IGenericDAO<Cliente, Integer> 
{
	List<Cliente> allClientes() throws DaoException;

	List<Cliente> pesquisaCliente(Cliente filtro) throws DaoException;

}
