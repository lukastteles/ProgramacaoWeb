package com.br.uepb.dao;

import com.br.uepb.domain.SolicitacaoVagaDomain;

public interface SolicitacaoVagaDAO {

	public void addSolicitacaoVaga(SolicitacaoVagaDomain solicitacaoVaga);
	public SolicitacaoVagaDomain getSolicitacaoVaga(String idSolicitacao) throws Exception;
	public void aceitarSolicitacaoVaga(String idSolicitacao) throws Exception;
	public void deleteSolicitacaoVaga(String idSolicitacao) throws Exception;
	
}
