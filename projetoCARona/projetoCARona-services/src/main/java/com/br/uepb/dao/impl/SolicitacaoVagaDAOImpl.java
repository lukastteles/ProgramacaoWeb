package com.br.uepb.dao.impl;

import java.util.ArrayList;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.SolicitacaoVagaDAO;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class SolicitacaoVagaDAOImpl implements SolicitacaoVagaDAO {
	
	public int idSolicitacao = 1;
	ArrayList<SolicitacaoVagaDomain> listaSolicitacaoVagas = new ArrayList<SolicitacaoVagaDomain>();
	
	private static SolicitacaoVagaDAOImpl solocitacaoVagaDAOImpl;
	
	public static SolicitacaoVagaDAOImpl getInstance(){
		if(solocitacaoVagaDAOImpl == null){
			solocitacaoVagaDAOImpl = new SolicitacaoVagaDAOImpl();
			return solocitacaoVagaDAOImpl;
		}else{
			return solocitacaoVagaDAOImpl;
		}
	}
	
	@Override
	public void addSolicitacaoVaga(SolicitacaoVagaDomain solicitacaoVaga) {
		listaSolicitacaoVagas.add(solicitacaoVaga);
	}
		
	public void deleteSolicitacaoVaga(String idSolicitacao) throws Exception{		
		SolicitacaoVagaDomain solicitacaoVaga = getSolicitacaoVaga(idSolicitacao);
		listaSolicitacaoVagas.remove(solicitacaoVaga);
	}
	
	public SolicitacaoVagaDomain getSolicitacaoVaga(String idSolicitacao) throws Exception {
		for (SolicitacaoVagaDomain solicitacaoVagaDomain : listaSolicitacaoVagas) {
			if (solicitacaoVagaDomain.getId().equals(idSolicitacao)) {
				return solicitacaoVagaDomain;
			}			
		}
		
		throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INEXISTENTE);
	}
	
	public ArrayList<SolicitacaoVagaDomain> getSolicitacoesConfirmadas(String idCarona){
		ArrayList<SolicitacaoVagaDomain> solicitacoesCarona = new ArrayList<SolicitacaoVagaDomain>();
		for (SolicitacaoVagaDomain solicitacao : listaSolicitacaoVagas) {
			if ((solicitacao.getIdCarona().equals(idCarona)) && (solicitacao.getFoiAceita())) {
				solicitacoesCarona.add(solicitacao);
			}
		}
		return solicitacoesCarona;
	}
	
	public ArrayList<SolicitacaoVagaDomain> getSolicitacoesPendentes(String idCarona){
		ArrayList<SolicitacaoVagaDomain> solicitacoesCarona = new ArrayList<SolicitacaoVagaDomain>();
		for (SolicitacaoVagaDomain solicitacao : listaSolicitacaoVagas) {
			if ((solicitacao.getIdCarona().equals(idCarona)) && (!solicitacao.getFoiAceita())) {
				solicitacoesCarona.add(solicitacao);
			}
		}
		return solicitacoesCarona;
	}

	public void apagaSolicitacoes(){
		if (listaSolicitacaoVagas.size() > 0) {			
			listaSolicitacaoVagas.removeAll(listaSolicitacaoVagas);
		}
	}
}
