package testesDeUnidade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.br.uepb.business.CaronaBusiness;
import com.br.uepb.business.PontoDeEncontroBusiness;
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.business.UsuarioBusiness;
import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class PontoDeEncontroUnitTest {
	
	private UsuarioBusiness usuarioBusiness;
	private SessaoBusiness sessaoBusiness;
	private CaronaBusiness caronaBusiness;
	private PontoDeEncontroBusiness pontoEncontroBusiness;
	
	private String idCarona;
	
	/* cria o ambiente para realizar os testes
	 * neste caso será criado 1 usuario Mark 
	 */
	@Before
	public void iniciaBusiness(){
		usuarioBusiness = new UsuarioBusiness();
		sessaoBusiness = new SessaoBusiness();		
		caronaBusiness = new CaronaBusiness();
		pontoEncontroBusiness = new  PontoDeEncontroBusiness();
		
		//limpa os dados antes de iniciar		
		CaronaDAOImpl.getInstance().apagaCaronas();
		UsuarioDAOImpl.getInstance().apagaUsuarios();
		SessaoDAOImpl.getInstance().apagaSessoes();		
		
		//cria usuario e acessa a sessao
		try {
			//inicializa a sessao e o usuario
			usuarioBusiness.criarUsuario("Mark", "123", "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao("Mark", "123");						
			idCarona = caronaBusiness.cadastrarCarona("Mark", "Campina Grande", "Areia", "31/05/2015", "10:00", 4);			
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testeSugerirPontoEncontro(){		
		//sugerir ponto de encontro
		try {
			String ponto = "Acude Velho";
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, ponto);				
			String[] pontos = {"Acude Velho", "Hiper Bompreço", "Parque da Criança"};
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, pontos);	
		} catch (Exception e) {
			fail();
		}		
	}
	
	@Test
	public void testeSugerirPontoSessaoInvalida(){				
		//sugerir ponto de encontro sessao que não foi cadastrada
		try {
			String ponto = "Acude Velho";
			pontoEncontroBusiness.sugerirPontoEncontro("sessao1", idCarona, ponto);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//sugerir ponto de encontro com sessao vazia
		try {
			String ponto = "Acude Velho";
			pontoEncontroBusiness.sugerirPontoEncontro("", idCarona, ponto);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//sugerir ponto de encontro com sessao null
		try {
			String ponto = "Acude Velho";
			pontoEncontroBusiness.sugerirPontoEncontro(null, idCarona, ponto);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
	}
	
	@Test
	public void testeSugerirPontoCaronaInvalida(){		
		//sugerir ponto de encontro carona vazia
		try {
			String ponto = "Acude Velho";
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", "", ponto);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}			
		
		//sugerir ponto de encontro carona null
		try {
			String ponto = "Acude Velho";
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", null, ponto);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//sugerir ponto de encontro carona inexistente
		try {
			String ponto = "Acude Velho";
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", "C", ponto);
			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}				
	}

	@Test
	public void testeSugerirPontoInvalido(){				
		//sugerir ponto de encontro vazio
		try {
			String ponto = "";
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, ponto);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.PONTO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}			
		
		//sugerir ponto de encontro null
		try {
			String ponto = null;
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, ponto);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.PONTO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//sugerir pontos de encontro null
		try {
			String[] pontos = {"Acude Velho", null, "Parque da Criança"};
			
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, pontos);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.PONTO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
				
		//sugerir pontos de encontro vazios
		try {
			String[] pontos = {"", "", "Parque da Criança"};
			
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, pontos);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.PONTO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testResponderSugestao(){		

		try {
			String ponto = "Acude Velho";
			String idSugestao1 = pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, ponto);				
			String[] pontos = {"Hiper Bompreço", "Parque da Criança", "Colegio 11 de Outubro"};
			String idSugestao2 = pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, pontos);			
			String[] pontosAceitos = {"Parque da Criança", "Colegio 11 de Outubro"};
			
			pontoEncontroBusiness.responderSugestaoPontoEncontro("Mark", idCarona, idSugestao1, "Acude Velho");
			pontoEncontroBusiness.responderSugestaoPontoEncontro("Mark", idCarona, idSugestao2, pontosAceitos);
		} catch (Exception e) {
			fail();
		}		
	}
	
	@Test
	public void testResponderSugestaoPontoInvalido(){
		String idSugestao1 = "";
		String idSugestao2 = "";
		
		//sugerir ponto de encontro
		try {
			String ponto = "Acude Velho";
			idSugestao1 = pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, ponto);				
			String[] pontos = {"Hiper Bompreço", "Parque da Criança", "Colegio 11 de Outubro"};
			idSugestao2 = pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, pontos);			
		} catch (Exception e) {
			fail();
		}
		
		//informar ponto que nao existe na sugestao
		try {
			pontoEncontroBusiness.responderSugestaoPontoEncontro("Mark", idCarona, idSugestao1, "Parque da Criança");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.PONTO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		

		//aceitar 2 pontos e sugerir 1 
		try {				
			String[] pontosAceitos = {"Hiper Bompreço", "Parque da Criança", "Cirne Center"};
			pontoEncontroBusiness.responderSugestaoPontoEncontro("Mark", idCarona, idSugestao2, pontosAceitos);		
		} catch (Exception e) {
			fail();
		}		
		
		//lista de pontos invalidos
		try {
			String[] pontosAceitos = {"Hiper Bompreço", "", "Cirne Center"};
			pontoEncontroBusiness.responderSugestaoPontoEncontro("Mark", idCarona, idSugestao2, pontosAceitos);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.PONTO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testResponderSugestaoCaronaInvalida(){
		String idSugestao1 = "";
		String idSugestao2 = "";
		//sugerir ponto de encontro
		try {
			String ponto = "Acude Velho";
			idSugestao1 = pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, ponto);				
			String[] pontos = {"Hiper Bompreço", "Parque da Criança", "Colegio 11 de Outubro"};
			idSugestao2 = pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, pontos);			
		} catch (Exception e) {
			fail();
		}		
		
		//informar carona vazia
		try {
			pontoEncontroBusiness.responderSugestaoPontoEncontro("Mark", "", idSugestao1, "Acude Velho");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//informar carona null
		try {
			pontoEncontroBusiness.responderSugestaoPontoEncontro("Mark", null, idSugestao2, "Hiper Bompreço");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}

		//informar carona que não existe
		try {
			pontoEncontroBusiness.responderSugestaoPontoEncontro("Mark", "C", idSugestao1, "Acude Velho");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
	}
	
	@Test
	public void testResponderSugestaoInvalida(){		
		//sugerir ponto de encontro
		try {
			String ponto = "Acude Velho";
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, ponto);				
			String[] pontos = {"Hiper Bompreço", "Parque da Criança", "Colegio 11 de Outubro"};
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, pontos);			
		} catch (Exception e) {
			fail();
		}		
		
		//informar sugestao vazia
		try {
			pontoEncontroBusiness.responderSugestaoPontoEncontro("Mark", idCarona, "", "Acude Velho");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.PONTO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
		
		//informar sugestao null
		try {
			pontoEncontroBusiness.responderSugestaoPontoEncontro("Mark", idCarona, null, "Acude Velho");		
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.PONTO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		

		//informar sugestao que não existe
		try {			
			pontoEncontroBusiness.responderSugestaoPontoEncontro("Mark", idCarona, "C", "Acude Velho");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.PONTO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
	}	
	
	@Test
	public void testGetPontosSugeridos(){
		//sugerir ponto de encontro
		try {
			String ponto = "Acude Velho";
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, ponto);				
			String[] pontos = {"Hiper Bompreço", "Parque da Criança", "Colegio 11 de Outubro"};
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, pontos);			
		} catch (Exception e) {
			fail();
		}		
		
		//pontos sugeridos
		try {
			pontoEncontroBusiness.getPontosSugeridos("Mark", idCarona);			
		} catch (Exception e) {
			fail();
		}

		//lista pontos sugeridos
		try {
			String[] pontos = {"Acude Velho", "Hiper Bompreço", "Parque da Criança", "Colegio 11 de Outubro"};
			String[] pontosSugeridos = pontoEncontroBusiness.getPontosSugeridos("Mark", idCarona);
			Assert.assertEquals(pontos.length,  pontosSugeridos.length);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetPontosSugeridosSessaoInvalida(){
		//sugerir ponto de encontro
		try {
			String ponto = "Acude Velho";
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, ponto);				
			String[] pontos = {"Hiper Bompreço", "Parque da Criança", "Colegio 11 de Outubro"};
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, pontos);			
		} catch (Exception e) {
			fail();
		}		
		
		//sessao nao Cadastrada
		try {
			pontoEncontroBusiness.getPontosSugeridos("sessao1", idCarona);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
				
		//sessao vazia
		try {
			pontoEncontroBusiness.getPontosSugeridos("", idCarona);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
				
		//sessao null
		try {
			pontoEncontroBusiness.getPontosSugeridos(null, idCarona);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
	}
	
	@Test
	public void testeGetPontosSugeridosCaronaInvalida(){								
		//carona vazia
		try {
			pontoEncontroBusiness.getPontosSugeridos("Mark", "");	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}			
		
		//carona null
		try {
			pontoEncontroBusiness.getPontosSugeridos("Mark", null);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//carona inexistente
		try {
			pontoEncontroBusiness.getPontosSugeridos("Mark", "C");
			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}				
	}

	@Test
	public void getPontosEncontro(){		
		// ponto de encontro
		try {
			String ponto = "Acude Velho";
			String sugestao1 = pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, ponto);				
			String[] pontos = {"Hiper Bompreço", "Parque da Criança", "Colegio 11 de Outubro"};
			String sugestao2 = pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, pontos);
			pontoEncontroBusiness.responderSugestaoPontoEncontro("Mark", idCarona, sugestao1, ponto);				
			String[] pontosAceitos = {"Hiper Bompreço", "Parque da Criança"};
			pontoEncontroBusiness.responderSugestaoPontoEncontro("Mark", idCarona, sugestao2, pontosAceitos);
		} catch (Exception e) {
			fail();
		}		
		
		//pontos sugeridos
		try {
			String[] pontosEncontro = pontoEncontroBusiness.getPontosEncontro("Mark", idCarona);
			Assert.assertEquals(3, pontosEncontro.length);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetPontosEncontroSessaoInvalida(){			
		//sugerir ponto de encontro
		try {
			String ponto = "Acude Velho";
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, ponto);				
			String[] pontos = {"Hiper Bompreço", "Parque da Criança", "Colegio 11 de Outubro"};
			pontoEncontroBusiness.sugerirPontoEncontro("Mark", idCarona, pontos);			
		} catch (Exception e) {
			fail();
		}		
		
		//sessao nao Cadastrada
		try {
			pontoEncontroBusiness.getPontosEncontro("sessao1", idCarona);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
				
		//sessao vazia
		try {
			pontoEncontroBusiness.getPontosEncontro("", idCarona);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
				
		//sessao null
		try {
			pontoEncontroBusiness.getPontosEncontro(null, idCarona);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
	}
	
	@Test
	public void testeGetPontosEncontroCaronaInvalida(){						
		//carona vazia
		try {
			pontoEncontroBusiness.getPontosEncontro("Mark", "");	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}			
		
		//carona null
		try {
			pontoEncontroBusiness.getPontosEncontro("Mark", null);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//carona inexistente
		try {
			pontoEncontroBusiness.getPontosEncontro("Mark", "C");			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}				
	}
}
