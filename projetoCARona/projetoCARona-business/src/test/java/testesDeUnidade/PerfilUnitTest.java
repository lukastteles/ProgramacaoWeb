package testesDeUnidade;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.br.uepb.business.PerfilBusiness;
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.business.UsuarioBusiness;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class PerfilUnitTest {

	private SessaoBusiness sessaoBusiness;
	private UsuarioBusiness usuarioBusiness;
	private PerfilBusiness perfilBusiness;
	private String login;
	private String senha;
	private String nome;
	private String endereco;
	private String email;
	
	@Before
	public void iniciaBusiness(){
		sessaoBusiness = new SessaoBusiness();
		usuarioBusiness = new UsuarioBusiness();
		perfilBusiness = new PerfilBusiness();
		
		//limpa os dados antes de iniciar
		SessaoDAOImpl.getInstance().apagaSessoes();
		UsuarioDAOImpl.getInstance().apagaUsuarios();
		
		cadastraUsuarioTest();
		abrirSessao();
	}
	
	private void abrirSessao() {
		login = "mark";
		senha = "m@rk";
		
		try {
			sessaoBusiness.abrirSessao(login, senha);
		} catch (ProjetoCaronaException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
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
		
	}
	
	@Test
	public void testaPerfil(){
		
		try {
			perfilBusiness.visualizaPerfil(login, login);
		} catch (ProjetoCaronaException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
		try {
			perfilBusiness.getNome(login);
			perfilBusiness.getEndereco(login);
			perfilBusiness.getEmail(login);
			perfilBusiness.getHistoricoDeCaronas(login);
			perfilBusiness.getHistoricoDeVagasEmCaronas(login);
			perfilBusiness.getCaronasSegurasETranquilas(login);
			perfilBusiness.getCaronasQueNaoFuncionaram(login);
			perfilBusiness.getFaltasEmVagasDeCaronas(login);
			perfilBusiness.getPresencasEmVagasDeCaronas(login);
		} catch (ProjetoCaronaException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
	}
	
	@Test
	public void testaExcecoesPerfil(){
		
		/*
		 *Excecoes para visualiza perfil 
		 */
		
		//idSessao Vazio
		try {
			perfilBusiness.visualizaPerfil("", login);
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals("Sessão inválida", e.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		try {
			perfilBusiness.visualizaPerfil(null, login);
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals("Sessão inválida", e.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//Login Vazio
		try {
			perfilBusiness.visualizaPerfil(login, "");
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals("Login inválido", e.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		try {
			perfilBusiness.visualizaPerfil(login, null);
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals("Login inválido", e.getMessage());
		} catch (Exception e) {
			fail();
		}
		
	}
	
	
}
