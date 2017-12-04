package _dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import _dao.interfaces.IGenericDAO;
import _dao.util.HibernateUtil;
import exception.DaoException;

public abstract class GenericDAOImpl<T, ID extends Serializable> implements
		IGenericDAO<T, ID> {

	protected Session getSession() throws DaoException {
		return HibernateUtil.getSession();
	}

	public void save(T entity) throws DaoException {
		Session hibernateSession = this.getSession();
		hibernateSession.saveOrUpdate(entity);
	}

	public void merge(T entity) throws DaoException {
		Session hibernateSession = this.getSession();
		hibernateSession.merge(entity);
		hibernateSession.flush();
		hibernateSession.clear();
	}

	public void delete(T entity) throws DaoException {
		Session hibernateSession = this.getSession();
		hibernateSession.delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<T> findMany(Query query) throws DaoException {
		List<T> t;
		t = (List<T>) query.list();
		return t;
	}

	@SuppressWarnings("unchecked")
	public Set<T> findManySet(Query query) throws DaoException {
		Set<T> t;
		t = (Set<T>) query.list();
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public T findOne(Query query) throws DaoException {
		T t;
		t = (T) query.uniqueResult();
		return t;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T findByID(Class clazz, Integer id) throws DaoException {
		Session hibernateSession = this.getSession();
		T t = null;
		t = (T) hibernateSession.get(clazz, id);
		return t;
	}

	@SuppressWarnings({ "rawtypes" })
	public List findAll(Class clazz) throws DaoException {
		Session hibernateSession = this.getSession();
		List T = null;
		Query query = hibernateSession.createQuery("from " + clazz.getName());
		T = query.list();
		return T;
	}
}
