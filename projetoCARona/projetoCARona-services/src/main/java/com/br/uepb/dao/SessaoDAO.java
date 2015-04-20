package com.br.uepb.dao;

import com.br.uepb.domain.SessaoDomain;

/**
 * Interface DAO para o objeto SessaoDomain
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
public interface SessaoDAO {
	
	/**
	 * Adiciona uma sessão na lista
	 * @param sessao Sessão a ser adicionada
	 * @throws Exception Lança exceção se não houver usuário com o login e senha fornecidos pela sessão
	 */
	public void addSessao(SessaoDomain sessao) throws Exception;
	
	
	/**
	 * Pega uma sessão pelo id
	 * @param login Id da sessão
	 * @return Sessão com o id especificado
	 * @throws Exception Lança exceção se não houver sessão para o id especificado ou se ele for vazio
	 */
	public SessaoDomain getSessao(String login) throws Exception;
	
	
	/**
	 * Apaga uma sessão da lista
	 * @param login Id da sessão
	 * @throws Exception Lança exceção caso não exista sessão para aquele login ou se ele for vazio
	 */
	public void deleteSessao(String login) throws Exception;
	
	
	/**
	 * Apaga todas as sessões da lista
	 */
	public void apagaSessoes();
}
