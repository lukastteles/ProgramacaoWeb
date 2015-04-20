package com.br.uepb.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.exceptions.ProjetoCaronaException;


/**
 * Classe para validar alguns campos informados nas classes de domínio
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
public class ValidarCampos {

	/**
	 * Método para verificar se a data está dentro do padrão dd/MM/yyyy 
	 * @param data Data a ser validada
	 * @throws Exception Lança exceção de a data informada for null, vazia ou não estivier no padrão dd/MM/yyyy
	 */
	public void validarData(String data) throws Exception {
		if ( (data == null) || (data.trim().equals("")) ){
			throw new ProjetoCaronaException(MensagensErro.DATA_INVALIDA);
		}
		
		try { 
			//conversão de string para data			
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");			
			formato.setLenient(false); 
			
			new java.sql.Date( ((java.util.Date)formato.parse(data)).getTime() );
		} catch (Exception e) {
			throw new ProjetoCaronaException(MensagensErro.DATA_INVALIDA);
		}		
	}
	
	/**
	 * Método para verificar se a hora está dentro do padrão HH:mm
	 * @param hora Hora a ser validade
	 * @throws Exception Lança exceção de a hora informada for null, vazia ou não estivier no padrão HH:mm 
	 */
	public void validarHora(String hora) throws Exception {
		if ( (hora == null) || (hora.trim().equals("")) ){
			throw new ProjetoCaronaException(MensagensErro.HORA_INVALIDA);
		}
		
		try { 
			//conversão de string para data
			DateFormat formato = new SimpleDateFormat("HH:mm");			
			formato.setLenient(false); 			
			
			new java.sql.Date( ((java.util.Date)formato.parse(hora)).getTime() );
		} catch (Exception e) {
			throw new ProjetoCaronaException(MensagensErro.HORA_INVALIDA);
		}		
	}
	
	//TODO: Acrescentar metodo para validar email
	/**
	 * Método para verificar se o email está dentro do padrão email@email.com
	 * @param email Email a ser validado
	 * @throws Exception Lança exceção de o email informado for null, vazio ou não estivier no padrão email@email.com 
	 */
	public void validarEmail(String email) throws Exception
	{
		if ((email == null) || (email.trim().length() == 0)) {
			throw new ProjetoCaronaException(MensagensErro.EMAIL_INVALIDO);
		}
		
	}
		
}
