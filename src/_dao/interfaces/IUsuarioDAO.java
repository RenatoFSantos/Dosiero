package _dao.interfaces;

import java.util.List;
import java.util.Map;

import _model.vo.Categoria;
import _model.vo.Usuario;
import exception.DaoException;

public interface IUsuarioDAO extends IGenericDAO<Usuario, Integer> 
{
	Usuario findByLoginPwd(Usuario user) throws DaoException;

	List<Usuario> allUsuarios() throws DaoException;

	List<Usuario> pesquisaUsuario(Usuario filtro) throws DaoException;
	
	Map<Object, String> acessoUsuario(Integer idCliente, Integer idUsuario) throws DaoException;

	List<Usuario> listaUsuarioPorCategoria(Categoria filtro) throws DaoException;
	
	List<Usuario> listaUsuarioPorMatricula(String filtro) throws DaoException;

	List<Usuario> listaUsuarioPorNome(String filtro) throws DaoException;
	
	List<Usuario> listaUsuarioPorId(Integer filtro) throws DaoException;

}
