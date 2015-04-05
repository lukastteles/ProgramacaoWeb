package com.br.uepb.business;

import java.text.Normalizer;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.domain.CaronaDomain;

@Component
public class CaronaBusiness {
	
	/** Objeto DAO para manipular os objetos da classe UsuarioDomain*/
	private CaronaDAOImpl caronaDAOImpl =  new CaronaDAOImpl();
	private int idCarona = 1;
	
	
	public ArrayList<CaronaDomain> localizarCarona(String idSessao, String origem, String destino) throws Exception{
		ArrayList<CaronaDomain> caronas;

		if (verificaCaracteres(origem) == false){
			System.out.println("aqui");
			throw new Exception("Origem inválida");
		}

		if (verificaCaracteres(destino) == false){
			throw new Exception("Destino inválido");
		}
				
		if ( (origem.equals("")) && (destino.equals("")) ) {
			caronas = caronaDAOImpl.listCaronas();
		}
		else if (origem.equals(""))  {
			caronas = caronaDAOImpl.listCaronasByDestino(destino);
			
		}
		else if (destino.equals("")){
			caronas = caronaDAOImpl.listCaronasByOrigem(origem);
			
		}
		else {
			caronas = caronaDAOImpl.listCaronas(origem, destino);
		}
		
		return caronas;
	}
	
	public String cadastrarCarona(String idSessao, String origem, String destino, String data, String hora, int vagas){
		String carona = ""+idCarona;
		CaronaDomain caronaDomain = new CaronaDomain(idSessao, carona, origem, destino, data, hora, vagas);		
		
		caronaDAOImpl.addCarona(caronaDomain);
		idCarona++; //TODO: retirar este contador depois que inserir a persistencia com o BD
		return caronaDomain.getID();
	}
	
	public String getAtributoCarona(String idCarona, String atributo) throws Exception{
		CaronaDomain carona = caronaDAOImpl.getCarona(idCarona);
		
		if(atributo.equals("origem")){
			return carona.getOrigem();
		}else if(atributo.equals("destino")){
			return carona.getDestino();
		}else if(atributo.equals("data")){
			return carona.getData();
		}else if(atributo.equals("vagas")){
			return ""+carona.getVagas();
		}else if(atributo.equals("") || atributo == null){
			throw new Exception("Atributo Inválido"); 
		}else{
			throw new Exception("Atributo inexistente");
		}
				
	}
	
	public CaronaDomain getCarona(String idCarona) throws Exception{
		return  caronaDAOImpl.getCarona(idCarona);
	}
	
	//TODO: colocar este metodo em outra classe
	private boolean verificaCaracteres(String valor){

		String patternTexto = valor;
		patternTexto = patternTexto.replaceAll("[-?!().]", "");

		if (patternTexto.equals(valor)) {
			return true; 
		}
		else {
			return false;
		}		
	}
}
