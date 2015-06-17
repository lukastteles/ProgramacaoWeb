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
import com.br.uepb.viewModels.CadastroUsuarioViewModels;

@Controller
public class CadastroUsuarioController {
	
	private static final Log LOG = LogFactory.getLog(HomeController.class);

	@Autowired
	private UsuarioBusiness usuarioBusiness;
	
	@Autowired
	private SessaoBusiness sessaoBusiness;
	
	@RequestMapping(value = "/home/cadastroUsuario.html", method = RequestMethod.GET)
    public ModelAndView showCadastroUsuario(HttpServletRequest request){
		LOG.debug("Iniciada a execucao do metodo: showCadastroUsuario");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cadastroUsuario");
		
		modelAndView.addObject("usuarioVieModel", new CadastroUsuarioViewModels());
		
		LOG.debug("Finalizada a execucao do metodo: showCadastroUsuario");
		
		return modelAndView;
    }
	
	//TODO falta criar um metodo para capturar os erros  
	@RequestMapping(value = "/home/cadastroUsuario.html", method = RequestMethod.POST)
	public ModelAndView addNovoUsuario(@ModelAttribute("usuarioVieModel") @Valid CadastroUsuarioViewModels usuarioVieModel, BindingResult bindingResult, HttpServletRequest request) throws Exception {
		
		LOG.debug("Iniciada a execucao do metodo: addNovoUsuario POST");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cadastroUsuario");

		if(bindingResult.hasErrors()){
			modelAndView.addObject("usuarioVieModel", usuarioVieModel);			
			return modelAndView;
		}
		
		try {
			usuarioBusiness.criarUsuario(usuarioVieModel.getLogin(), usuarioVieModel.getSenha(), usuarioVieModel.getNome(), usuarioVieModel.getEndereco(), usuarioVieModel.getEmail());			
		} catch (Exception e) {
			LOG.debug("Problemas ao tentar criar um novo usuario no metodo: addNovoUsuario POST - Erro: "+e.getMessage());			
			return modelAndView;
		}

		modelAndView.setViewName("home");
		LOG.debug("Finalizada a execucao do metodo: addNovoUsuario POST");
		
		return modelAndView;
	}

}
