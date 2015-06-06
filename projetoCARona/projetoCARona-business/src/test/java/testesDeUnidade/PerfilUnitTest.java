package testesDeUnidade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.br.uepb.business.CaronaBusiness;
import com.br.uepb.business.PerfilBusiness;
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
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.domain.SolicitacaoVagaDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class PerfilUnitTest {

	private SessaoBusiness sessaoBusiness; 
	private UsuarioBusiness usuarioBusiness;
	private PerfilBusiness perfilBusiness;
	private CaronaBusiness caronaBusiness;
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
		caronaBusiness = new CaronaBusiness();
		
		//limpa os dados antes de iniciar
		SolicitacaoVagaDAOImpl.getInstance().apagaSolicitacoes();
		PontoDeEncontroDAOImpl.getInstance().apagaPontosEncontro();
		CaronaDAOImpl.getInstance().apagaCaronas();		
		UsuarioDAOImpl.getInstance().apagaUsuarios();
		SessaoDAOImpl.getInstance().apagaSessoes();
		InteresseEmCaronaDAOImpl.getInstance().apagaInteresses();
		
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
	public void testaCadastroInteresse() throws Exception{
		//Cadastrar interesse com sessao vazia
		try {
			perfilBusiness.cadastraInteresse("", "Campina Grande", "Joao Pessoa", "31/05/2015", "12:00", "18:00");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//Cadastrar interesse com sessao null
		try {
			perfilBusiness.cadastraInteresse(null, "Campina Grande", "Joao Pessoa", "31/05/2015", "12:00", "18:00");	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
				
		//Cadastrar interesse com sessao nao cadastrada
		try {
			perfilBusiness.cadastraInteresse("sessao1", "Campina Grande", "Joao Pessoa", "31/05/2015", "12:00", "18:00");			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//Cadastrar interesse com origem vazia
		try {
			perfilBusiness.cadastraInteresse(login, "", "Joao Pessoa", "31/05/2015", "12:00", "18:00");			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.ORIGEM_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//Cadastrar interesse com origem null
		try {
			perfilBusiness.cadastraInteresse(login, null, "Joao Pessoa", "31/05/2015", "12:00", "18:00");			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.ORIGEM_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//Cadastrar interesse com origem invalida
		try {
			perfilBusiness.cadastraInteresse(login, "Campina-Grande", "Joao Pessoa", "31/05/2015", "12:00", "18:00");			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.ORIGEM_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
		
		//Cadastrar interesse com destino vazio
		try {
			perfilBusiness.cadastraInteresse(login, "Campina Grande", "", "31/05/2015", "12:00", "18:00");			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.DESTINO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//Cadastrar interesse com destino null
		try {
			perfilBusiness.cadastraInteresse(login, "Campina Grande", null, "31/05/2015", "12:00", "18:00");			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.DESTINO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//Cadastrar interesse com destino invalido
		try {
			perfilBusiness.cadastraInteresse(login, "Campina Grande", "Joao-Pessoa", "31/05/2015", "12:00", "18:00");			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.DESTINO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
				
		//Cadastrar interesse com data vazia
		try {
			perfilBusiness.cadastraInteresse(login, "Campina Grande", "Joao Pessoa", "", "12:00", "18:00");			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.DATA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//Cadastrar interesse com data null
		try {
			perfilBusiness.cadastraInteresse(login, "Campina Grande", "Joao Pessoa", null, "12:00", "18:00");			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.DATA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//Cadastrar interesse com data invalida
		try {
			perfilBusiness.cadastraInteresse(login, "Campina Grande", "Joao Pessoa", "29/02/2015", "12:00", "18:00");			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.DATA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
					
		//Cadastrar interesse com hora inicial invalida
		try {
			perfilBusiness.cadastraInteresse(login, "Campina Grande", "Joao Pessoa", "31/05/2015", "oito", "18:00");			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.HORA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
		
		//Cadastrar interesse com hora final invalida
		try {
			perfilBusiness.cadastraInteresse(login, "Campina Grande", "Joao Pessoa", "31/05/2015", "18:00", "oito");			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.HORA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		


		//Cadastrar interesse 
		try {
			int interesse = perfilBusiness.cadastraInteresse(login, "Campina Grande", "Joao Pessoa", "31/05/2015", "18:00", "22:00");			
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testaVerificarMensagensPerfil() throws Exception{
		//Testa de login é vazio
		try {
			perfilBusiness.verificarMensagensPerfil("");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
				
		//Testa de login é null
		try {
			perfilBusiness.verificarMensagensPerfil(null);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
						
		//Testa de login é invalido
		try {
			perfilBusiness.verificarMensagensPerfil("sessao1");			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//Testa funcao
		try {
			perfilBusiness.verificarMensagensPerfil(login);
		} catch (Exception e) {
			fail();
		}
		
		//Cadatrar interesse e verificar se o retorno da mensagem não é vazio
		try {
			perfilBusiness.cadastraInteresse(login, "Campina Grande", "Joao Pessoa", "31/05/2015", "12:00", "18:00");
			perfilBusiness.verificarMensagensPerfil(login);
		} catch (Exception e) {
			fail();
		}
		
		//Cadatrar interesse e verificar se o retorno da mensagem não é vazio
		try {
			caronaBusiness.cadastrarCarona(login, "Campina Grande", "Joao Pessoa", "31/05/2015", "16:00", 3);
			perfilBusiness.cadastraInteresse(login, "Campina Grande", "Joao Pessoa", "31/05/2015", "12:00", "18:00");
			perfilBusiness.verificarMensagensPerfil(login);
		} catch (Exception e) {
			fail();
		}	
	}
		
	@Test
	public void testaExcecoesPerfil(){
		
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
	
	@Test
	public void testaGetHistoricoDeVagas() throws Exception{
		//criar historico de caronas
		usuarioBusiness.criarUsuario("luana", "luana", "Luana", "rua", "luana@email.com");
		sessaoBusiness.abrirSessao("luana", "luana");
		String idCarona1 = caronaBusiness.cadastrarCarona("luana", "Campina Grande", "Joao Pessoa", "31/01/2015", "14:00", 5);
		String idCarona2 = caronaBusiness.cadastrarCarona("luana", "Campina Grande", "Araruna", "31/01/2015", "14:00", 5);
		
		//usuario login solicita vaga
		SolicitacaoVagaBusiness solicitacaoBusiness = new SolicitacaoVagaBusiness();
		int idSolicitacao1 = solicitacaoBusiness.solicitarVaga(login, idCarona1);
		int idSolicitacao2 = solicitacaoBusiness.solicitarVaga(login, idCarona2);
		
		//luana aceita solicitacao
		solicitacaoBusiness.aceitarSolicitacao("luana", idSolicitacao1+"");
		solicitacaoBusiness.aceitarSolicitacao("luana", idSolicitacao2+"");
		
		int vagasEmCaronas = 2;//total de caronas solicitadas
		
		try {
			String[] historicoVagas = perfilBusiness.getHistoricoDeVagasEmCaronas(login);
			assertEquals(vagasEmCaronas, historicoVagas.length);
		} catch (ProjetoCaronaException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
	}
}
