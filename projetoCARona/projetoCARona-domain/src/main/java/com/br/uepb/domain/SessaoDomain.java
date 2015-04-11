package com.br.uepb.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SessaoDomain {
	//TODO: Criar uma classe para tratar as excecoes
	
	/**
	 * Classe para registrar as sessões iniciadas
	 * @author luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 2ª Iteração
	 */
	
	//final static Logger logger = Logger.getLogger(SessaoDomain.class);

	/** Login da Sessão	 */
	@NotNull(message = "O Login não pode ser nulo")
	@Size(min=5, max=15, message="Tamanho de login inválido./n informe um login entre 5 e 15 caracteres")
	private String login;
	
	/** Senha da Sessão	 */
	@NotNull(message="O Login não pode ser nulo")
	@Size(min=6, message="A senha deve ter no mínimo 6 caracteres")
	@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}$")
	private String senha;
	
	public SessaoDomain(String login, String senha) throws Exception {
		setLogin(login);
		setSenha(senha);
	}
		
	/**
	 * Método para retorno do Login da Sessao
	 * @return String Login da Sessão
	 */
	public String getLogin() {		
		return login;
	}

	/**
	 * Método para informar o Login do Usuário
	 * O Login da sessão é definido pelo usuário no momento em que ele se cadastrar no sistema  
	 * @param login String - Login do Usuário
	 * @throws Exception Login inválido
	 */
	private void setLogin(String login) throws Exception{
		if ( (login == null) || (login.trim().equals("")) ) {
			//logger.debug("Login inválido");
			throw new Exception("Login inválido");
		}
		this.login = login;
	}
	
	/**
	 * Método para retorno da Senha do Usuário
	 * @return String Senha da Sessão
	 */
	public String getSenha() {		
		return senha;
	}

	/**
	 * Método para informar o Login da Sessão
	 * A Senha da sessão é definida pelo usuário no momento em que ele se cadastrar no sistema  
	 * @param senha String - Senha do Usuário
	 * @throws Exception Login inválido
	 */
	public void setSenha(String senha) throws Exception {
		if ( (senha == null) || (senha.trim().equals("")) ) {
			//logger.debug("Login inválido");
			throw new Exception("Login inválido");
		}
		
		this.senha = senha;
	}	
}
