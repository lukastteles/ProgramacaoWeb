package com.br.uepb.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.SessaoDAO;
import com.br.uepb.dao.hibernateUtil.HibernateUtil;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

@Service
public class SessaoDAOImpl implements SessaoDAO {
	
	final static Logger logger = Logger.getLogger(SessaoDAOImpl.class);
	private static SessaoDAOImpl sessaoDAOImpl;
	
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	//Lista de caronas
	ArrayList<SessaoDomain> listaSessoes = new ArrayList<SessaoDomain>();
	
	public static SessaoDAOImpl getInstance(){
		if(sessaoDAOImpl == null){
			sessaoDAOImpl = new SessaoDAOImpl();
			return sessaoDAOImpl;
		}else{
			return sessaoDAOImpl;
		}
	}
	
	@Override
	//@Transactional
	public void addSessao(SessaoDomain sessao) throws Exception {
		//verificar se existe usuario com esse login-senha		
		UsuarioDomain usuario = UsuarioDAOImpl.getInstance().getUsuario(sessao.getLogin());
				
		//Se a senha informada for diferente da senha do usuario
		if (!usuario.getSenha().equals(sessao.getSenha())) {
			logger.debug("addSessao() Excceção: "+MensagensErro.LOGIN_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.LOGIN_INVALIDO);
		}
		
		boolean sessaoExiste = false;
		for (SessaoDomain sessaoLista : listaSessoes) {
			if (sessaoLista.getLogin().equals(sessao.getLogin())){
				sessaoExiste = true;
				break;
			}
		}
		
		//So adiciona na lista se a sessao ainda não existir
		if (!sessaoExiste) {
			listaSessoes.add(sessao);
			
			//TODO: teste para abrir e fechar a sessao sessionFactory		
			Session session = sessionFactory.openSession();	
			
			session.close();	
		}
		
	}

	@Override
	public SessaoDomain getSessao(String login) throws Exception {
		if ((login == null) || (login.trim().equals(""))) {
			logger.debug("getSessao() Excceção: "+MensagensErro.SESSAO_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.SESSAO_INVALIDA);
		}
		
		for (SessaoDomain sessao : listaSessoes) {
			if (sessao.getLogin().equals(login)) {
				return sessao;
			}
		}
		logger.debug("getSessao() Excceção: "+MensagensErro.SESSAO_INEXISTENTE);
		throw new ProjetoCaronaException(MensagensErro.SESSAO_INEXISTENTE);
		
	}
	
	public void deleteSessao(String login) throws Exception{
		SessaoDomain sessaoApagar = null;
		for (SessaoDomain sessao : listaSessoes) {
			if (sessao.getLogin().equals(login)) {
				sessaoApagar = sessao;
				break;
			}
		}		
		
		if (sessaoApagar != null) {
			listaSessoes.remove(sessaoApagar);
		}
		else {
			logger.debug("deleteSessao() Excceção: "+MensagensErro.SESSAO_INEXISTENTE);
			throw new ProjetoCaronaException(MensagensErro.SESSAO_INEXISTENTE);
		}
	}
	
	public void apagaSessoes(){
		logger.debug("apagando lista de sessões");
		if (listaSessoes.size() > 0) {
			listaSessoes.removeAll(listaSessoes);
			
		}
	}
}
