package _dao.interfaces;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;

import exception.DaoException;

public interface GenericDAO<T, ID extends Serializable>
{

	public void save(T entity) throws DaoException;

	public void merge(T entity) throws DaoException;

	public void delete(T entity) throws DaoException;

	public List<T> findMany(Query query) throws DaoException;

	public T findOne(Query query) throws DaoException;

	@SuppressWarnings("rawtypes")
	public List findAll(Class clazz) throws DaoException;

	@SuppressWarnings("rawtypes")
	public T findByID(Class clazz, Integer id) throws DaoException;
}
