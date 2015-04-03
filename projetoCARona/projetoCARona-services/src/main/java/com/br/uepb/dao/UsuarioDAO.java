package com.br.uepb.dao;

import com.br.uepb.domain.UsuarioDomain;

public interface UsuarioDAO {
	
	/**
	 * Interface para funcionalidades de UsuarioDAO
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 1ª Iteração
	 */

	public UsuarioDomain getUsuario(String login) throws Exception;
	public void addUsuario(UsuarioDomain usuario) throws Exception;
	
	
	
}
