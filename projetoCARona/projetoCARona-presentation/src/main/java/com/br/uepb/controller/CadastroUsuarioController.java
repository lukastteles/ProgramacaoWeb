package com.br.uepb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.business.UsuarioBusiness;
import com.br.uepb.domain.UsuarioDomain;

@Controller
public class CadastroUsuarioController {
	
	private static final Log LOG = LogFactory.getLog(HomeController.class);

	@Autowired
	private UsuarioBusiness usuarioBusiness;
	
	@Autowired
	private SessaoBusiness sessaoBusiness;
	
	@RequestMapping(value = "/home/cadastroUsuario.html", method = RequestMethod.POST)
	public ModelAndView addNovoUsuario(@ModelAttribute("usuarioDomain") @Valid UsuarioDomain usuarioDomain, BindingResult bindingResult, HttpServletRequest request) throws Exception {
		
		LOG.debug("Iniciada a execucao do metodo: addNovoUsuario POST");

		ModelAndView modelAndView = new ModelAndView();
		
		
		modelAndView.setViewName("home");
		
		LOG.debug("Finalizada a execucao do metodo: addNovoUsuario POST");
		
		return modelAndView;
	}

}
