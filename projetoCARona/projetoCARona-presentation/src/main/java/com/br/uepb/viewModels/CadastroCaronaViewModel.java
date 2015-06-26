package com.br.uepb.viewModels;

public class CadastroCaronaViewModel {

	private String id = "";
	private String origem = "";
	private String destino = "";
	private String cidade = "";
	private String hora = "";
	private String data = "";
	private String dataVolta = "";
	private int vagas = 0;
	private int minimoCaroneiros = 0;
	private String tipoCarona = "C";
	
	public CadastroCaronaViewModel() { }
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
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
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
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
	public String getDataVolta() {
		return dataVolta;
	}
	public void setDataVolta(String dataVolta) {
		this.dataVolta = dataVolta;
	}
	public int getVagas() {
		return vagas;
	}
	public void setVagas(int vagas) {
		this.vagas = vagas;
	}
	public int getMinimoCaroneiros() {
		return minimoCaroneiros;
	}
	public void setMinimoCaroneiros(int minimoCaroneiros) {
		this.minimoCaroneiros = minimoCaroneiros;
	}
	public String getTipoCarona() {
		return tipoCarona;
	}
	public void setTipoCarona(String tipoCarona) {
		this.tipoCarona = tipoCarona;
	}	
}
