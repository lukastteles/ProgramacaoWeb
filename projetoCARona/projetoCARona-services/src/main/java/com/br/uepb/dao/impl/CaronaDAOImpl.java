package com.br.uepb.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.br.uepb.dao.CaronaDAO;
import com.br.uepb.domain.CaronaDomain;

public class CaronaDAOImpl implements CaronaDAO{

	/**
	 * Classe DAO para o objeto CaronaDomain
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 1ª Iteração
	 */

	final static Logger logger = Logger.getLogger(CaronaDAOImpl.class);
	//Lista de caronas
	ArrayList<CaronaDomain> listaCaronas = new ArrayList<CaronaDomain>();

	@Override
	public void addCarona(CaronaDomain carona) {
		listaCaronas.add(carona);
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
	public CaronaDomain getCarona(String idCarona) throws Exception  {				
		for (CaronaDomain carona : listaCaronas) {
			if (carona.getID().equals(idCarona)) {
				return carona;
			}
		}
		
		throw new Exception("Carona não Localizada");
	}

}
