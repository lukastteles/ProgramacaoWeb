package controller;

import java.util.ArrayList;

import model.Usuario;

public class ContaUsuarioController {

	private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	
	public void criarUsuario(String login, String senha, String nome, String endereco, String email) {
		try {
			//Tratamento dos campos do Usuário
			tratamentoCampoLogin(login);
			tratamentoCampoNome(nome);
			tratamentoCampoEmail(email);
			procuraEmailExistente(email);
			procuraLoginExistente(login);
			
			Usuario novoUsuario = new Usuario(login, senha, nome, endereco, email);		 
			usuarios.add(novoUsuario);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	private void procuraLoginExistente(String login){
		for (int i = 0; i < usuarios.size(); i++) {
			if(usuarios.get(i).getLogin().equals(login)){
				throw new Error(MensagemErro.LOGIN_EXISTENTE);
			}	
		}
	}
	
	private void procuraEmailExistente(String email){
		for (int i = 0; i < usuarios.size(); i++) {
			if(usuarios.get(i).getEmail().equals(email)){
				throw new Error(MensagemErro.EMAIL_EXISTENTE);
			}	
		}
	}
	
	private void verificaLoginUsuario(String login, String senha){
		Usuario usuario = null;
		
		for (int i = 0; i < usuarios.size(); i++) {
			if(usuarios.get(i).getLogin().equals(login)){
				usuario = usuarios.get(i);
				if (!usuario.getSenha().equals(senha)) {
					throw new Error(MensagemErro.LOGIN_INVALIDO);
				}
			}	
		}
		
		if (usuario == null) {
			throw new Error(MensagemErro.USUARIO_INEXISTENTE);
		}
		
	}
	
	public int abrirSessao(String login, String senha){		
		int idSessao = 001;
		try {
			//Tratamento dos campos do Usuário
			tratamentoCampoLogin(login);
			verificaLoginUsuario(login, senha);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
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
				
		if (usuario == null) {
			return MensagemErro.USUARIO_INEXISTENTE;
		}
		
		switch(atributo){
			case "login": return usuario.getLogin();
			case "nome": return usuario.getNome();
			case "senha": return usuario.getSenha();
			case "endereco": return usuario.getEndereco();
			case "email": return usuario.getEmail();
			default: return MensagemErro.ATRIBUTO_INEXISTENTE;				
		}
		
	}
	
	private void tratamentoEmailExistente(String login, String email) {
		if (getAtributoUsuario(login, "email").equals(email)){
			throw new Error(MensagemErro.EMAIL_EXISTENTE);
		}
	}
	
	private void tratamentoCampoLogin(String valor) {
		if ( (valor == null) || (valor.equals("")) ) { 
			throw new Error(MensagemErro.LOGIN_INVALIDO);
		}		
	}
	
	private void tratamentoCampoNome(String valor) {
		if ( (valor == null) || (valor.equals("")) ) { 
			throw new Error(MensagemErro.NOME_INVALIDO);
		}		
	}
	
	private void tratamentoCampoEmail(String valor) {
		if ( (valor == null) || (valor.equals("")) ) { 
			throw new Error(MensagemErro.EMAIL_INVALIDO);
		}		
	}
}
