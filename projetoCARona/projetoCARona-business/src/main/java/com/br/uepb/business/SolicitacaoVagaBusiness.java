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
	
	/**
	 * Classe para as regras de negócio referentes a solicitação de vaga
	 * @author Luana Janaina / Lukas Teles
	 * @version 0.1
	 * @since 20/04/2015
	 */

	/**
	 * Método para retornar os dados referentes a solicitação da vaga
	 * @param idSolicitacao String - Id da solicitação
	 * @param atributo String - Tipo do dado da solicitação a ser retornado
	 * @return String - Dado da solicitação
	 * @throws Exception - Lança exceção se qualquer atributo informado for null, vazio ou se a solicitação ou o atributo não existir 
	 */
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
		
	/**
	 * Método para retornar uma lista das solicitações de vaga confirmadas para a carona
	 * @param idSessao String - Id da sessão
	 * @param idCarona String Id da carona
	 * @return ArrayList<SolicitacaoVagaDomain> - Lista de solicitações de vaga confirmadas
	 * @throws Exception - Lança exceção se qualquer atributo informado for null, vazio ou se a carona não pertencer ao usuário sa sessão informada
	 */
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
	
	/**
	 * Método para retornar uma lista das solicitacao de vaga que estão pendentes para a carona
	 * @param idSessao String - Id da sessão
	 * @param idCarona String Id da carona
	 * @return ArrayList<SolicitacaoVagaDomain> - Lista de solicitações de vaga pendentes
	 * @throws Exception - Lança exceção se qualquer atributo informado for null, vazio ou se a carona não pertencer ao usuário da sessão informada
	 */
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
	
	/**
	 * Método para solicitar uma vaga na carona informada
	 * @param idSessao String - Id da sessão
	 * @param idCarona String Id da carona
	 * @return int - Id da solicitação
	 * @throws Exception - Lança exceção se qualquer atributo informado for null, vazio ou se a carona não pertencer ao usuário da sessão informada
	 */
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
			SolicitacaoVagaDomain solicitacaoVaga = new SolicitacaoVagaDomain(SolicitacaoVagaDAOImpl.getInstance().idSolicitacao+"", idSessao, idCarona);
			SolicitacaoVagaDAOImpl.getInstance().addSolicitacaoVaga(solicitacaoVaga);			
			SolicitacaoVagaDAOImpl.getInstance().idSolicitacao++;
			int id = Integer.parseInt(solicitacaoVaga.getId());			
			return id;
		}		
	}
	
	/**
	 * Método para solicitar uma vaga informando um ponto de encontro. 
	 * Se o ponto de encontro não existir na lista de sugestão de pontos de encontro este será adicionado
	 * @param idSessao String - Id da sessão
	 * @param idCarona String Id da carona
	 * @return int - Id da solicitação
	 * @throws Exception - Lança exceção se qualquer atributo informado for null, vazio ou se a carona não pertencer ao usuário da sessão informada
	 */
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
			PontoDeEncontroDomain pontoEncontro;
			if (carona.pontoExiste(ponto)) {
				pontoEncontro = carona.getPontoEncontroByNome(ponto);
			} else {
				//Se o pontoEncontro não existir cria uma nova sugestao
				String idSugestao = ""+ CaronaDAOImpl.getInstance().idPontoEncontro;
				CaronaDAOImpl.getInstance().idPontoEncontro++;
				pontoEncontro = new PontoDeEncontroDomain(idSugestao, ponto);
				CaronaDAOImpl.getInstance().getCarona(idCarona).addPontoDeEncontro(pontoEncontro);
			}
			
			SolicitacaoVagaDomain solicitacaoVaga = new SolicitacaoVagaDomain(SolicitacaoVagaDAOImpl.getInstance().idSolicitacao+"", idSessao, idCarona, pontoEncontro);
			SolicitacaoVagaDAOImpl.getInstance().addSolicitacaoVaga(solicitacaoVaga);			
			
			SolicitacaoVagaDAOImpl.getInstance().idSolicitacao++;
			int id = Integer.parseInt(solicitacaoVaga.getId());			
			return id;
		}
	}
	
	/**
	 * Método para aceitar uma solicitacaod e vaga para a carona. 
	 * Ao aceitar uma solicitacao o sistema está confirmando a vaga na carona informada na solicitacao
	 * @param idSessao String - Id da sessão
	 * @param idSolicitacao String - Id da solicitação
	 * @throws Exception - Lança exceção se qualquer atributo informado for null, vazio ou se a solicitacao não pertencer ao usuário da sessão informada
	 */	
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
	
	/**
	 * Método para aceitar a solicitacao de vaga que já tem um ponto de encontro adicionado
	 * Ao aceitar uma solicitacao o sistema está confirmando a vaga na carona e ainda aceitando o Ponto de Encontro informado na solicitacao
	 * @param idSessao String - Id da sessão
	 * @param idSolicitacao String - Id da solicitação
	 * @throws Exception - Lança exceção se qualquer atributo informado for null, vazio ou se a solicitacao não pertencer ao usuário da sessão informada
	 */
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
	
	/**
	 * Método para informar a desistencia da solicitação de vaga na carona
	 * @param idSessao String - Id da sessão
	 * @param idCarona String - Id da carona
	 * @param idSolicitacao String - Id da solicitacao
	 * @throws Exception - Lança exceção se qualquer atributo informado for null, vazio ou inexistente ou se a solicitacao não pertencer ao usuário da sessão informada
	 */
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

	/**
	 * Método para rejeitar a solicitação de uma vaga na carona 
	 * @param idSessao String - Id da sessão
	 * @param idSolicitacao String - Id da solicitação
	 * @throws Exception - Lança exceção se qualquer atributo informado for null, vazio ou inexistente ou se a a carona relacionada a solicitacao não pertencer ao usuário da sessão informada
	 */
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
