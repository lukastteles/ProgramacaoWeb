package com.br.uepb.dao;

import java.util.List;

import com.br.uepb.domain.PontoDeEncontroDomain;

public interface PontoDeEncontroDAO {

	/**
	 * Método para adicionar um ponto de encontro para a carona
	 * @param ponto Ponto de encontro para a carona
	 * @throws Exception Lança exceção se hover problema com a conexão com o banco
	 */
	public void addPontoDeEncontro(PontoDeEncontroDomain ponto) throws Exception;
	
	
	/**
	 * Método para retornar um conjunto de pontos de encontro pertencentes de uma sugestão de ponto de encontro da carona
	 * @param idCarona Id da carona que o ponto pertence 
	 * @param idSugestao Id da sugestão de ponto de encontro  
	 * @return Lista de Pontos dos Encontro de uma sugestão de pontos de encontro da carona
	 * @throws Exception Lança exceção se hover problema com a conexão com o banco
	 */
	public List<PontoDeEncontroDomain> getPontosSugestao(String idCarona, String idSugestao) throws Exception;
	
	
	/**
	 * Método para retornar todos os pontos de encontro (aceitos ou não) da carona
	 * @param idCarona Id da carona que o ponto pertence
	 * @return Lista de todos os pontos de encontro da carona
	 * @throws Exception Lança exceção se hover problema com a conexão com o banco
	 */
	public List<PontoDeEncontroDomain> listPontos(String idCarona) throws Exception;
	
	
	/**
	 * Método para retornar um ponto de encontro da carona
	 * @param idCarona Id da carona que o ponto pertence
	 * @param ponto Nome do ponto de encontro
	 * @return Ponto de encontro da carona
	 * @throws Exception Lança exceção se o ponto informado não estiver adicionado lista de pontos de encontro da carona
	 */
	public PontoDeEncontroDomain getPontoEncontroByNome(String idCarona, String ponto) throws Exception;
	
	
	/**
	 * Método para informar um conjunto de pontos de encontro aceitos para a carona
	 * @param idCarona Id da carona que o ponto pertence
	 * @return Lista dos pontos de encontro aceitos
	 * @throws Exception Lança exceção se hover problema com a conexão com o banco
	 */
	public List<PontoDeEncontroDomain> getPontoEncontroAceitos(String idCarona) throws Exception;
	
	
	/**
	 * Método para verificar se um ponto de encontro existe 
	 * @param idCarona Id da carona que o ponto pertence
	 * @param ponto Nome do ponto de encontro
	 * @return Retorna true se o ponto de encontro existir ou false no caso contrário 
	 * @throws Exception Lança exceção se hover problema com a conexão com o banco
	 */
	public Boolean pontoExiste(String idCarona, String ponto) throws Exception;


	/**
	 *  Atualiza algum atributo que tenha sido modificado para um ponto de encontro desde que o Id exista
	 * @param ponto Ponto a ser atualizada
	 */
	public void atualizaPonto(PontoDeEncontroDomain ponto);

	/**
	 * Apaga um ponto de encontro
	 * @param ponto ponto a ser apagado
	 */
	void apagaPonto(PontoDeEncontroDomain ponto);
	
	/**
	 * Apaga todos os pontos de encontro da lista
	 */
	void apagaPontosEncontro();

}
