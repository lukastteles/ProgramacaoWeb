package com.br.uepb.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.br.uepb.business.HomeBusiness;
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.viewModels.CadastroCaronaViewModel;

@Controller
public class HomeUsuarioController {
	private static final Log LOG = LogFactory.getLog(HomeController.class);

	@Autowired
	private HomeBusiness homeBusiness;
	
	@Autowired
	private SessaoBusiness sessaoBusiness;
	
	@RequestMapping(value = "/home/homeUsuario.html", method = RequestMethod.GET)
	public ModelAndView getUsuarioHome(HttpServletRequest request) {
		
		LOG.debug("Iniciada a execucao do metodo: getUsuarioHome GET");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("homeUsuario");
		//modelAndView.addObject("carona", new CadastroCaronaViewModel());
		
		LOG.debug("Finalizada a execucao do metodo: getUsuarioHome GET");
		
		return modelAndView;
	}
		
}
