package com.br.uepb.domain;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe de domínio que define o modelo para o Perfil do Usuário
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 18/04/2015
 */
@Entity
@Table(name="PERFIL_USUARIO")
public class PerfilDomain {
	
	@Id
	@GeneratedValue
	private int idPerfil;
	
	/** Nome do usuário */
	//@NotNull(message = "O Nome não pode ser nulo")
	private String nome;
	
	/** Endereço do usuário */
	//@NotNull(message = "O Endereço não pode ser nulo")
	private String endereco;
	
	/** Email do usuário */
	//@NotNull(message = "O Email não pode ser nulo")
	//@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	private String email;
	
	/** Historico de caronas */
	private ArrayList<String> historicoDeCaronas = new ArrayList<String>();
	
	/** Historico de vagas em caronas */
	private ArrayList<String> historicoDeVagasEmCaronas = new ArrayList<String>();
		
	/** Historico de caronas seguras e tranquilas */
	private ArrayList<String> caronasSegurasETranquilas = new ArrayList<String>();
	
	/** Historico de caronas que não funcionaram */
	private ArrayList<String> caronasQueNaoFuncionaram = new ArrayList<String>();
	
	/** Historico de faltas em vagas de caronas */
	private ArrayList<String> faltasEmVagasDeCaronas = new ArrayList<String>();
	
	/** Historico de presencas em vagas de caronas */
	private ArrayList<String> presencasEmVagasDeCaronas = new ArrayList<String>();

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

	/**
	 * Método para adicionar o id da carona no histórico de caronas
	 * @param id Id da carona
	 */
	public void addHistoricoDeCaronas(String id) {
		historicoDeCaronas.add(id);
	}
	
	/**
	 * Método para retornar o histórico de caronas
	 * @return Histórico de caronas
	 */
	public String[] getHistoricoDeCaronas() {
		String[] array = new String[historicoDeCaronas.size()];
		array = historicoDeCaronas.toArray(array);
		return  array;
	}

	/**
	 * Método para adicionar o id da carona no histórico de vagas em caronas
	 * @param id Id da carona
	 */
	public void addHistoricoDeVagasEmCaronas(String id) {
		historicoDeVagasEmCaronas.add(id);
	}
	
	/**
	 * Método para retornar o histórico de vagas em carona
	 * @return Histórico de vagas em carona
	 */
	public String[] getHistoricoDeVagasEmCaronas() {
		String[] array = new String[historicoDeVagasEmCaronas.size()];
		array = historicoDeVagasEmCaronas.toArray(array);
		return  array;
	}

	//TODO: Ainda nao classifica as caronas nesses US
	/**
	 * Método para retornar o histórico de caronas seguras e tranquilas
	 * @return Histórico de caronas seguras e tranquilas
	 */
	public String[] getCaronasSegurasETranquilas() {
		String[] array = new String[caronasSegurasETranquilas.size()];
		array = caronasSegurasETranquilas.toArray(array);
		return  array;
	}

	//TODO: Ainda nao classifica as caronas nesses US
	/**
	 * Método para retornar o histórico de caronas que não funcionaram
	 * @return Histórico de caronas que não funcionaram
	 */
	public String[] getCaronasQueNaoFuncionaram() {
		String[] array = new String[caronasQueNaoFuncionaram.size()];
		array = caronasQueNaoFuncionaram.toArray(array);
		return  array;
	}

	//TODO: Ainda nao registra as faltas ou presencas nas caronas nesses US
	/**
	 * Método para retornar o histórico de faltas em vagas de caronas
	 * @return Histórico de faltas em vagas de caronas
	 */
	public String[] getFaltasEmVagasDeCaronas() {
		String[] array = new String[faltasEmVagasDeCaronas.size()];
		array = faltasEmVagasDeCaronas.toArray(array);
		return  array;
	}

	//TODO: Ainda nao registra as faltas ou presencas nas caronas nesses US
	/**
	 * Método para retornar o histórico de presencas em vagas de carona
	 * @return Histórico de presencas em vagas de carona
	 */
	public String[] getPresencasEmVagasDeCaronas() {
		String[] array = new String[presencasEmVagasDeCaronas.size()];
		array = presencasEmVagasDeCaronas.toArray(array);
		return  array;
	}

}