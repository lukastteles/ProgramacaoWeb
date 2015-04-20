package com.br.uepb.dao;

import java.util.ArrayList;

import com.br.uepb.domain.SolicitacaoVagaDomain;

public interface SolicitacaoVagaDAO {

	/**
	 * Interface DAO para o objeto SolicitacaoVagaDomain
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 20/04/2015
	 */
	
	
	/**
	 * Adiciona uma solicitação de vaga na lista
	 * @param solicitacaoVaga SolocitacaoVagaDomain - Solicitação de vaga a ser adicionada
	 */
	public void addSolicitacaoVaga(SolicitacaoVagaDomain solicitacaoVaga);
	
	
	/**
	 * Apaga uma solicitação de vaga da lista
	 * @param idSolicitacao String - Id da Solicitação de vaga
	 * @throws Exception - Lança exceção se não houver solicitação para id especificado ou se ele for vazio
	 */
	public void deleteSolicitacaoVaga(String idSolicitacao) throws Exception;
	
	
	/**
	 * Pega uma solicitação de vaga na lista
	 * @param idSolicitacao String - Id da Solicitação
	 * @return SolicitacaoVagaDomain - Solicitação com o id especificado
	 * @throws Exception - Lança exceção se não houver solicitação para id especificado ou se ele for vazio
	 */
	public SolicitacaoVagaDomain getSolicitacaoVaga(String idSolicitacao) throws Exception;
	
	
	/**
	 * Pega todas as solicitacões que já foram confirmadas
	 * @param idCarona String - id da carona
	 * @return ArrayList<SolicitacaoVagaDomain> - Lista de solicitações de vaga
	 */
	public ArrayList<SolicitacaoVagaDomain> getSolicitacoesConfirmadas(String idCarona);
	
	
	/**
	 * Pega todas as solicitacões que não foram confirmadas
	 * @param idCarona String - id da carona
	 * @return ArrayList<SolicitacaoVagaDomain> - Lista de solicitações de vaga
	 */
	public ArrayList<SolicitacaoVagaDomain> getSolicitacoesPendentes(String idCarona);
	
	
	/**
	 * Apaga todas as solicitações de vaga da lista
	 */
	public void apagaSolicitacoes();
}
