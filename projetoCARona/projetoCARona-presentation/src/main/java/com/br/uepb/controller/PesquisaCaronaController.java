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
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.business.SolicitacaoVagaBusiness;
import com.br.uepb.dao.impl.SolicitacaoVagaDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.viewModels.CadastroCaronaViewModel;
import com.br.uepb.viewModels.PesquisaCaronaViewModels;

@Controller
public class PesquisaCaronaController {
	
	private static final Log LOG = LogFactory.getLog(CaronaController.class);
	
	@Autowired
	private SessaoBusiness sessaoBusiness;

	@Autowired
	private CaronaBusiness caronaBusiness;
	
	@Autowired
	private SolicitacaoVagaBusiness solicitaVagaBusiness;
	
	private ArrayList<CaronaDomain> listaCaronas;
	
	@RequestMapping(value = "/home/pesquisaCarona.html", method = RequestMethod.GET)
	public ModelAndView pesquisarCaronas(HttpServletRequest request) {
		
		LOG.debug("Iniciada a execucao do metodo: pesquisaCarona GET");
		
		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pesquisaCarona");
		modelAndView.addObject("caronaDomainViewModel", new CadastroCaronaViewModel());
		modelAndView.addObject("listaCaronas", new ArrayList<PesquisaCaronaViewModels>());
		modelAndView.addObject("totalCaronas", 0);
		modelAndView.addObject("filtoConsulta", "");
		
		LOG.debug("Finalizada a execucao do metodo: pesquisaCarona GET");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/home/pesquisaCarona.html", method = RequestMethod.POST)
	public ModelAndView listarCaronas(@ModelAttribute("caronaDomainViewModel") @Valid CadastroCaronaViewModel caronaDomainViewModel, BindingResult bindingResult, HttpServletRequest request) throws Exception {
		
		LOG.debug("Iniciada a execucao do metodo: listarCaronas POST");		
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("pesquisaCarona");
		
		if(bindingResult.hasErrors()){
			modelAndView.addObject("caronaDomainViewModel", caronaDomainViewModel);
			return modelAndView;
		}
		
		try{
			SessaoDomain sessao = (SessaoDomain)request.getSession().getAttribute("sessao");
		
			List<CaronaDomain> listaCaronas;
			if ((caronaDomainViewModel.getCidade() == null) || (caronaDomainViewModel.getCidade().isEmpty())) {
				listaCaronas = caronaBusiness.localizarCarona(sessao.getLogin(), caronaDomainViewModel.getOrigem(), caronaDomainViewModel.getDestino());				
			}
			else {
				listaCaronas = caronaBusiness.localizarCaronaMunicipal(sessao.getLogin(), caronaDomainViewModel.getCidade() ,caronaDomainViewModel.getOrigem(), caronaDomainViewModel.getDestino());				
			}			
			
			ArrayList<PesquisaCaronaViewModels> pesquisaCaronas = new ArrayList<PesquisaCaronaViewModels>();
			for (CaronaDomain caronaDomain : listaCaronas) {
				if (caronaDomain.getVagas() > 0) {
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
					pesquisaCaronas.add(modeloCarona);
				}
			}
			modelAndView.addObject("listaCaronas", pesquisaCaronas);
			modelAndView.addObject("totalCaronas", pesquisaCaronas.size());
			
			//tratamento do filtro
			String filtro = "";
			if ((caronaDomainViewModel.getOrigem() != null) && (!caronaDomainViewModel.getOrigem().isEmpty())) {
				filtro += " - Origem: "+caronaDomainViewModel.getOrigem();
			}
			if ((caronaDomainViewModel.getDestino() != null) && (!caronaDomainViewModel.getDestino().isEmpty())) {
				filtro += " - Destino: "+caronaDomainViewModel.getDestino();
			}
			if ((caronaDomainViewModel.getCidade() != null) && (!caronaDomainViewModel.getCidade().isEmpty())) {
				filtro += " - Cidade: "+caronaDomainViewModel.getCidade();
			}
			
			modelAndView.addObject("filtoConsulta", filtro);
		}catch(Exception e){
			modelAndView.addObject("caronaDomain", caronaDomainViewModel);
			LOG.debug("Problemas ao tentar listar as caronas no metodo: listarCaronas POST - Erro: "+e.getMessage());		
			return modelAndView;
		}
		
		LOG.debug("Finalizada a execucao do metodo: listarCaronas POST");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/home/solicitarVagaCarona.html", method = RequestMethod.GET)
	public ModelAndView solicitarVagaCarona(HttpServletRequest request) {
		LOG.debug("Iniciada a execucao do metodo: solicitarVagaCarona POST");
		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		String idCarona = (String) request.getParameter("id");
		try {
			solicitaVagaBusiness.solicitarVaga(sessao.getLogin(), idCarona);
		} catch (Exception e) {
			LOG.debug("Problemas ao tentar solicitar uma vaga na carona no metodo: solicitarVagaCarona GET - Erro: "+e.getMessage());		
			return new ModelAndView("redirect:/home/pesquisaCarona.html");
		}
		
		LOG.debug("Finalizada a execucao do metodo: solicitarVagaCarona POST");		
		return new ModelAndView("redirect:/home/pesquisaCarona.html");
	}
}
