package com.br.uepb.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.SolicitacaoVagaDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;
import com.br.uepb.validator.ValidarCampos;
import com.uepb.email.EmailPadrao;

/**
 * Classe para as regras de negocio referentes a carona
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */
@Component
public class CaronaBusiness {
	
	final static Logger logger = Logger.getLogger(CaronaBusiness.class);
	
	/**
	 * Metodo para localizar todas as caronas informadas de uma determinada origem para um destino
	 * Observações:
	 * - Se o parametro origem ira for informado, ira localizar todas as caronas pertencentes ao destino informado
	 * - Se o parametro destino ira for informado, ira localizar todas as caronas pertencentes a origem informada
	 * - Se ira for informado nem origem nem destino da carona, ira localizar todas as caronas, sem excecao
	 * @param idSessao Id da sessao atual
	 * @param origem Local de origem da carona
	 * @param destino Local de destino da carona
	 * @return Lista das caronas localizadas
	 * @throws Exception Lança exceção se qualquer parametro informado for null ou vazio ou se a sessao for invalida
	 */
	public List<CaronaDomain> localizarCarona(String idSessao, String origem, String destino) throws Exception{
		logger.debug("localizando carona para origem: "+origem+" e destino: "+destino);
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		List<CaronaDomain> caronas;

		if (verificaCaracteres(origem) == false){
			logger.debug("localizarCarona() Exceção: "+MensagensErro.ORIGEM_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.ORIGEM_INVALIDA);
		}

		if (verificaCaracteres(destino) == false){
			logger.debug("localizarCarona() Exceção: "+MensagensErro.DESTINO_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.DESTINO_INVALIDO);
		}
				
		if ( (origem.trim().equals("")) && (destino.trim().equals("")) ) {
			caronas = CaronaDAOImpl.getInstance().listCaronas();
		}
		else if (origem.trim().equals(""))  {
			caronas = CaronaDAOImpl.getInstance().listCaronasByDestino(destino);
			
		}
		else if (destino.trim().equals("")){
			caronas = CaronaDAOImpl.getInstance().listCaronasByOrigem(origem);
			
		}
		else {
			caronas = CaronaDAOImpl.getInstance().listCaronas(origem, destino);
		}
		logger.debug("retornando caronas encontradas");
		return caronas;
	}
	
	/**
	 * Metodo para localizar todas as caronas informadas de uma determinada origem para um destino
	 * Observações:
	 * - Se o parametro origem ira for informado, ira localizar todas as caronas pertencentes ao destino informado
	 * - Se o parametro destino ira for informado, ira localizar todas as caronas pertencentes a origem informada
	 * - Se ira for informado nem origem nem destino da carona, ira localizar todas as caronas, sem excecao
	 * @param idSessao Id da sessão atual
	 * @param cidade Cidade onde acontecera a carona municipal
	 * @param origem Local de origem da carona
	 * @param destino Local de destino da carona
	 * @return Lista das caronas localizadas
	 * @throws Exception Lança excecao se qualquer parametro informado for null ou vazio ou se a sessao for invalida
	 */
	public List<CaronaDomain> localizarCaronaMunicipal(String idSessao, String cidade, String origem, String destino) throws Exception{
		logger.debug("localizando carona para origem: "+origem+" e destino: "+destino);
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		List<CaronaDomain> caronas;

		if (verificaCaracteres(origem) == false){
			logger.debug("localizarCarona() Exceção: "+MensagensErro.ORIGEM_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.ORIGEM_INVALIDA);
		}

		if (verificaCaracteres(destino) == false){
			logger.debug("localizarCarona() Exceção: "+MensagensErro.DESTINO_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.DESTINO_INVALIDO);
		}
		if(origem == "" && destino == ""){
			caronas = CaronaDAOImpl.getInstance().listCaronasMunicipais(cidade);
		}else{
			caronas = CaronaDAOImpl.getInstance().listCaronasMunicipais(cidade, origem, destino);
		}
		
		if(caronas.isEmpty()){
			throw new ProjetoCaronaException(MensagensErro.CIDADE_INEXISTENTE);
		}
		logger.debug("retornando caronas encontradas");
		return caronas;
	}
	/**
	 * Metodo para cadatrar a carona que um usuario esta oferecendo
	 * @param idSessao Id da sessão atual
	 * @param origem Local de origem da carona
	 * @param destino Local de destino da carona
	 * @param data Data de saida da carona
	 * @param hora Hora de partida da carona
	 * @param vagas Quantidade de vagas disponiveis na carona
	 * @return Id da carona dacastrada
	 * @throws Exception Lança excecao se qualquer parametro informado for null ou vazio ou se a sessao for invalida
	 */
	public String cadastrarCarona(String idSessao, String origem, String destino, String data, String hora, int vagas) throws Exception{		
		logger.debug("cadastrando carona");
		//funcao para verificar se a sessao existe
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		//adiciona a caronas na lista de caronas
		String carona = ""+ CaronaDAOImpl.getInstance().getIdCarona();
		logger.debug("criando carona");
		CaronaDomain caronaDomain = new CaronaDomain(idSessao, carona, origem, destino, data, hora, vagas);
		logger.debug("carona criada");
		logger.debug("adicionando carona na lista");
		CaronaDAOImpl.getInstance().addCarona(caronaDomain);
		logger.debug("carona adicionada na lista");
		
		//Adiciona a carona ao usuario correspondente
		//UsuarioDomain usuario = UsuarioDAOImpl.getInstance().getUsuario(idSessao);	
		//usuario.addCarona(caronaDomain.getID());
		//logger.debug("carona adicionada no historico do usuario");
		
		//CaronaDAOImpl.getInstance().idCarona++;
		return caronaDomain.getID();
	}
	
