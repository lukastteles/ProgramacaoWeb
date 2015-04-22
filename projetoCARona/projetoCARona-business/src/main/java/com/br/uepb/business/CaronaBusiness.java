package com.br.uepb.business;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

/**
 * Classe para as regras de negócio referentes à carona
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
@Component
public class CaronaBusiness {
	
	final static Logger logger = Logger.getLogger(CaronaBusiness.class);
	
	/**
	 * Método para localizar todas as caronas informadas de uma determinada origem para um destino
	 * Observações:
	 * - Se o parâmetro origem não for informado, irá localizar todas as caronas pertencentes ao destino informado
	 * - Se o parâmetro destino não for informado, irá localizar todas as caronas pertencentes a origem informada
	 * - Se não for informado nem origem nem destino da carona, irá localizar todas as caronas, sem exceção
	 * @param idSessao Id da sessão atual
	 * @param origem Local de origem da carona
	 * @param destino Local de destino da carona
	 * @return Lista das caronas localizadas
	 * @throws Exception Lança exceção se qualquer parâmetro informado for null ou vazio ou se a sessao for inválida
	 */
	public ArrayList<CaronaDomain> localizarCarona(String idSessao, String origem, String destino) throws Exception{
		logger.debug("localizando carona para origem: "+origem+" e destino: "+destino);
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		ArrayList<CaronaDomain> caronas;

		if (verificaCaracteres(origem) == false){
			logger.debug("localizarCarona() Exceção: "+MensagensErro.ORIGEM_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.ORIGEM_INVALIDA);
		}

		if (verificaCaracteres(destino) == false){
			logger.debug("localizarCarona() Exceção: "+MensagensErro.DESTINO_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.DESTINO_INVALIDO);
		}
				
		if ( (origem.trim().equals("")) && (destino.trim().equals("")) ) {
			caronas = CaronaDAOImpl.getInstance().listCaronas();
		}
		else if (origem.trim().equals(""))  {
			caronas = CaronaDAOImpl.getInstance().listCaronasByDestino(destino);
			
		}
		else if (destino.trim().equals("")){
			caronas = CaronaDAOImpl.getInstance().listCaronasByOrigem(origem);
			
		}
		else {
			caronas = CaronaDAOImpl.getInstance().listCaronas(origem, destino);
		}
		logger.debug("retornando caronas encontradas");
		return caronas;
	}
	
	/**
	 * Método para cadatrar a carona que um usuário está oferecendo
	 * @param idSessao Id da sessão atual
	 * @param origem Local de origem da carona
	 * @param destino Local de destino da carona
	 * @param data Data de saída da carona
	 * @param hora Hora de partida da carona
	 * @param vagas Quantidade de vagas disponíveis na carona
	 * @return Id da carona dacastrada
	 * @throws Exception Lança exceção se qualquer parâmetro informado for null ou vazio ou se a sessao for inválida
	 */
	public String cadastrarCarona(String idSessao, String origem, String destino, String data, String hora, int vagas) throws Exception{		
		logger.debug("cadastrando carona");
		//funcao para verificar se a sessao existe
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		//adiciona a caronas na lista de caronas
		String carona = ""+ CaronaDAOImpl.getInstance().idCarona;
		logger.debug("criando carona");
		CaronaDomain caronaDomain = new CaronaDomain(idSessao, carona, origem, destino, data, hora, vagas);
		logger.debug("carona criada");
		logger.debug("adicionando carona na lista");
		CaronaDAOImpl.getInstance().addCarona(caronaDomain);
		logger.debug("carona adicionada na lista");
		
		//Adiciona a carona ao usuario correspondente
		UsuarioDomain usuario = UsuarioDAOImpl.getInstance().getUsuario(idSessao);	
		usuario.addCarona(caronaDomain.getID());
		usuario.getPerfil().addHistoricoDeCaronas(caronaDomain.getID());
		logger.debug("carona adicionada no historico do usuario");
		
		CaronaDAOImpl.getInstance().idCarona++;
		return caronaDomain.getID();
	}
	
	/**
	 * Método para retornar os dados da carona
	 * @param idCarona Id da carona
	 * @param atributo Tipo de dado da carona a ser retornado 
	 * @return Dado da carona
	 * @throws Exception Lança exceção se qualquer parâmetro informado for null, vazio ou se a carona e/ou atributo for inexistente
	 */
	public String getAtributoCarona(String idCarona, String atributo) throws Exception{
		logger.debug("buscando atributo da carona");
		
		if( (atributo == null) || (atributo.trim().equals(""))){
			logger.debug("getAtributoCarona() Exceção: "+MensagensErro.ATRIBUTO_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INVALIDO);
		}
		
		if ((idCarona == null) || (idCarona.trim().equals(""))) {
			logger.debug("getAtributoCarona() Exceção: "+MensagensErro.IDENTIFICADOR_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.IDENTIFICADOR_INVALIDO);
		}
		
		CaronaDomain carona = null; 
		
		try {
			carona = CaronaDAOImpl.getInstance().getCarona(idCarona);			
		} catch (Exception e) {
			logger.debug("getAtributoCarona() Exceção: "+MensagensErro.ITEM_INEXISTENTE);
			//if (e.getMessage().equals(MensagensErro.CARONA_INEXISTENTE)) {
				throw new ProjetoCaronaException(MensagensErro.ITEM_INEXISTENTE);
			//}		
		}
		
		if(atributo.equals("origem")){
			return carona.getOrigem();
		}else if(atributo.equals("destino")){
			return carona.getDestino();
		}else if(atributo.equals("data")){
			return carona.getData();
		}else if(atributo.equals("vagas")){
			return ""+carona.getVagas();
		}else {
			logger.debug("getAtributoCarona() Exceção: "+MensagensErro.ATRIBUTO_INEXISTENTE);
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INEXISTENTE);
		}
				
	}
	
	/**
	 * Método para retornar o trajeto(origem - destino) que a carona irá fazer
	 * @param idCarona Id da carona
	 * @return Trajeto da carona
	 * @throws Exception Lança exceção se o id da carona for null, vazio ou inexistente
	 */
	public String getTrajeto(String idCarona) throws Exception{
		logger.debug("buscando trajeto de uma carona");
		CaronaDomain carona = null;
		
		try {
			carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		} catch (Exception e) {
			if (e.getMessage().equals(MensagensErro.CARONA_INVALIDA)) {
				logger.debug("getTrajeto() Exceção: "+MensagensErro.TRAJETO_INVALIDO);
					throw new ProjetoCaronaException(MensagensErro.TRAJETO_INVALIDO);
			}
			else {
				logger.debug("getTrajeto() Exceção: "+MensagensErro.TRAJETO_INEXISTENTE);
				throw new ProjetoCaronaException(MensagensErro.TRAJETO_INEXISTENTE);
			}			
		}
		
		String trajeto = carona.getOrigem()+" - "+carona.getDestino();
		
		logger.debug("trajeto encontrado ("+trajeto+")");
		
		return trajeto;	
	}
	
	/**
	 * Método para retornar uma carona
	 * @param idCarona Id da carona
	 * @return Retorna a carona cadastrada
	 * @throws Exception Lança exceção se o id da carona for null, vazio ou inexistente 
	 */
	public CaronaDomain getCarona(String idCarona) throws Exception{		
		logger.debug("buscando carona");
		return  CaronaDAOImpl.getInstance().getCarona(idCarona);
	}
	
	/**
	 * Método para retornar a carona de um determinado usuário com base no índice da lista de caronas cadastradas 
	 * @param idSessao Id da sessão atual
	 * @param indexCarona Indice da lista de caronas
	 * @return Retorna a carona cadastrada
	 * @throws Exception Lança exceção se o id da carona for null, vazio ou inexistente ou se o indice informado for inválido
	 */
	public CaronaDomain getCaronaUsuario(String idSessao, int indexCarona) throws Exception{
		logger.debug("buscando carona de um usuário");
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		String idCarona = UsuarioDAOImpl.getInstance().getUsuario(idSessao).getIdCaronaByIndex(indexCarona);		
		return CaronaDAOImpl.getInstance().getCarona(idCarona);		
	}
	
	/**
	 * Método para retornar umaa lista de todas as caronas de um usuario cadastradas  
	 * @param idSessao Id da sessão atual
	 * @return Lista das caronas cadastradas
	 * @throws Exception Lança exceção se o id da sessão for null, vazio ou inexistente
	 */
	public ArrayList<CaronaDomain> getTodasCaronasUsuario(String idSessao) throws Exception{
		logger.debug("buscando todas as carona de um usuário");
		UsuarioDomain usuario =  UsuarioDAOImpl.getInstance().getUsuario(idSessao);
		ArrayList<CaronaDomain> caronasUsuario = new ArrayList<CaronaDomain>();
		for (String idCarona : usuario.getCaronas()) {
			CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
			caronasUsuario.add(carona);
		}
		logger.debug("caronas encontradas");
		return caronasUsuario;
	}

	//TODO: verificar este metodo
	private boolean verificaCaracteres(String valor){

		String patternTexto = valor;
		patternTexto = patternTexto.replaceAll("[-?!().]", "");

		if (patternTexto.equals(valor)) {
			return true; 
		}
		else {
			return false;
		}		
	}
}
