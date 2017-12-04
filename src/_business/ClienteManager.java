package _business;

import java.util.ArrayList;
import java.util.List;

import _dao.impl.ClienteDAO;
import _dao.impl.ClienteModuloDAO;
import _dao.interfaces.IClienteDAO;
import _dao.interfaces.IClienteModuloDAO;
import _dao.util.HibernateUtil;
import _model.vo.Cliente;
import _model.vo.ClienteModulo;
import exception.ControllerException;
import exception.DaoException;

public class ClienteManager {

	private IClienteDAO clienteDAO = new ClienteDAO();
	private IClienteModuloDAO clienteModuloDAO = new ClienteModuloDAO();

	public ClienteManager() {
		HibernateUtil.getSession();
	}

	public void salvar(Cliente cliente) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			
			
			clienteDAO.save(cliente);
			
			// --- EXCLUINDO OS MODULOS RELACIONADOS ANTES DE GRAVAR
			excluiTodosModulosDoCliente(cliente);
			
			// --- VERIFICA SE EXISTE NOVOS MODULOS PARA GRAVAR E SE ESTE CLIENTE ESTÁ ATIVO
			if (cliente.getClienteModulos() != null && cliente.getClienteModulos().size() > 0 && cliente.getClie_in_delecao() == 0)
			{
				for (ClienteModulo itemModulo : cliente.getClienteModulos()) 
				{
					clienteModuloDAO.save(itemModulo);
				}
			}
			
			HibernateUtil.commitTransaction();

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public boolean excluiTodosModulosDoCliente(Cliente filtro) throws ControllerException {
		boolean result = true;
		List<ClienteModulo> lista = new ArrayList<ClienteModulo>();
		try {
			lista = clienteModuloDAO.pesquisaClienteModuloPorCliente(filtro);
			if (lista != null && lista.size()>0) {
				for (ClienteModulo clienteModulo : lista) {
				clienteModuloDAO.delete(clienteModulo);
				}
			}
		} catch (DaoException e) {
			result = false;
			throw new ControllerException(e);
		}
		return result;
	}

	public void deletar(Cliente cliente) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			clienteDAO.delete(cliente);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {

			HibernateUtil.closeSession();
		}

	}

	public List<Cliente> listaClientes() throws ControllerException {

		List<Cliente> clientes = new ArrayList<Cliente>();

		try {
			// --- Rotina para buscar todos os objetos do banco
			clientes = clienteDAO.allClientes();

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return clientes;
	}

	public List<Cliente> pesquisa(Cliente filtro) throws ControllerException {

		List<Cliente> clientes = new ArrayList<Cliente>();

		try {
			// --- Rotina para buscar todos os usuários do banco
			clientes = clienteDAO.pesquisaCliente(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return clientes;
	}
	

}