	/**
	 * Metodo para cadatrar a carona na mesma cidade
	 * @param idSessao Id da sessão atual
	 * @param origem Local de origem da carona
	 * @param destino Local de destino da carona
	 * @param cidade Nome da cidade onde a carona sera realizada
	 * @param data Data de saida da carona
	 * @param hora Hora de partida da carona
	 * @param vagas Quantidade de vagas disponiveis na carona
	 * @return Id da carona cadastrada
	 * @throws Exception Lança exceção se qualquer parametro informado for null ou vazio ou se a sessao for invalida 
	 */
	public String cadastrarCaronaMunicipal(String idSessao, String origem, String destino, String cidade, String data, String hora, int vagas) throws Exception{
		logger.debug("cadastrando carona");
		//funcao para verificar se a sessao existe
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		//adiciona a caronas na lista de caronas
		String carona = ""+ CaronaDAOImpl.getInstance().getIdCarona();
		logger.debug("criando carona municipal");
		CaronaDomain caronaDomain = new CaronaDomain(idSessao, carona, origem, destino, cidade, data, hora, vagas);
		logger.debug("carona criada");
		logger.debug("adicionando carona na lista");
		CaronaDAOImpl.getInstance().addCarona(caronaDomain);
		logger.debug("carona adicionada na lista");
		
		//CaronaDAOImpl.getInstance().idCarona++;
		return caronaDomain.getID();
	}
	
	/**
	 * Metodo para cadatrar uma carona relampago
	 * @param idSessao Id da sessão atual
	 * @param origem Local de origem da carona
	 * @param destino Local de destino da carona 
	 * @param dataIda Data de saida da carona
	 * @param dataVolta Data de volta da carona
	 * @param minimoCaroneiros Quantidade minima de caroneiros
	 * @param hora Hora de partida da carona
	 * @return Id da carona cadastrada
	 * @throws Exception Lança exceção se qualquer parametro informado for null ou vazio ou se a sessao for invalida 
	 */
	public String cadastrarCaronaRelampago(String idSessao, String origem, String destino, String dataIda, String dataVolta, int minimoCaroneiros, String hora) throws Exception{
		logger.debug("cadastrando carona");
		//funcao para verificar se a sessao existe
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		
		//adiciona a caronas na lista de caronas
		String carona = ""+ CaronaDAOImpl.getInstance().getIdCarona();
		logger.debug("criando carona relampago");
		CaronaDomain caronaDomain = new CaronaDomain(idSessao, carona, origem, destino, dataIda, dataVolta, minimoCaroneiros, hora);
		logger.debug("carona criada");
		logger.debug("adicionando carona na lista");
		CaronaDAOImpl.getInstance().addCarona(caronaDomain);
		logger.debug("carona adicionada na lista");
		
		//CaronaDAOImpl.getInstance().idCarona++;
		return caronaDomain.getID();
	}
	
