package _dao.interfaces;

import java.util.List;

import _model.vo.Cliente;
import _model.vo.ClienteModulo;
import exception.DaoException;

public interface IClienteModuloDAO extends IGenericDAO<ClienteModulo, Integer> 
{
	List<ClienteModulo> allClienteModulos() throws DaoException;

	List<ClienteModulo> pesquisaClienteModuloPorCliente(Cliente filtro) throws DaoException;
	
	boolean excluiModulosDoCliente(Cliente filtro) throws DaoException;

}
