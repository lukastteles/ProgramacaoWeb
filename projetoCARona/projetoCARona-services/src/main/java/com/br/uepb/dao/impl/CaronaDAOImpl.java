package com.br.uepb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.CaronaDAO;
import com.br.uepb.dao.hibernateUtil.HibernateUtil;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.InteresseEmCaronaDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class CaronaDAOImpl implements CaronaDAO{
	
	//Funcoes para a base de dados
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session;
	private Transaction transaction;
	private Criteria criteria;
	
	//Variáveis temporárias para controlar o Id, que serão retiradas quando houver BD
	//private int idCarona = 1;
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

	public int getIdCarona() throws Exception{
		int idCarona;
		String obj;
		try{
		session = sessionFactory.openSession();	
		transaction = session.beginTransaction();
		obj =  (String)session.createQuery("Select Max(id) from CaronaDomain").uniqueResult();
		transaction.commit();
		session.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
		
		if(obj == null){
			idCarona = 1;
		}else{
			idCarona = Integer.parseInt(obj);
			idCarona++;
		}
		return idCarona;
	}
	
	@Override
	public void addCarona(CaronaDomain carona) throws Exception {
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
	public List<CaronaDomain> listCaronas(String login) throws Exception {
		List<CaronaDomain> caronas;		
		try{
			session = sessionFactory.openSession();	
			transaction = session.beginTransaction();
			caronas = session.createQuery(" from CaronaDomain order by id ").list();
			transaction.commit();
			session.close();
			return caronas;
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}					
	}

	@Override
	public List<CaronaDomain> listCaronas(String login, String origem, String destino) throws Exception {
		List<CaronaDomain> caronas;
		try{
			session = sessionFactory.openSession();	
			transaction = session.beginTransaction();
			caronas = session.createQuery(" from CaronaDomain "+
										  " where origem = \'"+origem+"\' "+
										  " and destino = \'"+destino+"\' "+
										  " order by id").list();
			transaction.commit();		  
			session.close();
			return caronas;
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}				
	}
	
	@Override
	public List<CaronaDomain> listCaronasByOrigem(String login, String origem) throws Exception {
		List<CaronaDomain> caronas;
		
		try{
			session = sessionFactory.openSession();	
			transaction = session.beginTransaction();
			caronas = session.createQuery(" from CaronaDomain "+
										  " where origem = \'"+origem+"\' "+
										  " order by id").list();
			transaction.commit();
			session.close();
			return caronas;
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}	
	}

	@Override
	public List<CaronaDomain> listCaronasByDestino(String login, String destino) throws Exception {
		List<CaronaDomain> caronas;

		try{
			session = sessionFactory.openSession();	
			transaction = session.beginTransaction();
			caronas = session.createQuery(" from CaronaDomain "+
										  " where destino = \'"+destino+"\' "+
										  " order by id").list();
			transaction.commit();
			session.close();
			return caronas;
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
	}

	@Override
	public List<CaronaDomain> listCaronasMunicipais(String cidade, String origem, String destino) throws Exception {
		List<CaronaDomain> caronas;
		try{
			session = sessionFactory.openSession();	
			transaction = session.beginTransaction();
			caronas = session.createQuery(" from CaronaDomain "+
										  " where origem = \'"+origem+"\' "+
										  " and destino = \'"+destino+"\' "+
										  " and cidade = \'"+cidade+"\' "+
										  " order by id").list();
			transaction.commit();		  
			session.close();
			return caronas;
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}			
				
	}
	
	@Override
	public List<CaronaDomain> listCaronasMunicipais(String cidade) throws Exception {
		List<CaronaDomain> caronas;
		
		try{
			session = sessionFactory.openSession();	
			transaction = session.beginTransaction();
			caronas = session.createQuery(" from CaronaDomain "+
										  " where cidade = \'"+cidade+"\' "+
										  " order by id").list();
			transaction.commit();		  
			session.close();
			return caronas;
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
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
	
	@Override
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
	
	@Override
	public CaronaDomain getCaronaByInteresse(InteresseEmCaronaDomain interesseEmCaronas) throws Exception {
		List<CaronaDomain> carona;
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(CaronaDomain.class);
			criteria.add(Restrictions.eq("origem", interesseEmCaronas.getOrigem()));
			criteria.add(Restrictions.eq("destino", interesseEmCaronas.getDestino()));
			if(!interesseEmCaronas.getData().trim().equals("")){
				criteria.add(Restrictions.eq("data", interesseEmCaronas.getData()));
			}
			//criteria.add(Restrictions.between("data", inresseEmCaronas.getHoraInicio(), inresseEmCaronas.getHoraFim()));
			carona = (ArrayList<CaronaDomain>) criteria.list();
			session.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
		
		if(carona.size() != 0){
			return carona.get(0);
		}
		return null;
	}
	
	
	@Override
	public void apagaCaronas(){
		logger.debug("apagando lista de caronas");
		session = sessionFactory.openSession();	
		transaction = session.beginTransaction();
		session.createQuery("delete from CaronaDomain").executeUpdate();
		transaction.commit();
		session.close();		
	}
	
}
