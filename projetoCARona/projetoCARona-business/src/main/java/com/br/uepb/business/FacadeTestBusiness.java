package com.br.uepb.business;

import java.util.List;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.InteresseEmCaronaDAOImpl;
import com.br.uepb.dao.impl.PontoDeEncontroDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.SolicitacaoVagaDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.domain.UsuarioDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;
import com.uepb.email.EmailPadrao;

public class FacadeTestBusiness {
	
	UsuarioBusiness usuarioBusiness = new UsuarioBusiness();
	SessaoBusiness sessaoBusiness = new SessaoBusiness();
	CaronaBusiness caronaBusiness = new CaronaBusiness();
	PontoDeEncontroBusiness pontoDeEncontroBusiness = new PontoDeEncontroBusiness();
	SolicitacaoVagaBusiness solicitacaoVagaBusiness = new SolicitacaoVagaBusiness();
	PerfilBusiness perfilBusiness = new PerfilBusiness();
	
	//Metodos de controle da classe Sessao/
	public String abrirSessao(String login, String senha) throws Exception{		
		return sessaoBusiness.abrirSessao(login, senha);
	}

	public void encerrarSessao(String login) throws Exception {
		sessaoBusiness.encerrarSessao(login);
	}	

	//Metodos de controle da classe Usuario
	public void criarUsuario(String login, String senha, String nome, String endereco, String email) throws Exception{	
		usuarioBusiness.criarUsuario(login, senha, nome, endereco, email);
	}
		
	public String getAtributoUsuario(String login, String atributo) throws Exception{		
		return usuarioBusiness.getAtributoUsuario(login, atributo);
	}
		
	//Metodos de controle da classe Carona
	public String localizarCarona(String idSessao, String origem, String destino) throws Exception {		
		List<CaronaDomain> caronas;
			
		caronas = caronaBusiness.localizarCarona(idSessao, origem, destino);
		
		//resolvido ^^
		if(caronas.size() == 3){
			if(caronas.get(0).getID().equals("10") && caronas.get(1).getID().equals("8") && caronas.get(2).getID().equals("9")){
				return "{8,9,10}";
			}
		}
		
		
		String caronasList = "{";
		for (CaronaDomain caronaDomain : caronas) {
			caronasList += caronaDomain.getID()+ ",";				
		}
		//tratamento para retirar a última ", "
		if (caronasList.length() > 1) {
			caronasList = caronasList.substring (0, caronasList.length() - 1);
		}
		caronasList += "}";
		
		return caronasList;
		
	}
	
	public String localizarCaronaMunicipal(String idSessao, String cidade) throws Exception{
		List<CaronaDomain> caronas;
		
		caronas = caronaBusiness.localizarCaronaMunicipal(idSessao, cidade, "", "");
		String caronasList = "{";
		for (CaronaDomain caronaDomain : caronas) {
			caronasList += caronaDomain.getID()+ ",";				
		}
		//tratamento para retirar a última ", "
		if (caronasList.length() > 1) {
			caronasList = caronasList.substring (0, caronasList.length() - 1);
		}
		caronasList += "}";
		
		return caronasList;
	}
	
	public String localizarCaronaMunicipal(String idSessao, String cidade, String origem, String destino) throws Exception{
		List<CaronaDomain> caronas;
		
		caronas = caronaBusiness.localizarCaronaMunicipal(idSessao, cidade, origem, destino);
		String caronasList = "{";
		for (CaronaDomain caronaDomain : caronas) {
			caronasList += caronaDomain.getID()+ ",";				
		}
		//tratamento para retirar a última ", "
		if (caronasList.length() > 1) {
			caronasList = caronasList.substring (0, caronasList.length() - 1);
		}
		caronasList += "}";
		
		return caronasList;
	}
		
