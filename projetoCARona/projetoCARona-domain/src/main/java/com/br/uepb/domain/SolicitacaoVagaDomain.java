package com.br.uepb.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Classe de domínio que define o modelo para a solicitacão de vaga na carona
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */

@Entity
@Table(name="SOLICITACAO_VAGA")
public class SolicitacaoVagaDomain {
	
	final static Logger logger = Logger.getLogger(SolicitacaoVagaDomain.class);
	
	/** Id da solicitacao */ //TODO: Deve ser gerado automaticamente
	@Id
	//@GeneratedValue
	private String id;
	
	/** Id do usuário */
	private String idUsuario;
	
	/** Id da carona */	
	private String idCarona;
	
	/** Variável para verificar se o a solicitação da vaga na carona foi aceita */
	private boolean foiAceita = false;
	
	/** Local onde o passageiro da solicitação irá esperar a carona */
	@OneToOne
    @JoinColumn(name="idPontoEncontro") 
    @Cascade(CascadeType.ALL)  
	private PontoDeEncontroDomain ponto;
	
	/** Avaliacao do motorista sobre a presenca do caroneiro na carona */
	private String reviewVaga;
	
	/** Avaliacao do caroneiro sobre a carona */
	private String reviewCarona;
	/**
	 * Método construtor de SolicitacaoVagaDomain
	 * @param id Id da solicitação
	 * @param idUsuario Id do usuário que irá solicitar a vaga na carona
	 * @param idCarona Id da carona
	 * @param ponto Local do ponto de encontro
	 */
	public SolicitacaoVagaDomain(String id, String idUsuario, String idCarona, PontoDeEncontroDomain ponto) {
		this.id = id;
		this.idUsuario = idUsuario;
		this.idCarona = idCarona;
		this.ponto = ponto;
	}
	
	
	/**
	 * Método construtor de SolicitacaoVagaDomain
	 * @param id Id da solicitação
	 * @param idUsuario Id do usuário que irá solicitar a vaga na carona
	 * @param idCarona Id da carona
	 */
	public SolicitacaoVagaDomain(String id, String idUsuario, String idCarona) {
		this.id = id;
		this.idUsuario = idUsuario;
		this.idCarona = idCarona;
	}
	
	/**
	 * Método construtor de SolicitacaoVagaDomain
	 */
	public SolicitacaoVagaDomain(){}
	
	/**
	 * Método para verificar se a solicitacao de vaga na carona foi aceita ou não
	 * @return Retorna true se a solicitação foi aceita ou false caso contrário
	 */
	public boolean getFoiAceita() {
		return foiAceita;
	}
	
	/**
	 * Método para informar se a solicitacao de vaga na carona foi aceita ou não
	 * @param foiAceita Ietorna true se a solicitação foi aceita ou false caso contrário
	 */
	public void setFoiAceita(boolean foiAceita) {
		this.foiAceita = foiAceita;
	}
	
	/**
	 * Método para Retornar o id da solicitação
	 * @return Id da solicitação
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Método para Retornar o id do usuário que pediu a solicitação da vaga
	 * @return Id do usuário que solicitou a vaga na carona
	 */
	public String getIdUsuario() {
		return idUsuario;
	}
	
	/**
	 * Método para retornar o id da carona
	 * @return Id da carona
	 */
	public String getIdCarona() {
		return idCarona;
	}
	
	/**
	 * Método para retornar o local de ponto de encontro da solicitação
	 * @return Ponto de encontro para da solicitação
	 */
	public PontoDeEncontroDomain getPonto() {
		return ponto;
	}

	/**
	 * Metodo para retornar a avaliacao da presenca do caroneiro na vaga
	 * @return Avaliacao da presenca do caroneiro na vaga ("faltou" ou "nao faltou")
	 */
	public String getReviewVaga() {
		return reviewVaga;
	}

	/** 
	 * Metodo para adicionar uma avaliacao da presenca do caroneiro na vaga	 
	 * @param reviewVaga Avaliacao da presenca do caroneiro na vaga ("faltou" ou "nao faltou")
	 */
	public void setReviewVaga(String reviewVaga) {
		this.reviewVaga = reviewVaga;
	}


	/**
	 * Metodo para retornar a avaliacao da carona feita pelo caroneiro
	 * @return Avaliacao da carona feita pelo caroneiro ("segura e tranquila" ou "nao funcionou")
	 */
	public String getReviewCarona() {
		return reviewCarona;
	}

	/**
	 * Metodo para adicionar avaliacao da carona feita pelo caroneiro
	 * @param reviewCarona Avaliacao da carona feita pelo caroneiro
	 */
	public void setReviewCarona(String reviewCarona) {
		this.reviewCarona = reviewCarona;
	}
}
