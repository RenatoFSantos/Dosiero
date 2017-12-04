package _business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import _dao.impl.UsuarioDAO;
import _dao.interfaces.IUsuarioDAO;
import _dao.util.HibernateUtil;
import _model.vo.Categoria;
import _model.vo.Usuario;
import exception.ControllerException;
import exception.DaoException;

public class UsuarioManager {

	private IUsuarioDAO usuarioDAO = new UsuarioDAO();

	public UsuarioManager() {
		HibernateUtil.getSession();
	}
	
	public Usuario findByLoginPwd(Usuario user) throws ControllerException {
		Usuario userEntity = null;

		try
		{
			userEntity = usuarioDAO.findByLoginPwd(user);
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
			HibernateUtil.closeSession();
		}

		return userEntity;
	}
	

	public void salvar(Usuario usuario) throws ControllerException {

		try {
			HibernateUtil.beginTransaction();
			usuarioDAO.save(usuario);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

	}

	public void deletar(Usuario usuario) throws ControllerException
	{

		try
		{
			HibernateUtil.beginTransaction();
			usuarioDAO.delete(usuario);
			HibernateUtil.commitTransaction();

		} catch (DaoException e) {
			HibernateUtil.rollbackTransaction();
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}
	}

	public List<Usuario> listaUsuarios() throws ControllerException {

		List<Usuario> usuarios = new ArrayList<Usuario>();

		try
		{
		// --- Rotina para buscar todos os usuários do banco
			usuarios = usuarioDAO.allUsuarios();
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
			HibernateUtil.closeSession();
		}
		
		return usuarios;
	}

	public List<Usuario> pesquisa(Usuario filtro) throws ControllerException {

		List<Usuario> usuarios = new ArrayList<Usuario>();

		try
		{
		// --- Rotina para buscar todos os usuários do banco
			usuarios = usuarioDAO.pesquisaUsuario(filtro);
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
			HibernateUtil.closeSession();
		}
		
		return usuarios;
	}

	public Map<Object, String> acessoUsuario(Integer idCliente, Integer idUsuario) throws ControllerException {
		// TODO Auto-generated method stub
		Map<Object, String> usuarioAcesso = new HashMap<Object, String>();
		
		try
		{
		// --- ROTINA PARA IDENTIFICAR QUAIS PÁGINAS O USUARIO TEM ACESSO
			usuarioAcesso = usuarioDAO.acessoUsuario(idCliente, idUsuario);
			
		}
		catch (DaoException e) 
		{
			throw new ControllerException(e);
		} 
		finally
		{
			HibernateUtil.closeSession();
		}
		
		return usuarioAcesso;
		
	}
	
	public List<Usuario> listaUsuarioPorCategoria(Categoria filtro) throws ControllerException {
		HibernateUtil.getSession();
		List<Usuario> usuarios = new ArrayList<Usuario>();

		try {
			// --- Rotina para buscar todos os usuários do banco
			usuarios = usuarioDAO.listaUsuarioPorCategoria(filtro);

		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return usuarios;
	}

	public List<Usuario> listaUsuarioEmprestimo(String filtro, String query) throws ControllerException {
		HibernateUtil.getSession();
		List<Usuario> usuarios = new ArrayList<Usuario>();

		try {
			if(filtro.equals("MT")) {
				// --- Selecionar usuários por matrícula
				usuarios = usuarioDAO.listaUsuarioPorMatricula(query);
			}
			if(filtro.equals("NM")) {
				// --- Selecionar usuários por nome
				usuarios = usuarioDAO.listaUsuarioPorNome(query);
			}
			if(filtro.equals("ID")) {
				// --- Selecionar usuários por id
				usuarios = usuarioDAO.listaUsuarioPorId(Integer.parseInt(query));
			}
		} catch (DaoException e) {
			throw new ControllerException(e);
		} finally {
			HibernateUtil.closeSession();
		}

		return usuarios;
	}
}
