package com.br.uepb.domain;

public class PontoDeEncontroDomain {
	
	private String id;
	private String idCarona;
	private String pontoDeEncontro;
	private boolean foiAceita = false;
	
	public PontoDeEncontroDomain(String id, String idCarona, String pontoDeEncontro) throws Exception {
		setId(id);
		setIdCarona(idCarona);
		setPontoDeEncontro(pontoDeEncontro);
		//foiAceita = false;
	}
	
	public String getId() {
		return id;
	}
	
	private void setId(String id) throws Exception {
		if ( (id == null) || (id.trim().equals("")) ){
			throw new Exception("id inválido");
		}
		this.id = id;
	}
	
	public String getIdCarona() {
		return idCarona;
	}
	
	private void setIdCarona(String idCarona) throws Exception {
		if ( (idCarona == null) || (idCarona.trim().equals("")) ){
			throw new Exception("idCarona inválido");
		}
		this.idCarona = idCarona;
	}
	
	public String getPontoDeEncontro() {
		return pontoDeEncontro;
	}

	public void setPontoDeEncontro(String pontoDeEncontro) throws Exception {
		if ( (pontoDeEncontro == null) || (pontoDeEncontro.trim().equals("")) ){
			throw new Exception("Ponto Inválido");
		}
		this.pontoDeEncontro = pontoDeEncontro;
		
	}
	
	public boolean isFoiAceita() {
		return foiAceita;
	}
	
	public void setFoiAceita(boolean foiAceita) {
		this.foiAceita = foiAceita;
	}
	
}
