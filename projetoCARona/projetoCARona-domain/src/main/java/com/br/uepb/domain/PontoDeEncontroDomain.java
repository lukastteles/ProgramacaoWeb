package com.br.uepb.domain;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.exceptions.ProjetoCaronaException;

/**
 * Classe de domínio que define o modelo o Ponto de Encontro
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 19/04/2015
 */
public class PontoDeEncontroDomain {
	
	/** Id da sugestão */ //TODO: Deve ser gerado automaticamente
	private String idSugestao;
	
	/** Local sugerido para ser um ponto de encontro da carona */
	private String pontoDeEncontro;
	
	/** Variável para verificar se o ponto de encontro foi aceito */
	private boolean foiAceita = false;
	
	/**
	 * Método construtor de PontoDeEncontroDomain
	 * @param idSugestao Id da Sugestão do(s) Ponto(s) de encontro
	 * @param pontoDeEncontro Nome do Ponto de Encontro
	 * @throws Exception Lança exceção se o ponto de encontro informado for null ou vazio
	 */
	public PontoDeEncontroDomain(String idSugestao, String pontoDeEncontro) throws Exception {
		setIdSugestao(idSugestao);
		setPontoDeEncontro(pontoDeEncontro);
	}
	
	/**
	 * Método para retornar o id da sugestão 
	 * @return Id da sugestão
	 */
	public String getIdSugestao() {
		return idSugestao;
	}

	/**
	 * Método para informar o id da sugestão
	 * @param idSugestao Id da sugestão
	 */
	private void setIdSugestao(String idSugestao) {
		this.idSugestao = idSugestao;
	}

	/**
	 * Método para retornar o ponto de encontro
	 * @return Ponto de Encontro
	 */
	public String getPontoDeEncontro() {
		return pontoDeEncontro;
	}

	/**
	 * Método para informar o ponto de encontro
	 * @param pontoDeEncontro Ponto de encontro
	 * @throws Exception Lança exceção de o poonto de encontro informado for null ou vazio
	 */
	public void setPontoDeEncontro(String pontoDeEncontro) throws Exception {
		if ( (pontoDeEncontro == null) || (pontoDeEncontro.trim().equals("")) ){
			throw new ProjetoCaronaException(MensagensErro.PONTO_INVALIDO);
		}
		this.pontoDeEncontro = pontoDeEncontro;		
	}
	
	/**
	 * Método para verificar se a sugestão do ponto de encontro foi aceita ou não
	 * @return Retorna true se o ponto de encontro foi aceito ou false caso contrario
	 */
	public boolean getFoiAceita() {
		return foiAceita;
	}
	
	/**
	 * Método para informar se a sugestão do ponto de encontro foi aceita ou não
	 * @param foiAceita Informa true se o ponto de encontro foi aceito ou false caso contrário
	 */
	public void setFoiAceita(boolean foiAceita) {
		this.foiAceita = foiAceita;
	}
	
}
