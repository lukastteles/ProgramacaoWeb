package com.br.uepb.viewModels;

public class PesquisaCaronaViewModels {
	
	private String idCarona;
	private String origem;
	private String destino;
	private String hora;
	private String data;
	private String dataVolta;
	private String idSessao;
	private String nomeMotorista;
	private int vagas;
	private String tipoCarona;
	private String cidade;
	private Boolean solicitouVaga = false;
	
	public String getIdCarona() {
		return idCarona;
	}
	public void setIdCarona(String idCarona) {
		this.idCarona = idCarona;
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
	public String getDataVolta() {
		return dataVolta;
	}
	public void setDataVolta(String dataVolta) {
		this.dataVolta = dataVolta;
	}
	public String getIdSessao() {
		return idSessao;
	}
	public void setIdSessao(String idSessao) {
		this.idSessao = idSessao;
	}
	public String getNomeMotorista() {
		return nomeMotorista;
	}
	public void setNomeMotorista(String nomeMotorista) {
		this.nomeMotorista = nomeMotorista;
	}
	public int getVagas() {
		return vagas;
	}
	public void setVagas(int vagas) {
		this.vagas = vagas;
	}
	public String getTipoCarona() {
		return tipoCarona;
	}
	public void setTipoCarona(String tipoCarona) {
		this.tipoCarona = tipoCarona;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public Boolean getSolicitouVaga() {
		return solicitouVaga;
	}
	public void setSolicitouVaga(Boolean solicitouVaga) {
		this.solicitouVaga = solicitouVaga;
	}
		
}
