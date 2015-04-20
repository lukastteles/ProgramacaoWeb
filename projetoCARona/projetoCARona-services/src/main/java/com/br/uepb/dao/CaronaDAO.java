package com.br.uepb.dao;

import java.util.ArrayList;

import com.br.uepb.domain.CaronaDomain;


/**
 * Interface DAO para o objeto CaronaDomain
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
public interface CaronaDAO {
	
	/**
	 * Adciona uma carona na lista
	 * @param carona Carona a ser adicionada
	 */
	public void addCarona(CaronaDomain carona);
	
	
	/**
	 * Pega a Lista de caronas
	 * @return Lista de caronas
	 */
	public ArrayList<CaronaDomain> listCaronas();
	
	
	/**
	 * Pega as caronas da lista pela origem e destino 
	 * @param origem Local de origem da carona
	 * @param destino Local de destino da carona
	 * @return Lista de caronas com a origem e o destino especificados
	 */
	public ArrayList<CaronaDomain> listCaronas(String origem, String destino);
	
	
	/**
	 * Pega as caronas da lista pela origem
	 * @param origem Local de origem da carona
	 * @return Lista de caronas com a origem especificada
	 */
	public ArrayList<CaronaDomain> listCaronasByOrigem(String origem);
	
	/**
	 * Pega as caronas da lista pelo destino
	 * @param destino Çocal de destino da carona
	 * @return Lista de caronas com o destino especificado
	 */
	public ArrayList<CaronaDomain> listCaronasByDestino(String destino);
	
	/**
	 * Pega uma carona pelo id
	 * @param idCarona Id da carona
	 * @return carona com o id especificado
	 * @throws Exception Lança exceção se o parâmetro estiver vazio, nulo ou não existir carona na lista com aquele id 
	 */
	public CaronaDomain getCarona(String idCarona) throws Exception;
	
	/**
	 * Apaga todas as caronas da lista
	 */
	public void apagaCaronas();
	
}
