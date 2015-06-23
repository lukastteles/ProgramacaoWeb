package com.br.uepb.dao;

import java.util.List;

import com.br.uepb.domain.InteresseEmCaronaDomain;

/**
 * Interface DAO para o objeto InteresseEmCaronaDomain
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 30/05/2015
 */

public interface InteresseEmCaronaDAO {

	/**
	 * Adiciona na lista o interesse em carona
	 * @param interesse Objeto para interesse em carona
	 * @return Id do objeto para interesse em carona
	 * @throws Exception Lanca excecao se houver problema com o banco
	 */
	int addIntereseEmCarona(InteresseEmCaronaDomain interesse) throws Exception;

	/**
	 * Pega lista de Interesses em carona do usuario
	 * @param idSessao Login do usuario
	 * @return Lista de Interesses em carona
	 * @throws Exception Lanca excecao se o login for invalido ou se houver problema com o banco
	 */
	List<InteresseEmCaronaDomain> getInteresseEmCaronas(String idSessao) throws Exception;
	
	/**
	 * Apaga um objeto Interesse em carona do usuario
	 * @param idInteresse id do objeto Interesse em caorna
	 * @throws Exception Lanca excecao se o id for invalido ou se houver problema com o banco
	 */
	void apagaInteresse(String idInteresse) throws Exception;

	/**
	 * Apaga todos os  Interesses em carona da lista
	 */
	void apagaInteresses();


}
