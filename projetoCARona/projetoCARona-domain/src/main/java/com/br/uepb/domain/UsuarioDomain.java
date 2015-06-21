package com.br.uepb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;

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

	final static Logger logger = Logger.getLogger(UsuarioDomain.class);
	
	/** Login do usuário */
	
	@NotNull(message = "Login inválido")
	//@Size(min=2, max=30, message="Login inválido")	
	@Id
	@Column(nullable=false)
	private String login;
	
	/** Senha do usuário para autenticação no sistema */
	/*
	@NotNull(message = "O login não pode ser null")
	@Size(min=2, message="A senha deve ter no mínimo 6 dígitos") */
	@Column(nullable=false)
	private String senha; 
	
	/** Perfil do usuário, contém nome, email e endereço */
	@OneToOne(orphanRemoval=true, fetch=FetchType.EAGER, cascade=javax.persistence.CascadeType.ALL)
	@JoinColumn(name="idPerfil", foreignKey=@ForeignKey(name = "fk_idPerfil_usuarios"))	
//	@OneToOne
//	@JoinColumn(name="idPerfil")
//	@Cascade(CascadeType.ALL)
	private PerfilDomain perfil;
	
	/** Caronas do usuário */
	//private ArrayList<String> idCaronas = new ArrayList<String>();
	
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
		setPerfil(nome, endereco, email);
	}
	
	//Necessário para o hibernate
	public UsuarioDomain() {}
	
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
	 * @throws Exception Lança exceção caso o login informado esteja vazio ou null
	 */	
	public void setLogin(String login) throws Exception{
		if ( (login == null) || (login.trim().equals("")) ){
			logger.debug("setLogin() Exceção: "+MensagensErro.LOGIN_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.LOGIN_INVALIDO);
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
			logger.debug("setSenha() Exceção: "+MensagensErro.SENHA_INVALIDA);
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
	
	public void setPerfil(PerfilDomain perfil){
		this.perfil = perfil;
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
			logger.debug("setPerfil() Exceção: "+MensagensErro.NOME_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.NOME_INVALIDO);
		}
		if ( (endereco == null) || (endereco.trim().equals("")) ){
			logger.debug("setPerfil() Exceção: "+MensagensErro.ENDERECO_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.ENDERECO_INVALIDO);
		}
		if ( (email == null) || (email.trim().equals("")) ){
			logger.debug("setPerfil() Exceção: "+MensagensErro.EMAIL_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.EMAIL_INVALIDO);
		}
		
		this.perfil = new PerfilDomain(nome, endereco, email);
		
	}
	
}

