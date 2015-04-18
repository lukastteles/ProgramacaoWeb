package com.br.uepb.business;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class PerfilBusiness {
	
	public String visualizaPerfil(String idSessao, String login) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		UsuarioDomain usuario;
		
		try{
			usuario = UsuarioDAOImpl.getInstance().getUsuario(login);
		}catch(Exception e){
			throw new ProjetoCaronaException(MensagensErro.LOGIN_INVALIDO);
		}
		
		usuario.getPerfil();
		return usuario.getLogin();
	}
	
	
	public String getNome(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getNome();
	}
	
	public String getEndereco(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getEndereco();
	}
	
	public String getEmail(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getEmail();
	}
	
	public String[] getHistoricoDeCaronas(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getHistoricoDeCaronas();
	}
	
	public String[] getHistoricoDeVagasEmCaronas(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getHistoricoDeVagasEmCaronas();
	}
	
	public String[] getCaronasSegurasETranquilas(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getCaronasSegurasETranquilas();
	}
	
	public String[] getCaronasQueNaoFuncionaram(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getCaronasQueNaoFuncionaram();
	}
	
	public String[] getFaltasEmVagasDeCaronas(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getFaltasEmVagasDeCaronas();
	}
	
	public String[] getPresencasEmVagasDeCaronas(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getPresencasEmVagasDeCaronas();
	}
	
}
