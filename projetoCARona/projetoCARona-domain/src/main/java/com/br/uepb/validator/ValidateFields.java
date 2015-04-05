package com.br.uepb.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class ValidateFields {

	public void validarData(String data) throws Exception {
		if ( (data == null) || (data.trim().equals("")) ){
			throw new Exception("Data inválida");
		}
		
		try { 
			//conversão de string para data			
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");			
			formato.setLenient(false); 
			
			java.sql.Date date = new java.sql.Date( ((java.util.Date)formato.parse(data)).getTime() );
		} catch (Exception e) {
			throw new Exception("Data Inválida");
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
			
			java.sql.Date hour = new java.sql.Date( ((java.util.Date)formato.parse(hora)).getTime() );
		} catch (Exception e) {
			throw new Exception("Hora Inválida");
		}		
	}
	
	
}
