package com.br.uepb.business;

import java.util.ArrayList;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.SolicitacaoVagaDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;
import com.br.uepb.validator.ValidarCampos;

public class FacadeTestBusiness {

	/**
	 * Classe para chamar os métodos a serem testados no EasyAccept
	 * @author luana Janaina / Lukas Teles 
	 * @version 0.1
	 * @since 1ª Iteração
	 */
	
	
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
		ArrayList<CaronaDomain> caronas;
			
		caronas = caronaBusiness.localizarCarona(idSessao, origem, destino);
		
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
		ValidarCampos validarCampos =  new ValidarCampos();
		validarCampos.validarVagas(vagas);		
		return caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, hora, Integer.parseInt(vagas));
	}
		
	public String getAtributoCarona(String idCarona, String atributo) throws Exception{
		return caronaBusiness.getAtributoCarona(idCarona, atributo);
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
		
	public String getCaronaUsuario(String idSessao, int indexCarona) throws Exception{
		CaronaDomain carona = caronaBusiness.getCaronaUsuario(idSessao, indexCarona);		
		return carona.getID();
	}
		
	public String getTodasCaronasUsuario(String idSessao) throws Exception{
		ArrayList<CaronaDomain> caronas = caronaBusiness.getTodasCaronasUsuario(idSessao);

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
		
	//TODO: Ver com o prof. Thiago a diferença entre as funcoes getPontosSugeridos e getPontosEncontro
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
		String[] pontos = pontoDeEncontroBusiness.getPontosSugeridos(idSessao, idCarona);		
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
		ArrayList<SolicitacaoVagaDomain> solicitacoes = solicitacaoVagaBusiness.getSolicitacoesConfirmadas(idSessao, idCarona);

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
		ArrayList<SolicitacaoVagaDomain> solicitacoes = solicitacaoVagaBusiness.getSolicitacoesPendentes(idSessao, idCarona);

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
		
	//TODO: Ver com o prof. Thiago a diferença entre as funcoes aceitarSolicitacao e aceitarSolicitacaoPontoEncontro
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
		if(atributo.equals("nome")){
			return perfilBusiness.getNome(login);
		}else if(atributo.equals("endereco")){
			return perfilBusiness.getEndereco(login);
		}else if(atributo.equals("email")){
			return perfilBusiness.getEmail(login);
		}else if(atributo.equals("historico de caronas")){
			String[] str = perfilBusiness.getHistoricoDeCaronas(login);
			return trataLista(str);
		}else if(atributo.equals("historico de vagas em caronas")){
			return trataLista(perfilBusiness.getHistoricoDeVagasEmCaronas(login));
		}else if(atributo.equals("caronas seguras e tranquilas")){
			return ""+perfilBusiness.getCaronasSegurasETranquilas(login).length;
		}else if(atributo.equals("caronas que não funcionaram")){
			return ""+perfilBusiness.getCaronasQueNaoFuncionaram(login).length;
		}else if(atributo.equals("faltas em vagas de caronas")){
			return ""+perfilBusiness.getFaltasEmVagasDeCaronas(login).length;
		}else if(atributo.equals("presenças em vagas de caronas")){
			return ""+perfilBusiness.getFaltasEmVagasDeCaronas(login).length;
		}else {
			throw new ProjetoCaronaException(MensagensErro.ATRIBUTO_INEXISTENTE);
		}		
	}
		
	public String visualizarPerfil(String idSessao, String login) throws Exception{
		return perfilBusiness.visualizaPerfil(idSessao, login);
	}
	
	//Metodos de controle da classe Sistema
	public void encerrarSistema(){
		
	}
		
	public void quit(){
			
	}
		
	public void zerarSistema(){
		SolicitacaoVagaDAOImpl.getInstance().apagaSolicitacoes();
		CaronaDAOImpl.getInstance().apagaCaronas();		
		UsuarioDAOImpl.getInstance().apagaUsuarios();
		SessaoDAOImpl.getInstance().apagaSessoes();
	}
	
	private String trataLista(String[] pontos){
		String ponto = "[";
		if(pontos.length > 1){
			for(int i = 0; i < pontos.length-1; i++){
				ponto+=pontos[i]+";";
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
