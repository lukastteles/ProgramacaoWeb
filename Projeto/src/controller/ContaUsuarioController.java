package controller;

import java.util.ArrayList;

import model.Usuario;

public class ContaUsuarioController {

	private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	
	public void criarUsuario(String login, String senha, String nome, String endereco, String email){
		 Usuario novoUsuario = new Usuario(login, senha, nome, endereco, email);		 
		 usuarios.add(novoUsuario);
	}
	
	public int abrirSessao(String login, String senha){
		int idSessao = 001;
		return idSessao;
	}
	
	public String getAtributoUsuario(String login, String atributo){
		
		Usuario usuario = null;
		
		for (int i = 0; i < usuarios.size(); i++) {
			if(usuarios.get(i).getLogin().equals(login)){
				usuario = usuarios.get(i);
				break;
			}	
		}
		
		
		switch(atributo){
			case "login": return usuario.getLogin();
			case "nome": return usuario.getNome();
			case "senha": return usuario.getSenha();
			case "endereco": return usuario.getEndereco();
			case "email": return usuario.getEmail();
			default: return "atributo nao existe";
			
		}
	}
	
}
