package com.br.uepb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.br.uepb.dao.InteresseEmCaronaDAO;
import com.br.uepb.dao.hibernateUtil.HibernateUtil;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.InteresseEmCaronaDomain;

public class InteresseEmCaronaDAOImpl implements InteresseEmCaronaDAO{

	//Funcoes para a base de dados
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session;
	private Transaction transaction;
	private Criteria criteria;
	
	private static InteresseEmCaronaDAOImpl interesseEmCaronaDAOImpl;
	
	public static InteresseEmCaronaDAOImpl getInstance() {
		if(interesseEmCaronaDAOImpl == null){
			interesseEmCaronaDAOImpl = new InteresseEmCaronaDAOImpl();
		}
		return interesseEmCaronaDAOImpl;
	}
	
	private InteresseEmCaronaDAOImpl() {}

	@Override
	public int addIntereseEmCarona(InteresseEmCaronaDomain interesse) throws Exception {
		int id;
		try{
			session = sessionFactory.openSession();	
			transaction = session.beginTransaction();
			id = (Integer) session.save(interesse);
			transaction.commit();
			session.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
		
		return id;
	}

	@Override
	public List<InteresseEmCaronaDomain> getInteresseEmCaronas(String idSessao) throws Exception {
		List<InteresseEmCaronaDomain> interesseEmCaronas;
		
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(InteresseEmCaronaDomain.class);
			criteria.add(Restrictions.eq("idSessao", idSessao));			 
			interesseEmCaronas = (ArrayList<InteresseEmCaronaDomain>) criteria.list();		  
			session.close();
			return interesseEmCaronas;
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Override
	public void apagaInteresses() {
		//logger.debug("apagando lista de interesse em carona");
		session = sessionFactory.openSession();	
		transaction = session.beginTransaction();
		session.createQuery("delete from InteresseEmCaronaDomain").executeUpdate();
		transaction.commit();
		session.close();
	}
	
	

	
}
