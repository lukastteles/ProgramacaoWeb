package com.br.uepb.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.exceptions.ProjetoCaronaException;


/**
 * Classe para validar alguns campos informados nas classes de domínio
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
public class ValidarCampos {

	final static Logger logger = Logger.getLogger(ValidarCampos.class);
	
	/**
	 * Método para verificar se a data está dentro do padrão dd/MM/yyyy ou se está nulo 
	 * @param data Data a ser validada
	 * @throws Exception Lança exceção de a data informada for null, vazia ou não estivier no padrão dd/MM/yyyy
	 */
	public void validarData(String data) throws Exception {
		if ( (data == null) || (data.trim().equals("")) ){
			logger.debug("validarData() Exceção: "+MensagensErro.DATA_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.DATA_INVALIDA);
		}
		
		validarFormatoData(data);
				
	}
	
	/**
	 * Método para verificar se a data está dentro do padrão dd/MM/yyyy
	 * @param data Data a ser validada
	 * @throws ProjetoCaronaException Lança exceção de a data informada não estivier no padrão dd/MM/yyyy
	 */
	public void validarFormatoData(String data) throws ProjetoCaronaException{
		try { 
			//conversão de string para data			
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");			
			formato.setLenient(false); 
			
			new java.sql.Date( ((java.util.Date)formato.parse(data)).getTime() );
		} catch (Exception e) {
			logger.debug("validarData() Exceção: "+MensagensErro.DATA_INVALIDA);
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
			logger.debug("validarHora() Exceção: "+MensagensErro.HORA_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.HORA_INVALIDA);
		}
		
		validarFormatoHora(hora);
				
	}
	
	public void validarFormatoHora(String hora) throws ProjetoCaronaException{
		try { 
			//conversão de string para data
			DateFormat formato = new SimpleDateFormat("HH:mm");			
			formato.setLenient(false); 			
			
			new java.sql.Date( ((java.util.Date)formato.parse(hora)).getTime() );
		} catch (Exception e) {
			logger.debug("validarHora() Exceção: "+MensagensErro.HORA_INVALIDA);
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
			logger.debug("validarEmail() Exceção: "+MensagensErro.EMAIL_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.EMAIL_INVALIDO);
		}
		
	}
		
}
