package com.br.uepb.business;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

@Component
public class CaronaBusiness {
	
	/**
	 * Classe as regras de negócio referentes à carona
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 20/04/2015
	 */
	
	/**
	 * Método para localizar todas as caronas informadas de uma determinada origem para um destino
	 * Observações:
	 * - Se o parâmetro origem não for informado, irá localizar todas as caronas pertencentes ao destino informado
	 * - Se o parâmetro destino não for informado, irá localizar todas as caronas pertencentes a origem informada
	 * - Se não for informado nem origem nem destino da carona, irá localizar todas as caronas, sem exceção
	 * @param idSessao String - Id da sessão atual
	 * @param origem String - Local de origem da carona
	 * @param destino String - Local de destino da carona
	 * @return ArrayList<CaronaDomain> - Lista das caronas localizadas
	 * @throws Exception - Lança exceção se qualquer parâmetro informado for null ou vazio ou se a sessao for inválida
	 */
	public ArrayList<CaronaDomain> localizarCarona(String idSessao, String origem, String destino) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		ArrayList<CaronaDomain> caronas;

		if (verificaCaracteres(origem) == false){
			throw new ProjetoCaronaException(MensagensErro.ORIGEM_INVALIDA);
		}

		if (verificaCaracteres(destino) == false){
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
		
		return caronas;
	}
	
	/**
	 * Método para cadatrar a carona que um usuário está oferecendo
	 * @param idSessao String - Id da sessão atual
	 * @param origem String - Local de origem da carona
	 * @param destino String - Local de destino da carona
	 * @param data String - Data de saída da carona
	 * @param hora String - Hora de partida da carona
	 * @param vagas int - Quantidade de vagas disponíveis na carona
	 * @return String - Id da carona dacastrada
	 * @throws Exception
	 */
	public String cadastrarCarona(String idSessao, String origem, String destino, String data, String hora, int vagas) throws Exception{		
		//funcao para verificar se a sessao existe
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		//adiciona a caronas na lista de caronas
		String carona = ""+ CaronaDAOImpl.getInstance().idCarona;
		CaronaDomain caronaDomain = new CaronaDomain(idSessao, carona, origem, destino, data, hora, vagas);				
		CaronaDAOImpl.getInstance().addCarona(caronaDomain);
		
		//Adiciona a carona ao usuario correspondente
		UsuarioDomain usuario = UsuarioDAOImpl.getInstance().getUsuario(idSessao);	
		usuario.addCarona(caronaDomain.getID());
		usuario.getPerfil().addHistoricoDeCaronas(caronaDomain.getID());
		
		CaronaDAOImpl.getInstance().idCarona++;
		return caronaDomain.getID();
	}
	
	/**
	 * Método para retornar os dados da carona
	 * @param idCarona String - Id da carona
	 * @param atributo String - Tipo de dado da carona a ser retornado 
	 * @return String - Dado da carona
	 * @throws Exception - Lança exceção se qualquer parâmetro informado for null, vazio ou se a carona e/ou atributo for inexistente
	 */
	public String getAtributoCarona(String idCarona, String atributo) throws Exception{

		if( (atributo == null) || (atributo.trim().equals(""))){
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INVALIDO);
		}
		
		if ((idCarona == null) || (idCarona.trim().equals(""))) {
			throw new ProjetoCaronaException(MensagensErro.IDENTIFICADOR_INVALIDO);
		}
		
		CaronaDomain carona = null; 
		
		try {
			carona = CaronaDAOImpl.getInstance().getCarona(idCarona);			
		} catch (Exception e) {
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
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INEXISTENTE);
		}
				
	}
	
	/**
	 * Método para retornar o trajeto(origem - destino) que a carona irá fazer
	 * @param idCarona String - Id da carona
	 * @return String - Trajeto da carona
	 * @throws Exception - Lança exceção se o id da carona for null, vazio ou inexistente
	 */
	public String getTrajeto(String idCarona) throws Exception{
		
		CaronaDomain carona = null;
		
		try {
			carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		} catch (Exception e) {
			if (e.getMessage().equals(MensagensErro.CARONA_INVALIDA)) {				
					throw new ProjetoCaronaException(MensagensErro.TRAJETO_INVALIDO);
			}
			else {
				throw new ProjetoCaronaException(MensagensErro.TRAJETO_INEXISTENTE);
			}			
		}
		
		
		String trajeto = carona.getOrigem()+" - "+carona.getDestino();
		
		return trajeto;	
	}
	
	/**
	 * Método para retornar uma carona
	 * @param idCarona - Id da carona
	 * @return CaronaDomain - Retorna a carona cadastrada
	 * @throws Exception - Lança exceção se o id da carona for null, vazio ou inexistente 
	 */
	public CaronaDomain getCarona(String idCarona) throws Exception{		
		return  CaronaDAOImpl.getInstance().getCarona(idCarona);
	}
	
	/**
	 * Método para retornar a carona de um determinado usuário com base no índice da lista de caronas cadastradas 
	 * @param idSessao String - Id da sessão atual
	 * @param indexCarona - Índice da lista de caronas
	 * @return CaronaDomain - Retorna a carona cadastrada
	 * @throws Exception - Lança exceção se o id da carona for null, vazio ou inexistente ou se o indice informado for inválido
	 */
	public CaronaDomain getCaronaUsuario(String idSessao, int indexCarona) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		String idCarona = UsuarioDAOImpl.getInstance().getUsuario(idSessao).getIdCaronaByIndex(indexCarona);		
		return CaronaDAOImpl.getInstance().getCarona(idCarona);		
	}
	
	/**
	 * Método para retornar umaa lista de todas as caronas de um usuario cadastradas  
	 * @param idSessao String - Id da sessão atual
	 * @return ArrayList<CaronaDomain> - Lista das caronas cadastradas
	 * @throws Exception - Lança exceção se o id da sessão for null, vazio ou inexistente
	 */
	public ArrayList<CaronaDomain> getTodasCaronasUsuario(String idSessao) throws Exception{
		UsuarioDomain usuario =  UsuarioDAOImpl.getInstance().getUsuario(idSessao);
		ArrayList<CaronaDomain> caronasUsuario = new ArrayList<CaronaDomain>();
		for (String idCarona : usuario.getCaronas()) {
			CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
			caronasUsuario.add(carona);
		}
				
		return caronasUsuario;
	}

	//TODO: verificar este metodo
	/**
	 * Método para verificar se os campos de origem e destino estão dentro do padrão:
	 * Para facilitar a padronização dos campos de origem e destino não será permitido informar estes campos utilizando caracteres especiais
	 * @param valor
	 * @return
	 */ 
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
