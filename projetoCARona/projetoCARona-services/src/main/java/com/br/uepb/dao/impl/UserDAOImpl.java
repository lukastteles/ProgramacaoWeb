package com.br.uepb.dao.impl;

import org.springframework.stereotype.Service;

import com.br.uepb.dao.UserDAO;
import com.br.uepb.domain.UsuarioDomain;

@Service
public class UserDAOImpl implements UserDAO {

	@Override
	public UsuarioDomain getUser(String cpf) {
		UsuarioDomain ud = new UsuarioDomain();
		ud.setCpf("0538953958395839");
		ud.setNome("noca");
		return ud;
	}

}
