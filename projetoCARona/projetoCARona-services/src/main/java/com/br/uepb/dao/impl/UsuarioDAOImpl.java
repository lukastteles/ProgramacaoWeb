package com.br.uepb.dao.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.UsuarioDAO;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

@Service
public class UsuarioDAOImpl implements UsuarioDAO {

	/**
	 * Classe DAO para o objeto UsuarioDomain
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 1ª Iteração
	 */
	private static UsuarioDAOImpl usuarioDAOImpl;
	
	//final static Logger logger = Logger.getLogger(UsuarioDAOImpl.class);
	//Lista de usuários
	ArrayList<UsuarioDomain> listaUsuarios = new ArrayList<UsuarioDomain>();
	
	//Singleton para UsuarioDAOImpl
	public static UsuarioDAOImpl getInstance(){
		if(usuarioDAOImpl == null){
			usuarioDAOImpl = new UsuarioDAOImpl();
		}
		return usuarioDAOImpl;		
	}
	
	///////////////////////
	private UsuarioDAOImpl() {
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
		if ( (login == null) || (login.trim().equals("")) ){
			throw new ProjetoCaronaException(MensagensErro.LOGIN_INVALIDO);
		}
		
		for (UsuarioDomain usuario : listaUsuarios) {
			if(usuario.getLogin().equals(login)){
				return usuario;
			}
		}
		//logger.debug("Usuário inexistente");
		throw new ProjetoCaronaException(MensagensErro.USUARIO_INEXISTENTE);
	}

	/**
	 * Método para adicionar um novo Usuário
	 * @param usuario UsuarioDomain - Usuário a ser cadastrado 
	 * @throws Exception 
	 */
	@Override
	public void addUsuario(UsuarioDomain usuario) throws Exception{
		if (loginExiste(usuario.getLogin())) {
			throw new ProjetoCaronaException(MensagensErro.USUARIO_JA_EXISTE);	
		}
		
		if (emailExiste(usuario.getPerfil().getEmail())) {
			throw new ProjetoCaronaException(MensagensErro.EMAIL_JA_EXISTE);	
		}	
		
		listaUsuarios.add(usuario);
		
		//logger
	}
	
	public boolean loginExiste(String login) {
		for (UsuarioDomain usuario : listaUsuarios) {
			if(usuario.getLogin().equals(login)){
				return true;
			}
		}
		return false;
	}
	
	public boolean emailExiste(String email) {
		for (UsuarioDomain usuario : listaUsuarios) {
			if(usuario.getPerfil().getEmail().equals(email)){
				return true;
			}
		}
		return false;
	}
	
	public void apagaUsuarios(){
		if (listaUsuarios.size() > 0) {
			listaUsuarios.removeAll(listaUsuarios);
		}
	}
}