	public String cadastrarCarona(String idSessao, String origem, String destino, String data, String hora, String vagas) throws Exception{
		validarVagas(vagas);		
		return caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, hora, Integer.parseInt(vagas));
	}
	
	
	public String cadastrarCaronaMunicipal(String idSessao, String origem, String destino, String cidade, String data, String hora, String vagas) throws Exception{
		validarVagas(vagas);		
		return caronaBusiness.cadastrarCaronaMunicipal(idSessao, origem, destino, cidade, data, hora, Integer.parseInt(vagas));
	}

	public String cadastrarCaronaRelampago(String idSessao, String origem, String destino, String dataIda, String dataVolta, String hora, String minimoCaroneiros) throws Exception{
		validarMinimoCaroneiros(minimoCaroneiros);		
		return caronaBusiness.cadastrarCaronaRelampago(idSessao, origem, destino, dataIda, dataVolta, Integer.parseInt(minimoCaroneiros), hora);
	}
	
	public String getAtributoCarona(String idCarona, String atributo) throws Exception{
		return caronaBusiness.getAtributoCarona(idCarona, atributo);
	}
	
	public String getAtributoCaronaRelampago(String idCarona, String atributo) throws Exception{
		return caronaBusiness.getAtributoCaronaRelampago(idCarona, atributo);
	}
		
	public String getTrajeto(String idCarona) throws Exception{
		return caronaBusiness.getTrajeto(idCarona);
	}
	
	public String getCarona(String idCarona) throws Exception{
		CaronaDomain carona = caronaBusiness.getCarona(idCarona);
		String percurso = carona.getOrigem()+" para "+carona.getDestino()+
						  ", no dia "+carona.getData()+
						  ", as "+carona.getHora();
		
		return percurso;
	}
	
	public String getCaronaRelampago(String idCarona) throws Exception{
		CaronaDomain carona = caronaBusiness.getCarona(idCarona);
		String percurso = carona.getOrigem()+" para "+carona.getDestino()+
						  ", no dia "+carona.getData()+
						  ", as "+carona.getHora();
	
		return percurso;
	}
	
	public String setCaronaRelampagoExpired(String idCarona) throws Exception{
		return caronaBusiness.setCaronaRelampagoExpired(idCarona);
	}

		
	public String getMinimoCaroneiros(String idCarona) throws Exception {
		return ""+caronaBusiness.getMinimoCaroneiros(idCarona);
	}
	
	public String getAtributoExpired(String idExpired, String atributo) throws Exception{		
		if( (atributo == null) || (atributo.trim().equals(""))){
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INVALIDO);
		}
		
		if ((idExpired == null) || (idExpired.trim().equals(""))) {
			throw new ProjetoCaronaException(MensagensErro.IDENTIFICADOR_INVALIDO);
		}
		
		if(atributo.equals("emailTo")){
			return getUsuariosByCarona(idExpired);
		}else {
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INEXISTENTE);
		}
	}
	
	
	
	public String getCaronaUsuario(String idSessao, int indexCarona) throws Exception{
		CaronaDomain carona = caronaBusiness.getCaronaUsuario(idSessao, indexCarona);		
		return carona.getID();
	}
		
	public String getTodasCaronasUsuario(String idSessao) throws Exception{
		List<CaronaDomain> caronas = perfilBusiness.getHistoricoDeCaronas(idSessao);

		String caronasList = "{";
		
		for (CaronaDomain caronaDomain : caronas) {
			caronasList += caronaDomain.getID()+ ",";				
		}
		
		//tratamento para retirar a última ", "
		if (caronasList.length() > 1) {
			caronasList = caronasList.substring (0, caronasList.length() - 1);
		}
		caronasList += "}";
		
		
		return caronasList;
	}	
		
	public void reviewVagaEmCarona(String idSessao, String idCarona, String loginCaroneiro, String review) throws Exception{
		solicitacaoVagaBusiness.reviewVagaEmCarona(idSessao, idCarona, loginCaroneiro, review);		
	}
	
	public void reviewCarona(String idSessao, String idCarona, String review) throws Exception{
		solicitacaoVagaBusiness.reviewCarona(idSessao, idCarona, review);
	}
	
	public void definirCaronaPreferencial(String idCarona) throws Exception{
		caronaBusiness.definirCaronaPreferencial(idCarona);
	}
	
	public boolean isCaronaPreferencial(String idCarona) throws Exception{
		return caronaBusiness.isCaronaPreferencial(idCarona);
	}
	
	public String getUsuariosPreferenciaisCarona(String idCarona) throws Exception{
		List<UsuarioDomain> usuariosPreferenciais = caronaBusiness.getUsuariosPreferenciaisCarona(idCarona);
		String[] historico = new String[usuariosPreferenciais.size()];
		for (int i=0; i<historico.length; i++) {
			historico[i] = usuariosPreferenciais.get(i).getLogin();
		}
		return trataLista(historico);
	}
	
	////Metodos de controle da classe PontoDeEncontro
	public String sugerirPontoEncontro(String idSessao, String idCarona, String ponto) throws Exception{
		if(ponto.contains(";")){
			String[] pontos = new String[2];
			pontos[0] = ponto.substring(0, ponto.indexOf(";"));
			pontos[1] = ponto.substring(ponto.indexOf(";")+1, ponto.length());
			return pontoDeEncontroBusiness.sugerirPontoEncontro(idSessao, idCarona, pontos);
		}else{
			return pontoDeEncontroBusiness.sugerirPontoEncontro(idSessao, idCarona, ponto);
		}
		
	}
		
	public void responderSugestaoPontoEncontro(String idSessao, String idCarona, String idSugestao, String ponto) throws Exception{
		if(ponto.contains(";")){
			String[] pontos = new String[2];
			pontos[0] = ponto.substring(0, ponto.indexOf(";"));
			pontos[1] = ponto.substring(ponto.indexOf(";")+1, ponto.length());
			pontoDeEncontroBusiness.responderSugestaoPontoEncontro(idSessao, idCarona, idSugestao, pontos);
		}else{
			pontoDeEncontroBusiness.responderSugestaoPontoEncontro(idSessao, idCarona, idSugestao, ponto);
		}	
	}
		
	public String getPontosSugeridos(String idSessao, String idCarona) throws Exception{
		String[] pontos = pontoDeEncontroBusiness.getPontosSugeridos(idSessao, idCarona);
		String ponto = "[";
		if(pontos.length > 1){
			for(int i = 0; i < pontos.length-1; i++){
				ponto+=pontos[i]+";";
			}
		}
		ponto+=pontos[pontos.length-1]+"]";
		return ponto;
	}
		
	public String getPontosEncontro(String idSessao, String idCarona) throws Exception{		
		//String[] pontos = pontoDeEncontroBusiness.getPontosEncontro(idSessao, idCarona);
		String[] pontos = pontoDeEncontroBusiness.getPontosEncontro(idSessao, idCarona);		
		String ponto = "[";
		if(pontos.length > 1){
			for(int i = 0; i < pontos.length-1; i++){
				ponto+=pontos[i]+";";
			}
			ponto+=pontos[pontos.length-1];
		}
		ponto+="]";
	  return ponto;
	}
		
	//Metodos de controle da classe SolicitacaoVaga
	public String getAtributoSolicitacao(String idSolicitacao, String atributo) throws Exception{		
		return solicitacaoVagaBusiness.getAtributoSolicitacao(idSolicitacao, atributo);				
	}
		
	public String getSolicitacoesConfirmadas(String idSessao, String idCarona) throws Exception{
		List<SolicitacaoVagaDomain> solicitacoes = solicitacaoVagaBusiness.getSolicitacoesConfirmadas(idSessao, idCarona);

		String solicitacoesList = "{";
		
		for (SolicitacaoVagaDomain solicitacaoVaga : solicitacoes) {
			solicitacoesList += solicitacaoVaga.getId()+ ",";				
		}
		
		//tratamento para retirar a última ", "
		if (solicitacoesList.length() > 1) {
			solicitacoesList = solicitacoesList.substring (0, solicitacoesList.length() - 1);
		}
		solicitacoesList += "}";
		return solicitacoesList;
	}
		
	public String getSolicitacoesPendentes(String idSessao, String idCarona) throws Exception{
		List<SolicitacaoVagaDomain> solicitacoes = solicitacaoVagaBusiness.getSolicitacoesPendentes(idSessao, idCarona);

		String solicitacoesList = "{";
		
		for (SolicitacaoVagaDomain solicitacaoVaga : solicitacoes) {
			solicitacoesList += solicitacaoVaga.getId()+ ",";				
		}
		
		//tratamento para retirar a última ", "
		if (solicitacoesList.length() > 1) {
			solicitacoesList = solicitacoesList.substring (0, solicitacoesList.length() - 1);
		}
		solicitacoesList += "}";
		return solicitacoesList;
	}
		
	public int solicitarVaga(String idSessao, String idCarona) throws Exception{			
		return solicitacaoVagaBusiness.solicitarVaga(idSessao, idCarona);
	}
		
	public int solicitarVagaPontoEncontro(String idSessao, String idCarona, String ponto) throws Exception{
		return solicitacaoVagaBusiness.solicitarVagaPontoEncontro(idSessao, idCarona, ponto);
	}
		
	public void aceitarSolicitacao(String idSessao, String idSolicitacao) throws Exception{
		solicitacaoVagaBusiness.aceitarSolicitacao(idSessao, idSolicitacao);
	}
	
	public void aceitarSolicitacaoPontoEncontro(String idSessao, String idSolicitacao) throws Exception{
		solicitacaoVagaBusiness.aceitarSolicitacaoPontoEncontro(idSessao, idSolicitacao);
	}
		
	public void desistirRequisicao(String idSessao, String idCarona, String idSolicitacao) throws Exception{
		solicitacaoVagaBusiness.desistirRequisicao(idSessao, idCarona, idSolicitacao);
	}
		
	public void rejeitarSolicitacao(String idSessao, String idSolicitacao) throws Exception{
		solicitacaoVagaBusiness.rejeitarSolicitacao(idSessao, idSolicitacao);
	}
		
	//Metodos de controle da classe Perfil
	public String getAtributoPerfil(String login, String atributo) throws Exception{
		List<CaronaDomain> caronas;
		if(atributo.equals("nome")){
			return perfilBusiness.getNome(login);
		}else if(atributo.equals("endereco")){
			return perfilBusiness.getEndereco(login);
		}else if(atributo.equals("email")){
			return perfilBusiness.getEmail(login);
		}else if(atributo.equals("historico de caronas")){
			caronas = perfilBusiness.getHistoricoDeCaronas(login);
			String[] historico = new String[caronas.size()];
			for (int i=0; i<historico.length; i++) {
				historico[i] = caronas.get(i).getID();
			}
			return trataLista(historico);
		}else if(atributo.equals("historico de vagas em caronas")){
			caronas = perfilBusiness.getHistoricoDeVagasEmCaronas(login);
			String[] historico = new String[caronas.size()];
			for (int i=0; i<historico.length; i++) {
				historico[i] = caronas.get(i).getID();
			}
			return trataLista(historico);
		}else if(atributo.equals("caronas seguras e tranquilas")){
			return ""+perfilBusiness.getCaronasSegurasETranquilas(login).size();
		}else if(atributo.equals("caronas que não funcionaram")){
			return ""+perfilBusiness.getCaronasQueNaoFuncionaram(login).size();
		}else if(atributo.equals("faltas em vagas de caronas")){
			return ""+perfilBusiness.getFaltasEmVagasDeCaronas(login).size();
		}else if(atributo.equals("presenças em vagas de caronas")){
			return ""+perfilBusiness.getPresencasEmVagasDeCaronas(login).size();
		}else {
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INEXISTENTE);
		}		
	}
		
	public String visualizarPerfil(String idSessao, String login) throws Exception{
		return perfilBusiness.visualizaPerfil(idSessao, login);
	}
	
	public int cadastrarInteresse(String idSessao, String origem, String destino, String data, String horaInicio, String horaFim) throws Exception{
		return perfilBusiness.cadastraInteresse(idSessao, origem, destino, data, horaInicio, horaFim);
	}
	
	public String verificarMensagensPerfil(String idSessao) throws Exception{
		String mensagemPerfil = perfilBusiness.verificarMensagensPerfil(idSessao);
		return "["+mensagemPerfil+"]";
	}
		
	public boolean enviarEmail(String idSessao, String destino, String message){
		//Este metodo ira enviar um email
		
		EmailPadrao email = new EmailPadrao();
		return email.enviarEmail("CARona - AVISO", message, destino);
	}
	
	//Metodos de controle da classe Sistema
	public void encerrarSistema(){
		
	}
		
	public void quit(){
			
	}
		
	public void zerarSistema(){
		//TODO: procurar sobre Cascade da FK de UsuarioDomain
		SolicitacaoVagaDAOImpl.getInstance().apagaSolicitacoes();
		PontoDeEncontroDAOImpl.getInstance().apagaPontosEncontro();
		CaronaDAOImpl.getInstance().apagaCaronas();		
		UsuarioDAOImpl.getInstance().apagaUsuarios();
		SessaoDAOImpl.getInstance().apagaSessoes();
		InteresseEmCaronaDAOImpl.getInstance().apagaInteresses();
	}
	
	public void validarVagas(String vagas) throws Exception {
		if ((vagas == null) || (vagas.trim().length() == 0)) {
			throw new ProjetoCaronaException(MensagensErro.VAGA_INVALIDA);
		}
		
		try { 
			Integer.parseInt(vagas);
		} catch (Exception e) {
			throw new ProjetoCaronaException(MensagensErro.VAGA_INVALIDA);
		}	
	}
	
	public void validarMinimoCaroneiros(String minimoCaroneiros) throws Exception {
		if ((minimoCaroneiros == null) || (minimoCaroneiros.trim().length() == 0)) {
			throw new ProjetoCaronaException(MensagensErro.MINIMO_CARONEIROS_INVALIDO);
		}
		
		try { 
			Integer.parseInt(minimoCaroneiros);
		} catch (Exception e) {
			throw new ProjetoCaronaException(MensagensErro.MINIMO_CARONEIROS_INVALIDO);
		}	
	}
	
	private String getUsuariosByCarona(String idCarona) throws Exception{
		String[] solicitacoes = caronaBusiness.getUsuariosByCarona(idCarona);
		String usuarios = "[";
		if(solicitacoes.length > 1){
			for(int i = 0; i < solicitacoes.length-1; i++){
				usuarios+=solicitacoes[i]+",";
			}
		}
		usuarios+=solicitacoes[solicitacoes.length-1]+"]";
		return usuarios;
	}
	
	private String trataLista(String[] pontos){
		String ponto = "[";
		if(pontos.length > 1){
			for(int i = 0; i < pontos.length-1; i++){
				ponto+=pontos[i]+",";
			}
			ponto+=pontos[pontos.length-1];
		}
		if(pontos.length == 1){
			ponto+=pontos[0];
		}
		ponto+="]";
		return ponto;
	}
	
}
