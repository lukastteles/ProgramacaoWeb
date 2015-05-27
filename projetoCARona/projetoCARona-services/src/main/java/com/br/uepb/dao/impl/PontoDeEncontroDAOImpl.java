package com.br.uepb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.PontoDeEncontroDAO;
import com.br.uepb.dao.hibernateUtil.HibernateUtil;
import com.br.uepb.domain.PontoDeEncontroDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class PontoDeEncontroDAOImpl implements PontoDeEncontroDAO{

	//Funcoes para a base de dados
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session;
	private Transaction transaction;
	private Criteria criteria;
	
	final static Logger logger = Logger.getLogger(PontoDeEncontroDAOImpl.class);
	
	//instancia Singleton de PontoDeEncontroImpl
	private static PontoDeEncontroDAOImpl pontoDeEncontroDAOImpl;
		
	//método para pegar instancia de PontoDeEncontroImpl
	public static PontoDeEncontroDAOImpl getInstance(){
		if(pontoDeEncontroDAOImpl == null){
			pontoDeEncontroDAOImpl = new PontoDeEncontroDAOImpl();
			return pontoDeEncontroDAOImpl;
		}else{
			return pontoDeEncontroDAOImpl;
		}
	}
		
	//Construtor de PontoDeEncontroImpl
	private PontoDeEncontroDAOImpl() {}
	
	@Override
	public void addPontoDeEncontro(PontoDeEncontroDomain ponto) throws Exception{
		//pontoDeEncontro.add(ponto);
		try{
			session = sessionFactory.openSession();	
			transaction = session.beginTransaction();
			session.save(ponto);
			transaction.commit();
			session.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Override
	public List<PontoDeEncontroDomain> getPontosSugestao(String idCarona, String idSugestao) throws Exception {
		List<PontoDeEncontroDomain> pontosSugestao;
		
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(PontoDeEncontroDomain.class);
			criteria.add(Restrictions.eq("idCarona", idCarona));
			criteria.add(Restrictions.eq("idSugestao", idSugestao));
			pontosSugestao = (ArrayList<PontoDeEncontroDomain>)criteria.list();
			session.close();
			return pontosSugestao;
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Override
	public List<PontoDeEncontroDomain> listPontos(String idCarona) throws Exception{
		List<PontoDeEncontroDomain> pontos;
		
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(PontoDeEncontroDomain.class);
			criteria.add(Restrictions.eq("idCarona", idCarona));
			pontos = (ArrayList<PontoDeEncontroDomain>)criteria.list();
			session.close();
			return pontos;
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Override
	public PontoDeEncontroDomain getPontoEncontroByNome(String idCarona, String ponto) throws Exception{		
		PontoDeEncontroDomain pontoDeEncontro;
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(PontoDeEncontroDomain.class);
			criteria.add(Restrictions.eq("idCarona", idCarona));
			criteria.add(Restrictions.eq("pontoDeEncontro", ponto));
			pontoDeEncontro = (PontoDeEncontroDomain) criteria.uniqueResult();
			session.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
		
		if(pontoDeEncontro != null){
			return pontoDeEncontro;
		}else{				
			logger.debug("getPontoEncontroByNome() Exceção: "+MensagensErro.PONTO_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.PONTO_INVALIDO);
		}
	}
	
	@Override
	public List<PontoDeEncontroDomain> getPontoEncontroAceitos(String idCarona) throws Exception {
		List<PontoDeEncontroDomain> pontosAceitos;
		
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(PontoDeEncontroDomain.class);
			criteria.add(Restrictions.eq("idCarona", idCarona));
			criteria.add(Restrictions.eq("foiAceita", true));
			pontosAceitos = (ArrayList<PontoDeEncontroDomain>)criteria.list();
			session.close();
			return pontosAceitos;
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Override
	public Boolean pontoExiste(String idCarona, String ponto) throws Exception{
		PontoDeEncontroDomain pontoDeEncontro;
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(PontoDeEncontroDomain.class);
			criteria.add(Restrictions.eq("idCarona", idCarona));
			criteria.add(Restrictions.eq("pontoDeEncontro", ponto));
			pontoDeEncontro = (PontoDeEncontroDomain) criteria.uniqueResult();
			session.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
		
		if(pontoDeEncontro != null){
			return false;
		}else{				
			return true;
		}
	}
	
	@Override
	public void apagaPontosEncontro(){
		logger.debug("apagando lista de Pontos de Encontro");
		session = sessionFactory.openSession();	
		transaction = session.beginTransaction();
		session.createQuery("delete from PontoDeEncontroDomain").executeUpdate();
		transaction.commit();
		session.close();
	}


	@Override
	public void atualizaPonto(PontoDeEncontroDomain ponto) {
		try 
		{
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(ponto);
		    session.getTransaction().commit();
		}
		catch (Exception e) 
		{
		    e.printStackTrace();
		}

		session.close();
		
	}
}
