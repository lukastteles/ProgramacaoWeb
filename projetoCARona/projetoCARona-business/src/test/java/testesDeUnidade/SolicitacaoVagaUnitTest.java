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
	
	private String idCarona;
	
	/* cria o ambiente para realizar os testes
	 * neste caso será criado 2 usuarios Mark e Teles
	 * Mark irá cadastrar uma carona com 4 vagas
	 */
	@Before
	public void iniciaBusiness(){
		usuarioBusiness = new UsuarioBusiness();
		sessaoBusiness = new SessaoBusiness();		
		caronaBusiness = new CaronaBusiness();		
		solicitacaoBusiness = new SolicitacaoVagaBusiness();
		pontoEncontro = new PontoDeEncontroBusiness();
		
		//limpa os dados antes de iniciar qualquer teste	
		CaronaDAOImpl.getInstance().apagaCaronas();
		UsuarioDAOImpl.getInstance().apagaUsuarios();
		SessaoDAOImpl.getInstance().apagaSessoes();		
		SolicitacaoVagaDAOImpl.getInstance().apagaSolicitacoes();

		try {
			//cria os usuarios
			usuarioBusiness.criarUsuario("Mark", "Mark", "Mark", "Rua", "mark@gmail.com");
			usuarioBusiness.criarUsuario("Teles", "Teles", "Teles", "Rua", "teles@gmail.com");
			
			//Mark cadastra 1 carona e sugere 1 ponto encontro
			sessaoBusiness.abrirSessao("Mark", "Mark");						
			idCarona = caronaBusiness.cadastrarCarona("Mark", "Campina Grande", "João Pessoa", "31/05/2015", "10:00", 4);
			pontoEncontro.sugerirPontoEncontro("Mark", idCarona, "Parque do Povo");
			sessaoBusiness.encerrarSessao("Mark");
			
			//Teles Sugere um conjunto de pontos	
			sessaoBusiness.abrirSessao("Teles", "Teles");		
			String[] pontos = {"Açude Velho", "Parque da Criança"};
			pontoEncontro.sugerirPontoEncontro("Teles", idCarona, pontos);
			sessaoBusiness.encerrarSessao("Teles");
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSolicitarVaga(){
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");	
			solicitacaoBusiness.solicitarVaga("Teles", idCarona);						
		} catch (Exception e) {
			fail();
		}	
		
		//verificar se o usuario que cadastrou a carona pode solicitar vaga
		try {
			sessaoBusiness.encerrarSessao("Teles");
			sessaoBusiness.abrirSessao("Mark", "Mark");
			solicitacaoBusiness.solicitarVaga("Mark", idCarona);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_NAO_IDENTIFICADA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//tentar solicitar vaga em carona já concluida
		try {
			//solicitar 4 vagas
			sessaoBusiness.encerrarSessao("Mark");
			sessaoBusiness.abrirSessao("Teles", "Teles");
			int idSolicitacao1 = solicitacaoBusiness.solicitarVaga("Teles", idCarona);
			int idSolicitacao2 = solicitacaoBusiness.solicitarVaga("Teles", idCarona);
			int idSolicitacao3 = solicitacaoBusiness.solicitarVaga("Teles", idCarona);
			int idSolicitacao4 = solicitacaoBusiness.solicitarVaga("Teles", idCarona);
			
			//aceitar 4 vagas para a carona estar completa
			sessaoBusiness.encerrarSessao("Teles");
			sessaoBusiness.abrirSessao("Mark", "Mark");
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao1+"");
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao2+"");
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao3+"");
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao4+"");
			
			//tenta solicitar a carona que já está completa
			sessaoBusiness.encerrarSessao("Mark");			
			sessaoBusiness.abrirSessao("Teles", "Teles");
			solicitacaoBusiness.solicitarVaga("Teles", idCarona);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.VAGAS_OCUPADAS, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSolicitarVagaSessaoInvalida(){	
		//sessao vazia
		try {
			solicitacaoBusiness.solicitarVaga("", idCarona);
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//sessao null
		try {
			solicitacaoBusiness.solicitarVaga(null, idCarona);
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//sessao invalida
		try {
			solicitacaoBusiness.solicitarVaga("sessao1", idCarona);
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
	}

	@Test
	public void testSolicitarVagaCaronaInvalida(){			
		//inicia a sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
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
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");	
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque do Povo");				
		} catch (Exception e) {
			fail();
		}	
		

		//tentar solicitar vaga com novo ponto 
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Aeroporto");	
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		
		//tentar solicitar vaga na carona do proprio usuario
		try {
			sessaoBusiness.abrirSessao("Mark", "Mark");
			solicitacaoBusiness.solicitarVagaPontoEncontro("Mark", idCarona, "Parque do Povo");	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_NAO_IDENTIFICADA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//tentar solicitar vaga em carona já concluida
		try {
			//solicitar 4 vagas
			sessaoBusiness.encerrarSessao("Mark");
			sessaoBusiness.abrirSessao("Teles", "Teles");
			int idSolicitacao1 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque do Povo");
			int idSolicitacao2 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque do Povo");
			int idSolicitacao3 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque do Povo");
			int idSolicitacao4 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque do Povo");
				
			//aceitar 4 vagas para a carona estar completa
			sessaoBusiness.encerrarSessao("Teles");
			sessaoBusiness.abrirSessao("Mark", "Mark");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao1+"");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao2+"");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao3+"");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao4+"");
					
			//tenta solicitar a carona que já está completa
			sessaoBusiness.encerrarSessao("Mark");			
			sessaoBusiness.abrirSessao("Teles", "Teles");
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque do Povo");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.VAGAS_OCUPADAS, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSolicitarVagaPontoSessaoInvalida(){
		//sessao vazia
		try {
			solicitacaoBusiness.solicitarVagaPontoEncontro("", idCarona, "Parque da Criança");
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
				
		//sessao null
		try {
			solicitacaoBusiness.solicitarVagaPontoEncontro(null, idCarona, "Parque da Criança");
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
				
		//sessao inexistente
		try {
			solicitacaoBusiness.solicitarVagaPontoEncontro("sessao", idCarona, "Parque da Criança");
		}  catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}					
	}
	
	@Test
	public void testSolicitarVagaPontoCaronaInvalida(){
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");					
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
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");					
		} catch (Exception e) {
			fail();
		}
		
		//Cria uma solicitacao e verifica o retorno
		int idSolicitacao=0;
		try {
			idSolicitacao = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque do Povo");
			Assert.assertEquals("Campina Grande", solicitacaoBusiness.getAtributoSolicitacao(idSolicitacao+"", "origem"));
			Assert.assertEquals("João Pessoa", solicitacaoBusiness.getAtributoSolicitacao(idSolicitacao+"", "destino"));
			Assert.assertEquals("Mark", solicitacaoBusiness.getAtributoSolicitacao(idSolicitacao+"", "Dono da carona"));
			Assert.assertEquals("Teles", solicitacaoBusiness.getAtributoSolicitacao(idSolicitacao+"", "Dono da solicitacao"));
			Assert.assertEquals("Parque do Povo", solicitacaoBusiness.getAtributoSolicitacao(idSolicitacao+"", "Ponto de Encontro"));
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetAtributoSolicitacaoInexistente(){
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");					
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
		
	}
	
	@Test
	public void testGetAtributoSolicitacaoInvalido(){
		int idSolicitacao=0;
		
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque do Povo");
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
		//abrir sessao
		int idSolicitacao = 0;
		int idSolicitacao2 = 0;
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao = solicitacaoBusiness.solicitarVaga("Teles", idCarona);
			idSolicitacao2 = solicitacaoBusiness.solicitarVaga("Teles", idCarona);
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");					
		} catch (Exception e) {
			fail();
		}
			
		try {			
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao+"");
		} catch (Exception e) {
			fail();
		}
		
		//aceitar a mesma solicitacao
		try {			
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//um usuario tentar aceitar a solicitacao de carona que pertence a outro
		try {			
			solicitacaoBusiness.aceitarSolicitacao("Teles", idSolicitacao2+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetAceitarSolicitacaoSessaoInvalida(){
		//abrir sessao
		int idSolicitacao1 = 0;
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao1 = solicitacaoBusiness.solicitarVaga("Teles", idCarona);
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");					
		} catch (Exception e) {
			fail();
		}
		
		//sessao inexistente
		try {
			solicitacaoBusiness.aceitarSolicitacao("sessao", idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//sessao vazia
		try {			
			solicitacaoBusiness.aceitarSolicitacao("", idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//sessao null
		try {			
			solicitacaoBusiness.aceitarSolicitacao(null, idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
	}
	
	@Test
	public void testGetAceitarSolicitacaoInvalida(){
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			solicitacaoBusiness.solicitarVaga("Teles", idCarona);
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");					
		} catch (Exception e) {
			fail();
		}
		
		//carona inexistente
		try {
			solicitacaoBusiness.aceitarSolicitacao("Mark", "C");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//carona vazia
		try {
			solicitacaoBusiness.aceitarSolicitacao("Mark", "");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//carona null
		try {
			solicitacaoBusiness.aceitarSolicitacao("Mark", null);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
	}
	
	@Test
	public void testAceitarSolicitacaoPontoEncontro(){
		int idSolicitacao1 = 0;
		int idSolicitacao2 = 0;
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao1 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			idSolicitacao2 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque da Criança");
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");					
		} catch (Exception e) {
			fail();
		}		
						
		try {
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao1+"");
		} catch (Exception e) {
			fail();
		}
		
		//aceitar a mesma solicitacao
		try {			
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//tentar aceitar solicitacao de outro usuario
		try {
			sessaoBusiness.encerrarSessao("Mark");
			sessaoBusiness.abrirSessao("Teles", "Teles");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Teles", idSolicitacao2+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testAceitarSolicitacaoPontoEncontroSessaoInvalida(){
		int idSolicitacao = 0;
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");					
		} catch (Exception e) {
			fail();
		}		
			
		//sessao inexistente
		try {
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("sessao", idSolicitacao+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//sessao vazia
		try {
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("", idSolicitacao+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//sessao null
		try {
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro(null, idSolicitacao+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testAceitarSolicitacaoPontoEncontroInvalida(){		
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");					
		} catch (Exception e) {
			fail();
		}		
			
		//carona inexistente
		try {
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", "C");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//carona vazia
		try {
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", "");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//carona null
		try {
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", "");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
	}
	
	@Test
	public void testGetSolicitacoesConfirmadas(){
		int idSolicitacao1 = 0;
		int idSolicitacao2 = 0;
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao1 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			idSolicitacao2 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque da Criança");
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque do Povo");
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");		
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao1+"");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao2+"");
		} catch (Exception e) {
			fail();
		}		

		try {							
			ArrayList<SolicitacaoVagaDomain> solicitacoes = new ArrayList<SolicitacaoVagaDomain>();						
			solicitacoes =  solicitacaoBusiness.getSolicitacoesConfirmadas("Mark", idCarona);
			
			Assert.assertEquals(2, solicitacoes.size());
		} catch (Exception e) {
			fail();
		}
		
		//tentar ver solicitacoes de outro usuario
		try {
			sessaoBusiness.encerrarSessao("Mark");			
			sessaoBusiness.abrirSessao("Teles", "Teles");	
			solicitacaoBusiness.getSolicitacoesConfirmadas("Teles", idCarona);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_NAO_IDENTIFICADA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetSolicitacoesConfirmadasSessaoInvalida(){
		int idSolicitacao1 = 0;
		int idSolicitacao2 = 0;
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao1 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			idSolicitacao2 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque da Criança");
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque do Povo");
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");		
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao1+"");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao2+"");
		} catch (Exception e) {
			fail();
		}	
	
		//sessao inexistente
		try {
			solicitacaoBusiness.getSolicitacoesConfirmadas("sessao", idCarona);
		} catch (ProjetoCaronaException projetoCaronaErro) {
					assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//sessao vazia
		try {
			solicitacaoBusiness.getSolicitacoesConfirmadas("", idCarona);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//sessao null
		try {
			solicitacaoBusiness.getSolicitacoesConfirmadas(null, idCarona);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
			
	}
	
	@Test
	public void testGetSolicitacoesConfirmadasCaronaInvalida(){
		int idSolicitacao1 = 0;
		int idSolicitacao2 = 0;
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao1 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			idSolicitacao2 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque da Criança");
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque do Povo");
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");		
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao1+"");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao2+"");
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
		int idSolicitacao1 = 0;
		int idSolicitacao2 = 0;
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao1 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			idSolicitacao2 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque da Criança");
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque do Povo");
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");		
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao1+"");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao2+"");
		} catch (Exception e) {
			fail();
		}	
		
		try {							
			ArrayList<SolicitacaoVagaDomain> solicitacoes = new ArrayList<SolicitacaoVagaDomain>();						
			solicitacoes =  solicitacaoBusiness.getSolicitacoesPendentes("Mark", idCarona);
			
			Assert.assertEquals(1, solicitacoes.size());
		} catch (Exception e) {
			fail();
		}
		
		//tentar ver solicitacoes de outro usuario
		try {
			sessaoBusiness.encerrarSessao("Mark");			
			sessaoBusiness.abrirSessao("Teles", "Teles");	
			solicitacaoBusiness.getSolicitacoesPendentes("Teles", idCarona);
		} catch (ProjetoCaronaException projetoCaronaErro) {
				assertEquals(MensagensErro.CARONA_NAO_IDENTIFICADA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testGetSolicitacoesPendentesSessaoInvalida(){
		int idSolicitacao1 = 0;
		int idSolicitacao2 = 0;
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao1 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			idSolicitacao2 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque da Criança");
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque do Povo");
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");		
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao1+"");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao2+"");
		} catch (Exception e) {
			fail();
		}	
		
		//sessao inexistente
		try {
			solicitacaoBusiness.getSolicitacoesPendentes("sessao", idCarona);
		} catch (ProjetoCaronaException projetoCaronaErro) {
					assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//sessao vazia
		try {
			solicitacaoBusiness.getSolicitacoesPendentes("", idCarona);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//sessao null
		try {
			solicitacaoBusiness.getSolicitacoesPendentes(null, idCarona);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
				
	@Test
	public void testGetSolicitacoesPendentesCaronaInvalida(){
		int idSolicitacao1 = 0;
		int idSolicitacao2 = 0;
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao1 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			idSolicitacao2 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque da Criança");
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque do Povo");
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");		
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao1+"");
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao2+"");
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
	
	@Test
	public void testDesistirRequisicao(){
		int idSolicitacao1 = 0;
		int idSolicitacao3 = 0;
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao1 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Parque da Criança");
			idSolicitacao3 = solicitacaoBusiness.solicitarVaga("Teles", idCarona);
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");		
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao1+"");
		} catch (Exception e) {
			fail();
		}
		
		//desistir de solicitacao confirmada e nao confirmada
		try {		
			sessaoBusiness.encerrarSessao("Mark");			
			sessaoBusiness.abrirSessao("Teles", "Teles");						
			solicitacaoBusiness.desistirRequisicao("Teles", idCarona, idSolicitacao1+"");
			solicitacaoBusiness.desistirRequisicao("Teles", idCarona, idSolicitacao3+"");
		} catch (Exception e) {
			fail();
		}
		
		//informar usuario diferente para a solicitacao
		try {
			sessaoBusiness.encerrarSessao("Teles");			
			sessaoBusiness.abrirSessao("Mark", "Mark");	
			solicitacaoBusiness.desistirRequisicao("Mark", idCarona, idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
				assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
		
	@Test
	public void testDesistirRequisicaoSessaoInvalida(){
		int idSolicitacao1 = 0;
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao1 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");		
		} catch (Exception e) {
			fail();
		}
				
		//sessao invalida
		try {
			solicitacaoBusiness.desistirRequisicao("sessao", idCarona, idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//sessao vazia
		try {
			solicitacaoBusiness.desistirRequisicao("", idCarona, idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//sessao null
		try {
			solicitacaoBusiness.desistirRequisicao(null, idCarona, idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testDesistirRequisicaoCaronaInvalida(){
		int idSolicitacao1 = 0;
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao1 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");		
		} catch (Exception e) {
			fail();
		}
				
		//carona invalida
		try {
			solicitacaoBusiness.desistirRequisicao("Mark", "C", idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//carona vazia
		try {
			solicitacaoBusiness.desistirRequisicao("Mark", "", idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//carona null
		try {
			solicitacaoBusiness.desistirRequisicao("Mark", null, idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testDesistirRequisicaoSolicitacaoInvalida(){

		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");		
		} catch (Exception e) {
			fail();
		}
				
		//solicitacao invalida
		try {
			solicitacaoBusiness.desistirRequisicao("Mark", idCarona, "C");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//solicitacao vazia
		try {
			solicitacaoBusiness.desistirRequisicao("Mark", idCarona, "");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//solicitacao null
		try {
			solicitacaoBusiness.desistirRequisicao("Mark", idCarona, null);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRejeitaoSolicitacao(){
		int idSolicitacao1 = 0;
		int idSolicitacao3 = 0;
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao1 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			idSolicitacao3 = solicitacaoBusiness.solicitarVaga("Teles", idCarona);
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");		
			solicitacaoBusiness.aceitarSolicitacaoPontoEncontro("Mark", idSolicitacao1+"");
		} catch (Exception e) {
			fail();
		}
		
		//informar usuario diferente para a solicitacao
		try {
			sessaoBusiness.encerrarSessao("Mark");			
			sessaoBusiness.abrirSessao("Teles", "Teles");	
			solicitacaoBusiness.rejeitarSolicitacao("Teles", idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//rejeitar solicitacoes
		try {							
			sessaoBusiness.encerrarSessao("Teles");			
			sessaoBusiness.abrirSessao("Mark", "Mark");		
			solicitacaoBusiness.rejeitarSolicitacao("Mark", idSolicitacao1+"");
			solicitacaoBusiness.rejeitarSolicitacao("Mark", idSolicitacao3+"");
		} catch (Exception e) {
			fail();
		}
		
		
	}
	
	@Test
	public void testRejeitaoSolicitacaoSessaoInvalida(){
		int idSolicitacao1 = 0;
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			idSolicitacao1 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");		
		} catch (Exception e) {
			fail();
		}
		
		//sessao invalida
		try {								
			solicitacaoBusiness.rejeitarSolicitacao("sessao", idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//sessao vazia
		try {								
			solicitacaoBusiness.rejeitarSolicitacao("", idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	
		//sessao null
		try {								
			solicitacaoBusiness.rejeitarSolicitacao(null, idSolicitacao1+"");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
	}
	
	@Test
	public void testRejeitaoSolicitacaoSolicitacaoInvalida(){
		//abrir sessao
		try {
			sessaoBusiness.abrirSessao("Teles", "Teles");
			solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			sessaoBusiness.encerrarSessao("Teles");
			
			sessaoBusiness.abrirSessao("Mark", "Mark");		
		} catch (Exception e) {
			fail();
		}
		
		//solicitacao invalida
		try {								
			solicitacaoBusiness.rejeitarSolicitacao("Mark", "C");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//solicitacao vazia
		try {								
			solicitacaoBusiness.rejeitarSolicitacao("Mark", "");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	
		//solicitacao null
		try {								
			solicitacaoBusiness.rejeitarSolicitacao("Mark", null);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
	}	

}
