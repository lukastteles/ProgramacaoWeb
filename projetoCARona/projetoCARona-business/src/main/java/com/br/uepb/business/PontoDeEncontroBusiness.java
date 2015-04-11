package com.br.uepb.business;

import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.PontoDeEncontroDomain;

public class PontoDeEncontroBusiness {
	
	int idPonto = 1;
	
	/** Objeto DAO para manipular os objetos da classe UsuarioDomain*/
	//private CaronaDAOImpl caronaDAOImpl =  new CaronaDAOImpl();
	
	public String sugerirPontoEncontro(String idSessao, String idCarona, String pontoDeEncontro) throws Exception{
		String id = ""+idPonto;
		idPonto++;
		PontoDeEncontroDomain ponto = new PontoDeEncontroDomain(id, idCarona, pontoDeEncontro);
		CaronaDAOImpl.getInstance().getCarona(idCarona).addPontoEncontro(ponto);
		return id;
	}
	
	public String[] sugerirPontoEncontro(String idSessao, String idCarona, String[] pontos) throws Exception{
		String[] ids = new String[pontos.length];
		int cont = 0;
		PontoDeEncontroDomain ponto;
		for (String string : pontos) {
			String id = "ponto"+idPonto;
			idPonto++;
			ponto = new PontoDeEncontroDomain(id, idCarona, string);
			CaronaDAOImpl.getInstance().getCarona(idCarona).addPontoEncontro(ponto);
			ids[cont] = id;
			cont++;
		}
		
		return ids;
	}
	
	public void responderSugestaoPontoEncontro(String idSessao, String idCarona, String idSugestao) throws Exception{
		PontoDeEncontroDomain ponto = CaronaDAOImpl.getInstance().getCarona(idCarona).getPontoEncontro(idSugestao);
		ponto.setFoiAceita(true);
		
	}
	
	public void responderSugestaoPontoEncontro(String idSessao, String idCarona, String[] sugestoes) throws Exception{
		CaronaDomain carona;
		PontoDeEncontroDomain ponto;
		
		for (String idSugestao : sugestoes) {
			carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
			ponto = carona.getPontoEncontro(idSugestao);
			ponto.setFoiAceita(true);
		}
		
	}
	
	
	//Proximas US...
	/*public void getPontosSugeridos(){
		
	}
	
	public void getPontosEncontro(){
		
	}*/
	
}
