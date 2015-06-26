package com.br.uepb.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.PontoDeEncontroDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.SolicitacaoVagaDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.PontoDeEncontroDomain;
import com.br.uepb.domain.SessaoDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

/**
 * Classe para as regras de negócio referentes a solicitação de vaga
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
@Component
public class SolicitacaoVagaBusiness {

	final static Logger logger = Logger.getLogger(SolicitacaoVagaBusiness.class);
	
	/**
	 * Método para retornar os dados referentes a solicitação da vaga
	 * @param idSolicitacao Id da solicitação
	 * @param atributo Tipo do dado da solicitação a ser retornado
	 * @return Dado da solicitação
	 * @throws Exception Lança exceção se qualquer atributo informado for null, vazio ou se a solicitação ou o atributo não existir 
	 */
	public String getAtributoSolicitacao(String idSolicitacao, String atributo) throws Exception{
		logger.debug("buscando atributo da solicitação");
		
		if( (idSolicitacao == null) || (idSolicitacao.trim().equals(""))){
			logger.debug("getAtributoSolicitacao() Exceção: "+MensagensErro.SOLICITACAO_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INVALIDA);
		}
		
		if( (atributo == null) || (atributo.trim().equals(""))){
			logger.debug("getAtributoSolicitacao() Exceção: "+MensagensErro.ATRIBUTO_INVALIDO);
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
			logger.debug("getAtributoSolicitacao() Exceção: "+MensagensErro.ATRIBUTO_INEXISTENTE);
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INEXISTENTE);
		}		 		
	}
		
	/**
	 * Método para retornar uma lista das solicitações de vaga confirmadas para a carona
	 * @param idSessao Id da sessão
	 * @param idCarona Id da carona
	 * @return Lista de solicitações de vaga confirmadas
	 * @throws Exception Lança exceção se qualquer atributo informado for null, vazio ou se a carona não pertencer ao usuário sa sessão informada
	 */
	public ArrayList<SolicitacaoVagaDomain> getSolicitacoesConfirmadas(String idSessao, String idCarona) throws Exception{
		logger.debug("buscando solicitacões comfirmadas");
		
		
		ArrayList<SolicitacaoVagaDomain> solicitacoes = SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesConfirmadas(idCarona);
		logger.debug("solicitacões comfirmadas encontradas");
		return solicitacoes; 
	}
	
	/**
	 * Método para retornar uma lista das solicitacao de vaga que estão pendentes para a carona
	 * @param idSessao Id da sessão
	 * @param idCarona Id da carona
	 * @return Lista de solicitações de vaga pendentes
	 * @throws Exception Lança exceção se qualquer atributo informado for null, vazio ou se a carona não pertencer ao usuário da sessão informada
	 */
	public ArrayList<SolicitacaoVagaDomain> getSolicitacoesPendentes(String idSessao, String idCarona) throws Exception{ 
		logger.debug("buscando solicitacões pendentes");
		SessaoDomain sessao = SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		//Metodo para garantir que o usuario informado na sessao é o dono da carona
		if (!carona.getIdSessao().equals(sessao.getLogin())) {
			logger.debug("getSolicitacoesPendentes() Exceção: "+MensagensErro.CARONA_NAO_IDENTIFICADA);
			throw new ProjetoCaronaException(MensagensErro.CARONA_NAO_IDENTIFICADA);
		}
		
		ArrayList<SolicitacaoVagaDomain> solicitacoes = SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesPendentes(idCarona);
		logger.debug("solicitacões pendentes encontradas");
		return solicitacoes; 
	}
	
	/**
	 * Metodo para retornar uma lista de solicitacao de vaga (pendentes e confirmadas) de um usuario para a carona
	 * @param idSessao Id da sessao
	 * @param idCarona Id da carona
	 * @return Lista de solicitacoes de vaga pendentes
	 * @throws Exception Lança exceção se qualquer atributo informado for null, vazio ou se a carona não pertencer ao usuário da sessão informada
	 */
	public SolicitacaoVagaDomain getSolicitacaoUsuario(String idSessao, String idCarona) throws Exception{
		logger.debug("buscando solicitacao do usuario");
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		SolicitacaoVagaDomain solicitacaoVaga = SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoUsuario(idSessao, idCarona);
		logger.debug("solicitacao do usuario encontrada");
		return solicitacaoVaga; 		
	}
	
	/**
	 * Metodo para retornar uma lista solicitacoes para um usuario (dono da carona)
	 * @param idSessao Id da sessao
	 * @return Lista das solicitacoes do usuario
	 * @throws Exception
	 */
	public List<SolicitacaoVagaDomain> getSolicitacoesPorUsuario(String idSessao) throws Exception{
		logger.debug("buscando solicitacao do usuario");
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		List<SolicitacaoVagaDomain> solicitacoes = SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesPendentesPorUsuario(idSessao);
		logger.debug("solicitacoes do usuario encontradas");
		return solicitacoes;
	}
	
	/**
	 * Método para solicitar uma vaga na carona informada
	 * @param idSessao Id da sessão
	 * @param idCarona Id da carona
	 * @return Id da solicitação
	 * @throws Exception Lança exceção se qualquer atributo informado for null, vazio ou se a carona não pertencer ao usuário da sessão informada
	 */
	public int solicitarVaga(String idSessao, String idCarona) throws Exception{		
		logger.debug("solicitando vaga numa carona");
		SessaoDomain sessao = SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		if(carona.isPreferencial() == true && !isPreferencial(idSessao, carona)){
			throw new ProjetoCaronaException(MensagensErro.USUARIO_NAO_PREFERENCIAL);
		}
		
		//garantir que um usuario não pode solicitar vaga na sua própria carona
		if (carona.getIdSessao().equals(sessao.getLogin())) {
			logger.debug("solicitarVaga() Exceção: "+MensagensErro.CARONA_NAO_IDENTIFICADA);
			throw new ProjetoCaronaException(MensagensErro.CARONA_NAO_IDENTIFICADA);
		}
		
		logger.debug("verificando se há vagas disponíveis");
		if (carona.getVagas() == 0) {
			logger.debug("solicitarVaga() Exceção: "+MensagensErro.VAGAS_OCUPADAS);
			throw new ProjetoCaronaException(MensagensErro.VAGAS_OCUPADAS);
		}
		else {
			logger.debug("criando solicitando de vaga");
			//Cria a solicitacao da vaga e adiciona na carona 
			SolicitacaoVagaDomain solicitacaoVaga = new SolicitacaoVagaDomain(SolicitacaoVagaDAOImpl.getInstance().getIdSolicitacao()+"", idSessao, idCarona);
			logger.debug("solicitação de vaga criada");
			SolicitacaoVagaDAOImpl.getInstance().addSolicitacaoVaga(solicitacaoVaga);
			logger.debug("solicitação de vaga adicionada na carona");
			int id = Integer.parseInt(solicitacaoVaga.getId());			
			return id;
		}		
	}
	
	/**
	 * Metodo para verificar se determinado usuario esta classificado como preferencial para a carona informada
	 * @param login Login do usuario
	 * @param carona Carona
	 * @return Retorna true se o usuario tiver classificado a carona como "segura e tranquila" ou false caso contrario
	 * @throws Exception Lança exceção se qualquer atributo informado for null, vazio ou se o usuario nao pertencer a carona
	 */
	public boolean isPreferencial(String login, CaronaDomain carona) throws Exception{
		//pega todas as caronas
		List<CaronaDomain> caronas = CaronaDAOImpl.getInstance().getHistoricoDeCaronas(carona.getIdSessao());
		
		//remove a carona preferencial da lista de caronas
		for (int i = 0; i < caronas.size(); i++) {
			if(caronas.get(i).getId().equals(carona.getId())){
				caronas.remove(i);
			}
		}
		
		//verifica os que avaliaram bem as outras caronas
		for (CaronaDomain caronaDomain : caronas) {
			List<SolicitacaoVagaDomain> vagasCarona = SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesConfirmadas(caronaDomain.getId());
			for (SolicitacaoVagaDomain vaga : vagasCarona) {
				if(vaga.getReviewCarona() != null){
					if(vaga.getReviewCarona().equals("segura e tranquila") && vaga.getIdUsuario().equals(login)){
						return true;
					}
				}
			}
		}			
		return false;
	}
	
	/**
	 * Método para solicitar uma vaga informando um ponto de encontro. 
	 * Se o ponto de encontro não existir na lista de sugestão de pontos de encontro este será adicionado
	 * @param idSessao Id da sessão
	 * @param idCarona Id da carona
	 * @param ponto Local do ponto de encontro
	 * @return Id da solicitação
	 * @throws Exception Lança exceção se qualquer atributo informado for null, vazio ou se a carona não pertencer ao usuário da sessão informada
	 */
	public int solicitarVagaPontoEncontro(String idSessao, String idCarona, String ponto) throws Exception{		
		logger.debug("solicitando vaga numa carona informando o ponto "+ponto);
		SessaoDomain sessao = SessaoDAOImpl.getInstance().getSessao(idSessao);
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		//Metodo para garantir que um usuario não pode solicitar vaga na sua própria carona
		if (carona.getIdSessao().equals(sessao.getLogin())) {
			logger.debug("solicitarVagaPontoEncontro() Exceção: "+MensagensErro.CARONA_NAO_IDENTIFICADA);
			throw new ProjetoCaronaException(MensagensErro.CARONA_NAO_IDENTIFICADA);
		}
		
		logger.debug("verificando se há vagas disponíveis");
		//Verifica se a carona tem vagas disponíveis
		if (carona.getVagas() == 0) {
			logger.debug("solicitarVagaPontoEncontro() Exceção: "+MensagensErro.VAGAS_OCUPADAS);
			throw new ProjetoCaronaException(MensagensErro.VAGAS_OCUPADAS);
		}
		else {
			//Cria a solicitacao da vaga e adiciona na carona
			logger.debug("Criando ponto "+ponto);
			PontoDeEncontroDomain pontoEncontro;
			if (PontoDeEncontroDAOImpl.getInstance().pontoExiste(idCarona, ponto)) {
				pontoEncontro = PontoDeEncontroDAOImpl.getInstance().getPontoEncontroByNome(idCarona, ponto);
			} else {
				//Se o pontoEncontro não existir cria uma nova sugestao
				String idSugestao = ""+ CaronaDAOImpl.getInstance().getIdPontoEncontro();
				pontoEncontro = new PontoDeEncontroDomain(idSessao, idCarona, idSugestao, ponto);
				PontoDeEncontroDAOImpl.getInstance().addPontoDeEncontro(pontoEncontro);
				logger.debug("ponto "+ponto+" criado");
			}
			
			SolicitacaoVagaDomain solicitacaoVaga = new SolicitacaoVagaDomain(SolicitacaoVagaDAOImpl.getInstance().getIdSolicitacao()+"", idSessao, idCarona, pontoEncontro);
			SolicitacaoVagaDAOImpl.getInstance().addSolicitacaoVaga(solicitacaoVaga);			
			logger.debug("solicitacao de vaga realizada");
			int id = Integer.parseInt(solicitacaoVaga.getId());			
			return id;
		}
	}
	
	/**
	 * Método para aceitar uma solicitacaod e vaga para a carona. 
	 * Ao aceitar uma solicitacao o sistema está confirmando a vaga na carona informada na solicitacao
	 * @param idSessao Id da sessão
	 * @param idSolicitacao Id da solicitação
	 * @throws Exception Lança exceção se qualquer atributo informado for null, vazio ou se a solicitacao não pertencer ao usuário da sessão informada
	 */	
	public void aceitarSolicitacao(String idSessao, String idSolicitacao) throws Exception{ 
		logger.debug("aceitando solicitação de vaga numa carona");
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		SolicitacaoVagaDomain solicitacaoVaga = SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(idSolicitacao);
		
		if (!solicitacaoVaga.getFoiAceita() ){
			solicitacaoVaga.setFoiAceita(true);
			SolicitacaoVagaDAOImpl.getInstance().atualizaSolicitacaoVaga(solicitacaoVaga);//atualiza solicitacao
			
			CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(solicitacaoVaga.getIdCarona());
			carona.diminuiVagas();
			CaronaDAOImpl.getInstance().atualizaCarona(carona);//atualiza carona
			
			logger.debug("solicitação aceita");
		}
		else {	
			logger.debug("aceitarSolicitacao() Exceção: "+MensagensErro.SOLICITACAO_INEXISTENTE);
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INEXISTENTE);			
		}	
	}
	
	/**
	 * Método para aceitar a solicitacao de vaga que já tem um ponto de encontro adicionado
	 * Ao aceitar uma solicitacao o sistema está confirmando a vaga na carona e ainda aceitando o Ponto de Encontro informado na solicitacao
	 * @param idSessao Id da sessão
	 * @param idSolicitacao Id da solicitação
	 * @throws Exception Lança exceção se qualquer atributo informado for null, vazio ou se a solicitacao não pertencer ao usuário da sessão informada
	 */
	public void aceitarSolicitacaoPontoEncontro(String idSessao, String idSolicitacao) throws Exception{
		logger.debug("aceitando solicitação de vaga numa carona que já tem um ponto de encontro adicionado");
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		SolicitacaoVagaDomain solicitacaoVaga = SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(idSolicitacao);
		
		//Garantir que o id do usuario que criou a carona é igual ao id da sessao
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(solicitacaoVaga.getIdCarona()); 
		if (!carona.getIdSessao().equals(idSessao)) {
			logger.debug("aceitarSolicitacaoPontoEncontro() Exceção: "+MensagensErro.SOLICITACAO_INEXISTENTE);
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INEXISTENTE);				
		}
		
		//aceita a vaga na carona
		if (!solicitacaoVaga.getFoiAceita() ){ 
			solicitacaoVaga.setFoiAceita(true);			
			SolicitacaoVagaDAOImpl.getInstance().atualizaSolicitacaoVaga(solicitacaoVaga);//atualiza solicitacao
			
			//coloca no historico da pessoa que solicitou a vaga na carona
			carona.diminuiVagas();
			CaronaDAOImpl.getInstance().atualizaCarona(carona);//atualiza carona
			
			logger.debug("solicitação aceita");
			
			//aceita o ponto de encontro para a carona
			PontoDeEncontroDomain pontoEncontro =  solicitacaoVaga.getPonto();
			if (!pontoEncontro.getFoiAceita()) {
				pontoEncontro.setFoiAceita(true);
				logger.debug("ponto de encontro aceito");
			}
		}
		else {	
			logger.debug("aceitarSolicitacaoPontoEncontro() Exceção: "+MensagensErro.SOLICITACAO_INEXISTENTE);
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INEXISTENTE);			
		}
		
	}
	
	/**
	 * Desfazendo uma solicitaçao aceita
	 * @param idSessao Login do motorias da carona solicitada
	 * @param idSolicitacao Id da Solicitacao
	 * @throws Exception 
	 */
	public void desfazerSolicitacaoAceita(String idSessao, String idSolicitacao) throws Exception {
		logger.debug("rejeitando uma solicitação de vaga");
		SessaoDAOImpl.getInstance().getSessao(idSessao);		

		/* Garantir que o id do usuario que criou a carona é igual ao id da sessao
		  Se a solicitacao nao pertencer ao usuario retorna solicitacao invalida */
		SolicitacaoVagaDomain solicitacao = SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(idSolicitacao);
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(solicitacao.getIdCarona()); 
		if (!carona.getIdSessao().equals(idSessao)) {
			logger.debug("rejeitarSolicitacao() Exceção: "+MensagensErro.SOLICITACAO_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INVALIDA);				
		}
		
		if (solicitacao.getFoiAceita() ){
			solicitacao.setFoiAceita(false);
			SolicitacaoVagaDAOImpl.getInstance().atualizaSolicitacaoVaga(solicitacao);//atualiza solicitacao
			
			carona.aumentaVagas();
			CaronaDAOImpl.getInstance().atualizaCarona(carona);//atualiza carona
			
			logger.debug("solicitação aceita");
		}
		else {	
			logger.debug("aceitarSolicitacao() Exceção: "+MensagensErro.SOLICITACAO_INEXISTENTE);
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INEXISTENTE);			
		}
	}
	
	/**
	 * Método para informar a desistencia da solicitação de vaga na carona
	 * @param idSessao Id da sessão
	 * @param idCarona Id da carona
	 * @param idSolicitacao Id da solicitacao
	 * @throws Exception Lança exceção se qualquer atributo informado for null, vazio ou inexistente ou se a solicitacao não pertencer ao usuário da sessão informada
	 */
	public void desistirRequisicao(String idSessao, String idCarona, String idSolicitacao) throws Exception{
		logger.debug("desistindo de uma requisicao de vaga numa carona");
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		/* Garantir que o id do usuario que solicitou é igual ao id da sessao
		  Se não a solicitacao nao pertencer ao usuario retorna solicitacao invalida */ 
		SolicitacaoVagaDomain solicitacao = SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(idSolicitacao);
		if (!solicitacao.getIdUsuario().equals(idSessao)) {
			logger.debug("desistirRequisicao() Exceção: "+MensagensErro.SOLICITACAO_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INVALIDA);				
		}
		
		SolicitacaoVagaDAOImpl.getInstance().deleteSolicitacaoVaga(idSolicitacao);
		
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		carona.aumentaVagas();
		CaronaDAOImpl.getInstance().atualizaCarona(carona);//atualiza carona
		
		logger.debug("solicitação de vaga cancelada");
	}

	/**
	 * Método para rejeitar a solicitação de uma vaga na carona 
	 * @param idSessao Id da sessão
	 * @param idSolicitacao Id da solicitação
	 * @throws Exception Lança exceção se qualquer atributo informado for null, vazio ou inexistente ou se a a carona relacionada a solicitacao não pertencer ao usuário da sessão informada
	 */
	public void rejeitarSolicitacao(String idSessao, String idSolicitacao) throws Exception{ 
		logger.debug("rejeitando uma solicitação de vaga");
		SessaoDAOImpl.getInstance().getSessao(idSessao);		

		/* Garantir que o id do usuario que criou a carona é igual ao id da sessao
		  Se a solicitacao nao pertencer ao usuario retorna solicitacao invalida */
		SolicitacaoVagaDomain solicitacao = SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(idSolicitacao);
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(solicitacao.getIdCarona()); 
		if (!carona.getIdSessao().equals(idSessao)) {
			logger.debug("rejeitarSolicitacao() Exceção: "+MensagensErro.SOLICITACAO_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INVALIDA);				
		}
		SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(idSolicitacao);
		
		SolicitacaoVagaDAOImpl.getInstance().deleteSolicitacaoVaga(idSolicitacao);
		logger.debug("solicitação de vaga rejeitada");
	}
	
	/**
	 * Metodo para o motorista adicionar uma avaliacao da presenca ou nao do caroneiro na carona. 
	 * As avaliacoes possiveis sao: "faltou" e "nao faltou" 
	 * @param idSessao Id da sessao
	 * @param idCarona Id da carona
	 * @sparam loginCaroneiro Id do caroneiro
	 * @param review Avaliacao da presenca do caroneiro na carona
	 * @throws Exception Lança exceção se qualquer atributo informado for null, vazio ou inexistente, 
	 * se a carona relacionada a solicitacao não pertencer ao usuário da sessão informada ou 
	 * se o review informado for invalido
	 */
	public void reviewVagaEmCarona(String idSessao, String idCarona, String loginCaroneiro, String review) throws Exception{
		logger.debug("realizando review na vaga");
		//Verificar se os parametros sao validos
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		logger.debug("buscando caorna");
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		logger.debug("carona encontrada");
		if (!carona.getIdSessao().equals(idSessao)) {
			logger.debug("rejeitarSolicitacao() Exceção: "+MensagensErro.SOLICITACAO_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.SOLICITACAO_INVALIDA);				
		}
		UsuarioDAOImpl.getInstance().getUsuario(loginCaroneiro);

		//verificar se usuario pertence a esta carona
		if (SolicitacaoVagaDAOImpl.getInstance().participouCarona(idCarona, loginCaroneiro) == false) {
			throw new ProjetoCaronaException(MensagensErro.USUARIO_SEM_VAGA_NA_CARONA);
		}
		
		if ((!review.equals(MensagensErro.FALTOU)) && (!review.equals(MensagensErro.NAO_FALTOU))) {
			throw new ProjetoCaronaException(MensagensErro.OPCAO_INVALIDA);
		}
		
		SolicitacaoVagaDomain solicitacaoVaga = SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(idCarona, loginCaroneiro);
		solicitacaoVaga.setReviewVaga(review);
		SolicitacaoVagaDAOImpl.getInstance().atualizaSolicitacaoVaga(solicitacaoVaga);
		logger.debug("review realizado");
	}
	
	/**
	 * Metodo para o caroneiro adicionar uma avaliacao sobre a carona. 
	 * As avaliacoes possiveis sao: "Segura e Tranquila" e "nao funcionou" 
	 * @param idSessao Id da sessao
	 * @param idCarona Id da carona
	 * @param review Avaliacao da carona
	 * @throws Exception Lanca excecao se qualquer atributo informado for null, vazio ou inexistente
	 * se a a carona relacionada a solicitacao não pertencer ao usuário da sessão informada ou
	 * se o review informado for invalido
	 */
	public void reviewCarona(String idSessao, String idCarona, String review) throws Exception{
		logger.debug("realizando review na carona");
		//Verificar se os parametros sao validos
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		logger.debug("buscando carona");
		CaronaDAOImpl.getInstance().getCarona(idCarona);
		logger.debug("carona encontrada");
		
		//verificar se usuario pertence a esta carona
		if (SolicitacaoVagaDAOImpl.getInstance().participouCarona(idCarona, idSessao) == false) {
			throw new ProjetoCaronaException(MensagensErro.USUARIO_SEM_VAGA_NA_CARONA);
		}
			
		if ((!review.equals(MensagensErro.SEGURA_TRANQUILA)) && (!review.equals(MensagensErro.NAO_FUNCIONOU))) {
			throw new ProjetoCaronaException(MensagensErro.OPCAO_INVALIDA);
		}
		
		SolicitacaoVagaDomain solicitacaoVaga = SolicitacaoVagaDAOImpl.getInstance().getSolicitacaoVaga(idCarona, idSessao);
		solicitacaoVaga.setReviewCarona(review);
		SolicitacaoVagaDAOImpl.getInstance().atualizaSolicitacaoVaga(solicitacaoVaga);
		logger.debug("review realizado");
	}

}
