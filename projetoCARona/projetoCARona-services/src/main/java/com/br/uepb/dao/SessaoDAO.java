package com.br.uepb.dao;

import com.br.uepb.domain.SessaoDomain;

public interface SessaoDAO {

	/**
	 * Interface para funcionalidades de SessaoDAO
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 2ª Iteração
	 */
	
	public void addSessao(SessaoDomain sessao);
	public void deleteSessao(SessaoDomain sessao) throws Exception;
}
