package com.br.uepb.dao;

import java.util.ArrayList;
import java.util.List;

import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;

/**
 * Interface DAO para o objeto SolicitacaoVagaDomain
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
public interface SolicitacaoVagaDAO {
	
	/**
	 * Adiciona uma solicitação de vaga na lista
	 * @param solicitacaoVaga Solicitação de vaga a ser adicionada
	 * @throws Exception 
	 */
	public void addSolicitacaoVaga(SolicitacaoVagaDomain solicitacaoVaga) throws Exception;
	
	
	/**
	 * Apaga uma solicitação de vaga da lista
	 * @param idSolicitacao Id da Solicitação de vaga
	 * @throws Exception Lança exceção se não houver solicitação para id especificado ou se ele for vazio
	 */
	public void deleteSolicitacaoVaga(String idSolicitacao) throws Exception;
	
	
	/**
	 * Pega uma solicitação de vaga na lista
	 * @param idSolicitacao Id da Solicitação
	 * @return Solicitação com o id especificado
	 * @throws Exception Lança exceção se não houver solicitação para id especificado ou se ele for vazio
	 */
	public SolicitacaoVagaDomain getSolicitacaoVaga(String idSolicitacao) throws Exception;
	
	
	/**
	 * Pega todas as solicitacões que já foram confirmadas
	 * @param idCarona id da carona
	 * @return Lista de solicitações de vaga
	 * @throws Exception 
	 */
	public ArrayList<SolicitacaoVagaDomain> getSolicitacoesConfirmadas(String idCarona) throws Exception;
	
	
	/**
	 * Pega todas as solicitacões que não foram confirmadas
	 * @param idCarona Id da carona
	 * @return Lista de solicitações de vaga
	 * @throws Exception 
	 */
	public ArrayList<SolicitacaoVagaDomain> getSolicitacoesPendentes(String idCarona) throws Exception;
	
	/**
	 * Pega a solicitaco do usuario para a carona informada
	 * @param idCarona Id da carona
	 * @return Soliciracao do usuario para a vaga
	 * @throws Exception
	 */
	public SolicitacaoVagaDomain getSolicitacaoUsuario(String login, String idCarona) throws Exception;
	
	/**
	 * Atualiza algum atributo que tenha sido modificado para uma solicitacao de vaga desde que o Id exista
	 * @param solicitacaoVaga Solicitacao a ser atualizada
	 */
	public void atualizaSolicitacaoVaga(SolicitacaoVagaDomain solicitacaoVaga);
	
	/**
	 * Retorna a lista de todas as caronas cadastradas
	 * @param login Id do motorista
	 * @return Lista de solicitações de vaga
	 * @throws Exception
	 */
	public List<SolicitacaoVagaDomain> getHistoricoDeVagasEmCaronas(String login) throws Exception;
	
	/**
	 * Informa se o usuario participou ou nao da carona
	 * @param idCarona Id da carona
	 * @param login Id do usuario
	 * @return boolean Retorna "true" se o usuario participou da carona ou "false" caso contrario
	 * @throws Exception
	 */
	public boolean participouCarona(String idCarona, String login) throws Exception;

	/**
	 * Pega uma solicitação de vaga na lista
	 * @param idCarona Id da carona
	 * @param loginCaroneiro Id do caroneiro participante da carona
	 * @return Solicitacao da vaga
	 * @throws Exception
	 */
	public SolicitacaoVagaDomain getSolicitacaoVaga(String idCarona, String loginCaroneiro) throws Exception;
	
	/**
	 * Retorna a lista de todas as solicitacoes de vaga cadastradas com base na avaliacao sobre o caroneiro informada
	 * @param login Id do usuario
	 * @param review Avaliacao da presenca do caroneiro na carona
	 * @return Lista de solicitacoes de vaga
	 * @throws Exception
	 */
	public List<SolicitacaoVagaDomain> getSolicitacoesByReviewVaga(String login, String review) throws Exception;

	/**
	 * Retorna a lista de solicitacoes de vaga com base na avaliacao da carona
	 * @param caronas Lista de caronas de determinado motorista
	 * @param reviewCarona avaliacao da carona
	 * @return Lista de solicitacoes de vaga
	 * @throws Exception
	 */
	public List<SolicitacaoVagaDomain> getSolicitacoesByReviewCarona(List<CaronaDomain> caronas, String reviewCarona) throws Exception;
	
	/**
	 * Apaga todas as solicitações de vaga da lista
	 */
	public void apagaSolicitacoes();
}
