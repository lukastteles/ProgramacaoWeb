package com.br.uepb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.CaronaDAO;
import com.br.uepb.dao.hibernateUtil.HibernateUtil;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class CaronaDAOImpl implements CaronaDAO{
	
	//Funcoes para a base de dados
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session;
	private Transaction transaction;
	private Criteria criteria;
	
	//Variáveis temporárias para controlar o Id, que serão retiradas quando houver BD
	public int idCarona = 1;
	public int idPontoEncontro = 1;
	
	final static Logger logger = Logger.getLogger(CaronaDAOImpl.class);

	ArrayList<CaronaDomain> listaCaronas = new ArrayList<CaronaDomain>();
	
	//instancia Singleton de CaronaDAOImpl
	private static CaronaDAOImpl caronaDAOImpl;
	
	//método para pegar instancia de CaronaDAOImpl
	public static CaronaDAOImpl getInstance(){
		if(caronaDAOImpl == null){
			caronaDAOImpl = new CaronaDAOImpl();
			return caronaDAOImpl;
		}else{
			return caronaDAOImpl;
		}
	}
	
	//Construtor de CaronaDAOImpl
	private CaronaDAOImpl(){ }

	
	@Override
	public void addCarona(CaronaDomain carona) throws Exception {
		//listaCaronas.add(carona);
		try{
			session = sessionFactory.openSession();	
			transaction = session.beginTransaction();
			session.save(carona);
			transaction.commit();
			session.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Override
	public List<CaronaDomain> listCaronas() throws Exception {
		List<CaronaDomain> caronas;		
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(CaronaDomain.class);
			//criteria.addOrder(Property.forName("id").asc());
			criteria.addOrder(Order.asc("id"));
			caronas = (ArrayList<CaronaDomain>) criteria.list();
			session.close();
			return caronas;
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
		
//		for (CaronaDomain carona : listaCaronas) {			
//				caronas.add(carona);
//		}
				
//		return caronas;					
	}

	@Override
	public List<CaronaDomain> listCaronas(String origem, String destino) throws Exception {
		ArrayList<CaronaDomain> caronas;// = new ArrayList<CaronaDomain>();
		
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(CaronaDomain.class);
			criteria.add(Restrictions.eq("origem", origem));
			criteria.add(Restrictions.eq("destino", destino));
			criteria.addOrder(Property.forName("id").asc());			 
			caronas = (ArrayList<CaronaDomain>)criteria.list();		  
			session.close();
			return caronas;
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
		
		/*
		for (CaronaDomain carona : listaCaronas) {
			if ( (carona.getOrigem().equals(origem)) &&
				 (carona.getDestino().equals(destino)) ) {
				caronas.add(carona);
			}
		}			
		return caronas;
		*/					
	}
	
	@Override
	public List<CaronaDomain> listCaronasByOrigem(String origem) throws Exception {
		ArrayList<CaronaDomain> caronas;// = new ArrayList<CaronaDomain>();
		
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(CaronaDomain.class);
			criteria.add(Restrictions.eq("origem", origem));
			//criteria.addOrder(Property.forName("id").asc());
			criteria.addOrder(Order.asc("id"));
			caronas = (ArrayList<CaronaDomain>)criteria.list();
			session.close();
			return caronas;
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
		
		/* for (CaronaDomain carona : listaCaronas) {
			if (carona.getOrigem().equals(origem)) {
				caronas.add(carona);
			}
		}
				
		return caronas;
		*/	
	}

	@Override
	public List<CaronaDomain> listCaronasByDestino(String destino) throws Exception {
		ArrayList<CaronaDomain> caronas; // = new ArrayList<CaronaDomain>();

		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(CaronaDomain.class);
			criteria.add(Restrictions.eq("destino", destino));
			criteria.addOrder(Property.forName("id").asc());
			caronas = (ArrayList<CaronaDomain>)criteria.list();
			session.close();
			return caronas;
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
		
		/* for (CaronaDomain carona : listaCaronas) {
			if (carona.getDestino().equals(destino)) {
				caronas.add(carona);
			}
		}
				
		return caronas;
		*/	
	}

	@Override
	public CaronaDomain getCarona(String idCarona) throws Exception  {		
		//tratamento para verificar de o IdCarona está null ou vazio
		if (idCarona == null) {
			logger.debug("getCarona() Exceção: "+MensagensErro.CARONA_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.CARONA_INVALIDA);
		}
		if (idCarona.trim().equals("")) {
			logger.debug("getCarona() Exceção: "+MensagensErro.CARONA_INEXISTENTE);
			throw new ProjetoCaronaException(MensagensErro.CARONA_INEXISTENTE);
		}
		
//		for (CaronaDomain carona : listaCaronas) {
//			if (carona.getID().equals(idCarona)) {
//				return carona;
//			}
//		
		CaronaDomain carona;
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(CaronaDomain.class);
			criteria.add(Restrictions.eq("id", idCarona));
			carona = (CaronaDomain) criteria.uniqueResult();
			session.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
		
		if(carona != null){
			return carona;
		}else{				
			//se não entrou no for é sinal que não existe nenhuma carona com esse parametro cadastrada
			logger.debug("getCarona() Exceção: "+MensagensErro.CARONA_INEXISTENTE);
			throw new ProjetoCaronaException(MensagensErro.CARONA_INEXISTENTE);
		}
	}
	
	//colocar na interface
	public List<CaronaDomain> getHistoricoDeCaronas(String login) throws Exception{
		ArrayList<CaronaDomain> historicoCaronas;
		
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(CaronaDomain.class);
			criteria.add(Restrictions.eq("idSessao", login));
			historicoCaronas = (ArrayList<CaronaDomain>) criteria.list();
			session.close();
			return historicoCaronas;
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Override
	public void apagaCaronas(){
		logger.debug("apagando lista de caronas");
		session = sessionFactory.openSession();	
		transaction = session.beginTransaction();
		session.createQuery("delete from CaronaDomain").executeUpdate();
		transaction.commit();
		session.close();
//		if (listaCaronas.size() > 0) {
//			listaCaronas.removeAll(listaCaronas);
//		}		
	}
	
	@Override
	public void atualizaCarona(CaronaDomain carona){
		try 
		{
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(carona);
		    session.getTransaction().commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		session.close();
	}
	
}
