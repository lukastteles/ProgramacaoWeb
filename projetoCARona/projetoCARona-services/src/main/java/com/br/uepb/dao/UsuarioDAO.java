package com.br.uepb.dao;

import com.br.uepb.domain.PerfilDomain;
import com.br.uepb.domain.UsuarioDomain;

/**
 * Interface DAO para o objeto UsuarioDomain
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/15
 */

public interface UsuarioDAO {
	
	/**
	 * Pega um usuário pelo id
	 * @param login Login do usuário
	 * @return Usuário com o login especificado
	 * @throws Exception Lança exceção se não houver usuário para id especificado ou se ele for vazio
	 */
	public UsuarioDomain getUsuario(String login) throws Exception;
	
	
	/**
	 * Adiciona um usuário na lista
	 * @param usuario Usuário a ser adicionado
	 * @throws Exception Lança exceção se já existir usuário com mesmo id ou email
	 */
	public void addUsuario(UsuarioDomain usuario) throws Exception;
	
	/**
	 * Metodo para retornar o perfil do usuario
	 * @param login Id do usuario
	 * @return Perfil do usuario
	 * @throws Exception Lança excecao se o id informado for invalido
	 */
	public PerfilDomain getPerfil(String login) throws Exception;
	
	/**
	 * Apaga todos os usuários da lista
	 */
	public void apagaUsuarios();


	
	
	
}
