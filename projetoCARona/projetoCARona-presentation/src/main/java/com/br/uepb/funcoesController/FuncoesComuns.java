package com.br.uepb.funcoesController;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.br.uepb.business.PontoDeEncontroBusiness;
import com.br.uepb.business.SolicitacaoVagaBusiness;
import com.br.uepb.controller.HomeController;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.PontoDeEncontroDomain;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;

@Component
public class FuncoesComuns {
	
	private static final Log LOG = LogFactory.getLog(HomeController.class);
	
	@Autowired
	private SolicitacaoVagaBusiness solicitacaoBusiness;
	@Autowired
	private PontoDeEncontroBusiness pontoEncontroBusiness;
	
	public boolean carregaDadosIniciais(ModelAndView modelAndView, SessaoDomain sessao){
		
		 modelAndView.addObject("nomeUsuario", sessao.getLogin());
	        
	     /* Lista todas as solicitacoes */
	     List<SolicitacaoVagaDomain> listaSolicitacoes = new ArrayList<SolicitacaoVagaDomain>();
	     List<PontoDeEncontroDomain> listaSugestoes = new ArrayList<PontoDeEncontroDomain>();
	     List<CaronaDomain> listaInteresses = new ArrayList<CaronaDomain>();
	     int totalNotificacoes = 0;
	     try {
	    	listaSolicitacoes = solicitacaoBusiness.getSolicitacoesPorUsuario(sessao.getLogin());
			listaSugestoes = pontoEncontroBusiness.getTodosPontosSugeridos(sessao.getLogin());
			listaInteresses = CaronaDAOImpl.getInstance().getCaronasByInteresses(sessao.getLogin());
				
			modelAndView.addObject("listaSolicitacoes", listaSolicitacoes);
			modelAndView.addObject("listaSugestoes", listaSugestoes);
			modelAndView.addObject("listaInteresses", listaInteresses);
				
			if (listaSolicitacoes != null ) {
				totalNotificacoes += listaSolicitacoes.size();
			}
			if (listaSugestoes != null) {
				totalNotificacoes += listaSugestoes.size();
			}
			if (listaInteresses != null) {
				totalNotificacoes += listaInteresses.size();
			}
		    modelAndView.addObject("totalNotificacoes", totalNotificacoes);
		       
		} catch (Exception e) {
			LOG.debug("Problemas ao tentar listar as solicitacoes do usuario - ERRO: "+e.getMessage());		
			return false;
		}
		
		return true;
	}
}
