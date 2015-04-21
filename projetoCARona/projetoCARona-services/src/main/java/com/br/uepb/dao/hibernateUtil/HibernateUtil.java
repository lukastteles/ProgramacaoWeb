package com.br.uepb.dao.hibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("deprecation")
public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	private static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration cfg = new AnnotationConfiguration();
				cfg.configure();
				sessionFactory = cfg.buildSessionFactory();
			} catch (Throwable ex) {
				// Log the exception.
				System.err.println("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}

		return sessionFactory;
	}
	
	public static Session openSession() {
		System.out.println("HibernateUtil - iniciando sessao");
		Session session = getSessionFactory().openSession();
		System.out.println("HibernateUtil - "+session.isOpen());
		return session ;
	}
	
	public static void closeSession() {
	    // Close caches and connection pools
	    getSessionFactory().close();
	}
}
