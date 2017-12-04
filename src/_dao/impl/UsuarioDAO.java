package _dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import _dao.GenericDAOImpl;
import _dao.interfaces.IUsuarioDAO;
import _dao.util.HibernateUtil;
import _model.vo.Categoria;
import _model.vo.PerfilPagina;
import _model.vo.Usuario;
import exception.DaoException;

public class UsuarioDAO extends GenericDAOImpl<Usuario, Integer> implements
		IUsuarioDAO {

	public UsuarioDAO() 
	{
		try
		{
			HibernateUtil.getInstance();
		}
		catch (DaoException e) 
		{
			// COLOCAR O LOG PARA FUNCIONAR DEPOIS
		}
	}

	public Usuario findByLoginPwd(Usuario user) throws DaoException {
		Usuario userEntity = null;

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select us from Usuario us ");
			sql.append("where us.usua_tx_login = :login ");
			sql.append("and us.usua_tx_senha = :senha ");
			sql.append("and us.usua_in_delecao=0 ");

			Query query = HibernateUtil.getSession().createQuery(sql.toString());
			query.setParameter("login", user.getUsua_tx_login());
			query.setParameter("senha", user.getUsua_tx_senha());

			userEntity = findOne(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return userEntity;
	}

	public List<Usuario> allUsuarios() throws DaoException {
		List<Usuario> usuarios = new ArrayList<Usuario>();

		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("select us from Usuario us ");
			sql.append("where us.usua_in_delecao=0 ");
			
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());

			usuarios = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return usuarios;
	}

	public List<Usuario> pesquisaUsuario(Usuario filtro) throws DaoException {
		List<Usuario> filtrousuarios = new ArrayList<Usuario>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select us from Usuario us ");
			sql.append("where upper(us.usua_nm_usuario) like :nome ");
			sql.append("and us.usua_in_delecao = 0 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("nome", "%" + filtro.getUsua_nm_usuario().toUpperCase() + "%");
			filtrousuarios = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtrousuarios;
	}

	
	public Map<Object, String> acessoUsuario(Integer idCliente, Integer idUsuario) throws DaoException {
		Map<Object, String> acessoUsuario = new HashMap<Object, String>();

		try {
			
			String sql = new String("");
			sql = "select res5.pagi_sq_id, res6.pagi_nm_pagina, res5.pepa_sq_id, res5.pepa_in_acesso, res5.perf_sq_id, res8.perf_nm_perfil from (";
			sql = sql + "    select res3.modu_sq_id, res3.pagi_sq_id, res4.pepa_sq_id, res4.pepa_in_acesso, res4.perf_sq_id from (";
			sql = sql + "       select res2.* from pagina res2 inner join";
			sql = sql + "          (select cliente_modulo.modu_sq_id from cliente_modulo where clie_sq_id= :codCliente) res1";
			sql = sql + "       on res2.modu_sq_id=res1.modu_sq_id";
			sql = sql + "    ) res3 inner join (";
			sql = sql + "       select pp.* from perfil_pagina pp where pp.perf_sq_id=(select perf.perf_sq_id from usuario usu inner join perfil perf on usu.perf_sq_id=perf.perf_sq_id where usua_sq_id= :codUsuario)";
			sql = sql + "    ) res4  on res3.pagi_sq_id = res4.pagi_sq_id";
			sql = sql + " ) res5 inner join pagina res6 on res5.pagi_sq_id=res6.pagi_sq_id";
			sql = sql + " inner join modulo res7 on res5.modu_sq_id=res7.modu_sq_id";
			sql = sql + " inner join perfil res8 on res5.perf_sq_id=res8.perf_sq_id";
			SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql).addEntity(PerfilPagina.class);
			query.setInteger("codUsuario", idUsuario);
			query.setInteger("codCliente", idCliente);
			List<PerfilPagina> results = query.list();
			Integer tamanho_lista = results.size();
			if(tamanho_lista>0) {
				for (PerfilPagina perfilPagina : results) {
		            acessoUsuario.put(perfilPagina.getObjPagina().getPagi_nm_pagina(), perfilPagina.getPepa_in_acesso());
				}
			}
		} catch (Exception e) {
			throw new DaoException(e);
		}

		return acessoUsuario;
	}
	
	public List<Usuario> listaUsuarioPorCategoria(Categoria filtro) throws DaoException {
		List<Usuario> filtrousuarios = new ArrayList<Usuario>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Usuario res1 ");
			sql.append("where res1.objCategoria.id = :codcat ");
			sql.append("and res1.usua_in_delecao = 0 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codcat", filtro.getId());
			filtrousuarios = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtrousuarios;
	}

	public List<Usuario> listaUsuarioPorMatricula(String filtro) throws DaoException {
		List<Usuario> filtrousuarios = new ArrayList<Usuario>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Usuario res1 ");
			sql.append("where res1.usua_cd_matricula like :codfiltro ");
			sql.append("and res1.usua_in_delecao = 0 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", "%" + filtro + "%");
			filtrousuarios = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtrousuarios;
	}

	public List<Usuario> listaUsuarioPorNome(String filtro) throws DaoException {
		List<Usuario> filtrousuarios = new ArrayList<Usuario>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Usuario res1 ");
			sql.append("where upper(res1.usua_nm_usuario) like :codfiltro ");
			sql.append("and res1.usua_in_delecao = 0 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", "%" + filtro.toUpperCase() + "%");
			filtrousuarios = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtrousuarios;
	}

	public List<Usuario> listaUsuarioPorId(Integer filtro) throws DaoException {
		List<Usuario> filtrousuarios = new ArrayList<Usuario>();

		try {

			StringBuffer sql = new StringBuffer("");
			sql.append("select res1 from Usuario res1 ");
			sql.append("where res1.id = :codfiltro ");
			sql.append("and res1.usua_in_delecao = 0 ");
			Query query = HibernateUtil.getSession()
					.createQuery(sql.toString());
			query.setParameter("codfiltro", filtro);
			filtrousuarios = findMany(query);

		} catch (Exception e) {
			throw new DaoException(e);
		}

		return filtrousuarios;
	}

}
