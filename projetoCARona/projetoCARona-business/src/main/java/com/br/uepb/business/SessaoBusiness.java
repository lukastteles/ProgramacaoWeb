package com.br.uepb.business;

import org.springframework.beans.factory.annotation.Autowired;

import com.br.uepb.dao.SessaoDAO;
import com.br.uepb.domain.SessaoDomain;

public class SessaoBusiness {

	/**
	 * Classe que contém a parte lógica da Sessão
	 * @author luana Janaina / Lukas Teles 
	 * @version 0.1
	 * @since 1ª Iteração
	 */

	@Autowired
	private SessaoDAO sessaoDao;
	private int idSessao = 1;
	
	/**
	 * Método para iniciar uma sessão com o usuário
	 * @param login String - Login do Usuário da Sessão
	 * @param senha String - Senha do Usuário da Sessão
	 * @return int Id da Sessão
	 * @throws Exception 
	 */
	public int abrirSessao(String login, String senha) {		
		SessaoDomain sessaoDomain = new SessaoDomain(idSessao, login, senha);		
		sessaoDao.addSessao(sessaoDomain);
		idSessao++;
		
		return idSessao;
	}
		
	/**
	 * Método para encerrar a sessão do usuário
	 * Após encerrar a sessão todas as informacoes referentes ao usuário serão perdidas 
	 */
	public void encerrarSistema(){
		//TODO: procurar um metodo para deixar o ID da sessão
		//sessaoDao.deleteSessao(sessao);
	}
	
}
