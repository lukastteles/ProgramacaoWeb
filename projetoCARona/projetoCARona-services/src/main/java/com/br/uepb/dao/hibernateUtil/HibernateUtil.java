package com.br.uepb.dao.hibernateUtil;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration().configure();
				StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
				applySettings(configuration.getProperties());
				sessionFactory = configuration.buildSessionFactory(builder.build());
			} catch (Throwable ex) {
				// Log the exception.
				System.err.println("Error: Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}
		return sessionFactory;
	}

/*
	private static Session openSession() {
		System.out.println("HibernateUtil - iniciando sessao");
		Session session = getSessionFactory().openSession();
		System.out.println("HibernateUtil - "+session.isOpen());
		return session ;
	}
	

	public static void closeSession() {
	    // Close caches and connection pools
	    getSessionFactory().close();
	}
	*/
}
