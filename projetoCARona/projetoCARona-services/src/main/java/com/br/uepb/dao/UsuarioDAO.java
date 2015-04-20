package com.br.uepb.dao;

import com.br.uepb.domain.UsuarioDomain;

public interface UsuarioDAO {
	
	/**
	 * Interface DAO para o objeto UsuarioDomain
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 20/04/15
	 */

	
	/**
	 * Pega um usuário pelo id
	 * @param login String - Login do usuário
	 * @return UsuarioDomain - Usuário com o login especificado
	 * @throws Exception - Lança exceção se não houver usuário para id especificado ou se ele for vazio
	 */
	public UsuarioDomain getUsuario(String login) throws Exception;
	
	
	/**
	 * Adiciona um usuário na lista
	 * @param usuario UsuarioDomain - Usuário a ser adicionado
	 * @throws Exception - Lança exceção se já existir usuário com mesmo id ou email
	 */
	public void addUsuario(UsuarioDomain usuario) throws Exception;
	
	
	/**
	 * Apaga todos os usuários da lista
	 */
	public void apagaUsuarios();
	
	
	
}
