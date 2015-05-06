package com.br.uepb.dao.impl;

import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.UsuarioDomain;

public class TesteMain {

	//TODO: Teles veja esse teste
	public static void main(String[] args) {
		try {
			//cria e adiciona o usuario na lista
			UsuarioDomain usuario = new UsuarioDomain("Luana", "123", "Luana", "rua", "email");
			UsuarioDAOImpl.getInstance().addUsuario(usuario);
			System.out.println("salvo\n");
			usuario = UsuarioDAOImpl.getInstance().getUsuario("Luana");
			System.out.println(usuario.getLogin());
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
		System.out.println("FIM");

	}

}
