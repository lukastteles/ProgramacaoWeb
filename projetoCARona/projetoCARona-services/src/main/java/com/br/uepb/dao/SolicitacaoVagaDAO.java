package com.br.uepb.dao;

import java.util.ArrayList;
import java.util.List;

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
	 * Apaga todas as solicitações de vaga da lista
	 */
	public void apagaSolicitacoes();

	/**
	 * Atualiza algum atributo que tenha sido modificado para uma solicitacao de vaga desde que o Id exista
	 * @param solicitacaoVaga Solicitacao a ser atualizada
	 */
	public void atualizaSolicitacaoVaga(SolicitacaoVagaDomain solicitacaoVaga);
	
	public List<SolicitacaoVagaDomain> getHistoricoDeVagasEmCaronas(String login) throws Exception;
	
	public boolean participouCarona(String idCarona, String login) throws Exception;

	public SolicitacaoVagaDomain getSolicitacaoVaga(String idCarona, String loginCaroneiro) throws Exception;
	
	public List<SolicitacaoVagaDomain> getSolicitacoesByFaltas(String login) throws Exception;
	
	public List<SolicitacaoVagaDomain> getSolicitacoesByPresencas(String login) throws Exception;
}
