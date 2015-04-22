package com.br.uepb.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.CaronaDAO;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class CaronaDAOImpl implements CaronaDAO{

	//Variáveis temporárias para controlar o Id, que serão retiradas quando houver BD
	public int idCarona = 1;
	public int idPontoEncontro = 1;
	
	final static Logger logger = Logger.getLogger(CaronaDAOImpl.class);

	ArrayList<CaronaDomain> listaCaronas = new ArrayList<CaronaDomain>();
	
	//instancia Singleton de CaronaDAOImpl
	private static CaronaDAOImpl caronaDAOImpl;
	
	//método para pegar instancia de CaronaDAOImpl
	public static CaronaDAOImpl getInstance(){
		if(caronaDAOImpl == null){
			caronaDAOImpl = new CaronaDAOImpl();
			return caronaDAOImpl;
		}else{
			return caronaDAOImpl;
		}
	}
	
	//Construtor de CaronaDAOImpl
	private CaronaDAOImpl(){ }

	
	@Override
	public void addCarona(CaronaDomain carona) {
		listaCaronas.add(carona);
	}
	
	@Override
	public ArrayList<CaronaDomain> listCaronas() {
		ArrayList<CaronaDomain> caronas = new ArrayList<CaronaDomain>();
		for (CaronaDomain carona : listaCaronas) {			
				caronas.add(carona);
		}
				
		return caronas;					
	}

	@Override
	public ArrayList<CaronaDomain> listCaronas(String origem, String destino) {
		ArrayList<CaronaDomain> caronas = new ArrayList<CaronaDomain>();
		for (CaronaDomain carona : listaCaronas) {
			if ( (carona.getOrigem().equals(origem)) &&
				 (carona.getDestino().equals(destino)) ) {
				caronas.add(carona);
			}
		}
				
		return caronas;					
	}
	
	@Override
	public ArrayList<CaronaDomain> listCaronasByOrigem(String origem) {
		ArrayList<CaronaDomain> caronas = new ArrayList<CaronaDomain>();
		for (CaronaDomain carona : listaCaronas) {
			if (carona.getOrigem().equals(origem)) {
				caronas.add(carona);
			}
		}
				
		return caronas;	
	}

	@Override
	public ArrayList<CaronaDomain> listCaronasByDestino(String destino) {
		ArrayList<CaronaDomain> caronas = new ArrayList<CaronaDomain>();
		for (CaronaDomain carona : listaCaronas) {
			if (carona.getDestino().equals(destino)) {
				caronas.add(carona);
			}
		}
				
		return caronas;	
	}

	@Override
	public CaronaDomain getCarona(String idCarona) throws Exception  {		
		//tratamento para verificar de o IdCarona está null ou vazio
		if (idCarona == null) {
			logger.debug("getCarona() Exceção: "+MensagensErro.CARONA_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.CARONA_INVALIDA);
		}
		if (idCarona.trim().equals("")) {
			logger.debug("getCarona() Exceção: "+MensagensErro.CARONA_INEXISTENTE);
			throw new ProjetoCaronaException(MensagensErro.CARONA_INEXISTENTE);
		}
		
		for (CaronaDomain carona : listaCaronas) {
			if (carona.getID().equals(idCarona)) {
				return carona;
			}
		}
		
		//se não entrou no for é sinal que não existe nenhuma carona com esse parametro cadastrada
		logger.debug("getCarona() Exceção: "+MensagensErro.CARONA_INEXISTENTE);
		throw new ProjetoCaronaException(MensagensErro.CARONA_INEXISTENTE);
	}

	
	public void apagaCaronas(){
		logger.debug("apagando lista de caronas");
		if (listaCaronas.size() > 0) {
			listaCaronas.removeAll(listaCaronas);
		}		
	}
}
