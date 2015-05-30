package com.br.uepb.dao;

import java.util.List;

import com.br.uepb.domain.InteresseEmCaronaDomain;

public interface InteresseEmCaronaDAO {

	int addIntereseEmCarona(InteresseEmCaronaDomain interesse) throws Exception;

	List<InteresseEmCaronaDomain> getInteresseEmCaronas(String idSessao) throws Exception;

}
