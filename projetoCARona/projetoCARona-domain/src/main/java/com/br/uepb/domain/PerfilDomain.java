package com.br.uepb.domain;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.exceptions.ProjetoCaronaException;

/**
 * Classe de domínio que define o modelo para o Perfil do Usuário
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 18/04/2015
 */
@Entity
@Table(name="PERFIL")
public class PerfilDomain {
	
	final static Logger logger = Logger.getLogger(PerfilDomain.class);
	
	//TODO: variavel adicionada	
	@Id
	@GeneratedValue
	/** Id do Perfil do usuário */
	private int id;
	
	/** Nome do usuário */
	//@NotNull(message = "O Nome não pode ser nulo")
	@Column(nullable=false)
	private String nome;
	
	/** Endereço do usuário */
	//@NotNull(message = "O Endereço não pode ser nulo")
	@Column(nullable=false)
	private String endereco;
	
	/** Email do usuário */
	//@NotNull(message = "O Email não pode ser nulo")
	//@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	@Column(unique=true, nullable=false)
	private String email;
	
	/**
	 * Método construtor de PerfilDomain
	 * @param nome Nome do usuário
	 * @param endereco Endereço do usuário
	 * @param email Email do usuário
	 */
	public PerfilDomain(String nome, String endereco, String email) {
		setNome(nome);
		setEndereco(endereco);
		setEmail(email);
	}
	
	private PerfilDomain(){
		
	}

	/**
	 * Método para retornar o id do Perfil do Usuário
	 * @return Id do perfil
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Método para retornar o nome do usuário
	 * @return Nome do usuário
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método informar o nome do usuário
	 * @param nome Nome do usuário
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Método para retornar o email do usuário
	 * @return Email do Usuário
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Método informar o email do usuário
	 * @param email Email do usuário
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Método para retornar o endereço do usuário
	 * @return Endereço do usuário
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * Método informar o endereço do usuário
	 * @param endereco Endereço do usuário
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

}