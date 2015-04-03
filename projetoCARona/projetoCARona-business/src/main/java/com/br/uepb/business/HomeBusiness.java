package com.br.uepb.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.uepb.dao.UserDAO;
import com.br.uepb.domain.UsuarioDomain;

@Component
public class HomeBusiness {
	
	@Autowired
	private UserDAO userDao;
	
	public UsuarioDomain retrieveUser(String cpf){
		return userDao.getUser(cpf);
	}
	
}
