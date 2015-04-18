package com.br.uepb.dao;

import java.util.ArrayList;

import com.br.uepb.domain.CaronaDomain;


public interface CaronaDAO {

	public void addCarona(CaronaDomain carona);
	public ArrayList<CaronaDomain> listCaronas();
	public ArrayList<CaronaDomain> listCaronas(String origem, String destino);
	public ArrayList<CaronaDomain> listCaronasByOrigem(String origem);
	public ArrayList<CaronaDomain> listCaronasByDestino(String destino);
	public CaronaDomain getCarona(String idCarona) throws Exception;
	public void apagaCaronas();
	
}
