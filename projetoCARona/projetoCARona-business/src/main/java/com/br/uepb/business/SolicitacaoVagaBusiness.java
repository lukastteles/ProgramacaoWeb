package com.br.uepb.business;

import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.domain.CaronaDomain;

public class SolicitacaoVagaBusiness {

	public String solicitarVagaPontoEncontro(String idSessao, String idCarona, String ponto) throws Exception{
		
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		
		return "";
	}
	
	//getAtributoSolicitacao
	//aceitarSolicitacaoPontoEncontro
	//desistirRequisicao
}
