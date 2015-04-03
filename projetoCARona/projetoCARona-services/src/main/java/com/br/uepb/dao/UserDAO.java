package com.br.uepb.dao;

import com.br.uepb.domain.UsuarioDomain;

public interface UserDAO {

	public UsuarioDomain getUser(String cpf);
	
}
