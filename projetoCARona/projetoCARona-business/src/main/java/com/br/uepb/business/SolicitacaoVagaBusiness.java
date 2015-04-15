package com.br.uepb.business;

import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.SolicitacaoVagaDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.PontoDeEncontroDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;

public class SolicitacaoVagaBusiness {

	int idSolicitacao = 1;
	
	public SolicitacaoVagaDomain getAtributoSolicitacao(String idSolicitacao, String atributo) throws Exception{
		
		if( (atributo == null) || (atributo.trim().equals(""))){
			throw new Exception("Atributo inválido");
		}
			
		return SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(idSolicitacao);
	}
	
	public int solicitarVagaPontoEncontro(String idSessao, String idCarona, String ponto) throws Exception{		
		//TODO: verificar sobre ponto de encontro com Teles
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		PontoDeEncontroDomain pontoEncontro = carona.getPontoEncontroByNome(ponto);		 
		SolicitacaoVagaDomain solicitacaoVaga = new SolicitacaoVagaDomain(idSolicitacao+"", idSessao, idCarona, pontoEncontro);
		SolicitacaoVagaDAOImpl.getInstance().addSolicitacaoVaga(solicitacaoVaga);
		idSolicitacao++;
		return idSolicitacao-1;
	}
	
	public void aceitarSolicitacaoPontoEncontro(String idSessao, String idSolicitacao) throws Exception{
		//metodo chamado apenas para retornar a sessao, caso não exista vai dar erro 
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		SolicitacaoVagaDAOImpl.getInstance().aceitarSolicitacaoVaga(idSolicitacao);		
	}
	
	public void desistirRequisicao(String idSessao, String idCarona, String idSolicitacao) throws Exception{
		//metodo chamado apenas para retornar a sessao, caso não exista vai dar erro 
		//SessaoDomain sessao = SessaoDAOImpl.getInstance().getSessao(idSessao);		
		//CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		//TODO: metodo incompleto
		
		SolicitacaoVagaDAOImpl.getInstance().deleteSolicitacaoVaga(idSolicitacao);
	}
	
	//getSolicitacoesConfirmadas();
	//getSolicitacoesPendentes();
	//solicitarVaga();
		
	//aceitarSolicitacao();
	//aceitarSolicitacaoPontoEncontro();
	//desistirRequisicao();
	//rejeitarSolicitacao();
}
