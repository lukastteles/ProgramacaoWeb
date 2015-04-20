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
	
	/** Historico de Caronas*/
	private ArrayList<String> historicoDeCaronas = new ArrayList<String>();
	
	/** Historico de vagas em Caronas*/
	private ArrayList<String> historicoDeVagasEmCaronas = new ArrayList<String>();
	
	
	/** Historico de Caronas Seguras e Tranquilas*/
	private ArrayList<String> caronasSegurasETranquilas = new ArrayList<String>();
	
	/** Historico de caronas que não Funcionaram*/
	private ArrayList<String> caronasQueNaoFuncionaram = new ArrayList<String>();
	
	/** Historico de faltas em Vagas de Caronas*/
	private ArrayList<String> faltasEmVagasDeCaronas = new ArrayList<String>();
	
	/** Historico de presencas em vagas de Caronas*/
	private ArrayList<String> presencasEmVagasDeCaronas = new ArrayList<String>();

	/**
	 * Método Construtor de PerfilDomain
	 * @param nome String - Nome do Usuário
	 * @param endereco String - Endereço do Usuário
	 * @param email String - Email do Usuário
	 */
	public PerfilDomain(String nome, String endereco, String email) {
		setNome(nome);
		setEndereco(endereco);
		setEmail(email);
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

	/**
	 * Adiciona o id da carona no histórico de caronas
	 * @param id - Id da Carona
	 */
	public void addHistoricoDeCaronas(String id) {
		historicoDeCaronas.add(id);
	}
	
	/**
	 * retorna o historico de caronas
	 * @return - historico de caronas
	 */
	public String[] getHistoricoDeCaronas() {
		String[] array = new String[historicoDeCaronas.size()];
		array = historicoDeCaronas.toArray(array);
		return  array;
	}

	/**
	 * Adiciona o id da carona no histórico de vagas em caronas
	 * @param id - Id da Carona
	 */
	public void addHistoricoDeVagasEmCaronas(String id) {
		historicoDeVagasEmCaronas.add(id);
	}
	
	/**
	 * Retorna o historico de vagas em carona
	 * @return - historico de vagas em carona
	 */
	public String[] getHistoricoDeVagasEmCaronas() {
		String[] array = new String[historicoDeVagasEmCaronas.size()];
		array = historicoDeVagasEmCaronas.toArray(array);
		return  array;
	}

	/**
	 * Retorna o historico de caronas seguras e tranquilas
	 * @return - historico de caronas seguras e tranquilas
	 */
	//Ainda nao classifica as caronas nesses US
	public String[] getCaronasSegurasETranquilas() {
		String[] array = new String[caronasSegurasETranquilas.size()];
		array = caronasSegurasETranquilas.toArray(array);
		return  array;
	}

	/**
	 * Retorna o histórico de caronas que não funcionaram
	 * @return - histórico de caronas que não funcionaram
	 */
	//Ainda nao classifica as caronas nesses US
	public String[] getCaronasQueNaoFuncionaram() {
		String[] array = new String[caronasQueNaoFuncionaram.size()];
		array = caronasQueNaoFuncionaram.toArray(array);
		return  array;
	}

	/**
	 * Retorna o historico de faltas em vagas de caronas
	 * @return - historico de faltas em vagas de caronas
	 */
	//Ainda nao registra as faltas ou presencas nas caronas nesses US
	public String[] getFaltasEmVagasDeCaronas() {
		String[] array = new String[faltasEmVagasDeCaronas.size()];
		array = faltasEmVagasDeCaronas.toArray(array);
		return  array;
	}

	/**
	 * Retorna o histórico de presencas em vagas de carona
	 * @return - histórico de presencas em vagas de carona
	 */
	//Ainda nao registra as faltas ou presencas nas caronas nesses US
	public String[] getPresencasEmVagasDeCaronas() {
		String[] array = new String[presencasEmVagasDeCaronas.size()];
		array = presencasEmVagasDeCaronas.toArray(array);
		return  array;
	}

}