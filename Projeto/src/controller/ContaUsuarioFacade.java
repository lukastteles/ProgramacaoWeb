package controller;

public class ContaUsuarioFacade {
	
	private ContaUsuarioController controller = new ContaUsuarioController();
	
	
	//criarUsuario login="mark" senha="m@rk" nome="Mark Zuckerberg" endereco="Palo Alto, California" email="mark@facebook.com"
	public void criarUsuario(String login, String senha, String nome, String endereco, String email){
		controller.criarUsuario(login, senha, nome, endereco, email);
	}
	
	public int abrirSessao(String login, String senha){
		return controller.abrirSessao(login, senha);
	}
	
	public String getAtributoUsuario(String login, String atributo){
		return controller.getAtributoUsuario(login, atributo);
	}
	
}
