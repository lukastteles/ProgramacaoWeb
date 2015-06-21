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
import com.br.uepb.business.SolicitacaoVagaBusiness;
import com.br.uepb.dao.impl.SolicitacaoVagaDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.domain.UserDomain;
import com.br.uepb.viewModels.CadastroCaronaViewModel;
import com.br.uepb.viewModels.PesquisaCaronaViewModels;

@Controller
public class CaronaController {

	private static final Log LOG = LogFactory.getLog(CaronaController.class);
	
	
	@Autowired
	private CaronaBusiness caronaBusiness;
	@Autowired
	private SolicitacaoVagaBusiness solicitaVagaBusiness;
	
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
		modelAndView.setViewName("carona");
		CaronaDomain carona;
		PesquisaCaronaViewModels modeloCarona = null;
		try {
			 carona = caronaBusiness.getCarona(idCarona);
			 modeloCarona = getViewModel(carona);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		modelAndView.addObject("carona", modeloCarona);
		
		//solicita vaga
		try {
			String vaga = (String) request.getParameter("vaga");
			if(vaga != null){
				solicitaVagaBusiness.solicitarVaga(sessao.getLogin(), idCarona);
			}
		}catch(Exception e){
			return modelAndView;
		}
		
		LOG.debug("Finalizada a execucao do metodo: cadastrarCarona GET");
		
		return modelAndView;
	}
	
	private PesquisaCaronaViewModels getViewModel(CaronaDomain caronaDomain) throws Exception{
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
		
		//Caronas confirmadas
		ArrayList<SolicitacaoVagaDomain> solicitacoesConfirmadas =  SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesConfirmadas(caronaDomain.getID());
		//Caronas pendentes
		ArrayList<SolicitacaoVagaDomain> solicitacoesPendentes =  SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesPendentes(caronaDomain.getID());
		
		Boolean usuarioSolicitou = false;
		for (SolicitacaoVagaDomain solicitacaoP : solicitacoesPendentes) {
			if (caronaDomain.getIdSessao() == solicitacaoP.getIdUsuario()) {
				usuarioSolicitou = true;
				break;
			}
		}
		
		if (usuarioSolicitou == false) {
			for (SolicitacaoVagaDomain solicitacaoC : solicitacoesConfirmadas) {
				if (caronaDomain.getIdSessao() == solicitacaoC.getIdUsuario()) {
					usuarioSolicitou = true;
					break;
				}
			}
		}
		
		modeloCarona.setSolicitouVaga(usuarioSolicitou);
		return modeloCarona;
	}
	
	
}
