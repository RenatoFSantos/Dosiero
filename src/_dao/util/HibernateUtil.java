package _dao.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import exception.DaoException;

public class HibernateUtil {
	private static HibernateUtil instance = null;
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;
	private static Session hibernateSession = null;
	private static final ThreadLocal session = new ThreadLocal();

	private HibernateUtil() throws DaoException {
		try {

            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			throw new DaoException(e);
		}

	}

	public static HibernateUtil getInstance() throws DaoException {

		if (instance == null) {
			instance = new HibernateUtil();
		}
		return instance;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void beginTransaction() {
		hibernateSession.beginTransaction();
	}

	public static void commitTransaction() {
		hibernateSession.getTransaction().commit();
	}

	public static void rollbackTransaction() {
		hibernateSession.getTransaction().rollback();
	}

	public static void closeSession() {
		hibernateSession = (Session) session.get();
		if(hibernateSession.isOpen()) {
			hibernateSession.close();
		}
		session.set(hibernateSession);
	}

	public static Session getSession() {
		hibernateSession = (Session) session.get();
		try
		{
			if (hibernateSession == null || !hibernateSession.isOpen()) {
				hibernateSession = getSessionFactory().openSession();
			} 
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException(e);
		}
		session.set(hibernateSession);
		
		return hibernateSession;
	}
}
