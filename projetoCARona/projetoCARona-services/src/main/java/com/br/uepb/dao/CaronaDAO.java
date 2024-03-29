package com.br.uepb.dao;

import java.util.List;

import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.InteresseEmCaronaDomain;


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
	 * @throws Exception 
	 */
	public void addCarona(CaronaDomain carona) throws Exception;
	
	
	/**
	 * Pega a Lista de caronas
	 * @param login Login do usuario
	 * @return Lista de caronas
	 * @throws Exception 
	 */	
	public List<CaronaDomain> listCaronas(String login) throws Exception;
	
	
	/**
	 * Pega as caronas da lista pela origem e destino 
	 * @param login Login do usuario
	 * @param origem Local de origem da carona
	 * @param destino Local de destino da carona
	 * @return Lista de caronas com a origem e o destino especificados
	 * @throws Exception 
	 */
	public List<CaronaDomain> listCaronas(String login, String origem, String destino) throws Exception;
	
	
	/**
	 * Pega as caronas da lista pela origem
	 * @param login Login do usuario
	 * @param origem Local de origem da carona
	 * @return Lista de caronas com a origem especificada
	 * @throws Exception 
	 */
	public List<CaronaDomain> listCaronasByOrigem(String login, String origem) throws Exception;
	
	/**
	 * Pega as caronas da lista pelo destino
	 * @param login Login do usuario
	 * @param destino Çocal de destino da carona
	 * @return Lista de caronas com o destino especificado
	 * @throws Exception 
	 */
	public List<CaronaDomain> listCaronasByDestino(String login, String destino) throws Exception;
	
	/**
 	 * Pega as caronas municipais da lista pela origem e destino
 	 * @param cidade Cidade onde acontecera a carona  
	 * @param origem Local de origem da carona
	 * @param destino Local de destino da carona
	 * @return Lista de caronas com a origem e o destino especificados
	 * @throws Exception 
	 */
	public List<CaronaDomain> listCaronasMunicipais(String cidade, String origem, String destino) throws Exception;
	
	/**
	 * Pega uma carona pelo id
	 * @param idCarona Id da carona
	 * @return carona com o id especificado
	 * @throws Exception Lança exceção se o parâmetro estiver vazio, nulo ou não existir carona na lista com aquele id 
	 */
	public CaronaDomain getCarona(String idCarona) throws Exception;
	
	/**
	 *  Atualiza algum atributo que tenha sido modificado para uma Carona desde que o Id exista
	 * @param carona Carona a ser atualizada
	 */
	public void atualizaCarona(CaronaDomain carona);

	/**
	 * Pega as caronas municipais de uma cidade da lista
	 * @param cidade Cidade onde acontecera a carona
	 * @return  Lista de caronas municipais da cidade
	 * @throws Exception
	 */
	public List<CaronaDomain> listCaronasMunicipais(String cidade) throws Exception;

	/**
	 * Retorna todas as caronas cadastradas de acordo com o requisito informado como parametro 
	 * @param interesseEmCaronas Informacao referente aos tipos de caronas desejadas
	 * @return carona desejada
	 * @throws Exception
	 */
	public CaronaDomain getCaronaByInteresse(InteresseEmCaronaDomain interesseEmCaronas) throws Exception;
	
	/**
	 * Retorna a lista de todas as caronas encontradas com base na lista de interesses do usuario cadastrada
	 * @param idSessao Id do usuario
	 * @return Lista de Caronas
	 * @throws Exception
	 */
	public List<CaronaDomain> getCaronasByInteresses(String idSessao) throws Exception;
	
	/**
	 * Apaga todas as caronas da lista
	 */
	public void apagaCaronas();

	/**
	 * Retorna todas a caronas de um usuario
	 * @param login Login do usuario
	 * @return Lista de caronas do usuario
	 * @throws Exception Lanca excecao se o login for invalido ou se houver problema de conexao com o banco
	 */
	List<CaronaDomain> getHistoricoDeCaronas(String login) throws Exception;



}
