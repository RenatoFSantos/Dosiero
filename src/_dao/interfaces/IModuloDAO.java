package _dao.interfaces;

import java.util.List;

import _model.vo.Cliente;
import _model.vo.ClienteModulo;
import _model.vo.Modulo;
import exception.DaoException;

public interface IModuloDAO extends IGenericDAO<Modulo, Integer> 
{
	List<Modulo> allModulos() throws DaoException;

	List<Modulo> pesquisaModulo(Modulo filtro) throws DaoException;

	List<Modulo> pesquisaModuloPorCliente(Cliente filtro_cliente) throws DaoException;

}
