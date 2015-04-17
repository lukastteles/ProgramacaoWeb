package com.br.uepb.business;

import java.util.ArrayList;

import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.domain.UsuarioDomain;
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
	SugestaoPontoEncontroBusiness sugestaoPontoEncontroBusiness = new SugestaoPontoEncontroBusiness();
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
	public String sugerirPontoEncontro(String idSessao, String idCarona, String ponto) throws Exception{
		if(ponto.contains(";")){
			String[] pontos = new String[2];
			pontos[0] = ponto.substring(0, ponto.indexOf(";"));
			pontos[1] = ponto.substring(ponto.indexOf(";")+1, ponto.length());
			return sugestaoPontoEncontroBusiness.sugerirPontoEncontro(idSessao, idCarona, pontos);
		}else{
			return sugestaoPontoEncontroBusiness.sugerirPontoEncontro(idSessao, idCarona, ponto);
		}
		
	}
		
	public void responderSugestaoPontoEncontro(String idSessao, String idCarona, String idSugestao, String ponto) throws Exception{
		if(ponto.contains(";")){
			String[] pontos = new String[2];
			pontos[0] = ponto.substring(0, ponto.indexOf(";"));
			pontos[1] = ponto.substring(ponto.indexOf(";")+1, ponto.length());
			sugestaoPontoEncontroBusiness.responderSugestaoPontoEncontro(idSessao, idCarona, idSugestao, pontos);
		}else{
			sugestaoPontoEncontroBusiness.responderSugestaoPontoEncontro(idSessao, idCarona, idSugestao, ponto);
		}
		
	}
		
	public String getPontosSugeridos(String idSessao, String idCarona){
			
		return "";
	}
		
	public String getPontosEncontro(String idSessao, String idCarona){
			
		return "";
	}
		
	//Metodos de controle da classe SolicitacaoVaga
	public String getAtributoSolicitacao(String idSolicitacao, String atributo) throws Exception{
		
		SolicitacaoVagaDomain solicitacaoVaga = solicitacaoVagaBusiness.getAtributoSolicitacao(idSolicitacao, atributo);		
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
		}else {
			throw new Exception("Atributo inexistente");
		}

	}
		
	public String getSolicitacoesConfirmadas(String idSessao, String idCarona){
			
		return "";
	}
		
	public String getSolicitacoesPendentes(String idSessao, String idCarona){
			
		return "";
	}
		
	public int solicitarVagas(String idSessao, String idCarona){
			
		return 0;
	}
		
	public int solicitarVagaPontoEncontro(String idSessao, String idCarona, String ponto) throws Exception{
		return solicitacaoVagaBusiness.solicitarVagaPontoEncontro(idSessao, idCarona, ponto);
	}
		
	public void aceitarSolicitacao(String idSessao, String idSolicitacao){
			
	}
	
	public void aceitarSolicitacaoPontoEncontro(String idSessao, String idSolicitacao) throws Exception{
		solicitacaoVagaBusiness.aceitarSolicitacaoPontoEncontro(idSessao, idSolicitacao);
	}
		
	public void desistirRequisicao(String idSessao, String idCarona, String idSolicitacao) throws Exception{
		solicitacaoVagaBusiness.desistirRequisicao(idSessao, idCarona, idSolicitacao);
	}
		
	public void rejeitarSolicitacao(String idSessao, String idSolicitacao){
			
	}
		
	//Metodos de controle da classe Perfil
	public String getAtributoPerfil(String login, String atributo) throws Exception{
		return perfilBusiness.getAtributoPerfil(login, atributo);		
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
	
	}
}
