package com.br.uepb.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;

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
	 * Metodo para verificar se a data está dentro do padrão dd/MM/yyyy ou se está nulo 
	 * @param data Data a ser validada
	 * @throws Exception Lança excecao de a data informada for null, vazia ou não estivier no padrão dd/MM/yyyy
	 */
	public void validarData(String data) throws ProjetoCaronaException {
		if ( (data == null) || (data.trim().equals("")) ){
			logger.debug("validarData() Exceção: "+MensagensErro.DATA_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.DATA_INVALIDA);
		}
		
		//Verificar se a data está no formato correto
		validarFormatoData(data);
	}
		
	/**
	 * Metodo para verificar se a data inicial esta maior que a data final
	 * @param dataIni Data inicial
	 * @param dataFim Data final
	 * @throws ProjetoCaronaException Lança excecao de a dataIni for menor que a dataFim
	 */
	public void verificarDatas(String dataIni, String dataFim) throws ProjetoCaronaException{
		try {
			//data informada
			org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
			DateTime inicio = formatter.parseDateTime(dataIni);
			DateTime fim = formatter.parseDateTime(dataFim);
			
			//Se a dataIni for maior que a dataFim ira lançar uma exceção
			new Interval(inicio, fim);			
		} catch (Exception e) {
			logger.debug("validarData() Exceção: "+MensagensErro.DATA_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.DATA_INVALIDA);
		}
		
	}
	
	/**
	 * Metodo para verificar se a data informada está dentro do periodo de 48h.
	 * Se a data informada nao estiver dentro do periodo de 48 horas da data do sistema sera lancada uma excecao 
	 * @param data Data informada 
	 * @return true se a data estiver valida ou false caso contrario
	 * @throws ProjetoCaronaException Lança excecao de a data informada for null, vazia ou não estivier no padrão dd/MM/yyyy
	 */
	public boolean isDataIniValida(String data) throws ProjetoCaronaException{
		try {
			//data informada
			org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
			DateTime dataSistema = DateTime.now();
			DateTime dataIni = formatter.parseDateTime(data);
			
			//Verificar se a data inicial é maior que a data do sistema + 48h(2 dias)
			Interval intervalo = new Interval(dataSistema, dataIni);			
			Duration duracao = intervalo.toDuration();			
			long quantDias = duracao.getStandardDays();
			//Se a diferença entre a data do sistema e a data inicial for menor que 2 dias retorna false 
			if (quantDias < 2) {
				return false;
			}
			else {
				return true;
			}
		} catch (Exception e) {
			logger.debug("validarData() Exceção: "+MensagensErro.DATA_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.DATA_INVALIDA);
		}		
	}
	
	/**
	 * Método para verificar se a data está dentro do padrão dd/MM/yyyy
	 * @param data Data a ser validada
	 * @throws ProjetoCaronaException Lança exceção de a data informada não estiver no padrão dd/MM/yyyy
	 */
	public void validarFormatoData(String data) throws ProjetoCaronaException{
		try { 
			org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
			formatter.parseDateTime(data);
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
	public void validarHora(String hora) throws ProjetoCaronaException {
		if ( (hora == null) || (hora.trim().equals("")) ){
			logger.debug("validarHora() Exceção: "+MensagensErro.HORA_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.HORA_INVALIDA);
		}
		
		validarFormatoHora(hora);
				
	}
	
	/**
	 * Metodo para verificar se a hora informada esta dentro do padrao HH:mm
	 * @param hora
	 * @throws ProjetoCaronaException Lança excecao de a hora informada for null, vazia ou não estivier no padrão HH:mm
	 */
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
