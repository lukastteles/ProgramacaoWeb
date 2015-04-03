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
	
	//TODO: melhorar documentacao e adicionair mais testes
	
	/**
	 * Teste para verificar a funcionalidade "abrirSessao" da classe SessaoBusiness
	 */
	
	@Test
	public void testeAbrirSessao(){
		SessaoBusiness sessaoBusiness = new SessaoBusiness();
		String login = "Luana";
		String senha = "123";		
		Assert.assertNotNull(sessaoBusiness.abrirSessao(login, senha));
	}
	
	/**
	 * Teste para verificar a funcionalidade "EncerrarSistema" da classe SessaoBusiness
	 */
	@Test
	public void encerrarSistema(){
		//TODO: verificar o que colocar neste teste
	}
	
}
