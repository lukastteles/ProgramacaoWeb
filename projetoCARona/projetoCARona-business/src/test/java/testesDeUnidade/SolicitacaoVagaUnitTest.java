package testesDeUnidade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.br.uepb.business.CaronaBusiness;
import com.br.uepb.business.PontoDeEncontroBusiness;
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.business.SolicitacaoVagaBusiness;
import com.br.uepb.business.UsuarioBusiness;
import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.SolicitacaoVagaDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class SolicitacaoVagaUnitTest {

	private UsuarioBusiness usuarioBusiness;
	private SessaoBusiness sessaoBusiness;
	private CaronaBusiness caronaBusiness;	
	private SolicitacaoVagaBusiness solicitacaoBusiness;
	private PontoDeEncontroBusiness pontoEncontro;
	
	@Before
	public void iniciaBusiness(){
		usuarioBusiness = new UsuarioBusiness();
		sessaoBusiness = new SessaoBusiness();		
		caronaBusiness = new CaronaBusiness();		
		solicitacaoBusiness = new SolicitacaoVagaBusiness();
		pontoEncontro = new PontoDeEncontroBusiness();
		
		//limpa os dados antes de iniciar		
		CaronaDAOImpl.getInstance().apagaCaronas();
		UsuarioDAOImpl.getInstance().apagaUsuarios();
		SessaoDAOImpl.getInstance().apagaSessoes();		
		SolicitacaoVagaDAOImpl.getInstance().apagaSolicitacoes();
	}
	
	@Test
	public void testSolicitarVaga(){
		String caronaID1 = "";
		
		//cria usuario e acessa a sessao
		try {
			//inicializa a sessao e o usuario
			usuarioBusiness.criarUsuario("Mark", "123", "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao("Mark", "123");						
			caronaID1 = caronaBusiness.cadastrarCarona("Mark", "Campina Grande", "Areia", "31/05/2015", "10:00", 4);
			sessaoBusiness.encerrarSessao("Mark");
			
			usuarioBusiness.criarUsuario("Teles", "123", "Teles", "Rua", "teles@gmail.com");
			sessaoBusiness.abrirSessao("Teles", "123");
			
		} catch (Exception e) {
			fail();
		}
		
		try {
			solicitacaoBusiness.solicitarVaga("Teles", caronaID1);						
		} catch (Exception e) {
			fail();
		}				
	}
	
	@Test
	public void testSolicitarVagaSessaoInvalida(){
		String caronaID1 = "";
		
		//cria usuario e acessa a sessao
		try {
			//inicializa a sessao e o usuario
			usuarioBusiness.criarUsuario("Mark", "123", "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao("Mark", "123");						
			caronaID1 = caronaBusiness.cadastrarCarona("Mark", "Campina Grande", "Areia", "31/05/2015", "10:00", 5);
			
			usuarioBusiness.criarUsuario("Teles", "123", "Teles", "Rua", "teles@gmail.com");
			sessaoBusiness.abrirSessao("Teles", "123");
		} catch (Exception e) {
			fail();
		}
		
		//sessao vazia
		try {
			solicitacaoBusiness.solicitarVaga("", caronaID1);
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//sessao null
		try {
			solicitacaoBusiness.solicitarVaga(null, caronaID1);
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//sessao inexistente
		try {
			solicitacaoBusiness.solicitarVaga("sessao1", caronaID1);
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
	}

	@Test
	public void testSolicitarVagaCaronaInvalida(){		
		//cria usuario e acessa a sessao
		try {
			//inicializa a sessao e o usuario
			usuarioBusiness.criarUsuario("Mark", "123", "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao("Mark", "123");						
			caronaBusiness.cadastrarCarona("Mark", "Campina Grande", "Areia", "31/05/2015", "10:00", 5);
			
			usuarioBusiness.criarUsuario("Teles", "123", "Teles", "Rua", "teles@gmail.com");
			sessaoBusiness.abrirSessao("Teles", "123");
		} catch (Exception e) {
			fail();
		}
		
		//carona vazia
		try {
			solicitacaoBusiness.solicitarVaga("Teles", "");
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//carona null
		try {
			solicitacaoBusiness.solicitarVaga("Teles", null);
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//carona inexistente
		try {
			solicitacaoBusiness.solicitarVaga("Teles", "C");
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
	}
	
	@Test
	public void testSolicitarVagaPonto(){
		//cria usuario e acessa a sessao e adiciona Pontos de encontros
		String caronaID1 = null;
		try {
			//inicializa a sessao e o usuario
			usuarioBusiness.criarUsuario("Mark", "123", "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao("Mark", "123");						
			caronaID1 = caronaBusiness.cadastrarCarona("Mark", "Campina Grande", "Areia", "31/05/2015", "10:00", 4);
			
			usuarioBusiness.criarUsuario("Teles", "123", "Teles", "Rua", "teles@gmail.com");
			sessaoBusiness.abrirSessao("Teles", "123");
			String[] pontos = {"Açude Velho", "Parque da Criança"};
			pontoEncontro.sugerirPontoEncontro("Teles", caronaID1, pontos);
			pontoEncontro.sugerirPontoEncontro("Teles", caronaID1, "Parque do Povo");			
			
		} catch (Exception e) {
			fail();
		}
		
		try {
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", caronaID1, "Parque do Povo");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}	
		
		//tentar solicitar vaga para ponto nao aceito
		try {
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", caronaID1, "Parque da Criança");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.PONTO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSolicitarVagaPontoSessaoInvalida(){
		//cria usuario e acessa a sessao e adiciona Pontos de encontros
		String caronaID1 = null;
		try {
			//inicializa a sessao e o usuario
			usuarioBusiness.criarUsuario("Mark", "123", "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao("Mark", "123");						
			caronaID1 = caronaBusiness.cadastrarCarona("Mark", "Campina Grande", "Areia", "31/05/2015", "10:00", 4);
			
			usuarioBusiness.criarUsuario("Teles", "123", "Teles", "Rua", "teles@gmail.com");
			sessaoBusiness.abrirSessao("Teles", "123");							
			
		} catch (Exception e) {
			fail();
		}
		
		//sessao vazia
		try {
			solicitacaoBusiness.solicitarVagaPontoEncontro("", caronaID1, "Parque da Criança");
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
				
		//sessao null
		try {
			solicitacaoBusiness.solicitarVagaPontoEncontro(null, caronaID1, "Parque da Criança");
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
				
		//sessao inexistente
		try {
			solicitacaoBusiness.solicitarVagaPontoEncontro("sessao", caronaID1, "Parque da Criança");
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}					
	}
	
	@Test
	public void testSolicitarVagaPontoCaronaInvalida(){		
		//cria usuario e acessa a sessao
		String caronaID1 = null;
		try {
			//inicializa a sessao e o usuario
			usuarioBusiness.criarUsuario("Mark", "123", "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao("Mark", "123");						
			caronaID1 = caronaBusiness.cadastrarCarona("Mark", "Campina Grande", "Areia", "31/05/2015", "10:00", 4);
			
			usuarioBusiness.criarUsuario("Teles", "123", "Teles", "Rua", "teles@gmail.com");
			sessaoBusiness.abrirSessao("Teles", "123");
			String[] pontos = {"Açude Velho", "Parque da Criança"};
			pontoEncontro.sugerirPontoEncontro("Teles", caronaID1, pontos);
			pontoEncontro.sugerirPontoEncontro("Teles", caronaID1, "Parque do Povo");					
			
		} catch (Exception e) {
			fail();
		}
		
		//carona vazia
		try {
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", "", "Parque do Povo");
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//carona null
		try {
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", null, "Parque do Povo");
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//carona inexistente
		try {
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", "C", "Parque do Povo");
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}			
	}
	
	@Test
	public void testGetAtributoSolicitacoes(){
		//cria usuario e acessa a sessao
		String caronaID1 = null;
		try {
		//inicializa a sessao e o usuario
			usuarioBusiness.criarUsuario("Mark", "123", "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao("Mark", "123");						
			caronaID1 = caronaBusiness.cadastrarCarona("Mark", "Campina Grande", "Areia", "31/05/2015", "10:00", 4);
					
			usuarioBusiness.criarUsuario("Teles", "123", "Teles", "Rua", "teles@gmail.com");
			sessaoBusiness.abrirSessao("Teles", "123");
			String[] pontos = {"Açude Velho", "Parque da Criança"};
			pontoEncontro.sugerirPontoEncontro("Teles", caronaID1, pontos);
			pontoEncontro.sugerirPontoEncontro("Teles", caronaID1, "Parque do Povo");					
			
		} catch (Exception e) {
			fail();
		}
		
		int idSolicitacao=0;
		try {
			idSolicitacao = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", caronaID1, "Parque do Povo");
			Assert.assertEquals("Campina Grande", solicitacaoBusiness.getAtributoSolicitacao(idSolicitacao+"", "origem"));
			Assert.assertEquals("Areia", solicitacaoBusiness.getAtributoSolicitacao(idSolicitacao+"", "destino"));
			Assert.assertEquals("Mark", solicitacaoBusiness.getAtributoSolicitacao(idSolicitacao+"", "Dono da carona"));
			Assert.assertEquals("Teles", solicitacaoBusiness.getAtributoSolicitacao(idSolicitacao+"", "Dono da solicitacao"));
			Assert.assertEquals("Parque do Povo", solicitacaoBusiness.getAtributoSolicitacao(idSolicitacao+"", "Ponto de Encontro"));
		} catch (Exception e) {
			fail();
		}
		
		//solicitacao inexistente
		try {
			solicitacaoBusiness.getAtributoSolicitacao("C", "destino");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//solicitacao vazia
		try {
			solicitacaoBusiness.getAtributoSolicitacao("", "destino");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//solicitacao null
		try {
			solicitacaoBusiness.getAtributoSolicitacao(null, "destino");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//atributo inexistente
		try {
			solicitacaoBusiness.getAtributoSolicitacao(idSolicitacao+"", "Dados da Carona");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.ATRIBUTO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//atributo vazio
		try {
			solicitacaoBusiness.getAtributoSolicitacao(idSolicitacao+"", "");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.ATRIBUTO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}

		//atributo null
		try {
			solicitacaoBusiness.getAtributoSolicitacao(idSolicitacao+"", null);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.ATRIBUTO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
	}
	
	@Test
	public void testGetAceitarSolicitacao(){
		//cria usuario e acessa a sessao
		String caronaID1 = null;
		try {
			//inicializa a sessao e o usuario
			usuarioBusiness.criarUsuario("Mark", "123", "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao("Mark", "123");						
			caronaID1 = caronaBusiness.cadastrarCarona("Mark", "Campina Grande", "Areia", "31/05/2015", "10:00", 4);
					
			usuarioBusiness.criarUsuario("Teles", "123", "Teles", "Rua", "teles@gmail.com");
			sessaoBusiness.abrirSessao("Teles", "123");
			String[] pontos = {"Açude Velho", "Parque da Criança"};
			pontoEncontro.sugerirPontoEncontro("Teles", caronaID1, pontos);
			pontoEncontro.sugerirPontoEncontro("Teles", caronaID1, "Parque do Povo");					
				
		} catch (Exception e) {
			fail();
		}
						
		try {
			int idSolicitacao = solicitacaoBusiness.solicitarVaga("Teles", caronaID1);
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao+"");
		} catch (Exception e) {
			fail();
		}
		
		
		//sessao inexistente
		try {
			int idSolicitacao1 = solicitacaoBusiness.solicitarVaga("Teles", caronaID1);
			solicitacaoBusiness.aceitarSolicitacao("sessao", idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//sessao vazia
		try {
			int idSolicitacao1 = solicitacaoBusiness.solicitarVaga("Teles", caronaID1);
			solicitacaoBusiness.aceitarSolicitacao("", idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//sessao null
		try {
			int idSolicitacao1 = solicitacaoBusiness.solicitarVaga("Teles", caronaID1);
			solicitacaoBusiness.aceitarSolicitacao(null, idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//carona inexistente
		try {
			solicitacaoBusiness.solicitarVaga("Teles", caronaID1);
			solicitacaoBusiness.aceitarSolicitacao("Mark", "C");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//carona vazia
		try {
			solicitacaoBusiness.solicitarVaga("Teles", caronaID1);
			solicitacaoBusiness.aceitarSolicitacao("Mark", "");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//carona null
		try {
			solicitacaoBusiness.solicitarVaga("Teles", caronaID1);
			solicitacaoBusiness.aceitarSolicitacao("Mark", null);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
	}
	
	@Test
	public void testAceitarSolicitacaoPontoEncontro(){
		//cria usuario e acessa a sessao
		String caronaID1 = null;
		try {
			//inicializa a sessao e o usuario
			usuarioBusiness.criarUsuario("Mark", "123", "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao("Mark", "123");						
			caronaID1 = caronaBusiness.cadastrarCarona("Mark", "Campina Grande", "Areia", "31/05/2015", "10:00", 4);
					
			usuarioBusiness.criarUsuario("Teles", "123", "Teles", "Rua", "teles@gmail.com");
			sessaoBusiness.abrirSessao("Teles", "123");
			String[] pontos = {"Açude Velho", "Parque da Criança"};
			pontoEncontro.sugerirPontoEncontro("Teles", caronaID1, pontos);
			pontoEncontro.sugerirPontoEncontro("Teles", caronaID1, "Parque do Povo");					
				
		} catch (Exception e) {
			fail();
		}
						
		try {
			int idSolicitacao = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", caronaID1, "Parque do Povo");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao+"");
		} catch (Exception e) {
			fail();
		}
		
		
		//sessao inexistente
		try {
			int idSolicitacao = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", caronaID1, "Parque do Povo");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("sessao", idSolicitacao+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//sessao vazia
		try {
			int idSolicitacao = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", caronaID1, "Parque do Povo");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("", idSolicitacao+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//sessao null
		try {
			int idSolicitacao = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", caronaID1, "Parque do Povo");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro(null, idSolicitacao+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//carona inexistente
		try {
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", caronaID1, "Parque do Povo");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", "C");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//carona vazia
		try {
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", caronaID1, "Parque do Povo");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", "");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//carona null
		try {
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", caronaID1, "Parque do Povo");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", "");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
	}
	
	@Test
	public void testGetSolicitacoesConfirmadas(){
		String caronaID1 = null;
		try {
			//inicializa a sessao e o usuario
			usuarioBusiness.criarUsuario("Mark", "123", "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao("Mark", "123");						
			caronaID1 = caronaBusiness.cadastrarCarona("Mark", "Campina Grande", "Areia", "31/05/2015", "10:00", 4);
					
			usuarioBusiness.criarUsuario("Teles", "123", "Teles", "Rua", "teles@gmail.com");
			sessaoBusiness.abrirSessao("Teles", "123");
		} catch (Exception e) {
			fail();
		}
		
		try {
			int idSolicitacao1 = solicitacaoBusiness.solicitarVaga("Teles", caronaID1);
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao1+"");
			int idSolicitacao2 = solicitacaoBusiness.solicitarVaga("Teles", caronaID1);
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao2+"");
			
			ArrayList<SolicitacaoVagaDomain> solicitacoes = new ArrayList<SolicitacaoVagaDomain>();						
			solicitacoes =  solicitacaoBusiness.getSolicitacoesConfirmadas("Mark", caronaID1);
			Assert.assertEquals(2, solicitacoes.size());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		
		//sessao inexistente
		try {
			solicitacaoBusiness.getSolicitacoesConfirmadas("sessao", caronaID1);
		} catch (ProjetoCaronaException projetoCaronaErro) {
					assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//sessao vazia
		try {
			solicitacaoBusiness.getSolicitacoesConfirmadas("", caronaID1);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//sessao null
		try {
			solicitacaoBusiness.getSolicitacoesConfirmadas(null, caronaID1);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//carona inexistente
		try {
			solicitacaoBusiness.getSolicitacoesConfirmadas("Mark", "C");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
						
		//carona vazia
		try {
			solicitacaoBusiness.getSolicitacoesConfirmadas("Mark", "");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
						
		//carona null
		try {
			solicitacaoBusiness.getSolicitacoesConfirmadas("Mark", null);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
	}
	
	@Test
	public void testGetSolicitacoesPendentes(){
		String caronaID1 = null;
		try {
			//inicializa a sessao e o usuario
			usuarioBusiness.criarUsuario("Mark", "123", "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao("Mark", "123");						
			caronaID1 = caronaBusiness.cadastrarCarona("Mark", "Campina Grande", "Areia", "31/05/2015", "10:00", 4);
					
			usuarioBusiness.criarUsuario("Teles", "123", "Teles", "Rua", "teles@gmail.com");
			sessaoBusiness.abrirSessao("Teles", "123");
		} catch (Exception e) {
			fail();
		}
		
		try {
			int idSolicitacao1 = solicitacaoBusiness.solicitarVaga("Teles", caronaID1);
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao1+"");
			solicitacaoBusiness.solicitarVaga("Teles", caronaID1);
			solicitacaoBusiness.solicitarVaga("Teles", caronaID1);
			solicitacaoBusiness.solicitarVaga("Teles", caronaID1);
			
			ArrayList<SolicitacaoVagaDomain> solicitacoes = new ArrayList<SolicitacaoVagaDomain>();						
			solicitacoes =  solicitacaoBusiness.getSolicitacoesPendentes("Mark", caronaID1);
			Assert.assertEquals(3, solicitacoes.size());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		
		//sessao inexistente
		try {
			solicitacaoBusiness.getSolicitacoesPendentes("sessao", caronaID1);
		} catch (ProjetoCaronaException projetoCaronaErro) {
					assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//sessao vazia
		try {
			solicitacaoBusiness.getSolicitacoesPendentes("", caronaID1);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//sessao null
		try {
			solicitacaoBusiness.getSolicitacoesPendentes(null, caronaID1);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//carona inexistente
		try {
			solicitacaoBusiness.getSolicitacoesPendentes("Mark", "C");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
						
		//carona vazia
		try {
			solicitacaoBusiness.getSolicitacoesPendentes("Mark", "");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
						
		//carona null
		try {
			solicitacaoBusiness.getSolicitacoesPendentes("Mark", null);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
	}
	

	//public void desistirRequisicao(String idSessao, String idCarona, String idSolicitacao) throws Exception
	//public void rejeitarSolicitacao(String idSessao, String idSolicitacao) throws Exception
}
