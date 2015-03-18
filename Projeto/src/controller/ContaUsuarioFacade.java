package controller;

public class ContaUsuarioFacade {
	//TODO : Mudar o nome dessa classe 
	
	private ContaUsuarioController usuarioController = new ContaUsuarioController();
	private CaronaController caronaController = new CaronaController();
	
	public void criarUsuario(String login, String senha, String nome, String endereco, String email) throws Exception{	
		usuarioController.criarUsuario(login, senha, nome, endereco, email);
	}
	
	public int abrirSessao(String login, String senha){
		return usuarioController.abrirSessao(login, senha);
	}
	
	public String getAtributoUsuario(String login, String atributo){
		return usuarioController.getAtributoUsuario(login, atributo);
	}
	
	public void zerarSistema(){
		usuarioController.zerarSistema();
	}
	
	public void encerrarSistema(){
		usuarioController.encerrarSistema();
	}

	public String localizarCarona(String idSessao, String origem, String destino) {
		return caronaController.localizarCarona(idSessao, origem, destino);
	}
	
	public String cadastrarCarona(String idSessao, String origem, String destino, String data, String hora, int vagas) {
		return caronaController.cadastrarCarona(idSessao, origem, destino, data, hora, vagas);
	}
	
	public String getAtributoCarona(String idCarona, String atributo){
		return caronaController.getAtributoCarona(idCarona, atributo);
	}
		
	public String getTrajeto (String idCarona){
		return caronaController.getTrajeto(idCarona);
	}
	
	public String getCarona(String idCarona){
		return caronaController.getCarona(idCarona);
	}
}
