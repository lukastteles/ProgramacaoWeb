package com.br.uepb.business;

import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.PontoDeEncontroDomain;
import com.br.uepb.domain.SugestaoPontoEncontroDomain;

public class SugestaoPontoEncontroBusiness {
	
	int idSugestao = 1;
	
	/** Objeto DAO para manipular os objetos da classe UsuarioDomain*/
	//private CaronaDAOImpl caronaDAOImpl =  new CaronaDAOImpl();
	
	public String sugerirPontoEncontro(String idSessao, String idCarona, String ponto) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		String id = ""+idSugestao;
		idSugestao++;
		SugestaoPontoEncontroDomain sugestao = new SugestaoPontoEncontroDomain(id, ponto);
		CaronaDAOImpl.getInstance().getCarona(idCarona).addSugestaoPontoEncontro(sugestao);
		return id;
	}
	
	public String sugerirPontoEncontro(String idSessao, String idCarona, String[] pontos) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		String id = ""+idSugestao;
		idSugestao++;
		SugestaoPontoEncontroDomain sugestao = new SugestaoPontoEncontroDomain(id, pontos);
		CaronaDAOImpl.getInstance().getCarona(idCarona).addSugestaoPontoEncontro(sugestao);
		return id;
	}
	
	public void responderSugestaoPontoEncontro(String idSessao, String idCarona, String idSugestao, String ponto) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		SugestaoPontoEncontroDomain sugestao = CaronaDAOImpl.getInstance().getCarona(idCarona).getSugestaoPontoEncontro(idSugestao);
		sugestao.setFoiAceitaPonto(ponto, true);
		
	}
	
	public void responderSugestaoPontoEncontro(String idSessao, String idCarona, String idSugestao, String ponto[]) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		SugestaoPontoEncontroDomain sugestao = CaronaDAOImpl.getInstance().getCarona(idCarona).getSugestaoPontoEncontro(idSugestao);
		
		for (int i = 0; i < ponto.length; i++) {
			try{
				sugestao.setFoiAceitaPonto(ponto[i], true);
			}catch(Exception e){
				sugerirPontoEncontro(idSessao, idCarona, ponto[i], true);
				
			}
		}
		
	}

	private void sugerirPontoEncontro(String idSessao, String idCarona, String ponto, boolean foiAceita) throws Exception {
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		String id = ""+idSugestao;
		idSugestao++;
		SugestaoPontoEncontroDomain sugestao = new SugestaoPontoEncontroDomain(id, ponto);
		CaronaDAOImpl.getInstance().getCarona(idCarona).addSugestaoPontoEncontro(sugestao);
		CaronaDAOImpl.getInstance().getCarona(idCarona).getSugestaoPontoEncontro(id).setFoiAceitaPonto(ponto, foiAceita);
		
		
	}
	
	
	//Proximas US...
	/*public void getPontosSugeridos(){
		
	}
	
	public void getPontosEncontro(){
		
	}*/
	
}
