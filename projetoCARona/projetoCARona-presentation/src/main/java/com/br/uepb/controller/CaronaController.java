package com.br.uepb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
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
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.PontoDeEncontroDomain;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.funcoesController.FuncoesComuns;
import com.br.uepb.validator.ValidarCampos;
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
	
	@Autowired
	private FuncoesComuns funcoesComuns;
	
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
		
		if (!funcoesComuns.carregaDadosIniciais(modelAndView, sessao)) {
			LOG.debug("Problemas ao tentar listar as funcoes do usuario no metodo: cadastrarCarona GET ");		
			return modelAndView;
        }
		
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
		ValidarCampos valida = new ValidarCampos();
		SolicitacaoVagaDomain minhaSolicitacao = null;
		try {
			carona = caronaBusiness.getCarona(idCarona);
			modeloCarona = getViewModel(carona, sessao);
			pontos = pontoDeEncontroBusiness.getPontosSugeridos(sessao.getLogin(), carona.getId());
			
			//verifica se a carona é do usuario que solicitou
			if(carona.getIdSessao().equals(sessao.getLogin())){
				solicitacoes = solicitaVagaBusiness.getSolicitacoesConfirmadas(carona.getIdSessao(), carona.getId());
				solicitacoesPendentes = solicitaVagaBusiness.getSolicitacoesPendentes(carona.getIdSessao(), carona.getId());
				solicitacoes.addAll(solicitacoesPendentes);
				modelAndView.addObject("listaSolicitacoes", solicitacoes);
				modelAndView.addObject("numSolicitacoes", solicitacoes.size());
				modelAndView.setViewName("minhaCarona");
			}else{
				if(modeloCarona.getVagaAceita()){
					solicitacoes = solicitaVagaBusiness.getSolicitacoesConfirmadas(carona.getIdSessao(), carona.getId());
					
					for (SolicitacaoVagaDomain solicitacao : solicitacoes) {
						if(solicitacao.getIdUsuario().equals(sessao.getLogin())){
							solicitacao.setIdUsuario("Você");
							minhaSolicitacao = solicitacao;
						}
					}
					
					modelAndView.addObject("listaSolicitacoes", solicitacoes);
					modelAndView.addObject("numSolicitacoes", solicitacoes.size());
				}
				modelAndView.setViewName("carona");
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try{
			ValidarCampos validaData = new ValidarCampos();
			valida.verificarDatas(DateTime.now().toString("dd/MM/yyyy"), carona.getData());
			modelAndView.addObject("avalia", false);
		}catch(Exception e){
			if(carona.getIdSessao().equals(sessao.getLogin())){
				modelAndView.addObject("avalia", true);
			}else{
				if(modeloCarona.getVagaAceita()){
					if(minhaSolicitacao.getReviewCarona() != null){
						modelAndView.addObject("minhaAvaliacao", minhaSolicitacao.getReviewCarona());
					}else{
						modelAndView.addObject("avalia", true);
					}
				}
			}
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
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
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
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
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
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
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
	
	@RequestMapping(value = "/home/aceitarSolicitacao.html", method = RequestMethod.GET)
	public ModelAndView aceitarSolicitacao(HttpServletRequest request) {
		LOG.debug("Iniciada a execucao do metodo: aceitarSolicitacao GET");
		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		String idCarona = (String) request.getParameter("id");
		String idSolicitacao = (String) request.getParameter("idSolicitacao");
		try{
			solicitaVagaBusiness.aceitarSolicitacao(sessao.getLogin(), idSolicitacao);
		}catch(Exception e){
			return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
		}
		LOG.debug("Finalizada a execucao do metodo: aceitarSolicitacao GET");
		return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
	}
	
	@RequestMapping(value = "/home/desfazerSolicitacao.html", method = RequestMethod.GET)
	public ModelAndView desfazerSolicitacao(HttpServletRequest request) {
		LOG.debug("Iniciada a execucao do metodo: desfazerSolicitacao GET");
		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		String idCarona = (String) request.getParameter("id");
		String idSolicitacao = (String) request.getParameter("idSolicitacao");
		try{
			solicitaVagaBusiness.desfazerSolicitacaoAceita(sessao.getLogin(), idSolicitacao);
		}catch(Exception e){
			return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
		}
		LOG.debug("Finalizada a execucao do metodo: desfazerSolicitacao GET");
		return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
	}
	
	@RequestMapping(value = "/home/recusarSolicitacao.html", method = RequestMethod.GET)
	public ModelAndView recusarSolicitacao(HttpServletRequest request) {
		LOG.debug("Iniciada a execucao do metodo: recusarSolicitacao GET");
		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		String idCarona = (String) request.getParameter("id");
		String idSolicitacao = (String) request.getParameter("idSolicitacao");
		try{
			solicitaVagaBusiness.rejeitarSolicitacao(sessao.getLogin(), idSolicitacao);
		}catch(Exception e){
			return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
		}
		LOG.debug("Finalizada a execucao do metodo: recusarSolicitacao GET");
		return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
	}
	
	@RequestMapping(value = "/home/naoFuncionou.html", method = RequestMethod.GET)
	public ModelAndView naoFuncionou(HttpServletRequest request) {
		LOG.debug("Iniciada a execucao do metodo: naoFuncionou GET");
		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		String idCarona = (String) request.getParameter("id");
		try{
			solicitaVagaBusiness.reviewCarona(sessao.getLogin(), idCarona, "não funcionou");
		}catch(Exception e){
			return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
		}
		LOG.debug("Finalizada a execucao do metodo: naoFuncionou GET");
		return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
	}
	
	@RequestMapping(value = "/home/seguraETraquila.html", method = RequestMethod.GET)
	public ModelAndView seguraETraquila(HttpServletRequest request) {
		LOG.debug("Iniciada a execucao do metodo: seguraETraquila GET");
		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		String idCarona = (String) request.getParameter("id");
		try{
			solicitaVagaBusiness.reviewCarona(sessao.getLogin(), idCarona, "segura e tranquila");
		}catch(Exception e){
			return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
		}
		LOG.debug("Finalizada a execucao do metodo: seguraETraquila GET");
		return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
	}
	
	@RequestMapping(value = "/home/faltou.html", method = RequestMethod.GET)
	public ModelAndView faltou(HttpServletRequest request) {
		LOG.debug("Iniciada a execucao do metodo: faltou GET");
		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		String idCarona = (String) request.getParameter("id");
		String loginCaroneiro = (String) request.getParameter("idCaroneiro");
		try{
			solicitaVagaBusiness.reviewVagaEmCarona(sessao.getLogin(), idCarona, loginCaroneiro, "faltou");
		}catch(Exception e){
			return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
		}
		LOG.debug("Finalizada a execucao do metodo: faltou GET");
		return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
	}
	
	@RequestMapping(value = "/home/participou.html", method = RequestMethod.GET)
	public ModelAndView participou(HttpServletRequest request) {
		LOG.debug("Iniciada a execucao do metodo: participou GET");
		SessaoDomain sessao = (SessaoDomain) request.getSession().getAttribute("sessao");
		if (sessao == null) {
			return new ModelAndView("redirect:/home/login.html");
		}
		
		String idCarona = (String) request.getParameter("id");
		String loginCaroneiro = (String) request.getParameter("idCaroneiro");
		try{
			solicitaVagaBusiness.reviewVagaEmCarona(sessao.getLogin(), idCarona, loginCaroneiro, "não faltou");
		}catch(Exception e){
			return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
		}
		LOG.debug("Finalizada a execucao do metodo: participou GET");
		return new ModelAndView("redirect:/home/carona.html?id="+idCarona);
	}
	
	private PesquisaCaronaViewModels getViewModel(CaronaDomain caronaDomain, SessaoDomain sessao) throws Exception{
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
		
		//Verifica se a solicitacao está na lista de caronas pendentes
		String idSolicitacao="";
		Boolean usuarioSolicitou = false;
		Boolean foiAceita = false;
		SolicitacaoVagaDomain solicitacaoVaga =  solicitaVagaBusiness.getSolicitacaoUsuario(sessao.getLogin(), caronaDomain.getId());
		if (solicitacaoVaga != null){
			usuarioSolicitou = true;
			foiAceita = solicitacaoVaga.getFoiAceita();
			idSolicitacao = solicitacaoVaga.getId();
		}
		modeloCarona.setIdSolicitacao(idSolicitacao);
		modeloCarona.setSolicitouVaga(usuarioSolicitou);
		modeloCarona.setVagaAceita(foiAceita);
		
		return modeloCarona;
	}
	
	
}
