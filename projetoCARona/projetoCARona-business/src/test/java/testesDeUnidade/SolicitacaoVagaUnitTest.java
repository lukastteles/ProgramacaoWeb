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
import com.br.uepb.dao.impl.InteresseEmCaronaDAOImpl;
import com.br.uepb.dao.impl.PontoDeEncontroDAOImpl;
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
		
		//limpa os dados antes de iniciar
		SolicitacaoVagaDAOImpl.getInstance().apagaSolicitacoes();
		PontoDeEncontroDAOImpl.getInstance().apagaPontosEncontro();
		CaronaDAOImpl.getInstance().apagaCaronas();		
		UsuarioDAOImpl.getInstance().apagaUsuarios();
		SessaoDAOImpl.getInstance().apagaSessoes();
		InteresseEmCaronaDAOImpl.getInstance().apagaInteresses();

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
		
		//Verificar se um usuario nao preferencial consegue solicitar uma carona preferencial
		try {
			//definir carona como preferencial
			caronaBusiness.definirCaronaPreferencial(idCarona);
			
			//testar se o usuario consegue solicitar alguma vaga em uma carona que ele não tem preferencia
			sessaoBusiness.abrirSessao("Teles", "Teles");
			solicitacaoBusiness.solicitarVaga("Teles", idCarona);
			sessaoBusiness.encerrarSessao("Teles");
			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.USUARIO_NAO_PREFERENCIAL, projetoCaronaErro.getMessage());
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
		

		//tentar solicitar vaga com novo ponto - deve criar o novo ponto de encontro 
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
		
		//saber a quantidade de usuarios da carona
		try{
			assertEquals(1, caronaBusiness.getUsuariosByCarona(idCarona).length);
		}catch(Exception e){
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
			assertEquals(MensagensErro.SOLICITACAO_INEXISTENTE, projetoCaronaErro.getMessage());
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
	
	@Test
	public void testeReviewVagaEmCaronaSessaoInvalida(){
		//Preparar o ambiente para os testes
		try {
			//Teles solicita uma vaga na carona
			sessaoBusiness.abrirSessao("Teles", "Teles");
			int idSolicitacao = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			sessaoBusiness.encerrarSessao("Teles");
			
			//Mark aceita a solicitacao de teles
			sessaoBusiness.abrirSessao("Mark", "Mark");
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao+"");
			
		} catch (Exception e) {
			fail();
		}
				
		//reviewVagaEmCarona - sessao vazia
		try {								
			solicitacaoBusiness.reviewVagaEmCarona("", idCarona, "Teles", MensagensErro.FALTOU);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//reviewVagaEmCarona - sessao null
		try {								
			solicitacaoBusiness.reviewVagaEmCarona(null, idCarona, "Teles", MensagensErro.FALTOU);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//reviewVagaEmCarona - sessao inexistente
		try {								
			solicitacaoBusiness.reviewVagaEmCarona("sessao", idCarona, "Teles", MensagensErro.FALTOU);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
				
		//reviewVagaEmCarona - usuario que nao é o dono da carona
		try {					
			usuarioBusiness.criarUsuario("Luana", "luana", "luana", "rua", "luana@email.com");
			sessaoBusiness.abrirSessao("Luana", "luana");
			solicitacaoBusiness.reviewVagaEmCarona("Luana", idCarona, "Teles", MensagensErro.FALTOU);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SOLICITACAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testeReviewVagaEmCarona_CaronaInvalida(){
		//Preparar o ambiente para os testes
		try {
			//Teles solicita uma vaga na carona
			sessaoBusiness.abrirSessao("Teles", "Teles");
			int idSolicitacao = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			sessaoBusiness.encerrarSessao("Teles");
			
			//Mark aceita a solicitacao de teles
			sessaoBusiness.abrirSessao("Mark", "Mark");
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao+"");
			
		} catch (Exception e) {
			fail();
		}
				
		//reviewVagaEmCarona - carona vazia
		try {								
			solicitacaoBusiness.reviewVagaEmCarona("Mark", "", "Teles", MensagensErro.FALTOU);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//reviewVagaEmCarona - carona null
		try {								
			solicitacaoBusiness.reviewVagaEmCarona("Mark", null, "Teles", MensagensErro.FALTOU);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//reviewVagaEmCarona - carona inexistente
		try {								
			solicitacaoBusiness.reviewVagaEmCarona("Mark", "id", "Teles", MensagensErro.FALTOU);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
	}

	@Test
	public void testeReviewVagaEmCarona_UsuarioInvalido(){
		//Preparar o ambiente para os testes
		try {
			//Teles solicita uma vaga na carona
			sessaoBusiness.abrirSessao("Teles", "Teles");
			int idSolicitacao = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			sessaoBusiness.encerrarSessao("Teles");
			
			//Mark aceita a solicitacao de teles
			sessaoBusiness.abrirSessao("Mark", "Mark");
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao+"");
			
		} catch (Exception e) {
			fail();
		}
				
		//reviewVagaEmCarona - usuario vazio
		try {								
			solicitacaoBusiness.reviewVagaEmCarona("Mark", idCarona, "", MensagensErro.FALTOU);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.LOGIN_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//reviewVagaEmCarona - usuario null
		try {								
			solicitacaoBusiness.reviewVagaEmCarona("Mark", idCarona, null, MensagensErro.FALTOU);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.LOGIN_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//reviewVagaEmCarona - usuario inexistente
		try {								
			solicitacaoBusiness.reviewVagaEmCarona("Mark", idCarona, "Usuario", MensagensErro.FALTOU);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.USUARIO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
		
		//reviewVagaEmCarona - usuario existe mas nao pertence a carona
		try {					
			usuarioBusiness.criarUsuario("luana", "luana", "luana", "rua", "luana@email.com");
			solicitacaoBusiness.reviewVagaEmCarona("Mark", idCarona, "luana", MensagensErro.FALTOU);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.USUARIO_SEM_VAGA_NA_CARONA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}					
	}
	
	@Test
	public void testeReviewVagaEmCarona(){
		//Preparar o ambiente para os testes
		try {
			//Teles solicita uma vaga na carona
			sessaoBusiness.abrirSessao("Teles", "Teles");
			int idSolicitacao = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			sessaoBusiness.encerrarSessao("Teles");
			
			//Mark aceita a solicitacao de teles
			sessaoBusiness.abrirSessao("Mark", "Mark");
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao+"");
			
		} catch (Exception e) {
			fail();
		}
				
		//reviewVagaEmCarona - opcao de review invalida
		try {								
			solicitacaoBusiness.reviewVagaEmCarona("Mark", idCarona, "Teles", "nao gostei do usuario");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.OPCAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
				
		//reviewVagaEmCarona - caso normal
		try {					
			solicitacaoBusiness.reviewVagaEmCarona("Mark", idCarona, "Teles", MensagensErro.FALTOU);
		} catch (Exception e) {
			fail();
		}	
	}
	
	@Test
	public void testReviewCarona(){
		//Preparar o ambiente para os testes
		try {
			//Teles solicita uma vaga na carona
			sessaoBusiness.abrirSessao("Teles", "Teles");
			int idSolicitacao = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");			
			
			//Mark aceita a solicitacao de teles
			sessaoBusiness.abrirSessao("Mark", "Mark");
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao+"");
			
		} catch (Exception e) {
			fail();
		}
				
		//reviewCarona - opcao de review invalida
		try {								
			solicitacaoBusiness.reviewCarona("Teles", idCarona, "Nao gostei do motorista");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.OPCAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
						
		//reviewCarona - caso normal
		try {					
			solicitacaoBusiness.reviewCarona("Teles", idCarona, MensagensErro.SEGURA_TRANQUILA);
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testReviewCarona_usuarioInvalido(){
		//Preparar o ambiente para os testes
		try {
			//Teles solicita uma vaga na carona
			sessaoBusiness.abrirSessao("Teles", "Teles");
			int idSolicitacao = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			sessaoBusiness.encerrarSessao("Teles");
			
			//Mark aceita a solicitacao de teles
			sessaoBusiness.abrirSessao("Mark", "Mark");
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao+"");
			
		} catch (Exception e) {
			fail();
		}
				
		//reviewCarona - sessao vazia
		try {								
			solicitacaoBusiness.reviewCarona("", idCarona, MensagensErro.SEGURA_TRANQUILA);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//reviewCarona - sessao null
		try {								
			solicitacaoBusiness.reviewCarona(null, idCarona, MensagensErro.SEGURA_TRANQUILA);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//reviewCarona - sessao invalida
		try {								
			solicitacaoBusiness.reviewCarona("sessao", idCarona, MensagensErro.SEGURA_TRANQUILA);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//reviewCarona - usuario nao pertence a carona 
		try {								
			usuarioBusiness.criarUsuario("luana", "luana", "luana", "rua", "luana@email.com");
			sessaoBusiness.abrirSessao("luana", "luana");
			solicitacaoBusiness.reviewCarona("luana", idCarona, MensagensErro.SEGURA_TRANQUILA);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.USUARIO_SEM_VAGA_NA_CARONA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testReviewCarona_caronaInvalido(){
		//Preparar o ambiente para os testes
		try {
			//Teles solicita uma vaga na carona
			sessaoBusiness.abrirSessao("Teles", "Teles");
			int idSolicitacao = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			
			//Mark aceita a solicitacao de teles
			sessaoBusiness.abrirSessao("Mark", "Mark");
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao+"");
			
		} catch (Exception e) {
			fail();
		}
		
		//reviewCarona - carona vazia
		try {								
			solicitacaoBusiness.reviewCarona("Teles", "", MensagensErro.SEGURA_TRANQUILA);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//reviewCarona - carona null
		try {								
			solicitacaoBusiness.reviewCarona("Teles", null, MensagensErro.SEGURA_TRANQUILA);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
				
		//reviewCarona - carona invalida
		try {								
			solicitacaoBusiness.reviewCarona("Teles", "carona", MensagensErro.SEGURA_TRANQUILA);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}				
	}
	
	@Test
	public void testaSolicitacoesVagaCarona(){
		//Preparar o ambiente para os testes
		String idCarona2 = "";
		try {
			//Criar 3 usuarios 1 - dono da carona e 2 - passageiros
			usuarioBusiness.criarUsuario("luana", "luana", "luana", "rua", "luana@email.com");
			
			//Teles solicita uma vaga na carona
			sessaoBusiness.abrirSessao("Teles", "Teles");
			int idSolicitacao1 = solicitacaoBusiness.solicitarVagaPontoEncontro("Teles", idCarona, "Açude Velho");
			
			//Luana solicita uma vaga na carona
			sessaoBusiness.abrirSessao("luana", "luana");
			int idSolicitacao2 = solicitacaoBusiness.solicitarVagaPontoEncontro("luana", idCarona, "Açude Velho");
			
			//Mark aceita a solicitacao de teles e de luana
			sessaoBusiness.abrirSessao("Mark", "Mark");
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao1+"");
			solicitacaoBusiness.aceitarSolicitacao("Mark", idSolicitacao2+"");
			
			//luana avalia a carona como segura e tranquila
			solicitacaoBusiness.reviewCarona("luana", idCarona, MensagensErro.SEGURA_TRANQUILA);

			//Teles avalia a carona como nao funcionou
			solicitacaoBusiness.reviewCarona("Teles", idCarona, MensagensErro.NAO_FUNCIONOU);
			
			//Mark cadastra outra carona e define a carona como preferencial
			idCarona2 = caronaBusiness.cadastrarCarona("Mark", "Campina Grande", "Araruna", "31/05/2015", "16:00", 2);
			caronaBusiness.definirCaronaPreferencial(idCarona2);
		} catch (Exception e) {
			fail();
		}

		//Procurar quantos os usuarios marcaram a carona como segura e tranquila
		//apenas Luana marcou a carona como segura e tranquila
		try {
			assertEquals(1, caronaBusiness.getUsuariosPreferenciaisCarona(idCarona2).size());
		} catch (Exception e) {
			fail();
		}
		
		//Teles tenta solicita vaga na carona2, mas nao é permitido pois a carona esta como preferencial
		//e ele não avaliou a carona como segura e tranquila
		try {							
			solicitacaoBusiness.solicitarVaga("Teles", idCarona2);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.USUARIO_NAO_PREFERENCIAL, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//Luana tenta solicita vaga na carona2
		try {							
			solicitacaoBusiness.solicitarVaga("luana", idCarona2);
		} catch (Exception e) {
			fail();
		}	
		
	}

}
