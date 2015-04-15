package com.br.uepb.dao;

import com.br.uepb.domain.SessaoDomain;

public interface SessaoDAO {

	public void addSessao(SessaoDomain sessao) throws Exception;
	public SessaoDomain getSessao(String login) throws Exception;
	public void deleteSessao(String login) throws Exception;
}
