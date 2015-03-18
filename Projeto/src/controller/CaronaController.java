package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Carona;

public class CaronaController {
	private int idCarona = 1;
	private ArrayList<Carona> caronas = new ArrayList<Carona>();
			
	public String localizarCarona(String idSessao, String origem, String destino) {
		
		String listaCaronas = "{";
		if ( (destino.trim().equals("")) && (origem.trim().equals("")) ) {
			for (int i = 0; i < caronas.size(); i++) {
				//Só coloca a virgula depois de acrescentar algum elemento na lista
				if (listaCaronas.length() > 1) {
					listaCaronas += ",";
				}
				
				listaCaronas += caronas.get(i).getID();			
			}
			listaCaronas += "}";
			return listaCaronas;
		}
		else if (destino.trim().equals("")) {
			for (int i = 0; i < caronas.size(); i++) {
				if (caronas.get(i).getOrigem().equals(origem)) {
					//Só coloca a virgula depois de acrescentar algum elemento na lista
					if (listaCaronas.length() > 1){
						listaCaronas += ",";
					}
					listaCaronas += caronas.get(i).getID();
				}
			}
			listaCaronas += "}";
			return listaCaronas;
		}
		else if (origem.trim().equals(""))  {
			for (int i = 0; i < caronas.size(); i++) {
				if (caronas.get(i).getDestino().equals(destino)) {
					//Só coloca a virgula depois de acrescentar algum elemento na lista
					if (listaCaronas.length() > 1) {
						listaCaronas += ",";
					}
					listaCaronas += caronas.get(i).getID();
				}
			}
			listaCaronas += "}";
			return listaCaronas;
		}
		else {
			for (int i = 0; i < caronas.size(); i++) {
				if (  (caronas.get(i).getOrigem().equals(origem)) && (caronas.get(i).getDestino().equals(destino)) ){
					return "{"+caronas.get(i).getID()+"}";			
				}
			}
		}
		
		return "{}";
	}
		
	public String cadastrarCarona(String idSessao, String origem, String destino, String data, String hora, int vagas) {
		try {
			//tratamento dos campos
			tratamentoSessao(idSessao);
			tratamentoOrigem(origem);
			tratamentoDestino(destino);
			tratamentoData(data);
			
			
			
			//Cadastro da Carona
			String id = ""+idCarona;
			Carona novaCarona = new Carona(id, origem, destino, hora, data, vagas);
			caronas.add(novaCarona);
			idCarona++;
			return id;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}		
		return MensagemErro.CARONA_INVALIDA;
	}
	
	public String getAtributoCarona(String idCarona, String atributo){
		Carona carona = null;
		
		for (int i = 0; i < caronas.size(); i++) {
			if (caronas.get(i).getID().equals(idCarona)){
				carona = caronas.get(i);			
			}
		}
		
		if (carona == null) {
			return "Carrona vazia";
		}
			
		switch(atributo){
			case "idCarona": return carona.getID();
			case "origem": return carona.getOrigem();
			case "destino": return carona.getDestino();
			case "hora": return carona.getHora();
			case "data": return carona.getData();		
			default: throw new Error(MensagemErro.ATRIBUTO_INEXISTENTE);				
		}
		
	}
		
	public String getTrajeto (String idCarona){
		try {
			Carona carona = buscaCarona(idCarona);
			String trajeto = carona.getOrigem()+" - "+carona.getDestino();
			return trajeto;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return "Trajeto não encontrado";
	}
	
	public String getCarona(String idCarona){
		try {
			Carona carona = buscaCarona(idCarona);
			String dadosCarona = carona.getOrigem()+" para "+carona.getDestino()+", no dia "+
								 carona.getData()+", as "+carona.getHora();
			return dadosCarona;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return MensagemErro.CARONA_INEXISTENTE;
	}
	
	private Carona buscaCarona(String idCarona) {

		Carona carona = null;
		
		for (int i = 0; i < caronas.size(); i++) {
			if (caronas.get(i).getID().equals(idCarona)){
				carona = caronas.get(i);
				return carona;
			}
		}
		
		if (carona == null) {
			throw new Error(MensagemErro.CARONA_INEXISTENTE);
		}
		
		return carona;
	}

	private void tratamentoSessao(String idSessao) {
		if ( (idSessao == null) || (idSessao.equals("")) ) { 
			throw new Error(MensagemErro.SESSAO_INVALIDA);
		}		
	}
	
	private void tratamentoOrigem(String origem) {
		if ( (origem == null) || (origem.equals("")) ) { 
			throw new Error(MensagemErro.ORIGEM_INVALIDA);
		}		
	}
	
	private void tratamentoDestino(String destino) {
		if ( (destino == null) || (destino.equals("")) ) { 
			throw new Error(MensagemErro.DESTINO_INVALIDO);
		}		
	}
	
	private void tratamentoData(String data) {
		if ( (data == null) || (data.equals("")) ) { 
			throw new Error(MensagemErro.DATA_INVALIDA);
		}		
		
	}	
}
