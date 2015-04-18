package com.br.uepb.dao.impl;

import java.util.ArrayList;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.SessaoDAO;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

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
			throw new ProjetoCaronaException(MensagensErro.LOGIN_INVALIDO);
		}
		
		boolean sessaoExiste = false;
		for (SessaoDomain sessaoLista : listaSessoes) {
			if (sessaoLista.getLogin().equals(sessao.getLogin())){
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
			throw new ProjetoCaronaException(MensagensErro.SESSAO_INVALIDA);
		}
		
		for (SessaoDomain sessao : listaSessoes) {
			if (sessao.getLogin().equals(login)) {
				return sessao;
			}
		}
		
		throw new ProjetoCaronaException(MensagensErro.SESSAO_INEXISTENTE);
		
	}
	
	public void deleteSessao(String login) throws Exception{
		SessaoDomain sessaoApagar = null;
		for (SessaoDomain sessao : listaSessoes) {
			if (sessao.getLogin().equals(login)) {
				sessaoApagar = sessao;
				break;
			}
		}		
		
		if (sessaoApagar != null) {
			listaSessoes.remove(sessaoApagar);
		}
		else {
			throw new ProjetoCaronaException(MensagensErro.SESSAO_INEXISTENTE);
		}
	}
	
	public void apagaSessoes(){
		if (listaSessoes.size() > 0) {
			listaSessoes.removeAll(listaSessoes);
			
		}
	}
}
