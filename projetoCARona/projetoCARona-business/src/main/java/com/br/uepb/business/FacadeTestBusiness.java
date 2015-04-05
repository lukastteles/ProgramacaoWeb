package com.br.uepb.business;

import java.util.ArrayList;

import com.br.uepb.domain.CaronaDomain;

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
	
	//Metodos de controle da classe Sessao/
	public String abrirSessao(String login, String senha){		
		return sessaoBusiness.abrirSessao(login, senha);
	}
		
	public void encerrarSistema(){
		sessaoBusiness.encerrarSistema();
	}

	//Metodos de controle da classe Usuario/
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
		
	public String cadastrarCarona(String idSessao, String origem, String destino, String data, String hora, int vagas){
		return caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, hora, vagas);
	}
		
	public String getAtributoCarona(String idCarona, String atributo) throws Exception{
		return caronaBusiness.getAtributoCarona(idCarona, atributo);
	}
		
	public String getTrajeto(String idCarona){
		
		return "";
	}
		
	public String getCarona(String idCarona) throws Exception{
		CaronaDomain carona = caronaBusiness.getCarona(idCarona);
		//#expect "João Pessoa para Campina Grande, no dia 25/11/2026, as 06:59" getCarona idCarona=${carona3ID}
		String percurso = carona.getOrigem()+" para "+carona.getDestino()+
						  ", no dia "+carona.getData()+
						  ", as "+carona.getHora();
						  
		
		return percurso;
	}
		
	public int getCaronaUsuario(int idSessao, int indexCarona){
			
		return 0;
	}
		
	public String getTodasCaronasUsuario(int idSessao){
			
		return "";
	}	
		
	////Metodos de controle da classe PontoDeEncontro
	public String sugerirPontoEncontro(String idSessao, String idCarona, String pontos) throws Exception{
		return pontoDeEncontroBusiness.sugerirPontoEncontro(idSessao, idCarona, pontos);
	}
		
	public void reponderSugestaoPontoEncontro(String idSessao, String idCarona, String idSugestao, String pontos) throws Exception{
		pontoDeEncontroBusiness.responderSugestaoPontoEncontro(idSessao, idCarona, idSugestao);
	}
		
	public String getPontosSugeridos(int idSessao, int idCarona){
			
		return "";
	}
		
	public String getPontosEncontro(int idSessao, int idCarona){
			
		return "";
	}
		
	//Metodos de controle da classe SolicitacaoVaga
	public String getAtributoSolicitacao(int idSolicitacao, String atributo){
			
		return "";
	}
		
	public String getSolicitacoesConfirmadas(int idSessao, int idCarona){
			
		return "";
	}
		
	public String getSolicitacoesPendentes(int idSessao, int idCarona){
			
		return "";
	}
		
	public int solicitarVagas(int idSessao, int idCarona){
			
		return 0;
	}
		
	public int solicitarVagaPontoEncontro(int idSessao, int idCarona, String ponto){
			
		return 0;
	}
		
	public void aceitarSolicitacao(int idSessao, int idSolicitacao){
			
	}
	
	public void aceitarSolicitacaoPontoEncontro(int idSessao, int idSolicitacao){
			
	}
		
	public void desistirRequisicao(int idSessao, int idCarona, int idSolicitacao){
			
	}
		
	public void rejeitarSolicitacao(int idSessao, int idSolicitacao){
			
	}
		
	//Metodos de controle da classe Perfil
	public String getAtributoPerfil(String login, String atributo){
			
		return "";
	}
		
	public int visualizarPerfil(){
			
		return 0;
	}
}
