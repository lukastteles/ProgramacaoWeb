package com.br.uepb.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.exceptions.ProjetoCaronaException;

/**
 * Classe para registrar as sessões iniciadas
 * @author luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
public class SessaoDomain {
	
	final static Logger logger = Logger.getLogger(SessaoDomain.class);

	/** Login da sessão	 */
	@NotNull(message = "O Login não pode ser nulo")
	@Size(min=5, max=15, message="Tamanho de login inválido./n informe um login entre 5 e 15 caracteres")
	private String login;
	
	/** Senha da sessão	 */
	@NotNull(message="O Login não pode ser nulo")
	@Size(min=6, message="A senha deve ter no mínimo 6 caracteres")
	@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,}$")
	private String senha;
	
	public SessaoDomain() {}
	
	/**
	 * Método construtor da classe SessaoDomain
	 * @param login Login da sessão
	 * @param senha Senha da sessão
	 * @throws Exception Lança exceção
	 */
	public SessaoDomain(String login, String senha) throws Exception {
		setLogin(login);
		setSenha(senha);
	}
	
	
		
	/**
	 * Método para retornar o login da sessão
	 * @return Login da sessão
	 */
	public String getLogin() {		
		return login;
	}

	/**
	 * Método para informar o login do usuário
	 * O Login da sessão é definido pelo usuário no momento em que ele se cadastrar no sistema  
	 * @param login Login do usuário
	 * @throws Exception Lança exceção se o login informado for null ou vazio
	 */
	public void setLogin(String login) throws Exception{
		if ( (login == null) || (login.trim().equals("")) ) {
			logger.debug("setLogin() Excceção: "+MensagensErro.LOGIN_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.LOGIN_INVALIDO);
		}
		this.login = login;
	}
	
	/**
	 * Método para retornar a senha do usuário da sessão
	 * @return Senha do usuário da sessão
	 */
	public String getSenha() {		
		return senha;
	}

	/**
	 * Método para informar o login da sessão
	 * A Senha da sessão é definida pelo usuário no momento em que ele se cadastrar no sistema  
	 * @param senha Senha do usuário
	 * @throws Exception Lança exceção se a senha informada for vazia ou null
	 */
	public void setSenha(String senha) throws Exception {
		if ( (senha == null) || (senha.trim().equals("")) ) {
			logger.debug("setSenha() Excceção: "+MensagensErro.LOGIN_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.LOGIN_INVALIDO);
		}
		
		this.senha = senha;
	}	
}
