package model;

import java.util.ArrayList;

public class Carona {
	private String id;
	private String origem;
	private String destino;
	private String hora;
	private String data;
	private int vagas;
	private ArrayList<PontoDeEncontro> pontoDeEncontro = new ArrayList<PontoDeEncontro>();
	private ArrayList<SolicitacaoVaga> solicitacaoVaga = new ArrayList<SolicitacaoVaga>();
	
	public Carona(String id, String origem, String destino, String hora, String data, int vagas) {
		this.id = id;
		this.origem = origem;
		this.destino = destino;
		this.hora = hora;
		this.data = data;
		this.vagas = vagas;
	}
	
	public Carona(){ }
	
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
	
	
	

}
