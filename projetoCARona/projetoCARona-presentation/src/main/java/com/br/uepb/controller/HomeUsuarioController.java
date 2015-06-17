package com.br.uepb.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.br.uepb.business.HomeBusiness;
import com.br.uepb.business.SessaoBusiness;

public class HomeUsuarioController {
	private static final Log LOG = LogFactory.getLog(HomeController.class);

	@Autowired
	private HomeBusiness homeBusiness;
	
	@Autowired
	private SessaoBusiness sessaoBusiness;
	
		
}
