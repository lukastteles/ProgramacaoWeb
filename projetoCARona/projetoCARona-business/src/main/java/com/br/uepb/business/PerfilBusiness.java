package com.br.uepb.business;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class PerfilBusiness {
	
	/**
	 * Classe para as regras de negócio referentes ao perfil
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 20/04/2015
	 */
	
	/**
	 * Método para visualizar as informações do perfil
	 * @param idSessao String - Id da sessão atual
	 * @param login String - Login do usuário
	 * @return String - Retorna o login do usuário
	 * @throws Exception - Lança exceção se qualquer parâmetro informado for null, vazio ou inexistente ou se o idSessao for diferente do login do usuário
	 */
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
	
	/**
	 * Método para retornar o nome do usuário
	 * @param login String - Login do usuário
	 * @return String - Nome do usuário
	 * @throws Exception - Lança exceção se o login for null, vazio ou inexistente
	 */
	public String getNome(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getNome();
	}
	
	/**
	 * Método para retornar o endereço do usuário 
	 * @param login String - Login do usuário
	 * @return String - Endereço do usuário
	 * @throws Exception - Lança exceção se o login for null, vazio ou inexistente 
	 */
	public String getEndereco(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getEndereco();
	}
	
	/**
	 * Método para retornar o email do usuário
	 * @param login String - Login do usuário
	 * @return String - Email do usuário
	 * @throws Exception - Lança exceção se o login for null, vazio ou inexistente
	 */
	public String getEmail(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getEmail();
	}
	
	/**
	 * Método para retornar o histórico das caronas do usuário
	 * @param login String - Login do usuário
	 * @return String[] - Histórico das caronas do usuário
	 * @throws Exception - Lança exceção se o login for null, vazio ou inexistente
	 */
	public String[] getHistoricoDeCaronas(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getHistoricoDeCaronas();
	}
	
	/**
	 * Método para retornar o histórico de vagas na carona
	 * @param login String - Login do usuário 
	 * @return String[] - Histórico de vagas nas caronas do usuário
	 * @throws Exception - Lança exceção se o login for null, vazio ou inexistente
	 */
	public String[] getHistoricoDeVagasEmCaronas(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getHistoricoDeVagasEmCaronas();
	}
	
	/**
	 * Método para retornar o histórico das caronas classificadas como tranquilas e seguras
	 * @param login String - Login do usuário 
	 * @return String[] - Histórico das caronas tranquilas e seguras
	 * @throws Exception - Lança exceção se o login for null, vazio ou inexistente
	 */
	public String[] getCaronasSegurasETranquilas(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getCaronasSegurasETranquilas();
	}
	
	/**
	 * Método para retornar o histórico das caronas que não funcionaram
	 * @param login String - Login do usuário 
	 * @return String[] - Histórico das caronas que não funcionaram
	 * @throws Exception - Lança exceção se o login for null, vazio ou inexistente
	 */
	public String[] getCaronasQueNaoFuncionaram(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getCaronasQueNaoFuncionaram();
	}
	
	/**
	 * Método para retornar o histórico da quantidade de vagas que faltam nas caronas oferecidas pelo usuário
	 * @param login String - Login do usuário  
	 * @return String[] - Histórico da quantidade de vagas que faltam nas caronas
	 * @throws Exception - Lança exceção se o login for null, vazio ou inexistente
	 */
	public String[] getFaltasEmVagasDeCaronas(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getFaltasEmVagasDeCaronas();
	}
	
	/**
	* Método para retornar o histórico das presenças nas caronas 
	* @param login String - Login do usuário  
	* @return String[] - Histórico da presenças nas caronas
	* @throws Exception - Lança exceção se o login for null, vazio ou inexistente
	*/
	public String[] getPresencasEmVagasDeCaronas(String login) throws Exception{
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getPresencasEmVagasDeCaronas();
	}
	
}
