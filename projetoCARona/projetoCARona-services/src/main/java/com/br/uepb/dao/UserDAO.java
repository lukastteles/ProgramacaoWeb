package com.br.uepb.dao;

import com.br.uepb.domain.UserDomain;

public interface UserDAO {

	public UserDomain getUser(String cpf);
	
}
