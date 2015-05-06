package com.br.uepb.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.exceptions.ProjetoCaronaException;

/**
 * Classe de domínio que define o modelo o Ponto de Encontro
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 19/04/2015
 */

@Entity
@Table(name="PONTOS_ENCONTRO")
public class PontoDeEncontroDomain {
	
	final static Logger logger = Logger.getLogger(PontoDeEncontroDomain.class);
	
	/** Id do Ponto de Encontro */
	@Id
	@GeneratedValue
	private int id;
	
	/** Id da sugestão */ //TODO: Deve ser gerado automaticamente
	private String idSugestao;
		
	/** Id da carona */ 
	private String idCarona;
	
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
	 * Método para retornar o id do ponto de encontro 
	 * @return Id do ponto de encontro
	 */
	public int getId() {
		return id;
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
	 * Método para retornar o id da carona do usuário 
	 * @return Id da carona
	 */
	public String getIdCarona(){
		return idCarona;
	}
	
	/**
	 * Método para informar o id da carona do usuário
	 * @param idCarona Id da sugestão
	 */
	public void setIdCarona(String idCarona){
		this.idCarona = idCarona;
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
			logger.debug("setPontoDeEncontro() Exceção: "+MensagensErro.PONTO_INVALIDO); 
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
