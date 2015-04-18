package com.br.uepb.dao.impl;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.br.uepb.dao.CaronaDAO;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;

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
	
	private static CaronaDAOImpl caronaDAOImpl;
	
	public static CaronaDAOImpl getInstance(){
		if(caronaDAOImpl == null){
			caronaDAOImpl = new CaronaDAOImpl();
			return caronaDAOImpl;
		}else{
			return caronaDAOImpl;
		}
	}
	
	private CaronaDAOImpl(){
		
	}

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
			throw new Exception("Carona Inválida");
		}
		if (idCarona.trim().equals("")) {
			throw new Exception("Carona Inexistente");
		}
		
		for (CaronaDomain carona : listaCaronas) {
			if (carona.getID().equals(idCarona)) {
				return carona;
			}
		}
		
		//se não entrou no for é sinal que não existe nenhuma carona com esse parametro cadastrada
		throw new Exception("Carona Inexistente");
	}
}
