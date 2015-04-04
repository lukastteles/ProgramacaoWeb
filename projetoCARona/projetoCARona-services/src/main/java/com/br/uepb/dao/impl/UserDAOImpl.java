package com.br.uepb.dao.impl;

import org.springframework.stereotype.Service;

import com.br.uepb.dao.UserDAO;
import com.br.uepb.domain.UserDomain;

@Service
public class UserDAOImpl implements UserDAO {

	@Override
	public UserDomain getUser(String cpf) {
		UserDomain ud = new UserDomain();
		ud.setCpf("0538953958395839");
		ud.setNome("noca");
		return ud;
	}
}