package com.br.uepb.business;

import java.util.ArrayList;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.SolicitacaoVagaDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.PontoDeEncontroDomain;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class SolicitacaoVagaBusiness {

	int idSolicitacao = 1;
	
	public String getAtributoSolicitacao(String idSolicitacao, String atributo) throws Exception{
		
		if( (idSolicitacao == null) || (idSolicitacao.trim().equals(""))){
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INVALIDA);
		}
		
		if( (atributo == null) || (atributo.trim().equals(""))){
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INVALIDO);
		}
		
		SolicitacaoVagaDomain solicitacaoVaga = SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(idSolicitacao);
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(solicitacaoVaga.getIdCarona());
		
		if(atributo.equals("origem")){
			return carona.getOrigem();
		}else if(atributo.equals("destino")){
			return carona.getDestino();
		}else if(atributo.equals("Dono da carona")){	
			UsuarioDomain motorista = UsuarioDAOImpl.getInstance().getUsuario(carona.getIdSessao());	
			return motorista.getPerfil().getNome();
		}else if(atributo.equals("Dono da solicitacao")){
			UsuarioDomain caroneiro = UsuarioDAOImpl.getInstance().getUsuario(solicitacaoVaga.getIdUsuario());
			return ""+caroneiro.getPerfil().getNome();
		}else if(atributo.equals("Ponto de Encontro")){
			return solicitacaoVaga.getPonto().getPontoDeEncontro();
		}else {
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INEXISTENTE);
		}		 		
	}
		
	public ArrayList<SolicitacaoVagaDomain> getSolicitacoesConfirmadas(String idSessao, String idCarona) throws Exception{
		SessaoDomain sessao = SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		if (!carona.getIdSessao().equals(sessao.getLogin())) {
			throw new ProjetoCaronaException(MensagensErro.CARONA_NAO_IDENTIFICADA);
		}
		
		ArrayList<SolicitacaoVagaDomain> solicitacoes = SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesConfirmadas(idCarona);
		
		return solicitacoes; 
	}
	
	public ArrayList<SolicitacaoVagaDomain> getSolicitacoesPendentes(String idSessao, String idCarona) throws Exception{ 
		SessaoDomain sessao = SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		if (!carona.getIdSessao().equals(sessao.getLogin())) {
			throw new ProjetoCaronaException(MensagensErro.CARONA_NAO_IDENTIFICADA);
		}
		
		ArrayList<SolicitacaoVagaDomain> solicitacoes = SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesPendentes(idCarona);
		
		return solicitacoes; 
	}
	
	public int solicitarVaga(String idSessao, String idCarona) throws Exception{
		//solicitacao1ID=solicitarVaga idSessao=${sessaoBill} idCarona=${carona4ID}
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		//Procura a carona nas listas de Carona
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		if (carona.getVagas() == 0) {
			throw new ProjetoCaronaException(MensagensErro.VAGAS_OCUPADAS);
		}
		else {
			//Cria a solicitacao da vaga e adiciona na carona 
			SolicitacaoVagaDomain solicitacaoVaga = new SolicitacaoVagaDomain(idSolicitacao+"", idSessao, idCarona);
			SolicitacaoVagaDAOImpl.getInstance().addSolicitacaoVaga(solicitacaoVaga);			
			idSolicitacao++;
			int id = Integer.parseInt(solicitacaoVaga.getId());			
			return id;
		}		
	}
	
	public int solicitarVagaPontoEncontro(String idSessao, String idCarona, String ponto) throws Exception{		
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		//Procura a carona nas listas de Carona
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		if (carona.getVagas() == 0) {
			throw new ProjetoCaronaException(MensagensErro.VAGAS_OCUPADAS);
		}
		else {
			//Cria a solicitacao da vaga e adiciona na carona
			PontoDeEncontroDomain pontoEncontro = carona.getPontoEncontroByNome(ponto);		 
			SolicitacaoVagaDomain solicitacaoVaga = new SolicitacaoVagaDomain(idSolicitacao+"", idSessao, idCarona, pontoEncontro);
			SolicitacaoVagaDAOImpl.getInstance().addSolicitacaoVaga(solicitacaoVaga);			
			
			idSolicitacao++;
			int id = Integer.parseInt(solicitacaoVaga.getId());			
			return id;
		}
	}
	
	public void aceitarSolicitacao(String idSessao, String idSolicitacao) throws Exception{ 
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		SolicitacaoVagaDomain solicitacaoVaga = SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(idSolicitacao);
		
		
		if (!solicitacaoVaga.getFoiAceita() ){
			solicitacaoVaga.setFoiAceita(true);
			CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(solicitacaoVaga.getIdCarona());
			UsuarioDAOImpl.getInstance().getUsuario(idSessao).getPerfil().addHistoricoDeVagasEmCaronas(carona.getID());
			carona.diminuiVagas();			
		}
		else {			
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INEXISTENTE);			
		}	
	}
	
	public void aceitarSolicitacaoPontoEncontro(String idSessao, String idSolicitacao) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
				
		SolicitacaoVagaDomain solicitacaoVaga = SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(idSolicitacao);
		
		if (!solicitacaoVaga.getFoiAceita() ){ //TODO: trocar metodo de isFoiAceita para getFoiAceita
			solicitacaoVaga.setFoiAceita(true);
			CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(solicitacaoVaga.getIdCarona());
			carona.diminuiVagas();			
		}
		else {			
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INEXISTENTE);			
		}
		
	}
	
	public void desistirRequisicao(String idSessao, String idCarona, String idSolicitacao) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		SolicitacaoVagaDAOImpl.getInstance().deleteSolicitacaoVaga(idSolicitacao);
		
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		carona.aumentaVagas();		
	}

	public void rejeitarSolicitacao(String idSessao, String idSolicitacao) throws Exception{ 
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		SolicitacaoVagaDAOImpl.getInstance().deleteSolicitacaoVaga(idSolicitacao);		
	}
	

}
