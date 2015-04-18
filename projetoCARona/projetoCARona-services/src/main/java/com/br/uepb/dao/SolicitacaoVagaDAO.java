package com.br.uepb.dao;

import java.util.ArrayList;

import com.br.uepb.domain.SolicitacaoVagaDomain;

public interface SolicitacaoVagaDAO {

	public void addSolicitacaoVaga(SolicitacaoVagaDomain solicitacaoVaga);
	public void deleteSolicitacaoVaga(String idSolicitacao) throws Exception;
	public SolicitacaoVagaDomain getSolicitacaoVaga(String idSolicitacao) throws Exception;
	public ArrayList<SolicitacaoVagaDomain> getSolicitacoesConfirmadas(String idCarona);
	public ArrayList<SolicitacaoVagaDomain> getSolicitacoesPendentes(String idCarona);
	public void apagaSolicitacoes();
}
