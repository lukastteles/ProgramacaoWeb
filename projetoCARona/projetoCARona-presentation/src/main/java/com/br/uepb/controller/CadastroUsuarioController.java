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
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.funcoesController.FuncoesComuns;

@Controller
public class CadastroUsuarioController {
	
	private static final Log LOG = LogFactory.getLog(HomeController.class);

	@Autowired
	private UsuarioBusiness usuarioBusiness;
	
	@Autowired
	private SessaoBusiness sessaoBusiness;
	
	@Autowired
	private FuncoesComuns funcoesComuns;
	
	@RequestMapping(value = "/home/cadastroUsuario.html", method = RequestMethod.GET)
    public ModelAndView showCadastroUsuario(HttpServletRequest request){
		LOG.debug("Iniciada a execucao do metodo: showCadastroUsuario");

		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cadastroUsuario");
		try{
			modelAndView.addObject("usuarioDomain", new UsuarioDomain());
		}catch(Exception e){
			LOG.debug("Problemas ao tentar listar as funcoes do usuario no metodo: showCadastroUsuario GET - ERRO: "+e.getMessage());		
			return modelAndView;
		}
		
		if (!funcoesComuns.carregaDadosIniciais(modelAndView, sessao)) {
			LOG.debug("Problemas ao tentar listar as funcoes do usuario no metodo: showCadastroUsuario GET ");		
			return modelAndView;
        }
		
		LOG.debug("Finalizada a execucao do metodo: showCadastroUsuario");
		
		return modelAndView;
    }
	
	//TODO falta criar um metodo para capturar os erros  
	@RequestMapping(value = "/home/cadastroUsuario.html", method = RequestMethod.POST)
	public ModelAndView addNovoUsuario(@ModelAttribute("usuarioDomain") @Valid UsuarioDomain usuarioDomain, BindingResult bindingResult, HttpServletRequest request) throws Exception {
		
		LOG.debug("Iniciada a execucao do metodo: addNovoUsuario POST");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cadastroUsuario");
		
		if(bindingResult.hasErrors()){			
			modelAndView.addObject("usuarioDomain", usuarioDomain);
			return modelAndView;
		}
		
		try {
			usuarioBusiness.criarUsuario(usuarioDomain.getLogin(), usuarioDomain.getSenha(), usuarioDomain.getPerfil().getNome(), usuarioDomain.getPerfil().getEndereco(), usuarioDomain.getPerfil().getEmail());			
		} catch (Exception e) {
			LOG.debug("Problemas ao tentar criar um novo usuario no metodo: addNovoUsuario POST - Erro: "+e.getMessage());			
			return modelAndView;
		}			
		//adiciona sessao
		SessaoDomain sessao = new SessaoDomain(usuarioDomain.getLogin(), usuarioDomain.getSenha());
		sessaoBusiness.abrirSessao(sessao.getLogin(), sessao.getSenha());
		request.getSession().setAttribute("sessao", sessao);
		
		
		LOG.debug("Finalizada a execucao do metodo: addNovoUsuario POST");
		
		return new ModelAndView("redirect:/home/homeUsuario.html");
	}

}
