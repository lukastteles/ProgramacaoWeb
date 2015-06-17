package com.br.uepb.controller;

import java.util.ArrayList;

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

import com.br.uepb.business.CaronaBusiness;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.UserDomain;
import com.br.uepb.viewModels.CadastroCaronaViewModel;

@Controller
public class CaronaController {

	private static final Log LOG = LogFactory.getLog(CaronaController.class);
	
	
	@Autowired
	private CaronaBusiness caronaBusiness;
	
	@RequestMapping(value = "/home/cadastroCarona.html", method = RequestMethod.GET)
	public ModelAndView cadastrarCarona(HttpServletRequest request) {
		
		LOG.debug("Iniciada a execucao do metodo: cadastrarCarona GET");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cadastroCarona");
		modelAndView.addObject("carona", new CadastroCaronaViewModel());
		
		LOG.debug("Finalizada a execucao do metodo: cadastrarCarona GET");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/home/cadastroCarona.html", method = RequestMethod.POST)
	public ModelAndView cadastroCarona(@ModelAttribute("carona") @Valid CadastroCaronaViewModel carona, BindingResult bindingResult, HttpServletRequest request) throws Exception {
		
		LOG.debug("Iniciada a execucao do metodo: cadastroCarona POST");		
		
		ModelAndView modelAndView = new ModelAndView();
		if(bindingResult.hasErrors()){
			modelAndView.setViewName("cadastroCarona");
			modelAndView.addObject("carona", carona);
			return modelAndView;
		}
		String tipo = carona.getTipoCarona();
		
		SessaoDomain sessao = null;
		try{
			sessao = (SessaoDomain)request.getSession().getAttribute("sessao");
			if(tipo.equals("C")){
				caronaBusiness.cadastrarCarona(sessao.getLogin(), carona.getOrigem(), carona.getDestino(), carona.getData(), carona.getHora(), carona.getVagas());
			}else if(tipo.equals("M")){
				caronaBusiness.cadastrarCaronaMunicipal(sessao.getLogin(), carona.getOrigem(), carona.getDestino(), carona.getCidade(), carona.getData(), carona.getHora(), carona.getVagas());
			}else if(tipo.equals("R")){
				caronaBusiness.cadastrarCaronaRelampago(sessao.getLogin(), carona.getOrigem(), carona.getDestino(), carona.getData(), carona.getDataVolta(), carona.getMinimoCaroneiros(), carona.getHora());
			}else{
				LOG.debug("Não setou o tipo");
			}
			
			
		}catch(Exception e){
			modelAndView.setViewName("cadastroCarona");
			modelAndView.addObject("carona", carona);
			return modelAndView;
		}
		
		LOG.debug("Finalizada a execucao do metodo: cadastroCarona POST");
		return new ModelAndView("redirect:/home/home.html");
	}
	
}
