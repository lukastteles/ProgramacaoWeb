package com.br.uepb.exceptions;

/**
 * Classe de exceção própria do projeto
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
public class ProjetoCaronaException extends Exception {

	private static final long serialVersionUID = -454716580266392313L;

	/**
	 * Método construtor da classe ProjetoCaronaException que irá gerar a exceção informada
	 * @param mensagem Mensagem referente à exceção gerada
	 */
	public ProjetoCaronaException(String mensagem) {
		super(mensagem);
	}
}
