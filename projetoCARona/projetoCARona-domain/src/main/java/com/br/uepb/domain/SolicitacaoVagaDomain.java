package com.br.uepb.domain;

public class SolicitacaoVagaDomain {

	private String id;
	private String idUsuario;
	private String idCarona;
	private boolean foiAceita = false;
	private PontoDeEncontroDomain ponto;
	
	public SolicitacaoVagaDomain(String id, String idUsuario, String idCarona, PontoDeEncontroDomain ponto) {
		this.id = id;
		this.idUsuario = idUsuario;
		this.idCarona = idCarona;
		this.ponto = ponto;
	}
	
	public SolicitacaoVagaDomain(String id, String idUsuario, String idCarona) {
		this.id = id;
		this.idUsuario = idUsuario;
		this.idCarona = idCarona;
	}
	
	public boolean getFoiAceita() {
		return foiAceita;
	}
	
	public void setFoiAceita(boolean foiAceita) {
		this.foiAceita = foiAceita;
	}
	
	public String getId() {
		return id;
	}
	
	public String getIdUsuario() {
		return idUsuario;
	}
	
	public String getIdCarona() {
		return idCarona;
	}
	
	public PontoDeEncontroDomain getPonto() {
		return ponto;
	}
}
