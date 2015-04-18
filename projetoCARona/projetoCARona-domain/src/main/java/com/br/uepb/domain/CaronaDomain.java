package com.br.uepb.domain;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.br.uepb.validator.ValidarCampos;

public class CaronaDomain {
	/**
	 * Classe de domínio que define o modelo para o Carona
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 1ª IteraçãoO
	 */
	
	ValidarCampos validar = new ValidarCampos();

	/** Id da Sessao*/ //Deve ser gerado automaticamente
	@NotNull(message = "O ID da Sessao não pode ser nulo")
	private String idSessao;
	
	/** Id da Carona*/ //Deve ser gerado automaticamente
	@NotNull(message = "O ID da Carona não pode ser nulo")	
	private String id;

	/** Local de Origem da Carona*/ 
	@NotNull(message = "O local de origem da Carona não pode ser nulo")
	private String origem;
	
	/** Local de Destino da Carona*/ 
	@NotNull(message = "O local de Destino da Carona não pode ser nulo")
	private String destino;
	
	/** Horário da Carona*/ 
	@NotNull(message = "O Horario da Carona não pode ser nulo")
	@Pattern(regexp = "d{2}\\:\\d{2}", message="Hora: preencha no formato hh:mm")	
	private String hora;
	
	/** Dia da Carona*/ 	
	@NotNull(message = "O dia da Carona não pode ser nulo")
	@Pattern(regexp = "d{2}\\/\\d{2}\\/\\d{4}", message="Data: preencha no formato dd/mm/yyyy")
	private String data;
	
	/** Quantidade de vagas disponívels para a Carona*/ 
	@NotNull(message = "A quantidade de vagas na Carona não pode ser nula")	
	private int vagas;
	
	private ArrayList<PontoDeEncontroDomain> pontoDeEncontro = new ArrayList<PontoDeEncontroDomain>();
	
	public CaronaDomain(String idSessao, String idCarona, String origem, String destino, String data, String hora, int vagas) throws Exception { 
		setID(idCarona);
		setIdSessao(idSessao);
		setOrigem(origem);
		setDestino(destino);
		setData(data);
		setHora(hora);
		setVagas(vagas);
	}
	
	
	public String getIdSessao(){
		return idSessao;
	}
	
	private void setIdSessao(String idSessao) throws Exception{
		if ( (idSessao == null) || (idSessao.trim().equals("")) ){
			throw new Exception("Sessão inválida");
		}
		
		this.idSessao = idSessao;
	}
	public String getID() {
		return id;
	}
	public void setID(String id) throws Exception {
		if ( (id == null) || (id.trim().equals("")) ){
			throw new Exception("Identificador da carona não informado");
		}
		
		this.id = id;
	}
	
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) throws Exception {
		if ( (origem == null) || (origem.trim().equals("")) ){
			throw new Exception("Origem inválida");
		}
		this.origem = origem;
	}
	
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) throws Exception {
		if ( (destino == null) || (destino.trim().equals("")) ){
			throw new Exception("Destino inválido");
		}
		this.destino = destino;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) throws Exception {
		//validacao da Hora
		validar.validarHora(hora);
		
		this.hora = hora;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) throws Exception {
		//validacao da Data
		validar.validarData(data);
		
		this.data = data;
	}
	public int getVagas() {
		return vagas;
	}
	public void setVagas(int vagas) {
		this.vagas = vagas;
	}
	
	public void aumentaVagas(){
		this.vagas++;
	}
	
	public void diminuiVagas(){
		this.vagas--;
	}
	
	public void addPontoDeEncontro(PontoDeEncontroDomain ponto){
		pontoDeEncontro.add(ponto);
	}
	
	public ArrayList<PontoDeEncontroDomain> getPontoEncontro(String idSugestao) throws Exception{
		ArrayList<PontoDeEncontroDomain> pontos = new ArrayList<PontoDeEncontroDomain>();
		for (PontoDeEncontroDomain ponto : pontoDeEncontro) {
			if(ponto.getIdSugestao().equals(idSugestao))
				pontos.add(ponto);
		}
		return pontos;
	}
	
	public ArrayList<PontoDeEncontroDomain> getTodosOsPontos(){
		return pontoDeEncontro;
	}
	
	public PontoDeEncontroDomain getPontoEncontroByNome(String ponto) throws Exception{
		for(PontoDeEncontroDomain pontoEncontro : pontoDeEncontro) {
			if(pontoEncontro.getPontoDeEncontro().equals(ponto)){ 
				return pontoEncontro;
			}
		}
		throw new Exception("Ponto Inválido");
	}
	
	//Metodos para pontos de Encontro
	public ArrayList<PontoDeEncontroDomain> getPontoEncontroAceitos() {
		ArrayList<PontoDeEncontroDomain> pontosAceitos = new ArrayList<PontoDeEncontroDomain>();		
		for (PontoDeEncontroDomain ponto : pontoDeEncontro) {
			if(ponto.isFoiAceita() == true)
				pontosAceitos.add(ponto);
		}
		return pontosAceitos;
	}
}
