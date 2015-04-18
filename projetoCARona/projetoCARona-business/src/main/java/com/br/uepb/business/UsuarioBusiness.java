package com.br.uepb.business;

import org.springframework.stereotype.Component;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

@Component
public class UsuarioBusiness {
	
	/**
	 * Classe que contém a lógica de negócio para as funcionalidades referentes ao Usuário
	 * @author Luana Janaina / Lukas Teles 
	 * @version 0.1
	 * @since 1ª Iteração
	 */
		
	//private UsuarioDAOImpl usuarioDao = new UsuarioDAOImpl();
	
	/**
	 * Método para criar um novo usuário
	 * @param login String - Login do Usuário
	 * @param senha String - Senha do Usuário
	 * @param nome String - Nome do Usuário
	 * @param endereco String - Endereço do Usuário
	 * @param email String - Email do Usuário
	 */
	public void criarUsuario(String login, String senha, String nome, String endereco, String email) throws Exception{
		UsuarioDomain usuario = new UsuarioDomain(login, senha, nome, endereco, email);
		UsuarioDAOImpl.getInstance().addUsuario(usuario);
	}
	
	/**
	 * Método para pegar o valor de um dos atributos do Usuário
	 * @param login String - Login do Usuário
	 * @param atributo String - Atributo a ser requisitado
	 * @return String - Valor do atributo solicitado
	 */
	public String getAtributoUsuario(String login, String atributo) throws Exception{		
		if((atributo == null) || (atributo.trim().equals(""))){
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INVALIDO); 
		}
		
		UsuarioDomain usuario = UsuarioDAOImpl.getInstance().getUsuario(login);
		
		//Não usamos switch-case por causa do compilador que ficou muendo pra colocar String no parâmetro do switch
		if(atributo.equals("login")){
			return usuario.getLogin();
		}else if(atributo.equals("senha")){
			return usuario.getSenha();
		}else if(atributo.equals("nome")){
			return usuario.getPerfil().getNome();
		}else if(atributo.equals("endereco")){
			return usuario.getPerfil().getEndereco();
		}else if(atributo.equals("email")){
			return usuario.getPerfil().getEmail();
		}else {
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INEXISTENTE);
		}
		
	}
}
