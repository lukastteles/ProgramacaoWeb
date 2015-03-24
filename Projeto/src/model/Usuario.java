package model;

import java.util.ArrayList;

public class Usuario {

	private String login;
	private String senha; 
	
	private String nome; //apagar depois
	private String email; //aagar depois
	private String endereco; //apagar depois
	
	private Perfil perfil;
	private ArrayList<Carona> caronas;
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	public Usuario(String login, String senha, String nome, String endereco, String email) {
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.endereco = endereco;
		this.email = email;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public ArrayList<Carona> getCaronas() {
		return caronas;
	}

	public void setCaronas(ArrayList<Carona> caronas) {
		this.caronas = caronas;
	}
	
}
