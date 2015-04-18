package testesDeUnidade;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.business.UsuarioBusiness;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class SessaoUnitTest {
	
	/**
	 * Classe de Teste para SessaoBusiness
	 * @author luana Janaina / Lukas Teles 
	 * @version 0.1
	 * @since 2ª Iteração
	 */

	private SessaoBusiness sessaoBusiness;
	private UsuarioBusiness usuarioBusiness;
	private String login;
	private String senha;
	private String nome;
	private String endereco;
	private String email;

	@Before
	public void iniciaBusiness(){
		sessaoBusiness = new SessaoBusiness();
		usuarioBusiness = new UsuarioBusiness();
		
		SessaoDAOImpl.getInstance().apagaSessoes();
		UsuarioDAOImpl.getInstance().apagaUsuarios();
		
		cadastraUsuarioTest();	
	}
	
	private void cadastraUsuarioTest() {
		login = "mark";
		senha = "m@rk";
		nome="Mark Zuckerberg";
		endereco = "Palo Alto, California";
		email = "mark@facebook.com";
		try {
			usuarioBusiness.criarUsuario(login, senha, nome, endereco, email);
		} catch (ProjetoCaronaException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
		try {
			usuarioBusiness.criarUsuario("outro", "senha", "nome", "endereco", "email@email.com");
		} catch (ProjetoCaronaException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testeAbrirEncerrarSessao(){
		
		String idSessao;
		try {
			idSessao = sessaoBusiness.abrirSessao(login, senha);
			Assert.assertEquals(login, idSessao);				
			sessaoBusiness.encerrarSessao(login);
		} catch (ProjetoCaronaException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
		try {
			idSessao = sessaoBusiness.abrirSessao("outro", "senha");
			Assert.assertEquals("outro", idSessao);				
			sessaoBusiness.encerrarSessao("outro");
		} catch (ProjetoCaronaException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testeSessaoExcecoes(){
		
		/*
		 * excecoes para abrirSessao
		 */
		String idSessao;
		
		//Login Vazio
		try {
			idSessao = sessaoBusiness.abrirSessao("", senha);				
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals("Login inválido", e.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		try {
			idSessao = sessaoBusiness.abrirSessao(null, senha);				
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals("Login inválido", e.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//Senha Vazia
		try {
			idSessao = sessaoBusiness.abrirSessao(login, "");				
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals("Login inválido", e.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		try {
			idSessao = sessaoBusiness.abrirSessao(login, null);				
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals("Login inválido", e.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//Login Incoreto
		try {
			idSessao = sessaoBusiness.abrirSessao("login", senha);				
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals("Usuário inexistente", e.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//Senha Incoreta
		try {
			idSessao = sessaoBusiness.abrirSessao(login, "senha");				
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals("Login inválido", e.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		/*
		 * Abrir Sessao 2 Vezes
		 */
		try {
			idSessao = sessaoBusiness.abrirSessao(login, senha);
			idSessao = sessaoBusiness.abrirSessao(login, senha);
		} catch (ProjetoCaronaException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
		/*
		 * excecoes para encerrar sessao
		 */
		
		//Login Vazio
		try {
			sessaoBusiness.encerrarSessao("");
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals("Sessão inexistente", e.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		try {
			sessaoBusiness.encerrarSessao(null);
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals("Sessão inexistente", e.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		/*
		 * Apaga Sessoes
		 */
		//Senha Incoreta
		try {
			idSessao = sessaoBusiness.abrirSessao(login, senha);
			idSessao = sessaoBusiness.abrirSessao("outro", "senha");
			SessaoDAOImpl.getInstance().apagaSessoes();
		} catch (ProjetoCaronaException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
	}

	
}
