package com.br.uepb.domain;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CaronaDomain {
	/**
	 * Classe de domínio que define o modelo para o Carona
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 1ª IteraçãoO
	 */
	

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
	private ArrayList<SolicitacaoVagaDomain> solicitacaoVaga = new ArrayList<SolicitacaoVagaDomain>();
	
	
	public CaronaDomain(String idSessao, String idCarona, String origem, String destino, String data, String hora, int vagas) { 
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
	
	private void setIdSessao(String idSessao){
		this.idSessao = idSessao;
	}
	public String getID() {
		return id;
	}
	public void setID(String id) {
		this.id = id;
	}
	
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getVagas() {
		return vagas;
	}
	public void setVagas(int vagas) {
		this.vagas = vagas;
	}
	
	public void addPontoEncontro(PontoDeEncontroDomain ponto){
		pontoDeEncontro.add(ponto);
	}
	
	public PontoDeEncontroDomain getPontoEncontro(String idPonto) throws Exception{
		for(PontoDeEncontroDomain ponto : pontoDeEncontro) {
			if(ponto.getId().equals(idPonto)){
				return ponto;
			}
		}
		throw new Exception("ponto não encontrado");
	}
}
