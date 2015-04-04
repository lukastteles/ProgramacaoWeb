package com.br.uepb.business;

import com.br.uepb.domain.PontoDeEncontro;

public class PontoDeEncontroBusiness {
	
	public String sugerirPontoEncontro(String idSessao, String idCarona, String pontoDeEncontro){
		PontoDeEncontro ponto = new PontoDeEncontro(idCarona, pontoDeEncontro);
		
		String idSugestao = null;
		return idSugestao;
	}
	
	public String sugerirPontoEncontro(String idSessao, String idCarona, String[] pontos){
		String idSugestao = null;
		return idSugestao;
	}
	
	public void responderSugestaoPontoEncontro(String idSessao, String idCarona, String idSugestao, String ponto){
		
	}
	
	public void getPontosSugeridos(){
		
	}
	
	public void getPontosEncontro(){
		
	}
	
}
