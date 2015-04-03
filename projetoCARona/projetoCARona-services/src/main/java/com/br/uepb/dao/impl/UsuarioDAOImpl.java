package com.br.uepb.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.br.uepb.dao.UsuarioDAO;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.UsuarioDomain;

@Service
public class UsuarioDAOImpl implements UsuarioDAO {

	/**
	 * Classe DAO para o objeto UsuarioDomain
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 1ª Iteração
	 */
	
	final static Logger logger = Logger.getLogger(UsuarioDAOImpl.class);
	//Lista de usuários
	ArrayList<UsuarioDomain> listaUsuarios = new ArrayList<UsuarioDomain>();
	
	
	///////////////////////
	public UsuarioDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	//////////////////////
	
	/**
	 * Método para encontrar um Usuário pelo login
	 * @param login String - login do Usuário a ser buscado
	 * @return UsuarioDomain - Usuário buscado
	 * @throws Exception - Usuário não encontrado
	 */
	@Override
	public UsuarioDomain getUsuario(String login) throws Exception {
		for (UsuarioDomain usuario : listaUsuarios) {
			if(usuario.getLogin().equals(login)){
				return usuario;
			}
		}
		logger.debug("Usuário não encontrado");
		throw new Exception("Usuário não encontrado");
	}

	/**
	 * Método para adicionar um novo Usuário
	 * @param usuario UsuarioDomain - Usuário a ser cadastrado 
	 */
	@Override
	public void addUsuario(UsuarioDomain usuario){
		listaUsuarios.add(usuario);
		//logger
	}
}
