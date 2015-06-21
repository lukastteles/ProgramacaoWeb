package com.br.uepb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.br.uepb.business.CaronaBusiness;
import com.br.uepb.business.HomeBusiness;
import com.br.uepb.business.PerfilBusiness;
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.business.UsuarioBusiness;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.viewModels.CadastroCaronaViewModel;
import com.br.uepb.viewModels.InterresseEmCaronasViewModel;
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
	
	@RequestMapping(value = "/home/homeUsuario.html", method = RequestMethod.GET)
	public ModelAndView getUsuarioHome(HttpServletRequest request) {
		
		LOG.debug("Iniciada a execucao do metodo: getUsuarioHome GET");

		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("homeUsuario");
		
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
	
	@RequestMapping(value = "/home/interresseCarona.html", method = RequestMethod.GET)
	public ModelAndView interresseCarona(HttpServletRequest request) {
		LOG.debug("Iniciada a execucao do metodo: getUsuarioHome GET");

		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("interresseCarona");
		modelAndView.addObject("interresse", new InterresseEmCaronasViewModel());
		
		LOG.debug("Finalizada a execucao do metodo: getUsuarioHome GET");
		
		return modelAndView;
	}
		
}
