package com.br.uepb.business;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.PontoDeEncontroDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.domain.PontoDeEncontroDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

/**
 * Classe as regras de negócio referentes ao ponto de encontro 
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
@Component
public class PontoDeEncontroBusiness {

	final static Logger logger = Logger.getLogger(PontoDeEncontroBusiness.class);
	
	/**
	 * Método para criar uma sugestão de um ponto de encontro para uma carona
	 * @param idSessao Id da sessão
	 * @param idCarona Id da carona
	 * @param pontoDeEncontro Local do ponto de encontro
	 * @return Id da sugestão criada
	 * @throws Exception Lança exceção se qualquer parâmetro for null, inválido ou inexistente
	 */
	public String sugerirPontoEncontro(String idSessao, String idCarona, String pontoDeEncontro) throws Exception{
		logger.debug("sugerindo "+pontoDeEncontro+" como ponto de encontro");		
		//Metodos apenas para ver se a sessao e carona entao invalidas
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		String idSugestao = ""+ CaronaDAOImpl.getInstance().getIdPontoEncontro();
		logger.debug("criando ponto de encontro "+pontoDeEncontro);
		PontoDeEncontroDomain ponto = new PontoDeEncontroDomain(idSessao, idCarona, idSugestao, pontoDeEncontro);
		logger.debug("ponto "+pontoDeEncontro+" criado");
		PontoDeEncontroDAOImpl.getInstance().addPontoDeEncontro(ponto);
		logger.debug("ponto "+pontoDeEncontro+" sugerido");
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
		logger.debug("sugerindo varios ponto de encontro");
		//Metodos apenas para ver se a sessao e carona entao invalidas
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		String idSugestao = ""+CaronaDAOImpl.getInstance().getIdPontoEncontro();
		
		logger.debug("criando e adicionando pontos de encontro");
		for (int i = 0; i < pontos.length; i++) {
			PontoDeEncontroDomain ponto = new PontoDeEncontroDomain(idSessao, idCarona, idSugestao, pontos[i]);
			PontoDeEncontroDAOImpl.getInstance().addPontoDeEncontro(ponto);
		}
		logger.debug("pontos de encontro adicionados");
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
		logger.debug("respondendo sugestao de ponto de encontro");
		//Metodos apenas para ver se a sessao e carona entao invalidas
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		List<PontoDeEncontroDomain> pontosSugestao = PontoDeEncontroDAOImpl.getInstance().getPontosSugestao(idCarona, idSugestao);
		logger.debug("buscando ponto");
		for (PontoDeEncontroDomain pontoSugestao : pontosSugestao) {
			if(pontoSugestao.getPontoDeEncontro().equals(ponto)){
				pontoSugestao.setFoiAceita(true);
				PontoDeEncontroDAOImpl.getInstance().atualizaPonto(pontoSugestao);
				ponto = "aceito";
			}
		}
		if(!ponto.equals("aceito")){
			logger.debug("responderSugestaoPontoEncontro() Exceção: "+MensagensErro.PONTO_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.PONTO_INVALIDO);
		}
		logger.debug("ponto aceito");
	}
	
	/**
	 * Faz que que um ponto aceito passe a ser não acito
	 * @param idSessao Id da sessão
	 * @param idCarona Id da carona
	 * @param idSugestao Id da sugestão
	 * @param ponto Ponto de encontro que será aceito para a carona
	 * @throws Exception Lança exceção se qualquer parâmetro for null, inválido ou inexistente ou se a sugestão não pertencer a carona
	 */
	public void desitirPontoEncontro(String idSessao, String idCarona, String idSugestao, String ponto) throws Exception{
		logger.debug("deixando de aceitar um ponto de encontro");
		//Metodos apenas para ver se a sessao e carona entao invalidas
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		List<PontoDeEncontroDomain> pontosSugestao = PontoDeEncontroDAOImpl.getInstance().getPontosSugestao(idCarona, idSugestao);
		logger.debug("buscando ponto");
		for (PontoDeEncontroDomain pontoSugestao : pontosSugestao) {
			if(pontoSugestao.getPontoDeEncontro().equals(ponto)){
				pontoSugestao.setFoiAceita(false);
				PontoDeEncontroDAOImpl.getInstance().atualizaPonto(pontoSugestao);
				ponto = "naoAceito";
			}
		}
		if(!ponto.equals("naoAceito")){
			logger.debug("responderSugestaoPontoEncontro() Exceção: "+MensagensErro.PONTO_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.PONTO_INVALIDO);
		}
		logger.debug("ponto não aceito");
	}
	
	/**
	 * Recusa o ponto sugegido e apaga-o
	 * @param idSessao Id da sessão
	 * @param idCarona Id da carona
	 * @param idSugestao Id da sugestão
	 * @param ponto Ponto de encontro que será aceito para a carona
	 * @throws Exception Lança exceção se qualquer parâmetro for null, inválido ou inexistente ou se a sugestão não pertencer a carona
	 */
	public void recusarPontoEncontro(String idSessao, String idCarona,String idSugestao, String ponto) throws Exception {
		logger.debug("recusando um ponto de encontro");
		//Metodos apenas para ver se a sessao e carona entao invalidas
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDAOImpl.getInstance().getCarona(idCarona);
		PontoDeEncontroDomain pontosSugestao = PontoDeEncontroDAOImpl.getInstance().getPontoEncontroByNome(idCarona, ponto);
		PontoDeEncontroDAOImpl.getInstance().apagaPonto(pontosSugestao);
		logger.debug("ponto recusado");
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
		logger.debug("respondendo sugestao de vários pontos de encontro");		
		//Metodos apenas para ver se a sessao e carona entao invalidas		
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		List<PontoDeEncontroDomain> pontosSugestao = PontoDeEncontroDAOImpl.getInstance().getPontosSugestao(idCarona, idSugestao);
		logger.debug("buscando pontos");
		for (int i = 0; i < pontos.length; i++) {
			for (PontoDeEncontroDomain pontoSugestao : pontosSugestao) {
				if(pontoSugestao.getPontoDeEncontro().equals(pontos[i])){
					pontoSugestao.setFoiAceita(true);
					PontoDeEncontroDAOImpl.getInstance().atualizaPonto(pontoSugestao);
					pontos[i] = "aceito";
				}
			}
		}
		logger.debug("pontos aceitos");
		for (int i = 0; i < pontos.length; i++) {
			//se nao pertence a sugestao, cria um novo ponto
			if(!pontos[i].equals("aceito")){
				logger.debug("adicionando novos pontos");
				String sugestao = sugerirPontoEncontro(idSessao, idCarona, pontos[i]);
				responderSugestaoPontoEncontro(idSessao, idCarona, sugestao, pontos[i]);
				
			}
		}
	}
	
	/**
	 * Metodo para retornar todos os pontos de encontro sugeridos para a carona 
	 * @param idSessao Id da sessso
	 * @param idCarona Id da carona
	 * @return Lista de todos os pontos de encontro sugeridos para a carona informada
	 * @throws Exception Lança exceção se qualquer parâmetro for null, inválido ou inexistente ou se a carona não pertencer ao usuario informado
	 */
	public List<PontoDeEncontroDomain> getPontosSugeridos(String idSessao, String idCarona) throws Exception{
		logger.debug("buscando todos os pontros sugeridos para uma carona");		
		//Metodos apenas para ver se a sessao e carona entao invalidas		
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		List<PontoDeEncontroDomain> todosOsPontos = PontoDeEncontroDAOImpl.getInstance().listPontos(idCarona);
		
		logger.debug("pontos encontrados");
		return todosOsPontos;
	}
	
	/**
	 * Metodo para retornar todos os pontos de encontro sugeridos para as caronas que o usuario cadastrou
	 * @param idSessao Id da sessao
	 * @return Lista de todos os pontos de encontro sugeridos para o usuario que cadastrou a carona
	 * @throws Exception Lança exceção se o login for null, inválido ou inexistente
	 */
	public List<PontoDeEncontroDomain> getTodosPontosSugeridos(String idSessao) throws Exception{
		logger.debug("buscando todos os pontros sugeridos para uma carona");		
		//Metodos apenas para ver se a sessao e carona entao invalidas		
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		List<PontoDeEncontroDomain> todosOsPontos = PontoDeEncontroDAOImpl.getInstance().listPontosSugeridos(idSessao);
		
		logger.debug("pontos encontrados");
		return todosOsPontos;
	}
	
	
	/**
	 * Método para todos os pontos de encontro aceitos para a carona
	 * @param idSessao Id da sessão
	 * @param idCarona Id da carona
	 * @return Lista de todos os pontos de encontro aceitos para a carona informada
	 * @throws Exception Lança exceção se qualquer parâmetro for null, inválido ou inexistente ou se a carona não pertencer ao usuario da sessao informada
	 */
	public String[] getPontosEncontro(String idSessao, String idCarona) throws Exception{
		logger.debug("buscando todos os pontros aceitos para uma carona");		
		//Metodos apenas para ver se a sessao e carona entao invalidas
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDAOImpl.getInstance().getCarona(idCarona);
				
		List<PontoDeEncontroDomain> pontosAceitos = PontoDeEncontroDAOImpl.getInstance().getPontoEncontroAceitos(idCarona);
		
		String[] aceitos = new String[pontosAceitos.size()];
		int count = 0;
		for (PontoDeEncontroDomain ponto : pontosAceitos) {
			aceitos[count] = ponto.getPontoDeEncontro();
			count++;
		}
		logger.debug("pontos encontrados");
		return aceitos;
		
	}
	
}
