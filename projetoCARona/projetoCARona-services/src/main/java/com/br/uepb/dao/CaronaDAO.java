package com.br.uepb.dao;

import java.util.ArrayList;

import com.br.uepb.domain.CaronaDomain;


public interface CaronaDAO {

//public UsuarioDomain getUsuario(String login) throws Exception;
	public void addCarona(CaronaDomain carona);
	public ArrayList<CaronaDomain> listCaronas(String origem, String destino) throws Exception;
	public CaronaDomain getCarona(String idCarona) throws Exception;
	
}
