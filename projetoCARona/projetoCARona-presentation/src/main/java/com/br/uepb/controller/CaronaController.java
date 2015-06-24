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
import com.br.uepb.business.PontoDeEncontroBusiness;
import com.br.uepb.business.SolicitacaoVagaBusiness;
import com.br.uepb.dao.impl.SolicitacaoVagaDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.PontoDeEncontroDomain;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.domain.UserDomain;
import com.br.uepb.viewModels.CadastroCaronaViewModel;
import com.br.uepb.viewModels.CadastroPontoDeEncontroViewModel;
import com.br.uepb.viewModels.PesquisaCaronaViewModels;

@Controller
public class CaronaController {

	private static final Log LOG = LogFactory.getLog(CaronaController.class);
	
	
	@Autowired
	private CaronaBusiness caronaBusiness;
	@Autowired
	private SolicitacaoVagaBusiness solicitaVagaBusiness;
	@Autowired
	private PontoDeEncontroBusiness pontoDeEncontroBusiness;
	
	@RequestMapping(value = "/home/cadastroCarona.html", method = RequestMethod.GET)
	public ModelAndView cadastrarCarona(HttpServletRequest request) {
		
		LOG.debug("Iniciada a execucao do metodo: cadastrarCarona GET");

		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
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
		return new ModelAndView("redirect:/home/homeUsuario.html");
	}
	
	@RequestMapping(value = "/home/carona.html", params={"id"}, method = RequestMethod.GET)
	public ModelAndView getCarona(HttpServletRequest request) {
		
		LOG.debug("Iniciada a execucao do metodo: cadastrarCarona GET");
		
		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		String idCarona = (String) request.getParameter("id");
		ModelAndView modelAndView = new ModelAndView();
		CaronaDomain carona = null;
		PesquisaCaronaViewModels modeloCarona = null;
		List<PontoDeEncontroDomain> pontos = null;
		List<SolicitacaoVagaDomain> solicitacoes = null;
		List<SolicitacaoVagaDomain> solicitacoesPendentes = null;
		try {
			 carona = caronaBusiness.getCarona(idCarona);
			 modeloCarona = getViewModel(carona, sessao);
			 pontos = pontoDeEncontroBusiness.getPontosSugeridos(sessao.getLogin(), carona.getID());
			 if(carona.getIdSessao().equals(sessao.getLogin())){
				modelAndView.setViewName("minhaCarona");
				solicitacoes = solicitaVagaBusiness.getSolicitacoesConfirmadas(sessao.getLogin(), carona.getID());
				solicitacoesPendentes = solicitaVagaBusiness.getSolicitacoesPendentes(sessao.getLogin(), carona.getID());
				solicitacoes.addAll(solicitacoesPendentes);
				modelAndView.addObject("listaSolicitacoes", solicitacoes);
				modelAndView.addObject("numSolicitacoes", solicitacoes.size());
			}else{
				modelAndView.setViewName("carona");
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		modelAndView.addObject("carona", modeloCarona);
		modelAndView.addObject("listaPontos", pontos);
		modelAndView.addObject("numPontos", pontos.size());
		modelAndView.addObject("ponto", new CadastroPontoDeEncontroViewModel());
		
		LOG.debug("Finalizada a execucao do metodo: cadastrarCarona GET");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/home/carona.html", method = RequestMethod.POST)
	public ModelAndView sugerirPonto(@ModelAttribute("ponto") @Valid CadastroPontoDeEncontroViewModel ponto, BindingResult bindingResult, HttpServletRequest request) throws Exception {
		LOG.debug("Iniciada a execucao do metodo: sugerirPonto POST");
		try{
			SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
			CaronaDomain carona = caronaBusiness.getCarona(ponto.getIdCarona());
			if(carona.getIdSessao().equals(sessao.getLogin())){
				String id = pontoDeEncontroBusiness.sugerirPontoEncontro(sessao.getLogin(), ponto.getIdCarona(), ponto.getPonto());
				pontoDeEncontroBusiness.responderSugestaoPontoEncontro(sessao.getLogin(), ponto.getIdCarona(), id, ponto.getPonto());
			}else{
				pontoDeEncontroBusiness.sugerirPontoEncontro(sessao.getLogin(), ponto.getIdCarona(), ponto.getPonto());
			}
				
		}catch(Exception e){
			return new ModelAndView("redirect:/home/carona.html?id="+ponto.getIdCarona());
		}
		LOG.debug("Finalizada a execucao do metodo: sugerirPonto POST");
		
		return new ModelAndView("redirect:/home/carona.html?id="+ponto.getIdCarona());
		
	}
	
	@RequestMapping(value = "/home/acitarPonto.html", method = RequestMethod.GET)
	public ModelAndView acitarPonto(HttpServletRequest request) {
		LOG.debug("Iniciada a execucao do metodo: acitarPonto GET");
		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		String idCarona = (String) request.getParameter("id");
		String ponto = (String) request.getParameter("ponto");
		String idPonto = (String) request.getParameter("idPonto");
		try{
			pontoDeEncontroBusiness.responderSugestaoPontoEncontro(sessao.getLogin(), idCarona, idPonto, ponto);
		}catch(Exception e ){
			return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
		}
		LOG.debug("Finalizada a execucao do metodo: acitarPonto GET");
		return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
	}
	
	@RequestMapping(value = "/home/desistirPonto.html", method = RequestMethod.GET)
	public ModelAndView desistirPonto(HttpServletRequest request) {
		LOG.debug("Iniciada a execucao do metodo: desistirPonto GET");
		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		String idCarona = (String) request.getParameter("id");
		String ponto = (String) request.getParameter("ponto");
		String idPonto = (String) request.getParameter("idPonto");
		try{
			pontoDeEncontroBusiness.desitirPontoEncontro(sessao.getLogin(), idCarona, idPonto, ponto);
		}catch(Exception e ){
			return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
		}
		LOG.debug("Finalizada a execucao do metodo: desistirPonto GET");
		return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
	}
	
	@RequestMapping(value = "/home/recusarPonto.html", method = RequestMethod.GET)
	public ModelAndView recusarPonto(HttpServletRequest request) {
		LOG.debug("Iniciada a execucao do metodo: recusarPonto GET");
		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		String idCarona = (String) request.getParameter("id");
		String ponto = (String) request.getParameter("ponto");
		String idPonto = (String) request.getParameter("idPonto");
		try{
			pontoDeEncontroBusiness.recusarPontoEncontro(sessao.getLogin(), idCarona, idPonto, ponto);
		}catch(Exception e ){
			return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
		}
		LOG.debug("Finalizada a execucao do metodo: recusarPonto GET");
		return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
	}
	
	private PesquisaCaronaViewModels getViewModel(CaronaDomain caronaDomain, SessaoDomain sessao) throws Exception{
		PesquisaCaronaViewModels modeloCarona = new PesquisaCaronaViewModels();
		modeloCarona.setIdCarona(caronaDomain.getID());
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
		
		//Verifica se a solicitacao está na lista de caronas pendentes
		String idSolicitacao="";
		Boolean usuarioSolicitou = false;
		SolicitacaoVagaDomain solicitacaoVaga =  solicitaVagaBusiness.getSolicitacaoUsuario(sessao.getLogin(), caronaDomain.getID());
		if (solicitacaoVaga != null){
			usuarioSolicitou = true;
			idSolicitacao = solicitacaoVaga.getId();
		}
		modeloCarona.setIdSolicitacao(idSolicitacao);
		modeloCarona.setSolicitouVaga(usuarioSolicitou);
		
		return modeloCarona;
	}
	
	
}
