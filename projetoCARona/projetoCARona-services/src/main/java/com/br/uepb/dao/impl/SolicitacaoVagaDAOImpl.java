package com.br.uepb.dao.impl;

import java.util.ArrayList;

import com.br.uepb.dao.SolicitacaoVagaDAO;
import com.br.uepb.domain.SolicitacaoVagaDomain;

public class SolicitacaoVagaDAOImpl implements SolicitacaoVagaDAO {
	
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
	
	public SolicitacaoVagaDomain getSolicitacaoVaga(String idSolicitacao) throws Exception {
		for (SolicitacaoVagaDomain solicitacaoVagaDomain : listaSolicitacaoVagas) {
			if (solicitacaoVagaDomain.getId().equals(idSolicitacao)) {
				return solicitacaoVagaDomain;
			}			
		}
		
		throw new Exception("Solicitação inexistente");
	}
	
	//TODO: retirar esse metodo
	public void aceitarSolicitacaoVaga(String idSolicitacao) throws Exception{				
	}
	
	public void deleteSolicitacaoVaga(String idSolicitacao) throws Exception{		
		SolicitacaoVagaDomain solicitacaoVaga = getSolicitacaoVaga(idSolicitacao);
		listaSolicitacaoVagas.remove(solicitacaoVaga);
	}
	
}
