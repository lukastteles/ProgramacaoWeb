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

import com.br.uepb.business.CaronaBusiness;
import com.br.uepb.business.PerfilBusiness;
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.business.UsuarioBusiness;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.InteresseEmCaronaDomain;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.funcoesController.FuncoesComuns;
import com.br.uepb.viewModels.InteresseEmCaronasViewModel;
import com.br.uepb.viewModels.PesquisaCaronaViewModels;

@Controller
public class HomeUsuarioController {
	private static final Log LOG = LogFactory.getLog(HomeController.class);

	@Autowired
	private UsuarioBusiness usuarioBusiness;
	@Autowired
	private SessaoBusiness sessaoBusiness;
	@Autowired
	private PerfilBusiness perfilBusiness;
	@Autowired
	private CaronaBusiness caronaBusiness;
	@Autowired
	private FuncoesComuns funcoesComuns;
	
	@RequestMapping(value = "/home/homeUsuario.html", method = RequestMethod.GET)
	public ModelAndView getUsuarioHome(HttpServletRequest request) {
		
		LOG.debug("Iniciada a execucao do metodo: getUsuarioHome GET");

		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("homeUsuario");
		
        /* Lista todas as caronas*/
		modelAndView.addObject("listaCaronas", new ArrayList<PesquisaCaronaViewModels>());
        if (!pesquisaCaronas(sessao, modelAndView)) {
        	modelAndView.addObject("listaCaronas", new ArrayList<PesquisaCaronaViewModels>());
			LOG.debug("Problemas ao tentar listar as caronas no metodo: getUsuarioHome GET");		
			return modelAndView;
		}
        
        if (!funcoesComuns.carregaDadosIniciais(modelAndView, sessao)) {
			LOG.debug("Problemas ao tentar listar as funcoes do usuario no metodo: getUsuarioHome GET ");		
			return modelAndView;
        }
        
		LOG.debug("Finalizada a execucao do metodo: getUsuarioHome GET");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/home/perfil.html", method = RequestMethod.GET)
	public ModelAndView getPerfil(HttpServletRequest request) {
		
		LOG.debug("Iniciada a execucao do metodo: getPerfil GET");

		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("perfil");
		
		if (!funcoesComuns.carregaDadosIniciais(modelAndView, sessao)) {
			LOG.debug("Problemas ao tentar listar as funcoes do usuario no metodo: getPerfil GET ");		
			return modelAndView;
        }
		
		String nome = null, endereco = null, email = null;
		int quantCaronasSegurasETranquilas = 0, quantCaronasQueNaoFuncionaram = 0, quantCaronasQueParticipei = 0, quantCaronasQueFaltei = 0;
		try {
			nome = usuarioBusiness.getAtributoUsuario(sessao.getLogin(), "nome");
			endereco = usuarioBusiness.getAtributoUsuario(sessao.getLogin(), "endereco");
			email = usuarioBusiness.getAtributoUsuario(sessao.getLogin(), "email");
			//TODO: implementar funcoes que pega essas quantidades
			//quantCaronasSegurasETranquilas = ;
			//quantCaronasQueNaoFuncionaram = ;
			//quantCaronasQueParticipei = ;
			//quantCaronasQueFaltei = ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try{
			
			//Minhas Caronas
			List<CaronaDomain> listaMinhasCaronas = perfilBusiness.getHistoricoDeCaronas(sessao.getLogin());
			List<PesquisaCaronaViewModels> pesquisaCaronas = new ArrayList<PesquisaCaronaViewModels>();
			carregaListasCarona(listaMinhasCaronas, pesquisaCaronas);
			
			//Caronas que participei
			List<CaronaDomain> listaCaronasParticipei = perfilBusiness.getHistoricoDeVagasEmCaronas(sessao.getLogin());
			List<PesquisaCaronaViewModels> pesquisaCaronasParticipei = new ArrayList<PesquisaCaronaViewModels>();
			carregaListasCarona(listaCaronasParticipei, pesquisaCaronasParticipei);
			
			modelAndView.addObject("listaCaronas", pesquisaCaronas);
			modelAndView.addObject("totalCaronas", pesquisaCaronas.size());
			modelAndView.addObject("listaCaronasParticipei", pesquisaCaronasParticipei);
			modelAndView.addObject("totalCaronasParticipei", pesquisaCaronasParticipei.size());
		}catch(Exception e){
			return modelAndView;
		}
		modelAndView.addObject("nome", nome);
		modelAndView.addObject("endereco", endereco);
		modelAndView.addObject("email", email);
		modelAndView.addObject("quantCaronasSegurasETranquilas", quantCaronasSegurasETranquilas);
		modelAndView.addObject("quantCaronasQueNaoFuncionaram", quantCaronasQueNaoFuncionaram);
		modelAndView.addObject("quantCaronasQueParticipei", quantCaronasQueParticipei);
		modelAndView.addObject("quantCaronasQueFaltei", quantCaronasQueFaltei);
		
		LOG.debug("Finalizada a execucao do metodo: getPerfil GET");
		
		return modelAndView;
	}

	private void carregaListasCarona(List<CaronaDomain> lista, List<PesquisaCaronaViewModels> pesquisa) throws Exception {
		for (CaronaDomain caronaDomain : lista) {
			PesquisaCaronaViewModels modeloCarona = new PesquisaCaronaViewModels();
			modeloCarona.setIdCarona(caronaDomain.getId());
			modeloCarona.setOrigem(caronaDomain.getOrigem());
			modeloCarona.setDestino(caronaDomain.getDestino());
			modeloCarona.setHora(caronaDomain.getHora());
			modeloCarona.setData(caronaDomain.getData());
			modeloCarona.setDataVolta(caronaDomain.getDataVolta());
			modeloCarona.setVagas(caronaDomain.getVagas());
			modeloCarona.setIdSessao(caronaDomain.getIdSessao());
			modeloCarona.setCidade(caronaDomain.getCidade());
			
			//Tratamento para o tipo da carona
			if (caronaDomain.getTipoCarona().equals("M")){
				modeloCarona.setTipoCarona("Municipal");
			}
			else if (caronaDomain.getTipoCarona().equals("R")){
				modeloCarona.setTipoCarona("Relâmpago");
			}
			else {
				modeloCarona.setTipoCarona("Interurbana");
			}
			
			String nomeMotorista = UsuarioDAOImpl.getInstance().getUsuario(caronaDomain.getIdSessao()).getPerfil().getNome();
			modeloCarona.setNomeMotorista(nomeMotorista);
			
			pesquisa.add(modeloCarona);
		}
	}
	
	@RequestMapping(value = "/home/cadastroInteresse.html", method = RequestMethod.GET)
	public ModelAndView interesseCarona(HttpServletRequest request) {
		LOG.debug("Iniciada a execucao do metodo: interesseCarona GET");

		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("cadastroInteresse");
		modelAndView.addObject("interesse", new InteresseEmCaronasViewModel());
		
		if (!funcoesComuns.carregaDadosIniciais(modelAndView, sessao)) {
			LOG.debug("Problemas ao tentar listar as funcoes do usuario no metodo: interesseCarona GET ");		
			return modelAndView;
        }
		
		LOG.debug("Finalizada a execucao do metodo: interesseCarona GET");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/home/cadastroInteresse.html", method = RequestMethod.POST)
	public ModelAndView cadastroInteresse(@ModelAttribute("interesse") @Valid InteresseEmCaronasViewModel interesse, BindingResult bindingResult, HttpServletRequest request) throws Exception {
		
		LOG.debug("Iniciada a execucao do metodo: cadastroInteresse POST");		
		
		ModelAndView modelAndView = new ModelAndView();
		if(bindingResult.hasErrors()){
			modelAndView.setViewName("interesseCarona");
			modelAndView.addObject("interesse", interesse);
			return modelAndView;
		}
		
		try{
			SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
			perfilBusiness.cadastraInteresse(sessao.getLogin(), interesse.getOrigem(), interesse.getDestino(), interesse.getData(), interesse.getHoraInicio(), interesse.getHoraFim());
		}catch(Exception e){
			modelAndView.setViewName("interesseCarona");
			modelAndView.addObject("interesse", interesse);
			return modelAndView;
		}
		
		LOG.debug("Finalizada a execucao do metodo: cadastroInteresse POST");
		return new ModelAndView("redirect:/home/interessesCarona.html");
	}
	
	@RequestMapping(value = "/home/interessesCarona.html", method = RequestMethod.GET)
	public ModelAndView listaInteressesCarona(HttpServletRequest request) {
		LOG.debug("Iniciada a execucao do metodo: listaInteressesCarona GET");
		
		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("interessesCarona");
		
		if (!funcoesComuns.carregaDadosIniciais(modelAndView, sessao)) {
			LOG.debug("Problemas ao tentar listar as funcoes do usuario no metodo: listaInteressesCarona GET ");		
			return modelAndView;
        }
		
		List<InteresseEmCaronaDomain> interesses;
		try {
			interesses = perfilBusiness.getInteresses(sessao.getLogin());
		} catch (Exception e) {
			return new ModelAndView("redirect:/home/homeUsuario.html");
		}
		
		modelAndView.addObject("listaInteresses", interesses);
		modelAndView.addObject("totalInteresses", interesses.size());
		LOG.debug("Finalizada a execucao do metodo: listaInteressesCarona GET");
		return modelAndView;
	}
	
	@RequestMapping(value = "/home/apagaInteresse.html", method = RequestMethod.GET)
	public ModelAndView apagaInteressesCarona(HttpServletRequest request) {
		LOG.debug("Iniciada a execucao do metodo: apagaInteressesCarona GET");
		
		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		String id = (String) request.getParameter("id");
		try {
			perfilBusiness.apagaInteresseEmCarona(id);
		} catch (Exception e) {
			return new ModelAndView("redirect:/home/interessesCarona.html");
		}
		LOG.debug("Finalizada a execucao do metodo: apagaInteressesCarona GET");
		return new ModelAndView("redirect:/home/interessesCarona.html");
	}
	
	
	private Boolean pesquisaCaronas(SessaoDomain sessao, ModelAndView modelAndView) {
		try{
			//SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
			List<CaronaDomain> listaCaronas = caronaBusiness.localizarCarona(sessao.getLogin(), "", "");				
			
			ArrayList<PesquisaCaronaViewModels> pesquisaCaronas = new ArrayList<PesquisaCaronaViewModels>();
			for (CaronaDomain caronaDomain : listaCaronas) {
				PesquisaCaronaViewModels modeloCarona = new PesquisaCaronaViewModels();
				modeloCarona.setIdCarona(caronaDomain.getId());
				modeloCarona.setOrigem(caronaDomain.getOrigem());
				modeloCarona.setDestino(caronaDomain.getDestino());
				modeloCarona.setHora(caronaDomain.getHora());
				modeloCarona.setData(caronaDomain.getData());
				modeloCarona.setDataVolta(caronaDomain.getDataVolta());
				modeloCarona.setVagas(caronaDomain.getVagas());
				modeloCarona.setIdSessao(caronaDomain.getIdSessao());
				modeloCarona.setCidade(caronaDomain.getCidade());
						
				//Tratamento para o tipo da carona
				if (caronaDomain.getTipoCarona().equals("M")){
					modeloCarona.setTipoCarona("Municipal");
				}
				else if (caronaDomain.getTipoCarona().equals("R")){
					modeloCarona.setTipoCarona("Relâmpago");
				}
				else {
					modeloCarona.setTipoCarona("Interurbana");
				}
						
				String nomeMotorista = UsuarioDAOImpl.getInstance().getUsuario(caronaDomain.getIdSessao()).getPerfil().getNome();
				modeloCarona.setNomeMotorista(nomeMotorista);
				pesquisaCaronas.add(modeloCarona);
				
			}
			
			modelAndView.addObject("listaCaronas", pesquisaCaronas);

		}catch(Exception e){
			LOG.debug("Problemas ao tentar listar as caronas - Erro: "+e.getMessage());		
			return false;
		}
		
		return true;
	}
}
