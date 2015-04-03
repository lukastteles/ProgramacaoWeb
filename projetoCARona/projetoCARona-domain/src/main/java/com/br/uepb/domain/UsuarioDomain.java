package com.br.uepb.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsuarioDomain {
	
	/**
	 * Classe de domínio que define o modelo para o Usuário
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 1ª Iteração
	 */
	
	/** Login do Usuário*/
	@NotNull(message = "O login não pode ser nulo")
	@Size(min=2, max=30, message="Tamanho inválido de login")
	private String login;
	
	/** Senha do Usuário para autenticação no sistema*/
	@NotNull(message = "O login não pode ser nulo")
	@Size(min=2, message="A senha deve ter no mínimo 6 dígitos")
	private String senha; 
	
	/** Perfil do Usuario, contém nome, email e endereço*/
	private PerfilDomain perfil;
	
	/**
	 * Método Construtor de UsuarioDomain
	 * @param login String - Login do Usuário
	 * @param senha String - Senha do Usuário
	 * @param nome String - Nome do Usuário
	 * @param email String - Email do Usuário
	 * @param endereco String - Endereço do Usuário
	 */
	public UsuarioDomain(String login, String senha, String nome, String endereco, String email) {
		this.login = login;
		this.senha = senha;
		this.perfil = new PerfilDomain(nome, endereco, email);
	}
	
	/**
	 * Método para pegar o Login do Usuário
	 * @return String - Login do Usuário
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Método para pegar a senha do Usuário
	 * @return String - Senha do Usuário
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * Método mudar a senha do Usuário
	 * @param senha String - Nova senha
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	/**
	 * Método para pegar o perfil do Usuário
	 * @return Perfil - Perfil do Usuário
	 */
	public PerfilDomain getPerfil() {
		return perfil;
	}

}

