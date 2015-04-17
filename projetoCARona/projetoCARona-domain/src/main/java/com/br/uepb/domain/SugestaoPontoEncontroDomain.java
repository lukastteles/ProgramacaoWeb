package com.br.uepb.domain;

import java.util.ArrayList;

public class SugestaoPontoEncontroDomain {
	
	private String id;
	private PontoDeEncontroDomain[] pontos;
	
	
	public SugestaoPontoEncontroDomain(String id, String[] pontosDeEncontro) throws Exception {
		setId(id);
		pontos = new PontoDeEncontroDomain[pontosDeEncontro.length];
		for(int i = 0; i < pontosDeEncontro.length; i++){
			pontos[i] = new PontoDeEncontroDomain(pontosDeEncontro[i]);
		}
	}

	public SugestaoPontoEncontroDomain(String id, String pontoDeEncontro) throws Exception {
		setId(id);
		pontos = new PontoDeEncontroDomain[1];
		pontos[0] = new PontoDeEncontroDomain(pontoDeEncontro);
	}

	public String getId() {
		return id;
	}
	
	private void setId(String id) {
		this.id = id;
	}

	public void setFoiAceitaPonto(String ponto, boolean foiAceita) throws Exception {
		for (int i = 0; i < pontos.length; i++) {
			if(pontos[i].getPontoDeEncontro().equals(ponto))
				pontos[i].setFoiAceita(foiAceita);
		}
		throw new Exception("ponto nao existe na sugestão");
		
	}

}
