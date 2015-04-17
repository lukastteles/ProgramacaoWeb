package com.br.uepb.domain;

import java.util.ArrayList;

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
	@NotNull(message = "Login inválido")
	@Size(min=2, max=30, message="Login inválido")
	private String login;
	
	/** Senha do Usuário para autenticação no sistema*/
	@NotNull(message = "O login não pode ser nulo")
	@Size(min=2, message="A senha deve ter no mínimo 6 dígitos")
	private String senha; 
	
	/** Perfil do Usuario, contém nome, email e endereço*/
	private PerfilDomain perfil;
	
	/** Caronas do usuário*/
	private ArrayList<String> idCaronas = new ArrayList<String>();
	
	/**
	 * Método Construtor de UsuarioDomain
	 * @param login String - Login do Usuário
	 * @param senha String - Senha do Usuário
	 * @param nome String - Nome do Usuário
	 * @param email String - Email do Usuário
	 * @param endereco String - Endereço do Usuário
	 * @throws Exception 
	 */
	public UsuarioDomain(String login, String senha, String nome, String endereco, String email) throws Exception {
		setLogin(login);
		setSenha(senha);
		setPerfil(nome, endereco, email);
	}
	
	/**
	 * Método para pegar o Login do Usuário
	 * @return String - Login do Usuário
	 */
	public String getLogin() {
		return login;
	}
	
	/**
	 * Método para inserir o Login do Usuário
	 * @return String - Senha do Usuário
	 */
	private void setLogin(String login) throws Exception{
		if ( (login == null) || (login.trim().equals("")) ){
			throw new Exception("Login inválido");
		}
		this.login = login;	
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
	 * @throws Exception 
	 */
	public void setSenha(String senha) throws Exception {
		if ( (senha == null) || (senha.trim().equals("")) ){
			throw new Exception("Login inválido");
		}
		
		this.senha = senha;
	}
	
	/**
	 * Método para pegar o perfil do Usuário
	 * @return Perfil - Perfil do Usuário
	 */
	public PerfilDomain getPerfil() {
		return perfil;
	}

	private void setPerfil(String nome, String endereco, String email) throws Exception{
		if ( (nome == null) || (nome.trim().equals("")) ){
			throw new Exception("Nome inválido");
		}
		if ( (endereco == null) || (endereco.trim().equals("")) ){
			throw new Exception("Endereco inválido");
		}
		if ( (email == null) || (email.trim().equals("")) ){
			throw new Exception("Email inválido");
		}
		
		this.perfil = new PerfilDomain(nome, endereco, email);
		
	}
	/**
	 * Método para pegar a lista de Caronas do Usuário
	 * @return ArrayList - Lista de Caronas do Usuário
	 */
	public ArrayList<String> getCaronas() {
		return idCaronas;
	}
	
	public void addCarona(String IdCarona){		
		idCaronas.add(IdCarona);
	}

}

