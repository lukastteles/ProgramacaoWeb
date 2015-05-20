package com.br.uepb.dao.impl;

import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.domain.UsuarioDomain;

public class TesteMain {

	//TODO: Teles veja esse teste
	public static void main(String[] args) {
		try {
			//cria e adiciona o usuario na lista
			SolicitacaoVagaDAOImpl.getInstance().apagaSolicitacoes();
			CaronaDAOImpl.getInstance().apagaCaronas();
			UsuarioDAOImpl.getInstance().apagaUsuarios();
			
			UsuarioDomain usuario = new UsuarioDomain("Luana", "123", "Luana", "rua", "email");
			UsuarioDAOImpl.getInstance().addUsuario(usuario);
			System.out.println("salvo\n");			
			usuario = UsuarioDAOImpl.getInstance().getUsuario("Luana");			
			System.out.println(usuario.getLogin());
			
			String idCarona = ""+ CaronaDAOImpl.getInstance().idCarona;
			CaronaDomain carona = new CaronaDomain("Luana", idCarona, "L", "D", "01/01/2015", "13:00", 3);
			CaronaDAOImpl.getInstance().addCarona(carona);
			
			SolicitacaoVagaDomain solicitacao = new SolicitacaoVagaDomain(SolicitacaoVagaDAOImpl.getInstance().idSolicitacao+"", "Luana", carona.getID());
			SolicitacaoVagaDAOImpl.getInstance().addSolicitacaoVaga(solicitacao);
			SolicitacaoVagaDomain solicitacao2 = SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(solicitacao.getId());
			System.out.println(solicitacao2.getId());
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
		System.out.println("FIM");

	}

}
