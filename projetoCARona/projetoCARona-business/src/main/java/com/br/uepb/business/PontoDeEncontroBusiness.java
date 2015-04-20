package com.br.uepb.business;

import java.util.ArrayList;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.domain.PontoDeEncontroDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

/**
 * Classe as regras de negócio referentes ao ponto de encontro 
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
public class PontoDeEncontroBusiness {

	/**
	 * Método para criar uma sugestão de um ponto de encontro para uma carona
	 * @param idSessao Id da sessão
	 * @param idCarona Id da carona
	 * @param pontoDeEncontro Local do ponto de encontro
	 * @return Id da sugestão criada
	 * @throws Exception Lança exceção se qualquer parâmetro for null, inválido ou inexistente
	 */
	public String sugerirPontoEncontro(String idSessao, String idCarona, String pontoDeEncontro) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		String idSugestao = ""+ CaronaDAOImpl.getInstance().idPontoEncontro;
		CaronaDAOImpl.getInstance().idPontoEncontro++;
		PontoDeEncontroDomain ponto = new PontoDeEncontroDomain(idSugestao, pontoDeEncontro);
		CaronaDAOImpl.getInstance().getCarona(idCarona).addPontoDeEncontro(ponto);
		return idSugestao;
	}
	
	/**
	 * Método para criar uma sugestão de uma lista de pontos de encontro para a carona 
	 * @param idSessao Id da sessão
	 * @param idCarona Id da carona
	 * @param pontos Conjunto de pontos sugeridos para a carona
	 * @return Id da sugestão
	 * @throws Exception Lança exceção se qualquer parâmetro for null, inválido ou inexistente
	 */
	public String sugerirPontoEncontro(String idSessao, String idCarona, String[] pontos) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		String idSugestao = ""+CaronaDAOImpl.getInstance().idPontoEncontro;
		CaronaDAOImpl.getInstance().idPontoEncontro++;
		
		for (int i = 0; i < pontos.length; i++) {
			PontoDeEncontroDomain ponto = new PontoDeEncontroDomain(idSugestao, pontos[i]);
			CaronaDAOImpl.getInstance().getCarona(idCarona).addPontoDeEncontro(ponto);
		}
		return idSugestao;
	}
	
	/**
	 * Método para aceitar a sugestão de ponto de encontro para a carona
	 * @param idSessao Id da sessão
	 * @param idCarona Id da carona
	 * @param idSugestao Id da sugestão
	 * @param ponto Ponto de encontro que será aceito para a carona
	 * @throws Exception Lança exceção se qualquer parâmetro for null, inválido ou inexistente ou se a sugestão não pertencer a carona 
	 */
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
	
	/**
	 * Método para aceitar uma lista de pontos de encontro sugeridos para a carona.
	 * É possível aceitar apenas alguns pontos da mesma sugestão e sugerir novos 
	 * @param idSessao Id da sessão
	 * @param idCarona Id da carona
	 * @param idSugestao Id da sugestão
	 * @param pontos Ponto de encontro que será aceito para a carona
	 * @throws Exception Lança exceção se qualquer parâmetro for null, inválido ou inexistente ou se a sugestão não pertencer a carona 
	 */
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
	
	/**
	 * Método para retornar todos os pontos de encontro sugeridos para a carona 
	 * @param idSessao Id da sessão
	 * @param idCarona Id da carona
	 * @return Lista de todos os pontos de encontro sugeridos para a carona informada
	 * @throws Exception Lança exceção se qualquer parâmetro for null, inválido ou inexistente ou se a carona não pertencer ao usuario informado
	 */
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
	
	/**
	 * Método para todos os pontos de encontro aceitos para a carona
	 * @param idSessao Id da sessão
	 * @param idCarona Id da carona
	 * @return Lista de todos os pontos de encontro aceitos para a carona informada
	 * @throws Exception Lança exceção se qualquer parâmetro for null, inválido ou inexistente ou se a carona não pertencer ao usuario da sessao informada
	 */
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
