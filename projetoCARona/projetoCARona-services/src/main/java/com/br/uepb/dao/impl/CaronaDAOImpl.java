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
	public ArrayList<CaronaDomain> getCarona(String origem, String destino) throws Exception {
		ArrayList<CaronaDomain> caronas = new ArrayList<CaronaDomain>();
		for (CaronaDomain carona : listaCaronas) {
			if ( (carona.getOrigem().equals(origem)) &&
				 (carona.getDestino().equals(destino)) ) {
				caronas.add(carona);
			}
		}
		
		if (caronas.size() > 0) {
			return caronas;			
		}
		else {
			//TODO: Verificar o tratamento
			throw new Exception("Carona não Localizada");
		}
	}

}
