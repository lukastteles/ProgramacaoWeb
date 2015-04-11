package com.br.uepb.domain;

public class SolicitacaoVagaDomain {

	private String id;
	private String idUsuario;
	private String idCarona;
	private boolean foiAceita = false;
	private String[] idsPonto;
	
	public SolicitacaoVagaDomain(String id, String idUsuario, String idCarona, String[] idsPonto) {
		this.id = id;
		this.idUsuario = idUsuario;
		this.idCarona = idCarona;
		this.idsPonto = idsPonto;
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
	
	public String getIdUsuario() {
		return idUsuario;
	}
	
	public String getIdCarona() {
		return idCarona;
	}
	
	public String[] getIdsPonto() {
		return idsPonto;
	}
}
