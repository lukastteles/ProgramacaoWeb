package com.br.uepb.domain;

public class PontoDeEncontroDomain {
	
	private String id;
	private String idCarona;
	private String pontoDeEncontro;
	private boolean foiAceita = false;
	
	public PontoDeEncontroDomain(String idCarona, String pontoDeEncontro) {
		//id
		this.idCarona = idCarona;
		this.pontoDeEncontro = pontoDeEncontro;
		//foiAceita = false;
	} 
	
	public String getPontoCarona() {
		return pontoDeEncontro;
	}
	
	public void setPontoCarona(String pontoDeEncontro) {
		this.pontoDeEncontro = pontoDeEncontro;
	}
	
	public boolean isFoiAceita() {
		return foiAceita;
	}
	
	public void setFoiAceita(boolean foiAceita) {
		this.foiAceita = foiAceita;
	}
	
	public String getId() {
		return id;
	}
	
	public String getIdCarona() {
		return idCarona;
	}
}
