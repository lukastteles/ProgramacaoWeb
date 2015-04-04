package com.br.uepb.business;

import com.br.uepb.domain.SessaoDomain;

public class SessaoBusiness {

	/**
	 * Classe que contém a parte lógica da Sessão
	 * @author luana Janaina / Lukas Teles 
	 * @version 0.1
	 * @since 1ª Iteração
	 */
	SessaoDomain sessaoDomain;
	
	/**
	 * Método para iniciar uma sessão com o usuário
	 * @param login String - Login do Usuário da Sessão
	 * @param senha String - Senha do Usuário da Sessão
	 * @return int Id da Sessão
	 * @throws Exception 
	 */
	public String abrirSessao(String login, String senha) {		
		//TODO: verificar se login e senha existem
		SessaoDomain sessaoDomain = new SessaoDomain(login, senha);							
		return sessaoDomain.getLogin();
	}
		
	/**
	 * Método para encerrar a sessão do usuário
	 * Após encerrar a sessão a conexão com o Banco de dados será encerrada
	 */
	public void encerrarSistema(){
		//Limpa a sessao
		if (sessaoDomain != null) {
			sessaoDomain = null;
			//TODO: encerrar conexao com banco de dados
		}
	}
	
}
