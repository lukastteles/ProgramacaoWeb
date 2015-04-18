package com.br.uepb.business;

import java.util.ArrayList;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.domain.PontoDeEncontroDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class PontoDeEncontroBusiness {
	
	int id = 1;
	
	/** Objeto DAO para manipular os objetos da classe UsuarioDomain*/
	//private CaronaDAOImpl caronaDAOImpl =  new CaronaDAOImpl();
	
	public String sugerirPontoEncontro(String idSessao, String idCarona, String pontoDeEncontro) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		String idSugestao = ""+id;
		id++;
		PontoDeEncontroDomain ponto = new PontoDeEncontroDomain(idSugestao, pontoDeEncontro);
		CaronaDAOImpl.getInstance().getCarona(idCarona).addPontoDeEncontro(ponto);
		return idSugestao;
	}
	
	public String sugerirPontoEncontro(String idSessao, String idCarona, String[] pontos) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		String idSugestao = ""+id;
		id++;
		
		for (int i = 0; i < pontos.length; i++) {
			PontoDeEncontroDomain ponto = new PontoDeEncontroDomain(idSugestao, pontos[i]);
			CaronaDAOImpl.getInstance().getCarona(idCarona).addPontoDeEncontro(ponto);
		}
		return idSugestao;
	}
	
	public void responderSugestaoPontoEncontro(String idSessao, String idCarona, String idSugestao, String ponto) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		ArrayList<PontoDeEncontroDomain> pontosSugestao = CaronaDAOImpl.getInstance().getCarona(idCarona).getPontoEncontro(idSugestao);
		
		for (PontoDeEncontroDomain pontoSugestao : pontosSugestao) {
			if(pontoSugestao.getPontoDeEncontro().equals(ponto)){
				pontoSugestao.setFoiAceita(true);
				ponto = "aceito";
			}
		}
		if(!ponto.equals("aceito")){
			throw new ProjetoCaronaException(MensagensErro.PONTO_INVALIDO);
		}
	}
	
	public void responderSugestaoPontoEncontro(String idSessao, String idCarona, String idSugestao, String pontos[]) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		ArrayList<PontoDeEncontroDomain> pontosSugestao = CaronaDAOImpl.getInstance().getCarona(idCarona).getPontoEncontro(idSugestao);
		for (int i = 0; i < pontos.length; i++) {
			for (PontoDeEncontroDomain pontoSugestao : pontosSugestao) {
				if(pontoSugestao.getPontoDeEncontro().equals(pontos[i])){
					pontoSugestao.setFoiAceita(true);
					pontos[i] = "aceito";
				}
			}
		}
		for (int i = 0; i < pontos.length; i++) {
			//se nao pertence a sugestao, cria um novo ponto
			if(!pontos[i].equals("aceito")){
				sugerirPontoEncontro(idSessao, idCarona, pontos[i]);
			}
		}
	}
	
	//
	// Descobrir a diferença entre os dois metodos seguintes
	//
	
	public String[] getPontosSugeridos(String idSessao, String idCarona) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		ArrayList<PontoDeEncontroDomain> todosOsPontos = CaronaDAOImpl.getInstance().getCarona(idCarona).getTodosOsPontos();
		String[] todos = new String[todosOsPontos.size()];
		int count = 0;
		for (PontoDeEncontroDomain ponto : todosOsPontos) {
			todos[count] = ponto.getPontoDeEncontro();
			count++;
		}
		return todos;
	}
	
	public String[] getPontosEncontro(String idSessao, String idCarona) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
				
		ArrayList<PontoDeEncontroDomain> pontosAceitos = CaronaDAOImpl.getInstance().getCarona(idCarona).getPontoEncontroAceitos();
		
		String[] aceitos = new String[pontosAceitos.size()];
		int count = 0;
		for (PontoDeEncontroDomain ponto : pontosAceitos) {
			aceitos[count] = ponto.getPontoDeEncontro();
			count++;
		}
		return aceitos;
		
	}
	
}
