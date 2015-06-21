package com.br.uepb.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.br.uepb.business.HomeBusiness;
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.UserDomain;

@Controller
public class HomeController {
	
	private static final Log LOG = LogFactory.getLog(HomeController.class);

	@Autowired
	private HomeBusiness homeBusiness;
	
	@Autowired
	private SessaoBusiness sessaoBusiness;

	@RequestMapping(value = "/home/home.html", method = RequestMethod.GET)
	public ModelAndView showWelcomeHtml(HttpServletRequest request) {
		
		LOG.debug("Iniciada a execucao do metodo: showWelcomeHtml");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		
		SessaoDomain sessao = (SessaoDomain)request.getSession().getAttribute("sessao");
		try{
			if(sessao != null){
				sessaoBusiness.encerrarSessao(sessao.getLogin());
				request.getSession().removeAttribute("sessao");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		LOG.debug("Finalizada a execucao do metodo: showWelcomeHtml");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/home/login.html", method = RequestMethod.GET)
	public ModelAndView showLogin(HttpServletRequest request) {
		
		LOG.debug("Iniciada a execucao do metodo: showWelcomeHtml");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		try {
			modelAndView.addObject("sessaoDomain", new SessaoDomain());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LOG.debug("Finalizada a execucao do metodo: showWelcomeHtml");
		
		return modelAndView;
	}
	

	@RequestMapping(value = "/home/login.html", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("sessaoDomain") @Valid SessaoDomain sessaoDomain, BindingResult bindingResult, HttpServletRequest request) throws Exception {
		
		LOG.debug("Iniciada a execucao do metodo: login POST");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		
		if(bindingResult.hasErrors()){
			modelAndView.addObject("sessaoDomain", sessaoDomain);
			return modelAndView;
		}
		
		SessaoDomain sessao = null;
		try{
			sessao = new SessaoDomain(sessaoDomain.getLogin(), sessaoDomain.getSenha());
			sessaoBusiness.abrirSessao(sessao.getLogin(), sessao.getSenha());
			request.getSession().setAttribute("sessao", sessao);
		}catch(Exception e){
			modelAndView.addObject("sessaoDomain", sessaoDomain);
			return modelAndView;
		}
		
		//modelAndView.setViewName("homeUsuario");
		//modelAndView.addObject("sessaoDomain", sessao);
		
		LOG.debug("Finalizada a execucao do metodo: login POST");
		
		return new ModelAndView("redirect:/home/homeUsuario.html");
	}
}
