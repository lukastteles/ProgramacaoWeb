package com.br.uepb.dao;

import java.util.ArrayList;

import com.br.uepb.domain.CaronaDomain;


public interface CaronaDAO {

//public UsuarioDomain getUsuario(String login) throws Exception;
	public void addCarona(CaronaDomain carona);
	public ArrayList<CaronaDomain> getCarona(String origem, String destino) throws Exception;	
	
}
