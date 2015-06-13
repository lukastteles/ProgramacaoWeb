package com.br.uepb.business;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.domain.SessaoDomain;

/**
 * Classe para as regras de negócio referentes à sessao
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
@Component
public class SessaoBusiness {

	final static Logger logger = Logger.getLogger(SessaoBusiness.class);
	SessaoDomain sessaoDomain;
	
	/**
	 * Método para iniciar uma sessão do usuário
	 * @param login Login do usuário da sessão
	 * @param senha Senha do usuário da sessão
	 * @return Id da sessão
	 * @throws Exception Lança exceção se qualquer parâmetro informado for null ou vazio ou se não existir nenhum usuário com os login e senha informados 
	 */
	public String abrirSessao(String login, String senha) throws Exception {
		logger.debug("abrindo sessão "+login);
		sessaoDomain = new SessaoDomain(login, senha);
		SessaoDAOImpl.getInstance().addSessao(sessaoDomain);
		logger.debug("sessão "+login+" aberta");
		return 	SessaoDAOImpl.getInstance().getSessao(login).getLogin();
	}
		
	/**
	 * Método para encerrar a sessão do usuário
	 * Após encerrar a sessão a conexão com os dados relacionados ao usuário atual será encerrada
	 * @param login Login do usuário
	 * @throws Exception Lança exceção se o login foi null ou vazio ou se não existir uma sessao aberta com o login informado
	 */
	public void encerrarSessao(String login) throws Exception{
		logger.debug("encerrando sessão "+login);
		SessaoDAOImpl.getInstance().deleteSessao(login);
		logger.debug("sessão "+login+" encerrada");
	}
	
}
