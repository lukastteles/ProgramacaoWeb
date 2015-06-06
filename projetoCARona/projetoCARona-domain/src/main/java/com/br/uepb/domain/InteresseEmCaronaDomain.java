package com.br.uepb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.exceptions.ProjetoCaronaException;
import com.br.uepb.validator.ValidarCampos;

/**
 * Classe de domínio que define o modelo para o Interesse em carona
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 30/05/2015
 */

@Entity
@Table(name="INTERESSE_CARONA")
public class InteresseEmCaronaDomain {
	
	final static Logger logger = Logger.getLogger(InteresseEmCaronaDomain.class);
	
	/** Id do Interesse em Carona */
	@Id
	@GeneratedValue
	private int id;
	
	/** Login do usuario*/
	@Column(nullable=false)
	private String idSessao;
	
	/** origem para a carona desejada*/
	@Column(nullable=false)
	private String origem;
	
	/** destino para a carona desejada*/
	@Column(nullable=false)
	private String destino;
	
	/** data para a carona desejada*/
	@Column(nullable=false)
	private String data;
	
	/** hora inicio para a carona desejada*/
	@Column(nullable=false)
	private String horaInicio;
	
	/** hora fim para a carona desejada*/
	@Column(nullable=false)
	private String horaFim;
	
	/**
	 * étodo construtor de InteresseEmCaronaDomain
	 * @param idSessao Armazena o login do usuário
	 * @param origem Local de origem desejada
	 * @param destino Local de destino desejado
	 * @param data Data que a carona deve acontecer
	 * @param horaInicio Horário em que a carona deverá acontecer
	 * @param horaFim Horário em que a carona deverá terminar
	 * @throws Exception Lança exceção caso algum dos campos informados esteja vazio, null ou inválido
	 */
	public InteresseEmCaronaDomain(String idSessao, String origem, String destino, String data, String horaInicio, String horaFim) throws Exception{
		setIdSessao(idSessao);
		setOrigem(origem);
		setDestino(destino);
		setData(data);
		setHoraInicio(horaInicio);
		setHoraFim(horaFim);
	}
	
	public InteresseEmCaronaDomain() {}

	/**
	 * Método para retornar o id do Interesse em Carona
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Método para retornar o login do usuario
	 * @return
	 */
	public String getIdSessao() {
		return idSessao;
	}

	/**
	 * Método para informar o login do usuario
	 * @param idSessao Login do usuario
	 * @throws Exception Lança exceção se o idSessao for null ou vazio 
	 */
	private void setIdSessao(String idSessao) throws ProjetoCaronaException {
		if ( (idSessao == null) || (idSessao.trim().equals("")) ){
			logger.debug("setIdSessao() Exceção: "+MensagensErro.SESSAO_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.SESSAO_INVALIDA);
		}
		
		this.idSessao = idSessao;
	}
	
	/**
	 * Método para retornar o local de origem interessado
	 * @return Origem da carona
	 */
	public String getOrigem() {
		return origem;
	}

	/**
	 * Método para informar o local de origem da carona de interesse
	 * @param origem Origem da carona
	 * @throws Exception Lança exceção de o local de origem informado for null ou vazio
	 */
	private void setOrigem(String origem) throws ProjetoCaronaException {
		if ( (origem == null) || (origem.trim().equals("")) ){
			logger.debug("setOrigem() Exceção: "+MensagensErro.ORIGEM_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.ORIGEM_INVALIDA);
		}
		
		ValidarCampos validar = new ValidarCampos();
		if (validar.verificaCaracteres(origem) == false){
			logger.debug("setOrigem() Exceção: "+MensagensErro.ORIGEM_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.ORIGEM_INVALIDA);
		}
		
		this.origem = origem;
	}

	/**
	 * Método para retornar o local de destino interessado
	 * @return Destino da carona
	 */
	public String getDestino() {
		return destino;
	}

	/**
	 * Método para informar o local de destino da carona de interesse
	 * @param destino Destino da carona
	 * @throws Exception Lança exceção se o local de destino informado for null ou vazio
	 */
	private void setDestino(String destino) throws ProjetoCaronaException {
		if ( (destino == null) || (destino.trim().equals("")) ){
			logger.debug("setDestino() Exceção: "+MensagensErro.DESTINO_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.DESTINO_INVALIDO);
		}
		
		ValidarCampos validar = new ValidarCampos();
		if (validar.verificaCaracteres(destino) == false){
			logger.debug("setDestino() Exceção: "+MensagensErro.DESTINO_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.DESTINO_INVALIDO);
		}

		this.destino = destino;
	}

	/**
	 * Método para retornar a data de saída interessado
	 * @return Data da carona
	 */
	public String getData() {
		return data;
	}

	/**
	 * Método para informar a data de saída da carona de interesse
	 * @param data Data da carona
	 * @throws Exception Lança exceção se a data informada estiver null, vazia ou estiver fora do padrão - dia/mês/ano(dd/mm/yyyy)
	 */
	private void setData(String data) throws Exception {
		if(data == null){
			throw new ProjetoCaronaException(MensagensErro.DATA_INVALIDA);
		}
		
		if (data.trim().equals("")){
			this.data = data;
		}else{
			//validacao da Data
			ValidarCampos validar = new ValidarCampos();
			validar.validarFormatoData(data);
			this.data = data;
		}
	}

	/**
	 * Método para retornar a hora de inicio interessado
	 * @return Hora de Inicio da carona
	 */
	public String getHoraInicio() {
		return horaInicio;
	}

	/**
	 * Método para informar a hora de inicio da carona de interesse
	 * @param horaInicio
	 * @throws Exception
	 */
	private void setHoraInicio(String horaInicio) throws Exception {
		if ( (horaInicio == null) || (horaInicio.trim().equals("")) ){
			this.horaInicio = horaInicio;
		}else{
			//validacao da Data
			ValidarCampos validar = new ValidarCampos();
			validar.validarFormatoHora(horaInicio);
			this.horaInicio = horaInicio;
		}
	}

	/**
	 * Método para retornar a hora do fim interessado
	 * @return Hora do fim da carona
	 */
	public String getHoraFim() {
		return horaFim;
	}

	/**
	 * Método para informar a hora do fim da carona de interesse
	 * @param horaFim
	 * @throws Exception
	 */
	private void setHoraFim(String horaFim) throws Exception {
		if ( (horaFim == null) || (horaFim.trim().equals("")) ){
			this.horaFim = horaFim;
		}else{
			//validacao da Data
			ValidarCampos validar = new ValidarCampos();
			validar.validarFormatoHora(horaFim);
			this.horaFim = horaFim;
		}
	}

	
}
