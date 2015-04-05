package com.br.uepb.business;

import java.text.Normalizer;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SessaoDomain;

@Component
public class CaronaBusiness {
	
	/** Objeto DAO para manipular os objetos da classe UsuarioDomain*/
	private CaronaDAOImpl caronaDAOImpl =  new CaronaDAOImpl();
	private int idCarona = 1;
	
	
	private SessaoDomain sessao;
	
	public ArrayList<CaronaDomain> localizarCarona(String idSessao, String origem, String destino) throws Exception{
		ArrayList<CaronaDomain> caronas;

		if (verificaCaracteres(origem) == false){
			throw new Exception("Origem inválida");
		}

		if (verificaCaracteres(destino) == false){
			throw new Exception("Destino inválido");
		}
				
		if ( (origem.trim().equals("")) && (destino.trim().equals("")) ) {
			caronas = caronaDAOImpl.listCaronas();
		}
		else if (origem.trim().equals(""))  {
			caronas = caronaDAOImpl.listCaronasByDestino(destino);
			
		}
		else if (destino.trim().equals("")){
			caronas = caronaDAOImpl.listCaronasByOrigem(origem);
			
		}
		else {
			caronas = caronaDAOImpl.listCaronas(origem, destino);
		}
		
		return caronas;
	}
	
	public String cadastrarCarona(String idSessao, String origem, String destino, String data, String hora, int vagas) throws Exception{		
		/*
		 * if ( (idSessao == null) || (idSessao.trim().equals("")) ){
			throw new Exception("Sess�o inv�lida");
		}
		
		 * if (!sessao.getLogin().equals(idSessao)) {
			throw new Exception("Sess�o inexistente");
		}*/
		
		String carona = ""+idCarona;
		CaronaDomain caronaDomain = new CaronaDomain(idSessao, carona, origem, destino, data, hora, vagas);		
		
		caronaDAOImpl.addCarona(caronaDomain);
		idCarona++; //TODO: retirar este contador depois que inserir a persistencia com o BD
		return caronaDomain.getID();
	}
	
	public String getAtributoCarona(String idCarona, String atributo) throws Exception{

		if( (atributo == null) || (atributo.trim().equals(""))){
			throw new Exception("Atributo Inválido");
		}
		
		CaronaDomain carona = null; 
		
		try {
			carona = caronaDAOImpl.getCarona(idCarona);			
		} catch (Exception e) {
			if (e.getMessage().equals("Carona Inválida")) {				
				throw new Exception("Identificador do carona é inválido");
			} 
			else if (e.getMessage().equals("Carona Inexistente")) {
				throw new Exception("Item inexistente");
			}		
			else {
				throw new Exception("Item inexistente");
			}
		}
		
		if(atributo.equals("origem")){
			return carona.getOrigem();
		}else if(atributo.equals("destino")){
			return carona.getDestino();
		}else if(atributo.equals("data")){
			return carona.getData();
		}else if(atributo.equals("vagas")){
			return ""+carona.getVagas();
		}else {
			throw new Exception("Atributo inexistente");
		}
				
	}
	
	public String getTrajeto(String idCarona) throws Exception{
		
		CaronaDomain carona = null;
		
		try {
			carona = caronaDAOImpl.getCarona(idCarona);
		} catch (Exception e) {
			if (e.getMessage().equals("Carona Inválida")) {				
					throw new Exception("Trajeto Inválida");
			}
			else if (e.getMessage().equals("Carona Inexistente")) {
				throw new Exception("Trajeto Inexistente");
			}			
			else {
				throw new Exception("Trajeto inexistente");
			}			
		}
		
		
		String trajeto = carona.getOrigem()+" - "+carona.getDestino();
		
		return trajeto;	
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
