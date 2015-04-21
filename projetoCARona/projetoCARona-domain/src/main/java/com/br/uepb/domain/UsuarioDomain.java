package com.br.uepb.domain;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.exceptions.ProjetoCaronaException;

/**
 * Classe de domínio que define o modelo para o Usuário
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 18/04/2015
 */
@Entity
@Table(name="USUARIOS")
public class UsuarioDomain {

	/** Login do usuário */
	/*
	@NotNull(message = "Login inválido")
	@Size(min=2, max=30, message="Login inválido") */
	@Id
	private String login;
	
	/** Senha do usuário para autenticação no sistema */
	/*
	@NotNull(message = "O login não pode ser null")
	@Size(min=2, message="A senha deve ter no mínimo 6 dígitos") */
	private String senha; 
	
	/** Perfil do usuário, contém nome, email e endereço */
	@OneToOne
	private PerfilDomain perfil;
	
	/** Caronas do usuário */
	private ArrayList<String> idCaronas = new ArrayList<String>();
	
	/**
	 * Método construtor de UsuarioDomain
	 * @param login Login do usuário
	 * @param senha Senha do usuário
	 * @param nome Nome do usuário
	 * @param email Email do usuário
	 * @param endereco Endereço do usuário
	 * @throws Exception Lança exceção caso qualquer campo informado esteja vazio, null ou inválido 
	 */
	public UsuarioDomain(String login, String senha, String nome, String endereco, String email) throws Exception {
		setLogin(login);
		setSenha(senha);
		
		//TODO: desfazer esse comentario
		//setPerfil(nome, endereco, email);
	}
	
	/**
	 * Método para retornar o login do usuário
	 * @return Login do usuário
	 */
	public String getLogin() {
		return login;
	}
	
	/**
	 * Método para inserir o Login do usuário
	 * @param login Login do usuário
	 * @throws Lança exceção caso o login informado esteja vazio ou null
	 */	
	private void setLogin(String login) throws Exception{
		if ( (login == null) || (login.trim().equals("")) ){
			throw new ProjetoCaronaException("Login inválido");
		}
		this.login = login;	
	}

	/**
	 * Método para retornar a senha do usuário
	 * @return Senha do usuário
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * Método mudar a senha do usuário
	 * @param senha Senha de login do usuario
	 * @throws Exception Lança exceção caso a senha informado esteja vazio ou null
	 */
	public void setSenha(String senha) throws Exception {
		if ( (senha == null) || (senha.trim().equals("")) ){
			throw new ProjetoCaronaException(MensagensErro.SENHA_INVALIDA);
		}
		
		this.senha = senha;
	}
	
	/**
	 * Método para retornar o perfil do usuário
	 * @return Perfil do usuário
	 */
	public PerfilDomain getPerfil() {
		return perfil;
	}

	/**
	 * Método que informa todos os parâmetros nome, endereço e email do Perfil
	 * @param nome Nome do usuário
	 * @param endereco Endereço do usuário
	 * @param email Email do usuário
	 * @throws Exception Lança exceção caso qualquer parâmetro informado esteja vazio ou null
	 */
	private void setPerfil(String nome, String endereco, String email) throws Exception{
		if ( (nome == null) || (nome.trim().equals("")) ){
			throw new ProjetoCaronaException(MensagensErro.NOME_INVALIDO);
		}
		if ( (endereco == null) || (endereco.trim().equals("")) ){
			throw new ProjetoCaronaException(MensagensErro.ENDERECO_INVALIDO);
		}
		if ( (email == null) || (email.trim().equals("")) ){
			throw new ProjetoCaronaException(MensagensErro.EMAIL_INVALIDO);
		}
		
		this.perfil = new PerfilDomain(nome, endereco, email);
		
	}
	
	/**
	 * Método para retornar a lista de caronas do usuário
	 * @return Lista de caronas do usuário
	 */
	public ArrayList<String> getCaronas() {
		return idCaronas;
	}
	
	/**
	 * Método que adiciona um salva o id da carona do Usuário
	 * @param id Id da carona
	 */
	public void addCarona(String id){		
		idCaronas.add(id);
	}

	/**
	 * Método para retornar o id de uma carona do Usuario pelo index 
	 * @param indexCarona Index da carona na lista de caronas
	 * @return Id da carona
	 * @throws Exception Lança exceção se o index informado for igual a zero ou maior que a quantidade de indices da lista
	 */
	public String getIdCaronaByIndex(int indexCarona) throws Exception  {		
		if ((indexCarona == 0) || (indexCarona > idCaronas.size())) {
			throw new ProjetoCaronaException(MensagensErro.INDICE_INVALIDO);
		}
		
		return idCaronas.get(indexCarona-1);
	}
}

