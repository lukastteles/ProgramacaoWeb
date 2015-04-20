package com.br.uepb.business;

import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.domain.SessaoDomain;

public class SessaoBusiness {

	/**
	 * Classe para as regras de negócio referentes à sessao
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 20/04/2015
	 */
	SessaoDomain sessaoDomain;
	
	/**
	 * Método para iniciar uma sessão do usuário
	 * @param login String - Login do usuário da sessão
	 * @param senha String - Senha do usuário da sessão
	 * @return String - Id da sessão
	 * @throws Exception - Lança exceção se qualquer parâmetro informado for null ou vazio ou se não existir nenhum usuário com os login e senha informados 
	 */
	public String abrirSessao(String login, String senha) throws Exception {
		sessaoDomain = new SessaoDomain(login, senha);
		SessaoDAOImpl.getInstance().addSessao(sessaoDomain);
		return 	SessaoDAOImpl.getInstance().getSessao(login).getLogin();
	}
		
	/**
	 * Método para encerrar a sessão do usuário
	 * Após encerrar a sessão a conexão com os dados relacionados ao usuário atual será encerrada
	 * @param login String - Login do usuário
	 * @throws Exception - Lança exceção se o login foi null ou vazio ou se não existir uma sessao aberta com o login informado
	 */
	public void encerrarSessao(String login) throws Exception{
		SessaoDAOImpl.getInstance().deleteSessao(login);		
	}
	
}
