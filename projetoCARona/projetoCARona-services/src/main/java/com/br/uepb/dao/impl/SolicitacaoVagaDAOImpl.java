package com.br.uepb.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.SolicitacaoVagaDAO;
import com.br.uepb.dao.hibernateUtil.HibernateUtil;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class SolicitacaoVagaDAOImpl implements SolicitacaoVagaDAO {
	
	final static Logger logger = Logger.getLogger(SolicitacaoVagaDAOImpl.class);
	
	//Funcoes para a base de dados
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private Session session;
	private Transaction transaction;
	private Criteria criteria;
	
	public int idSolicitacao = 1;
	//ArrayList<SolicitacaoVagaDomain> listaSolicitacaoVagas = new ArrayList<SolicitacaoVagaDomain>();
	
	private static SolicitacaoVagaDAOImpl solocitacaoVagaDAOImpl;
	
	public static SolicitacaoVagaDAOImpl getInstance(){
		if(solocitacaoVagaDAOImpl == null){
			solocitacaoVagaDAOImpl = new SolicitacaoVagaDAOImpl();
			return solocitacaoVagaDAOImpl;
		}else{
			return solocitacaoVagaDAOImpl;
		}
	}
	
	@Override
	public void addSolicitacaoVaga(SolicitacaoVagaDomain solicitacaoVaga) throws Exception {
		//listaSolicitacaoVagas.add(solicitacaoVaga);
		try{
			session = sessionFactory.openSession();	
			transaction = session.beginTransaction();
			session.save(solicitacaoVaga);
			transaction.commit();
			session.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	@Override
	public void deleteSolicitacaoVaga(String idSolicitacao) throws Exception{		
		SolicitacaoVagaDomain solicitacaoVaga = getSolicitacaoVaga(idSolicitacao);
		//listaSolicitacaoVagas.remove(solicitacaoVaga);
		session = sessionFactory.openSession();	
		transaction = session.beginTransaction();
		session.delete(solicitacaoVaga);
		transaction.commit();
		session.close();
	}
	
	@Override
	public SolicitacaoVagaDomain getSolicitacaoVaga(String idSolicitacao) throws Exception {
		/* for (SolicitacaoVagaDomain solicitacaoVagaDomain : listaSolicitacaoVagas) {
			if (solicitacaoVagaDomain.getId().equals(idSolicitacao)) {
				return solicitacaoVagaDomain;
			}			
		}*/		
		SolicitacaoVagaDomain SolicitacaoVagaDomain;// = new ArrayList<CaronaDomain>();
		
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(SolicitacaoVagaDomain.class);
			criteria.add(Restrictions.eq("id", idSolicitacao));
			SolicitacaoVagaDomain = (SolicitacaoVagaDomain) criteria.uniqueResult();
			session.close();
			return SolicitacaoVagaDomain;
		}catch(Exception e){
			//throw e;
			logger.debug("getSolicitacaoVaga() Exceção: "+MensagensErro.SOLICITACAO_INEXISTENTE);
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INEXISTENTE);
		}
		
	}
	
	@Override
	public ArrayList<SolicitacaoVagaDomain> getSolicitacoesConfirmadas(String idCarona) throws Exception{
		ArrayList<SolicitacaoVagaDomain> solicitacoesCarona = new ArrayList<SolicitacaoVagaDomain>();
		/*for (SolicitacaoVagaDomain solicitacao : listaSolicitacaoVagas) {
			if ((solicitacao.getIdCarona().equals(idCarona)) && (solicitacao.getFoiAceita())) {
				solicitacoesCarona.add(solicitacao);
			}
		}*/
		
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(SolicitacaoVagaDomain.class);
			criteria.add(Restrictions.eq("idCarona", idCarona));
			solicitacoesCarona = (ArrayList<SolicitacaoVagaDomain>)criteria.list();
			session.close();
			return solicitacoesCarona;
		}catch(Exception e){
			throw e;
		}
		
	}
	
	@Override
	public ArrayList<SolicitacaoVagaDomain> getSolicitacoesPendentes(String idCarona) throws Exception{
		ArrayList<SolicitacaoVagaDomain> solicitacoesCarona = new ArrayList<SolicitacaoVagaDomain>();
		/*for (SolicitacaoVagaDomain solicitacao : listaSolicitacaoVagas) {
			if ((solicitacao.getIdCarona().equals(idCarona)) && (!solicitacao.getFoiAceita())) {
				solicitacoesCarona.add(solicitacao);
			}
		}*/
		
		try{
			session = sessionFactory.openSession();
			criteria = session.createCriteria(SolicitacaoVagaDomain.class);
			criteria.add(Restrictions.eq("idCarona", idCarona));
			criteria.add(Restrictions.eq("foiAceita", false));
			solicitacoesCarona = (ArrayList<SolicitacaoVagaDomain>)criteria.list();
			session.close();
			return solicitacoesCarona;
		}catch(Exception e){
			throw e;
		}		
	}

	@Override
	public void apagaSolicitacoes(){
		logger.debug("apagando lista de solicitacoes");
		session = sessionFactory.openSession();	
		transaction = session.beginTransaction();
		session.createQuery("delete from SolicitacaoVagaDomain").executeUpdate();
		transaction.commit();
		session.close();
		
		//if (listaSolicitacaoVagas.size() > 0) {			
		//	listaSolicitacaoVagas.removeAll(listaSolicitacaoVagas);
		//}
	}
	
	@Override
	public void atualizaSolicitacaoVaga(SolicitacaoVagaDomain solicitacaoVaga){
		try 
		{
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(solicitacaoVaga);
		    session.getTransaction().commit();
		}
		catch (Exception e) 
		{
		    e.printStackTrace();
		}

		session.close();
	}
}
