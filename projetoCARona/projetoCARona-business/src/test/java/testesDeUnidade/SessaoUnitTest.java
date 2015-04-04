package testesDeUnidade;

import org.junit.Assert;
import org.junit.Test;

import com.br.uepb.business.SessaoBusiness;


public class SessaoUnitTest {
	
	/**
	 * Classe de Teste para SessaoBusiness
	 * @author luana Janaina / Lukas Teles 
	 * @version 0.1
	 * @since 2ª Iteração
	 */
	

	SessaoBusiness sessaoBusiness = new SessaoBusiness();
	
	//TODO: melhorar documentacao e adicionair mais testes
	
	/**
	 * Teste para verificar a funcionalidade "abrirSessao" da classe SessaoBusiness
	 */	
	@Test
	public void testeAbrirEncerrarSessao(){
		
		String login = "Luana";
		String senha = "123";
		String idSessao = sessaoBusiness.abrirSessao(login, senha);
		Assert.assertEquals(login, idSessao);				
		sessaoBusiness.encerrarSistema();
		
		login = "Lukas";
		senha = "123";
		idSessao = sessaoBusiness.abrirSessao(login, senha);
		Assert.assertEquals(login, idSessao);				
		sessaoBusiness.encerrarSistema();
		
	}

	
}
