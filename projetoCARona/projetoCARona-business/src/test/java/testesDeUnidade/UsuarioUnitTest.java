package testesDeUnidade;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.br.uepb.business.UsuarioBusiness;

public class UsuarioUnitTest {
	
	private UsuarioBusiness usuarioBusiness;
	
	@Before
	public void iniciaBusiness(){
		usuarioBusiness = new UsuarioBusiness();
	}
	
	@Test
	public void testeCadastraUsuario() throws Exception{
		
		//Parâmetros
		String login = "mark";
		String senha = "m@rk";
		String nome="Mark Zuckerberg";
		String endereco = "Palo Alto, California";
		String email = "mark@facebook.com";
		
		//cadastra um usuario
		usuarioBusiness.criarUsuario(login, senha, nome, endereco, email);
		
		//Asserts 
		Assert.assertEquals("mark", usuarioBusiness.getAtributoUsuario(login, "login"));
		Assert.assertEquals("m@rk", usuarioBusiness.getAtributoUsuario(login, "senha"));
		Assert.assertEquals("Mark Zuckerberg", usuarioBusiness.getAtributoUsuario(login, "nome"));
		Assert.assertEquals("Palo Alto, California", usuarioBusiness.getAtributoUsuario(login, "endereco"));
		Assert.assertEquals("mark@facebook.com", usuarioBusiness.getAtributoUsuario(login, "email"));
		
	}
	
	@Test
	public void testesExcecoesUsuario(){
		
		String login="steve";
		String senha="5t3v3";
		String nome="Steven Paul Jobs";
		String endereco="Palo Alto, California";
		String email="jobs@apple.com";
		
		//Login Vazio
		try{
			usuarioBusiness.criarUsuario("", senha, nome, endereco, email);
			fail();
		}catch(Exception e){
			Assert.assertEquals("Login inválido", e.getMessage());
		}
		
		try{
			usuarioBusiness.criarUsuario(null, senha, nome, endereco, email);
			fail();
		}catch(Exception e){
			Assert.assertEquals("Login inválido", e.getMessage());
		}
		
		//Senha Vazia
		try{
			usuarioBusiness.criarUsuario(login, "", nome, endereco, email);
			fail();
		}catch(Exception e){
			Assert.assertEquals("Senha inválida", e.getMessage());
		}
		
		try{
			usuarioBusiness.criarUsuario(login, null, nome, endereco, email);
			fail();
		}catch(Exception e){
			Assert.assertEquals("Senha inválida", e.getMessage());
		}
		
		//Nome Vazio
		try{
			usuarioBusiness.criarUsuario(login, senha, "", endereco, email);
			fail();
		}catch(Exception e){
			//Assert.assertEquals("Senha inválida", e.getMessage());
		}
		
		try{
			usuarioBusiness.criarUsuario(login, senha, "", endereco, email);
			fail();
		}catch(Exception e){
			//Assert.assertEquals("Senha inválida", e.getMessage());
		}
		
		
		try{
			usuarioBusiness.getAtributoUsuario(login, "Hakuna Matata");
			fail();
		}catch(Exception e){
			Assert.assertEquals("Atributo inexistente", e.getMessage());
		}
		
		
		//Depois de cadastrar
		try{
			usuarioBusiness.getAtributoUsuario(login, "");
			fail();
		}catch(Exception e){
			Assert.assertEquals("Atributo inexistente", e.getMessage());
		}
	}

}
