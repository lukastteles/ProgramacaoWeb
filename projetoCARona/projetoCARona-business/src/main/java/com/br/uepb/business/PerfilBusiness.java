package com.br.uepb.business;

import java.util.List;

import org.apache.log4j.Logger;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.SolicitacaoVagaDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

/**
 * Classe para as regras de negócio referentes ao perfil
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
public class PerfilBusiness {
	
	final static Logger logger = Logger.getLogger(PerfilBusiness.class);
	
	/**
	 * Método para visualizar as informações do perfil
	 * @param idSessao Id da sessão atual
	 * @param login Login do usuário
	 * @return Retorna o login do usuário
	 * @throws Exception Lança exceção se qualquer parâmetro informado for null, vazio ou inexistente ou se o idSessao for diferente do login do usuário
	 */
	public String visualizaPerfil(String idSessao, String login) throws Exception{
		logger.debug("visualizando perfil");
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		UsuarioDomain usuario;
		logger.debug("buscando usuário");
		try{
			usuario = UsuarioDAOImpl.getInstance().getUsuario(login);
		}catch(Exception e){
			throw new ProjetoCaronaException(MensagensErro.LOGIN_INVALIDO);
		}
		logger.debug("usuário encontrado");
		
		usuario.getPerfil();
		logger.debug("perfil encontrado");
		return usuario.getLogin();
	}
	
	/**
	 * Método para retornar o nome do usuário
	 * @param login Login do usuário
	 * @return Nome do usuário
	 * @throws Exception Lança exceção se o login for null, vazio ou inexistente
	 */
	public String getNome(String login) throws Exception{
		logger.debug("buscando nome do usuário");
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getNome();
	}
	
	/**
	 * Método para retornar o endereço do usuário 
	 * @param login Login do usuário
	 * @return Endereço do usuário
	 * @throws Exception Lança exceção se o login for null, vazio ou inexistente 
	 */
	public String getEndereco(String login) throws Exception{
		logger.debug("buscando endereço do usuário");
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getEndereco();
	}
	
	/**
	 * Método para retornar o email do usuário
	 * @param login Login do usuário
	 * @return Email do usuário
	 * @throws Exception Lança exceção se o login for null, vazio ou inexistente
	 */
	public String getEmail(String login) throws Exception{
		logger.debug("buscando email do usuário");
		return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getEmail();
	}
	
	/**
	 * Método para retornar o histórico das caronas do usuário
	 * @param login Login do usuário
	 * @return Histórico das caronas do usuário
	 * @throws Exception Lança exceção se o login for null, vazio ou inexistente
	 */
	public List<CaronaDomain> getHistoricoDeCaronas(String login) throws Exception{
		logger.debug("buscando histórico de caronas do usuário");
		List<CaronaDomain> historicoCaronas =  CaronaDAOImpl.getInstance().getHistoricoDeCaronas(login);
		return historicoCaronas;
	}
	
	/**
	 * Método para retornar o histórico de vagas na carona
	 * @param login Login do usuário 
	 * @return Histórico de vagas nas caronas do usuário
	 * @throws Exception Lança exceção se o login for null, vazio ou inexistente
	 */
	public String[] getHistoricoDeVagasEmCaronas(String login) throws Exception{
		logger.debug("buscando histórico de vagas em caronas do usuário");
		//return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getHistoricoDeVagasEmCaronas();
		
		List<SolicitacaoVagaDomain> historicoDeVagasEmCaronas =  SolicitacaoVagaDAOImpl.getInstance().getHistoricoDeVagasEmCaronas(login);
		String[] historico = new String[historicoDeVagasEmCaronas.size()];
		for (int i=0; i<historico.length; i++) {
			historico[i] = historicoDeVagasEmCaronas.get(i).getIdCarona();
		}
		return historico;
	}
	
	/**
	 * Método para retornar o histórico das caronas classificadas como tranquilas e seguras
	 * @param login Login do usuário 
	 * @return Histórico das caronas tranquilas e seguras
	 * @throws Exception Lança exceção se o login for null, vazio ou inexistente
	 */
	public List<SolicitacaoVagaDomain> getCaronasSegurasETranquilas(String login) throws Exception{
		logger.debug("buscando histórico de caronas seguras e tranquilas do usuário");				
		List<CaronaDomain> caronas = CaronaDAOImpl.getInstance().getHistoricoDeCaronas(login);
		List<SolicitacaoVagaDomain> solicitacoesVaga = SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesByReviewCarona(caronas, MensagensErro.SEGURA_TRANQUILA);		
		return solicitacoesVaga;
	}
	
	/**
	 * Método para retornar o histórico das caronas que não funcionaram
	 * @param login Login do usuário 
	 * @return Histórico das caronas que não funcionaram
	 * @throws Exception Lança exceção se o login for null, vazio ou inexistente
	 */
	public List<SolicitacaoVagaDomain> getCaronasQueNaoFuncionaram(String login) throws Exception{
		logger.debug("buscando histórico de caronas que não funcionaram");
		List<CaronaDomain> caronas = CaronaDAOImpl.getInstance().getHistoricoDeCaronas(login);
		List<SolicitacaoVagaDomain> solicitacoesVaga = SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesByReviewCarona(caronas, MensagensErro.NAO_FUNCIONOU);		
		return solicitacoesVaga;
	}
	
	/**
	 * Método para retornar o histórico da quantidade de vagas que faltam nas caronas oferecidas pelo usuário
	 * @param login Login do usuário  
	 * @return Histórico da quantidade de vagas que faltam nas caronas
	 * @throws Exception Lança exceção se o login for null, vazio ou inexistente
	 */
	public List<SolicitacaoVagaDomain> getFaltasEmVagasDeCaronas(String login) throws Exception{
		logger.debug("buscando histórico faltas em vagas de caronas do usuário");
		List<SolicitacaoVagaDomain> faltasEmCaronas = SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesByReviewVaga(login, MensagensErro.FALTOU);		
		return faltasEmCaronas;
	}
	
	/**
	* Método para retornar o histórico das presenças nas caronas 
	* @param login Login do usuário  
	* @return Histórico da presenças nas caronas
	* @throws Exception Lança exceção se o login for null, vazio ou inexistente
	*/
	public List<SolicitacaoVagaDomain> getPresencasEmVagasDeCaronas(String login) throws Exception{
		logger.debug("buscando histórico presenças em vagas de caronas do usuário");
		List<SolicitacaoVagaDomain> presencasEmCaronas = SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesByReviewVaga(login, MensagensErro.NAO_FALTOU);		
		return presencasEmCaronas;
	}
	
}
