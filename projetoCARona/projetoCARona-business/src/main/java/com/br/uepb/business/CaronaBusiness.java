package com.br.uepb.business;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.UsuarioDomain;

@Component
public class CaronaBusiness {
	
	/** Objeto DAO para manipular os objetos da classe UsuarioDomain*/
	//private CaronaDAOImpl caronaDAOImpl =  new CaronaDAOImpl();
	private int idCarona = 1;
	
	
	public ArrayList<CaronaDomain> localizarCarona(String idSessao, String origem, String destino) throws Exception{
		ArrayList<CaronaDomain> caronas;

		if (verificaCaracteres(origem) == false){
			throw new Exception("Origem inválida");
		}

		if (verificaCaracteres(destino) == false){
			throw new Exception("Destino inválido");
		}
				
		if ( (origem.trim().equals("")) && (destino.trim().equals("")) ) {
			caronas = CaronaDAOImpl.getInstance().listCaronas();
		}
		else if (origem.trim().equals(""))  {
			caronas = CaronaDAOImpl.getInstance().listCaronasByDestino(destino);
			
		}
		else if (destino.trim().equals("")){
			caronas = CaronaDAOImpl.getInstance().listCaronasByOrigem(origem);
			
		}
		else {
			caronas = CaronaDAOImpl.getInstance().listCaronas(origem, destino);
		}
		
		return caronas;
	}
	
	public String cadastrarCarona(String idSessao, String origem, String destino, String data, String hora, int vagas) throws Exception{		
		//funcao para verificar se a sessao existe
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		//adiciona a caronas na lista de caronas
		String carona = ""+idCarona;
		CaronaDomain caronaDomain = new CaronaDomain(idSessao, carona, origem, destino, data, hora, vagas);				
		CaronaDAOImpl.getInstance().addCarona(caronaDomain);
		
		//Adiciona a carona ao usuario correspondente
		UsuarioDomain usuario = UsuarioDAOImpl.getInstance().getUsuario(idSessao);	
		usuario.addCarona(caronaDomain.getID());
		usuario.getPerfil().addHistoricoDeCaronas(caronaDomain.getID());
		
		idCarona++; //TODO: retirar este contador depois que inserir a persistencia com o BD
		return caronaDomain.getID();
	}
	
	public String getAtributoCarona(String idCarona, String atributo) throws Exception{

		if( (atributo == null) || (atributo.trim().equals(""))){
			throw new Exception("Atributo inválido");
		}
		
		if ((idCarona == null) || (idCarona.trim().equals(""))) {
			throw new Exception("Identificador do carona é inválido");
		}
		
		CaronaDomain carona = null; 
		
		try {
			carona = CaronaDAOImpl.getInstance().getCarona(idCarona);			
		} catch (Exception e) {
			if (e.getMessage().equals("Carona Inexistente")) {
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
			carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
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
		return  CaronaDAOImpl.getInstance().getCarona(idCarona);
	}
	
	public CaronaDomain getCaronaUsuario(String idSessao, int indexCarona) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		String idCarona = UsuarioDAOImpl.getInstance().getUsuario(idSessao).getIdCaronaByIndex(indexCarona);		
		return CaronaDAOImpl.getInstance().getCarona(idCarona);		
	}
	
	public ArrayList<CaronaDomain> getTodasCaronasUsuario(String idSessao) throws Exception{
		UsuarioDomain usuario =  UsuarioDAOImpl.getInstance().getUsuario(idSessao);
		ArrayList<CaronaDomain> caronasUsuario = new ArrayList<CaronaDomain>();
		for (String idCarona : usuario.getCaronas()) {
			CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
			caronasUsuario.add(carona);
		}
				
		return caronasUsuario;
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
