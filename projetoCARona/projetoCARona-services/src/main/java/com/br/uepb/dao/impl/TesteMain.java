package com.br.uepb.dao.impl;

import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.UsuarioDomain;

public class TesteMain {

	//TODO: Teles veja esse teste
	public static void main(String[] args) {
		SessaoDomain sessao;
		try {
			//cria e adiciona o usuario na lista
			UsuarioDomain usuario = new UsuarioDomain("Luana", "123", "Luana", "rua", "email");
			UsuarioDAOImpl.getInstance().addUsuario(usuario);
			
			sessao = new SessaoDomain("Luana", "123");
			System.out.println("TESTE MAIN - ABRINDO SESSAO....");
			SessaoDAOImpl.getInstance().addSessao(sessao);
			System.out.println("TESTE MAIN - FECHANDO SESSAO....");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
		System.out.println("FIM");

	}

}
