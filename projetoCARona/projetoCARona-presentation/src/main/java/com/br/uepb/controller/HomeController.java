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
		
		//modelAndView.addObject("usuarioDomain", new UserDomain());
		//modelAndView.addObject("userName", "Noca Connected");
		
		request.removeAttribute("sessao");
		
		request.getSession().setAttribute("lstUsers", new ArrayList<UserDomain>());
		
		LOG.debug("Finalizada a execucao do metodo: showWelcomeHtml");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/home/home.html", method = RequestMethod.POST)
	public ModelAndView addNewUser(@ModelAttribute("usuarioDomain") @Valid UserDomain UserDomain, BindingResult bindingResult, HttpServletRequest request) {
		
		LOG.debug("Iniciada a execucao do metodo: addNewUser");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		
		if(bindingResult.hasErrors()){
			UserDomain.setLstUsers((List<UserDomain>) request.getSession().getAttribute("lstUsers"));
			modelAndView.addObject("usuarioDomain", UserDomain);
			modelAndView.addObject("userName", "Noca Connected");
			return modelAndView;
		}
		
		UserDomain ud = new UserDomain();
		
		ud.setCpf(UserDomain.getCpf());
		ud.setNome(UserDomain.getNome());
		ud.setLstUsers((List<UserDomain>) request.getSession().getAttribute("lstUsers"));
		ud.getLstUsers().add(ud);
		
		request.getSession().setAttribute("lstUsers", ud.getLstUsers());
		
		modelAndView.addObject("usuarioDomain", ud);
		modelAndView.addObject("userName", "Noca Connected");
		
		LOG.debug("Finalizada a execucao do metodo: addNewUser");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/home/homeDeleteUserAjax.html", method = RequestMethod.GET)
	public ModelAndView removeUser(String userName, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		List<UserDomain> lstUsers = (List<UserDomain>) request.getSession().getAttribute("lstUsers");
		int indexToRemove = -1;
		for (int i = 0; i < lstUsers.size(); i++) {
			if(lstUsers.get(i).getNome().equals(userName)){
				indexToRemove = i;
				break;
			}
		}
		if(indexToRemove != -1){
			lstUsers.remove(indexToRemove);
		}

		try {
		    Thread.sleep(5000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		request.getSession().setAttribute("lstUsers", lstUsers);
		modelAndView.addObject("lstUsersAsParameter", lstUsers);
		modelAndView.setViewName("home/conteudoUsuario");
		return modelAndView;
	}
	
	@RequestMapping(value = "/home/homeDeleteUser.html", method = RequestMethod.GET)
	public ModelAndView removeUserWithAjaxWithoutWait(String userName, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		List<UserDomain> lstUsers = (List<UserDomain>) request.getSession().getAttribute("lstUsers");
		int indexToRemove = -1;
		for (int i = 0; i < lstUsers.size(); i++) {
			if(lstUsers.get(i).getNome().equals(userName)){
				indexToRemove = i;
				break;
			}
		}
		if(indexToRemove != -1){
			lstUsers.remove(indexToRemove);
		}
		
		UserDomain ud = new UserDomain();
		ud.setLstUsers(lstUsers);
		
		request.getSession().setAttribute("lstUsers", lstUsers);
		modelAndView.setViewName("home");
		modelAndView.addObject("usuarioDomain", ud);
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
		}catch(Exception e){
			modelAndView.addObject("sessaoDomain", sessaoDomain);
			return modelAndView;
		}
		
		modelAndView.setViewName("homeUsuario");
		modelAndView.addObject("sessaoDomain", sessao);
		
		LOG.debug("Finalizada a execucao do metodo: login POST");
		
		return modelAndView;
	}
}
