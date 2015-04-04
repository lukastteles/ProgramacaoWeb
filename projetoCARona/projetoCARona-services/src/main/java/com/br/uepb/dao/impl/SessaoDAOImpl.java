package com.br.uepb.dao.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.br.uepb.dao.SessaoDAO;
import com.br.uepb.domain.SessaoDomain;

@Service
public class SessaoDAOImpl implements SessaoDAO {

	/**
	 * Classe DAO para o objeto SessaoDomain
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 2ª Iteração
	 */
	
	//TODO: Gerar documentacao
	
	//Lista de Sessoes
	ArrayList<SessaoDomain> listaSessoes = new ArrayList<SessaoDomain>();

	public SessaoDAOImpl() {
	}


	@Override
	public void addSessao(SessaoDomain sessao) {
		listaSessoes.add(sessao);
	};
	
	@Override
	public void deleteSessao(SessaoDomain sessao) throws Exception {
		for (SessaoDomain sessaoDomain : listaSessoes) {
			if (sessaoDomain.equals(sessao)) {
				listaSessoes.remove(sessao);
				return;
			}				
		}
		
		throw new Exception("Sessao inexistente");
	};
}
