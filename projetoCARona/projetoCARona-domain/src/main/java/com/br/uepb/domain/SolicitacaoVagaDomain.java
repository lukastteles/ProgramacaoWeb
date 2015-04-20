package com.br.uepb.domain;

/**
 * Classe de domínio que define o modelo para a solicitacão de vaga na carona
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
public class SolicitacaoVagaDomain {
	
	/** Id da solicitacao */ //TODO: Deve ser gerado automaticamente
	private String id;
	
	/** Id do usuário */
	private String idUsuario;
	
	/** Id da carona */
	private String idCarona;
	
	/** Variável para verificar se o a solicitação da vaga na carona foi aceita */
	private boolean foiAceita = false;
	
	/** Local onde o passageiro da solicitação irá esperar a carona */
	private PontoDeEncontroDomain ponto;
	
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
}
