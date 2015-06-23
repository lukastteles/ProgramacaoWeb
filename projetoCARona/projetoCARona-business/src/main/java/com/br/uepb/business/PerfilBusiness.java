package com.br.uepb.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.InteresseEmCaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.SolicitacaoVagaDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.InteresseEmCaronaDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

/**
 * Classe para as regras de negócio referentes ao perfil
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
@Component
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
	public List<CaronaDomain> getHistoricoDeVagasEmCaronas(String login) throws Exception{
		logger.debug("buscando histórico de vagas em caronas do usuário");
		//return UsuarioDAOImpl.getInstance().getUsuario(login).getPerfil().getHistoricoDeVagasEmCaronas();
		
		List<SolicitacaoVagaDomain> solicitacaoDeVagasEmCaronas =  SolicitacaoVagaDAOImpl.getInstance().getHistoricoDeVagasEmCaronas(login);
		List<CaronaDomain> historicoDeVagasEmCaronas = new ArrayList<CaronaDomain>();
		for (SolicitacaoVagaDomain solicitacao : solicitacaoDeVagasEmCaronas) {
			historicoDeVagasEmCaronas.add(CaronaDAOImpl.getInstance().getCarona(solicitacao.getIdCarona()));
		}
		return historicoDeVagasEmCaronas;
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

	/**
	 * Cadastra o interesse em uma carona que tenha origem, destino e data especificados. O Usuario será notificado caso uma carona compativel apareca.
	 * @param idSessao Id do Usuario
	 * @param origem Origem para a carona de interesse
	 * @param destino Destino para a carona de interesse
	 * @param data Data para a carona de interesse
	 * @param horaInicio Hora Inicio para a carona de interesse
	 * @param horaFim Hora Fim para a carona de interesse
	 * @return Retorna o id do cadastro de interesse
	 * @throws Exception Lanca excecao se a origem ou o destgino forem invalidos ou se hover problema com a conexão com o banco
	 */
	public int cadastraInteresse(String idSessao, String origem, String destino, String data, String horaInicio, String horaFim) throws Exception {
		SessaoDAOImpl.getInstance().getSessao(idSessao);				
		InteresseEmCaronaDomain interesse = new InteresseEmCaronaDomain(idSessao, origem, destino, data, horaInicio, horaFim);
		return InteresseEmCaronaDAOImpl.getInstance().addIntereseEmCarona(interesse);		
	}
	
	/**
	 * Pega os Interresses em caronas de um usuario
	 * @param idSessao Id do usuario
	 * @return Interresses do usuario
	 * @throws Exception Lança exceção se o id for invalido
	 */
	public List<InteresseEmCaronaDomain> getInteresses(String idSessao) throws Exception{
		List<InteresseEmCaronaDomain> interesses = InteresseEmCaronaDAOImpl.getInstance().getInteresseEmCaronas(idSessao);
		return interesses;
	}
	
	/**
	 * Apaga o interesse em carona
	 * @param idInteresse id do Interesse em carona 
	 * @throws Exception Lança exceção se o id for invalido
	 */
	public void apagaInteresseEmCarona(String idInteresse) throws Exception{
		InteresseEmCaronaDAOImpl.getInstance().apagaInteresse(idInteresse);
	}
	
	/**
	 * Verifica se há alguma mensagem referente ao interesse em caronas
	 * @param idSessao Id do Usuario
	 * @return Mensagem informando sobre caronas de interesse
	 * @throws Exception Lanca excecao se hover problema com a conexão com o banco
	 */
	public String verificarMensagensPerfil(String idSessao) throws Exception {
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		InteresseEmCaronaDomain interesseEmCaronas;
		CaronaDomain carona;
		UsuarioDomain usuario;
		String mensagem = "";
		
		List<InteresseEmCaronaDomain> interesses = InteresseEmCaronaDAOImpl.getInstance().getInteresseEmCaronas(idSessao);
		if (!interesses.isEmpty()) {
			interesseEmCaronas = interesses.get(0);
		
			carona = CaronaDAOImpl.getInstance().getCaronaByInteresse(interesseEmCaronas);
			if(carona == null){
				return mensagem;
			}
		
			usuario = UsuarioDAOImpl.getInstance().getUsuario(carona.getIdSessao());
		
			mensagem = "Carona cadastrada no dia "+carona.getData()+", às "+carona.getHora()+" de acordo com os seus interesses registrados. Entrar em contato com "+usuario.getPerfil().getEmail();
		}
		
		return mensagem;
	}
	

}
