package com.br.uepb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.apache.log4j.Logger;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.exceptions.ProjetoCaronaException;
import com.br.uepb.validator.ValidarCampos;

@Entity
@Table(name="INTERESSE_CARONA")
public class InteresseEmCaronaDomain {
	
	final static Logger logger = Logger.getLogger(InteresseEmCaronaDomain.class);
	
	/** Id do Interesse em Carona */
	@Id
	@GeneratedValue
	private int id;
	
	@Column(nullable=false)
	private String idSessao;
	
	@Column(nullable=false)
	private String origem;
	
	@Column(nullable=false)
	private String destino;
	
	@Column(nullable=false)
	private String data;
	
	@Column(nullable=false)
	private String horaInicio;
	
	@Column(nullable=false)
	private String horaFim;
	
	public InteresseEmCaronaDomain(String idSessao, String origem, String destino, String data, String horaInicio, String horaFim) throws Exception{
		setIdSessao(idSessao);
		setOrigem(origem);
		setDestino(destino);
		setData(data);
		setHoraInicio(horaInicio);
		setHoraFim(horaFim);
	}
	
	public InteresseEmCaronaDomain() {}

	public int getId() {
		return id;
	}

	public String getIdSessao() {
		return idSessao;
	}

	private void setIdSessao(String idSessao) throws ProjetoCaronaException {
		if ( (idSessao == null) || (idSessao.trim().equals("")) ){
			logger.debug("setIdSessao() Exceção: "+MensagensErro.SESSAO_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.SESSAO_INVALIDA);
		}
		
		this.idSessao = idSessao;
	}
	
	public String getOrigem() {
		return origem;
	}

	private void setOrigem(String origem) throws ProjetoCaronaException {
		if ( (origem == null) || (origem.trim().equals("")) ){
			logger.debug("setOrigem() Exceção: "+MensagensErro.ORIGEM_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.ORIGEM_INVALIDA);
		}
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	private void setDestino(String destino) throws ProjetoCaronaException {
		if ( (destino == null) || (destino.trim().equals("")) ){
			logger.debug("setDestino() Exceção: "+MensagensErro.DESTINO_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.DESTINO_INVALIDO);
		}
		this.destino = destino;
	}

	public String getData() {
		return data;
	}

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

	public String getHoraInicio() {
		return horaInicio;
	}

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

	public String getHoraFim() {
		return horaFim;
	}

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
