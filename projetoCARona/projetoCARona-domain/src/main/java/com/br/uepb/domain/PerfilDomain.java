package com.br.uepb.domain;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PerfilDomain {

	/**
	 * Classe de domínio que define o modelo para o Perfil do Usuário
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 1ª Iteração
	 */
	
	/** Nome do Usuário*/
	@NotNull(message = "O Nome não pode ser nulo")
	private String nome;
	
	/** Endereço do Usuário*/
	@NotNull(message = "O Endereço não pode ser nulo")
	private String endereco;
	
	/** Email do Usuário*/
	@NotNull(message = "O Email não pode ser nulo")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	private String email;
	
	
	private ArrayList<String> historicoDeCaronas = new ArrayList<String>();
	private ArrayList<String> historicoDeVagasEmCaronas = new ArrayList<String>();
	private ArrayList<String> caronasSegurasETranquilas = new ArrayList<String>();
	private ArrayList<String> caronasQueNaoFuncionaram = new ArrayList<String>();
	private ArrayList<String> faltasEmVagasDeCaronas = new ArrayList<String>();
	private ArrayList<String> presencasEmVagasDeCaronas = new ArrayList<String>();

	/**
	 * Método Construtor de PerfilDomain
	 * @param nome String - Nome do Usuário
	 * @param endereco String - Endereço do Usuário
	 * @param email String - Email do Usuário
	 */
	public PerfilDomain(String nome, String endereco, String email) {
		this.nome = nome;
		this.endereco = endereco;
		this.email = email;
	}

	/**
	 * Método para pegar o Nome do Usuário
	 * @return String - Nome do Usuário
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método mudar o nome do Usuário
	 * @param senha String - Novo nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Método para pegar o Email do Usuário
	 * @return String - Email do Usuário
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Método mudar o Email do Usuário
	 * @param senha String - Novo Email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Método para pegar o Endereço do Usuário
	 * @return String - Endereço do Usuário
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * Método mudar o Endereço do Usuário
	 * @param senha String - Novo Endereço
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getHistoricoDeCaronas() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getHistoricoDeVagasEmCaronas() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCaronasSegurasETranquilas() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCaronasQueNaoFuncionaram() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFaltasEmVagasDeCaronas() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPresencasEmVagasDeCaronas() {
		// TODO Auto-generated method stub
		return null;
	}

}