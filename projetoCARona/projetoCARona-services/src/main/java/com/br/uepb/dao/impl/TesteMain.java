package com.br.uepb.dao.impl;

import com.br.uepb.domain.UsuarioDomain;

public class TesteMain {

	public static void main(String[] args) {
		UsuarioDomain usuario;
		try {
			usuario = new UsuarioDomain("Luana", "Luana", "l", "l", "l");
			UsuarioDAOImpl.getInstance().addUsuario(usuario);
			System.out.println("TesteMain - criou sessao");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
