package com.br.uepb.domain;

public class PontoDeEncontroDomain {
	
	private String idSugestao;
	private String pontoDeEncontro;
	private boolean foiAceita = false;
	
	public PontoDeEncontroDomain(String idSugestao, String pontoDeEncontro) throws Exception {
		setIdSugestao(idSugestao);
		setPontoDeEncontro(pontoDeEncontro);
	}
	
	public String getIdSugestao() {
		return idSugestao;
	}

	private void setIdSugestao(String idSugestao) {
		this.idSugestao = idSugestao;
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
