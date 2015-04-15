package com.br.uepb.dao.impl;

import java.util.ArrayList;

import com.br.uepb.dao.SessaoDAO;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.UsuarioDomain;

public class SessaoDAOImpl implements SessaoDAO {
	
	private static SessaoDAOImpl sessaoDAOImpl;

	//Lista de caronas
	ArrayList<SessaoDomain> listaSessoes = new ArrayList<SessaoDomain>();
	
	public static SessaoDAOImpl getInstance(){
		if(sessaoDAOImpl == null){
			sessaoDAOImpl = new SessaoDAOImpl();
			return sessaoDAOImpl;
		}else{
			return sessaoDAOImpl;
		}
	}
	
	@Override
	public void addSessao(SessaoDomain sessao) throws Exception {
		//verificar se existe usuario com esse login-senha		
		UsuarioDomain usuario = UsuarioDAOImpl.getInstance().getUsuario(sessao.getLogin());
				
		//Se a senha informada for diferente da senha do usuario
		if (!usuario.getSenha().equals(sessao.getSenha())) {
			throw new Exception("Login inválido");
		}
		
		boolean sessaoExiste = false;
		for (SessaoDomain sessaoLista : listaSessoes) {
			if (sessaoLista.getLogin().equals(sessao.getLogin())) {
				sessaoExiste = true;
				break;
			}
		}
		
		//So adiciona na lista se a sessao ainda não existir
		if (!sessaoExiste) {
			listaSessoes.add(sessao);
		}
		
	}

	@Override
	public SessaoDomain getSessao(String login) throws Exception {
		if ((login == null) || (login.trim().equals(""))) {
			throw new Exception("Sessão inválida");
		}
		
		for (SessaoDomain sessao : listaSessoes) {
			if (sessao.getLogin().equals(login)) {
				return sessao;
			}
		}
		
		throw new Exception("Sessão inexistente");
		
	}
	
	public void deleteSessao(String login) throws Exception{
		SessaoDomain sessaoApagar = null;
		for (SessaoDomain sessao : listaSessoes) {
			if (sessao.getLogin().equals(login)) {
				sessaoApagar = sessao;
				break;
			}
		}		
		
		if (!sessaoApagar.getLogin().isEmpty()) {
			listaSessoes.remove(sessaoApagar);
		}
		else {
			throw new Exception("Sessao inexistente");
		}
	}
}
