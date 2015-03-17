package controller;

public class ContaUsuarioFacade {
	
	private ContaUsuarioController controller = new ContaUsuarioController();
	
	public void criarUsuario(String login, String senha, String nome, String endereco, String email) throws Exception{	
		controller.criarUsuario(login, senha, nome, endereco, email);
	}
	
	public int abrirSessao(String login, String senha){
		return controller.abrirSessao(login, senha);
	}
	
	public String getAtributoUsuario(String login, String atributo){
		return controller.getAtributoUsuario(login, atributo);
	}
	
}
