package com.br.uepb.business;

import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.UsuarioDomain;

public class PerfilBusiness {
	
	public String visualizaPerfil(String idSessao, String login) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		UsuarioDomain usuario = UsuarioDAOImpl.getInstance().getUsuario(login);
		usuario.getPerfil();
		return usuario.getLogin();
	}
	
	
	public String getAtributoPerfil(String login, String atributo) throws Exception{
		UsuarioDomain usuario = UsuarioDAOImpl.getInstance().getUsuario(login);
		
		if(atributo.equals("nome")){
			return usuario.getPerfil().getNome();
		}else if(atributo.equals("endereco")){
			return usuario.getPerfil().getEndereco();
		}else if(atributo.equals("email")){
			return usuario.getPerfil().getEmail();
		}else if(atributo.equals("historico de caronas")){
			return usuario.getPerfil().getHistoricoDeCaronas();
		}else if(atributo.equals("historico de vagas em caronas")){
			return usuario.getPerfil().getHistoricoDeVagasEmCaronas();
		}else if(atributo.equals("caronas seguras e tranquilas")){
			return usuario.getPerfil().getCaronasSegurasETranquilas();
		}else if(atributo.equals("caronas que não funcionaram")){
			return usuario.getPerfil().getCaronasQueNaoFuncionaram();
		}else if(atributo.equals("faltas em vagas de caronas")){
			return usuario.getPerfil().getFaltasEmVagasDeCaronas();
		}else if(atributo.equals("presenças em vagas de caronas")){
			return usuario.getPerfil().getPresencasEmVagasDeCaronas();
		}else {
			throw new Exception("Atributo inexistente");
		}
	}
	
}
