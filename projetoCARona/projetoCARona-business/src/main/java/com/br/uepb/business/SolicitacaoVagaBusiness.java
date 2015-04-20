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
		
		//Metodo para garantir que o usuario informado na sessao é o dono da carona
		if (!carona.getIdSessao().equals(sessao.getLogin())) {
			throw new ProjetoCaronaException(MensagensErro.CARONA_NAO_IDENTIFICADA);
		}
		
		ArrayList<SolicitacaoVagaDomain> solicitacoes = SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesConfirmadas(idCarona);
		
		return solicitacoes; 
	}
	
	public ArrayList<SolicitacaoVagaDomain> getSolicitacoesPendentes(String idSessao, String idCarona) throws Exception{ 
		SessaoDomain sessao = SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		//Metodo para garantir que o usuario informado na sessao é o dono da carona
		if (!carona.getIdSessao().equals(sessao.getLogin())) {
			throw new ProjetoCaronaException(MensagensErro.CARONA_NAO_IDENTIFICADA);
		}
		
		ArrayList<SolicitacaoVagaDomain> solicitacoes = SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesPendentes(idCarona);
		
		return solicitacoes; 
	}
	
	public int solicitarVaga(String idSessao, String idCarona) throws Exception{		
		SessaoDomain sessao = SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		//garantir que um usuario não pode solicitar vaga na sua própria carona
		if (carona.getIdSessao().equals(sessao.getLogin())) {
			throw new ProjetoCaronaException(MensagensErro.CARONA_NAO_IDENTIFICADA);
		}
		
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
		SessaoDomain sessao = SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		//Metodo para garantir que um usuario não pode solicitar vaga na sua própria carona
		if (carona.getIdSessao().equals(sessao.getLogin())) {
			throw new ProjetoCaronaException(MensagensErro.CARONA_NAO_IDENTIFICADA);
		}
		
		//Verifica se a carona tem vagas disponíveis
		if (carona.getVagas() == 0) {
			throw new ProjetoCaronaException(MensagensErro.VAGAS_OCUPADAS);
		}
		else {
			//TODO: criar metodo para sugerir ponto encontro
			//Cria a solicitacao da vaga e adiciona na carona
			PontoDeEncontroDomain pontoEncontro = carona.getPontoEncontroByNome(ponto);		 
			SolicitacaoVagaDomain solicitacaoVaga = new SolicitacaoVagaDomain(idSolicitacao+"", idSessao, idCarona, pontoEncontro);
			SolicitacaoVagaDAOImpl.getInstance().addSolicitacaoVaga(solicitacaoVaga);			
			
			idSolicitacao++;
			int id = Integer.parseInt(solicitacaoVaga.getId());			
			return id;
		}
	}
	
	/* Ao aceitar uma solicitacao o sistema está confirmando a vaga na carona informada na solicitacao */
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
	
	/* Ao aceitar uma solicitacao o sistema está confirmando a vaga na carona e 
	 * ainda aceitando o Ponto de Encontro informado na solicitacao  */
	public void aceitarSolicitacaoPontoEncontro(String idSessao, String idSolicitacao) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		SolicitacaoVagaDomain solicitacaoVaga = SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(idSolicitacao);
		
		//Garantir que o id do usuario que criou a carona é igual ao id da sessao
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(solicitacaoVaga.getIdCarona()); 
		if (!carona.getIdSessao().equals(idSessao)) {
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INVALIDA);				
		}
		
		//aceita a vaga na carona
		if (!solicitacaoVaga.getFoiAceita() ){ 
			solicitacaoVaga.setFoiAceita(true);			
			carona.diminuiVagas();
			
			//aceita o ponto de encontro para a carona
			PontoDeEncontroDomain pontoEncontro =  solicitacaoVaga.getPonto();
			if (!pontoEncontro.getFoiAceita()) {
				pontoEncontro.setFoiAceita(true);
			}
		}
		else {			
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INEXISTENTE);			
		}
		
	}
	
	public void desistirRequisicao(String idSessao, String idCarona, String idSolicitacao) throws Exception{
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		/* Garantir que o id do usuario que solicitou é igual ao id da sessao
		  Se não a solicitacao nao pertencer ao usuario retorna solicitacao invalida */ 
		SolicitacaoVagaDomain solicitacao = SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(idSolicitacao);
		if (!solicitacao.getIdUsuario().equals(idSessao)) {
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INVALIDA);				
		}
		
		SolicitacaoVagaDAOImpl.getInstance().deleteSolicitacaoVaga(idSolicitacao);
		
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		carona.aumentaVagas();		
	}

	public void rejeitarSolicitacao(String idSessao, String idSolicitacao) throws Exception{ 
		SessaoDAOImpl.getInstance().getSessao(idSessao);		

		/* Garantir que o id do usuario que criou a carona é igual ao id da sessao
		  Se a solicitacao nao pertencer ao usuario retorna solicitacao invalida */
		SolicitacaoVagaDomain solicitacao = SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(idSolicitacao);
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(solicitacao.getIdCarona()); 
		if (!carona.getIdSessao().equals(idSessao)) {
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INVALIDA);				
		}
		
		SolicitacaoVagaDAOImpl.getInstance().deleteSolicitacaoVaga(idSolicitacao);		
	}
	

}
