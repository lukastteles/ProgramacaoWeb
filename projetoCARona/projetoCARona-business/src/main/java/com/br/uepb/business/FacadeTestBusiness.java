package com.br.uepb.business;

public class FacadeTestBusiness {

	/**
	 * Classe para chamar os métodos a serem testados no EasyAccept
	 * @author luana Janaina / Lukas Teles 
	 * @version 0.1
	 * @since 1ª Iteração
	 */
	
	//TODO: Gerar documentação
	
	UsuarioBusiness usuarioBusiness = new UsuarioBusiness();
	
	//Metodos de controle da classe Sessao/
	public int abrirSessao(String login, String senha){		
		return 0;
	}
		
	public void encerrarSistema(){
		
	}

	//Metodos de controle da classe Usuario/
	public void criarUsuario(String login, String senha, String nome, String endereco, String email) throws Exception{	
		usuarioBusiness.criarUsuario(login, senha, nome, endereco, email);
	}
		
	public String getAtributoUsuario(String login, String atributo) throws Exception{		
		return usuarioBusiness.getAtributoUsuario(login, atributo);
	}
		
	//Metodos de controle da classe Carona
	public String localizarCarona(int idSessao, String origem, String destino){
		
		return "";
	}
		
	public int cadastrarCarona(int idSessao, String origem, String destino, String data, String hora, int vagas){
		
		return 0;
	}
		
	public String getAtributoCarona(int idCarona, String atributo){
			
		return "";
	}
		
	public String getTrajeto(int idCarona){
			
		return "";
	}
		
	public String getCarona(int idCarona){
			
		return "";
	}
		
	public int getCaronaUsuario(int idSessao, int indexCarona){
			
		return 0;
	}
		
	public String getTodasCaronasUsuario(int idSessao){
			
		return "";
	}	
		
	////Metodos de controle da classe PontoDeEncontro
	public int sugerirPontoEncontro(int idSessao, int idCarona, String pontos){
			
		return 0;
	}
		
	public int reponderSugestaoPontoEncontro(int idSessao, int idCarona, int idSugestao, String pontos){
		
		return 0;
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