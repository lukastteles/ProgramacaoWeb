package com.br.uepb.business;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.domain.CaronaDomain;

@Component
public class CaronaBusiness {
	
	/** Objeto DAO para manipular os objetos da classe UsuarioDomain*/
	private CaronaDAOImpl caronaDAOImpl =  new CaronaDAOImpl();
	private int idCarona = 1;
	
	
	public CaronaDomain localizarCarona(int idSessao, String origem, String destino) throws Exception {		
		ArrayList<CaronaDomain> caronas = caronaDAOImpl.getCarona(origem, destino);
		
		//TODO: verificar os outros tratamentos
		
		return null;
	}
	
	
	public String cadastrarCarona(String idSessao, String origem, String destino, String data, String hora, int vagas){
		String carona = "car"+idCarona;
		CaronaDomain caronaDomain = new CaronaDomain(idSessao, carona, origem, destino, data, hora, vagas);		
		
		caronaDAOImpl.addCarona(caronaDomain);
		idCarona++; //TODO: retirar este contador depois que inserir a persistencia com o BD
		return "Luana";
	}
	
}
