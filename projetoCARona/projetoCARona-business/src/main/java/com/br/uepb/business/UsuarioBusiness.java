package com.br.uepb.business;

import org.springframework.stereotype.Component;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

/**
 * Classe que contém a lógica de negócio para as funcionalidades referentes ao Usuário
 * @author Luana Janaina / Lukas Teles 
 * @version 0.1
 * @since 20/04/2015
 */
@Component
public class UsuarioBusiness {
	
	/**
	 * Método para criar um novo usuário
	 * @param login Login do usuário
	 * @param senha Senha do usuário
	 * @param nome Nome do usuário
	 * @param endereco Endereço do usuário
	 * @param email Email do usuário
	 * @throws Exception Lança exceção se qualquer parâmetro informado for null, vazio ou se não estiver dentro dos padrões definidos
	 * Os Seguintes padrões utilizados são:
	 *  - Data: dd/MM/yyyy
	 *  - Hora: HH:mm
	 *  - Email: email@email.com
	 */
	public void criarUsuario(String login, String senha, String nome, String endereco, String email) throws Exception{
		UsuarioDomain usuario = new UsuarioDomain(login, senha, nome, endereco, email);
		UsuarioDAOImpl.getInstance().addUsuario(usuario);
	}
	
	/**
	 * Método para pegar os dados relacionados do usuário
	 * @param login Login do usuário
	 * @param atributo Tipo de dado do usuário a ser retornado
	 * @return Dado do usuário
	 * @throws Exception Lança exceção se qualquer parâmetro informado for null, vazio ou inesistente
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
