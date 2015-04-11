package com.br.uepb.business;

import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.UsuarioDomain;

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
	public String abrirSessao(String login, String senha) throws Exception {
		//verificar se existe usuario com esse login-senha		
		UsuarioDomain usuario = UsuarioDAOImpl.getInstance().getUsuario(login);
		
		//Se a senha informada for diferente da senha do usuario
		if (!usuario.getSenha().equals(senha)) {
			throw new Exception("Login inválido");
		}
		
		sessaoDomain = new SessaoDomain(login, senha);							
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
