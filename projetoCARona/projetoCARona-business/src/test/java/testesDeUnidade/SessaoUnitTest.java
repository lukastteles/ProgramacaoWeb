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

	SessaoBusiness sessaoBusiness;
	String login;
	String senha;

	@Before
	public void iniciaBusiness(){
		sessaoBusiness = new SessaoBusiness();
		
		//limpa os dados antes de iniciar
		SessaoDAOImpl.getInstance().apagaSessoes();
	}
	
	@Test
	public void testeAbrirEncerrarSessao(){
		
		login = "Luana";
		senha = "123";
		
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
		
		
	}

	
}
