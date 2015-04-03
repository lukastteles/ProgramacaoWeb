package testesDeUnidade;

import org.junit.Assert;
import org.junit.Test;

import com.br.uepb.business.UsuarioBusiness;

public class UsuarioUnitTest {
	
	@Test
	public void testeCadastraUsuario() throws Exception{
		//Parâmetros
		String login = "mark";
		String senha = "m@rk";
		String nome="Mark Zuckerberg";
		String endereco = "Palo Alto, California";
		String email = "mark@facebook.com";
		
		//cadastra um usuario
		UsuarioBusiness usuarioBusiness = new UsuarioBusiness();
		usuarioBusiness.criarUsuario(login, senha, nome, endereco, email);
		
		//Asserts
		Assert.assertEquals("mark", usuarioBusiness.getAtributoUsuario(login, "login"));
		Assert.assertEquals("m@rk", usuarioBusiness.getAtributoUsuario(login, "senha"));
		Assert.assertEquals("Mark Zuckerberg", usuarioBusiness.getAtributoUsuario(login, "nome"));
		Assert.assertEquals("Palo Alto, California", usuarioBusiness.getAtributoUsuario(login, "endereco"));
		Assert.assertEquals("mark@facebook.com", usuarioBusiness.getAtributoUsuario(login, "email"));
	}

}