	/**
	 * Metodo para retornar os dados da carona
	 * @param idCarona Id da carona
	 * @param atributo Tipo de dado da carona a ser retornado 
	 * @return Dado da carona
	 * @throws Exception Lança exceção se qualquer parametro informado for null, vazio ou se a carona e/ou atributo for inexistente
	 */
	public String getAtributoCarona(String idCarona, String atributo) throws Exception{
		logger.debug("buscando atributo da carona");
		
		if( (atributo == null) || (atributo.trim().equals(""))){
			logger.debug("getAtributoCarona() Exceção: "+MensagensErro.ATRIBUTO_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INVALIDO);
		}
		
		if ((idCarona == null) || (idCarona.trim().equals(""))) {
			logger.debug("getAtributoCarona() Exceção: "+MensagensErro.IDENTIFICADOR_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.IDENTIFICADOR_INVALIDO);
		}
		
		CaronaDomain carona = null; 
		
		try {
			carona = CaronaDAOImpl.getInstance().getCarona(idCarona);			
		} catch (Exception e) {
			logger.debug("getAtributoCarona() Exceção: "+MensagensErro.ITEM_INEXISTENTE);
			//if (e.getMessage().equals(MensagensErro.CARONA_INEXISTENTE)) {
				throw new ProjetoCaronaException(MensagensErro.ITEM_INEXISTENTE);
			//}		
		}
		
		if(atributo.equals("origem")){
			return carona.getOrigem();
		}else if(atributo.equals("destino")){
			return carona.getDestino();
		}else if(atributo.equals("data")){
			return carona.getData();
		}else if(atributo.equals("vagas")){
			return ""+carona.getVagas();
		}else if(atributo.equals("ehMunicipal")){			
			if (carona.getTipoCarona().equals("M")) {
				return "true";
			} else { 
				return "false";
			}
		}else {
			logger.debug("getAtributoCarona() Exceção: "+MensagensErro.ATRIBUTO_INEXISTENTE);
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INEXISTENTE);
		}
				
	}
	
	/**
	 * Metodo para retornar os dados da carona relampago
	 * @param idCarona Id da carona
	 * @param atributo Tipo de dado da carona a ser retornado 
	 * @return Dado da carona
	 * @throws Exception Lança exceção se qualquer parametro informado for null, vazio ou se a carona e/ou atributo for inexistente
	 */
	public String getAtributoCaronaRelampago(String idCarona, String atributo) throws Exception{
		logger.debug("buscando atributo da carona");
		
		if( (atributo == null) || (atributo.trim().equals(""))){
			logger.debug("getAtributoCarona() Exceção: "+MensagensErro.ATRIBUTO_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INVALIDO);
		}
		
		if ((idCarona == null) || (idCarona.trim().equals(""))) {
			logger.debug("getAtributoCarona() Exceção: "+MensagensErro.IDENTIFICADOR_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.IDENTIFICADOR_INVALIDO);
		}
		
		CaronaDomain carona = null; 
		
		try {
			carona = CaronaDAOImpl.getInstance().getCarona(idCarona);			
		} catch (Exception e) {
			logger.debug("getAtributoCarona() Exceção: "+MensagensErro.ITEM_INEXISTENTE);
			//if (e.getMessage().equals(MensagensErro.CARONA_INEXISTENTE)) {
				throw new ProjetoCaronaException(MensagensErro.ITEM_INEXISTENTE);
			//}		
		}
		
		if(atributo.equals("origem")){
			return carona.getOrigem();
		}else if(atributo.equals("destino")){
			return carona.getDestino();
		}else if(atributo.equals("dataIda")){
			return carona.getData();
		}else if(atributo.equals("dataVolta")){
			return carona.getDataVolta();
		}else if(atributo.equals("minimoCaroneiros")){
			return ""+carona.getMinimoCaroneiros();
		}else if(atributo.equals("expired")){
			return ""+carona.isCaronaRelampagoExpirada();
		}
		else {
			logger.debug("getAtributoCarona() Exceção: "+MensagensErro.ATRIBUTO_INEXISTENTE);
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INEXISTENTE);
		}				
	}
	
	/**
	 * Metodo para obter os caroneiros pertencentes a carona
	 * @param idCarona Informa se a carona foi expirada ou nao
	 * @return Dado da carona
	 * @throws Exception Lança exceção se qualquer parametro informado for null, vazio ou se a carona e/ou atributo for inexistente
	 */
	public String[] getUsuariosByCarona(String idCarona) throws Exception{
	
		List<SolicitacaoVagaDomain> solicitacoesVaga = SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesConfirmadas(idCarona);
		String[] solicitacoes = new String[solicitacoesVaga.size()];
		int count = 0;
		for (SolicitacaoVagaDomain solicitacaoVaga : solicitacoesVaga) {		
			solicitacoes[count] = solicitacaoVaga.getIdUsuario();
			count++;
		}
		return solicitacoes;
	}
	
	/**
	 * Metodo para retornar o trajeto(origem - destino) que a carona ira fazer
	 * @param idCarona Id da carona
	 * @return Trajeto da carona
	 * @throws Exception Lança exceção se o id da carona for null, vazio ou inexistente
	 */
	public String getTrajeto(String idCarona) throws Exception{
		logger.debug("buscando trajeto de uma carona");
		CaronaDomain carona = null;
		
		try {
			carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		} catch (Exception e) {
			if (e.getMessage().equals(MensagensErro.CARONA_INVALIDA)) {
				logger.debug("getTrajeto() Exceção: "+MensagensErro.TRAJETO_INVALIDO);
					throw new ProjetoCaronaException(MensagensErro.TRAJETO_INVALIDO);
			}
			else {
				logger.debug("getTrajeto() Exceção: "+MensagensErro.TRAJETO_INEXISTENTE);
				throw new ProjetoCaronaException(MensagensErro.TRAJETO_INEXISTENTE);
			}			
		}
		
		String trajeto = carona.getOrigem()+" - "+carona.getDestino();
		
		logger.debug("trajeto encontrado ("+trajeto+")");
		
		return trajeto;	
	}
	
	/**
	 * Metodo para retornar uma carona
	 * @param idCarona Id da carona
	 * @return Retorna a carona cadastrada
	 * @throws Exception Lança exceção se o id da carona for null, vazio ou inexistente 
	 */
	public CaronaDomain getCarona(String idCarona) throws Exception{		
		logger.debug("buscando carona");
		return  CaronaDAOImpl.getInstance().getCarona(idCarona);
	}
	
	/**
	 * Metodo para obter a quantidade minima de caroneiros para para uma carona relampago
	 * @param idCarona
	 * @return
	 * @throws Exception
	 */
	public int getMinimoCaroneiros(String idCarona) throws Exception {
		return CaronaDAOImpl.getInstance().getCarona(idCarona).getMinimoCaroneiros();		
	};
	
	public String setCaronaRelampagoExpired(String idCarona) throws Exception{
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);		
		//verificaCaronaExpirada(idCarona);		
		
		carona.setCaronaRelampagoExpirada(true);		
		//atualiza a carona 
		CaronaDAOImpl.getInstance().atualizaCarona(carona);
		return carona.getID();
	}
	
	/**
	 * Metodo para retornar a carona de um determinado usuário com base no índice da lista de caronas cadastradas 
	 * @param idSessao Id da sessão atual
	 * @param indexCarona Indice da lista de caronas
	 * @return Retorna a carona cadastrada
	 * @throws Exception Lança exceção se o id da carona for null, vazio ou inexistente ou se o indice informado for inválido
	 */
	public CaronaDomain getCaronaUsuario(String idSessao, int indexCarona) throws Exception{
		logger.debug("buscando carona de um usuário");
		SessaoDAOImpl.getInstance().getSessao(idSessao);
		String idCarona = getIdCaronaByIndex(indexCarona, idSessao);		
		return CaronaDAOImpl.getInstance().getCarona(idCarona);		
	}
	
	
	/**
	 * Metodo para retornar o id de uma carona do Usuario pelo index 
	 * @param indexCarona Index da carona na lista de caronas
	 * @return Id da carona
	 * @throws Exception Lança exceção se o index informado for igual a zero ou maior que a quantidade de indices da lista
	 */
	private String getIdCaronaByIndex(int indexCarona, String login) throws Exception{
		List<CaronaDomain> caronas = CaronaDAOImpl.getInstance().getHistoricoDeCaronas(login);
		
		if ((indexCarona == 0) || (indexCarona > caronas.size())) {
			logger.debug("getIdCaronaByIndex() Exceção: "+MensagensErro.INDICE_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.INDICE_INVALIDO);
		}
		
		return caronas.get(indexCarona-1).getID();
	}
	
	/**
	 * Define a carona como preferencial por 24h para todos os caroneiros que deram uma review positiva ao dono da carona
	 * @param idCarona Id da carona
	 * @throws Exception Lanca excecao se idCarona for vazio, a carona ira existir ou problema com a consulta no banco de dados
	 */
	public void definirCaronaPreferencial(String idCarona) throws Exception {
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		carona.setPreferencial(true);
		CaronaDAOImpl.getInstance().atualizaCarona(carona);
		
	}
	
	/**
	 * Metodo para verivicar se uma carona é preferencial
	 * @param idCarona Id da carona
	 * @return Retorna valor booleano indicando se é ou ira preferencial
	 * @throws Exception Exception Lanca excecao se idCarona for vazio, a carona ira existir ou problema com a consulta no banco de dados
	 */
	public boolean isCaronaPreferencial(String idCarona) throws Exception {
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		return carona.isPreferencial();
	}
	
	//TODO: verificar este metodo
	private boolean verificaCaracteres(String valor){

		String patternTexto = valor;
		patternTexto = patternTexto.replaceAll("[-?!().]", "");

		if (patternTexto.equals(valor)) {
			return true; 
		}
		else {
			return false;
		}		
	}

	/**
	 * Pega todos os usuário que avaliaram uma carona como "segura e tranquila"
	 * @param idCarona Id da carona
	 * @return Retorna todos os usuarios preferenciais
	 * @throws Exception Lanca excecao se houver problema com a consulta no banco de dados
	 */
	public List<UsuarioDomain> getUsuariosPreferenciaisCarona(String idCarona) throws Exception {
		List<UsuarioDomain> usuariosPreferenciais = new ArrayList<UsuarioDomain>();
		//pega caronas preferencial
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		String login = carona.getIdSessao();
		
		//pega todas as caronas
		List<CaronaDomain> caronas = CaronaDAOImpl.getInstance().getHistoricoDeCaronas(login);
		
		//remove a carona preferencial da lista de caronas
		for (int i = 0; i < caronas.size(); i++) {
			if(caronas.get(i).getID().equals(carona.getID())){
				caronas.remove(i);
			}
		}
		
		//verifica os que avaliaram bem as outras caronas
		for (CaronaDomain caronaDomain : caronas) {
			List<SolicitacaoVagaDomain> vagasCarona = SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesConfirmadas(caronaDomain.getID());
			for (SolicitacaoVagaDomain vaga : vagasCarona) {
				if(vaga.getReviewCarona() != null){
					if(vaga.getReviewCarona().equals("segura e tranquila")){
						usuariosPreferenciais.add(UsuarioDAOImpl.getInstance().getUsuario(vaga.getIdUsuario()));
					}
				}
			}
		}
		
		return usuariosPreferenciais;
	}

	/**
	 * Metodo para verificar se alguma carona foi expirada
	 * @param idCarona Id da carona
	 * @return True se a carona estiver expirada ou false cao contrario
	 * @throws Exception Lanca excecao o id da carona for invalido
	 */
	private boolean verificaCaronaExpirada(String idCarona) throws Exception{		
		List<SolicitacaoVagaDomain> listaSolicitacoes;
		CaronaDomain carona = CaronaDAOImpl.getInstance().getCarona(idCarona);
		
		EmailPadrao email = new EmailPadrao();
		ValidarCampos validar = new ValidarCampos();
						
		if ( (carona.getVagas() > 0) && (!validar.isDataIniValida(carona.getData())) ) {
			//Lista todos os caroneiros
			listaSolicitacoes = SolicitacaoVagaDAOImpl.getInstance().getSolicitacoesConfirmadas(carona.getID());
				
			String destinatarios= ""; 
			for (SolicitacaoVagaDomain solicitacao : listaSolicitacoes) {
				destinatarios = destinatarios + UsuarioDAOImpl.getInstance().getUsuario(solicitacao.getIdUsuario()).getPerfil().getEmail() +",";				
			}

			//tratamento para retirar a última ", "
			if (destinatarios.length() > 1) {
				destinatarios = destinatarios.substring (0, destinatarios.length() - 1);
			}
				
			//Este metodo ira enviar um email para todos os caroneiros
			email.enviarEmail("CARona - AVISO", MensagensErro.CARONA_REJEITADA, destinatarios);
			
			return true;
		}
		
		return false;
	}
}
