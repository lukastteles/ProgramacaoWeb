package com.br.uepb.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class ValidarCampos {

	public void validarData(String data) throws Exception {
		if ( (data == null) || (data.trim().equals("")) ){
			throw new Exception("Data inválida");
		}
		
		try { 
			//conversão de string para data			
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");			
			formato.setLenient(false); 
			
			new java.sql.Date( ((java.util.Date)formato.parse(data)).getTime() );
		} catch (Exception e) {
			throw new Exception("Data inválida");
		}		
	}
	
	public void validarHora(String hora) throws Exception {
		if ( (hora == null) || (hora.trim().equals("")) ){
			throw new Exception("Hora inválida");
		}
		
		try { 
			//conversão de string para data
			DateFormat formato = new SimpleDateFormat("HH:mm");			
			formato.setLenient(false); 			
			
			new java.sql.Date( ((java.util.Date)formato.parse(hora)).getTime() );
		} catch (Exception e) {
			throw new Exception("Hora inválida");
		}		
	}
	
	public void validarEmail(String email) throws Exception
	{
		if ((email == null) || (email.trim().length() == 0)) {
			throw new Exception("Email inválido");
		}
		
		//TODO: Acrescentar aqui metodo para validar email
		
	}
	
	public void validarVagas(String vagas) throws Exception {
		if ((vagas == null) || (vagas.trim().length() == 0)) {
			throw new Exception("Vaga inválida");
		}
		
		try { 
			Integer.parseInt(vagas);
		} catch (Exception e) {
			throw new Exception("Vaga inválida");
		}	
	}
	
	
}
