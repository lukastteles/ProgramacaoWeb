package testesDeUnidade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.br.uepb.business.CaronaBusiness;
import com.br.uepb.business.SessaoBusiness;
import com.br.uepb.business.UsuarioBusiness;
import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.domain.CaronaDomain;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class CaronaUnitTest {

	private UsuarioBusiness usuarioBusiness;
	private SessaoBusiness sessaoBusiness;
	private CaronaBusiness caronaBusiness;
	
	//variaveis utilizadas
	private String idSessao;	
	private String origem;
	private String destino;
	private String data;
	private String hora;
	private int vagas;
	
/*
	public ArrayList<CaronaDomain> localizarCarona(String idSessao, String origem, String destino) ;	
	public CaronaDomain getCaronaUsuario(String idSessao, int indexCarona);
	public ArrayList<CaronaDomain> getTodasCaronasUsuario(String idSessao);	

 */	
	
	@Before
	public void iniciaBusiness(){
		usuarioBusiness = new UsuarioBusiness();
		sessaoBusiness = new SessaoBusiness();		
		caronaBusiness = new CaronaBusiness();		
		
		//limpa os dados antes de iniciar
		CaronaDAOImpl.getInstance().apagaCaronas();
		UsuarioDAOImpl.getInstance().apagaUsuarios();
		SessaoDAOImpl.getInstance().apagaSessoes();
	}
	
	@Test
	public void testCadastrarCarona() throws Exception{
		idSessao = "Luana";
		origem = "Campina Grande";
		destino = "João Pessoa";
		data = "23/06/2013";
		hora="16:00";
		vagas = 5;

		//Verificar qualquer falha no fluxo normal
		try {
			//inicializa a sessao e o usuario
			usuarioBusiness.criarUsuario(idSessao, idSessao, "Luana", "Rua", "luana@gmail.com");
			sessaoBusiness.abrirSessao(idSessao, idSessao);
						
			String caronaID1 = caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, hora, vagas);				
			CaronaDomain carona1 = caronaBusiness.getCarona(caronaID1);			

			//verifica se armazenou os valores realmente corretos
			Assert.assertEquals(caronaID1, carona1.getID());
			Assert.assertEquals(idSessao, carona1.getIdSessao());
			Assert.assertEquals(origem, carona1.getOrigem());
			Assert.assertEquals(destino, carona1.getDestino());
			Assert.assertEquals(data, carona1.getData());
			Assert.assertEquals(hora, carona1.getHora());
			Assert.assertEquals(vagas, carona1.getVagas());

			//verificar se a funcao getCarona retorna a mesma carona 			
			CaronaDomain carona2 = caronaBusiness.getCarona(caronaID1);
			Assert.assertEquals(carona1.getID(), carona2.getID());
			
			//Verifica se a carona do indice 1 é a mesma carona cadastrada
			CaronaDomain carona3 = caronaBusiness.getCaronaUsuario(idSessao, 1);
			Assert.assertEquals(carona1.getID(), carona3.getID()); 			
			
		} catch (Exception e) {
			fail();
		}			
	}
	
	@Test
	public void testCadastrarCaronaUsuarioInexistente() {
		idSessao = "Luana";
		origem = "Campina Grande";
		destino = "João Pessoa";
		data = "23/06/2013";
		hora="16:00";
		vagas = 5;
	
	//inicializa a sessao e o usuario 
		try {
			usuarioBusiness.criarUsuario(idSessao, idSessao, "Luana", "Rua", "luana@gmail.com");
			sessaoBusiness.abrirSessao(idSessao, idSessao);
			caronaBusiness.cadastrarCarona("Mark", origem, destino, data, hora, vagas);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}
	
	@Test
	public void testeCaronaSessaoInvalida() throws Exception{
		idSessao = "Luana";
		origem = "Campina Grande";
		destino = "João Pessoa";
		data = "23/06/2013";
		hora="16:00";
		vagas = 5;
	
	//inicializa a sessao e o usuario 
		try {
			usuarioBusiness.criarUsuario(idSessao, idSessao, "Luana", "Rua", "luana@gmail.com");
			sessaoBusiness.abrirSessao(idSessao, idSessao);
		} catch (Exception e) {
			fail();
		}

	//cadastrar carona com idSessao inválido ou null
		try {
			caronaBusiness.cadastrarCarona("", origem, destino, data, hora, vagas);
			caronaBusiness.cadastrarCarona(null, origem, destino, data, hora, vagas);
			caronaBusiness.cadastrarCarona("sessao1", origem, destino, data, hora, vagas);			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
	}
		
	@Test
	public void testeCaronaOrigemInvalida() throws Exception{		
		idSessao = "Luana";		
		destino = "João Pessoa";
		data = "23/06/2013";
		hora="16:00";
		vagas = 5;
		
	//inicializa a sessao e o usuario
		try {
			usuarioBusiness.criarUsuario(idSessao, idSessao, "Luana", "Rua", "luana@gmail.com");
			sessaoBusiness.abrirSessao(idSessao, idSessao);
		} catch (Exception e) {
			fail();
		}
		
	//cadastrar carona com origem invalida ou null
		try {
			caronaBusiness.cadastrarCarona(idSessao, "", destino, data, hora, vagas);
			caronaBusiness.cadastrarCarona(idSessao, null, destino, data, hora, vagas);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.ORIGEM_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testeCaronaDestinoInvalido() throws Exception{		
		idSessao = "Luana";
		origem = "Campina Grande";		
		data = "23/06/2013";
		hora="16:00";
		vagas = 5;
	
	//inicializa a sessao e o usuario
		try {
			usuarioBusiness.criarUsuario(idSessao, idSessao, "Luana", "Rua", "luana@gmail.com");
			sessaoBusiness.abrirSessao(idSessao, idSessao);
		} catch (Exception e) {
			fail();
		}

	//cadastrar carona com destino invalido ou null
		try {
			caronaBusiness.cadastrarCarona(idSessao, origem, "", data, hora, vagas);
			caronaBusiness.cadastrarCarona(idSessao, origem, null, data, hora, vagas);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.DESTINO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testeCaronaDataInvalida() throws Exception{		
		idSessao = "Luana";
		origem = "Campina Grande";
		destino = "João Pessoa";
		hora="16:00";
		vagas = 5;	

	//inicializa a sessao e o usuario
		try {
			usuarioBusiness.criarUsuario(idSessao, idSessao, "Luana", "Rua", "luana@gmail.com");
			sessaoBusiness.abrirSessao(idSessao, idSessao);
		} catch (Exception e) {
			fail();
		}
		
	//cadastrar carona com data invalida ou null
		try {
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, "", hora, vagas);
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, null, hora, vagas);
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, "04/13/2015", hora, vagas);
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, "31/02/2015", hora, vagas);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.DATA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
			
	@Test
	public void testCaronaHoraInvalida() throws Exception{
		String idSessao = "Luana";
		String origem = "Campina Grande";
		String destino = "João Pessoa";
		String data = "23/06/2013";
		int vagas = 5;	

	//inicializa a sessao e o usuario
		try {
			usuarioBusiness.criarUsuario(idSessao, idSessao, "Luana", "Rua", "luana@gmail.com");
			sessaoBusiness.abrirSessao(idSessao, idSessao);
		} catch (Exception e) {
			fail();
		}

	//cadastrar carona com hora invalida ou null
		try {
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, "", vagas);
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, null, vagas);
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, "8hs", vagas);
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, "oito", vagas);			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.HORA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetAtributoCarona(){
		idSessao = "Mark";
		origem = "Campina Grande";
		destino = "João Pessoa";
		data = "23/06/2013";
		hora="16:00";
		vagas = 3;	
		
		String caronaID1 = "";
		
	//inicializa a sessao e o usuario 
		try {
			usuarioBusiness.criarUsuario(idSessao, idSessao, "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao(idSessao, idSessao);
			caronaID1 = caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, hora, vagas);
		} catch (Exception e) {			
			fail();
		}

		//verifica se a funcao getAtributoCarona retorna os Valores Corretor
		try {		
			Assert.assertEquals(origem, caronaBusiness.getAtributoCarona(caronaID1, "origem"));
			Assert.assertEquals(destino, caronaBusiness.getAtributoCarona(caronaID1, "destino"));
			Assert.assertEquals(data, caronaBusiness.getAtributoCarona(caronaID1, "data"));
			Assert.assertEquals(vagas+"", caronaBusiness.getAtributoCarona(caronaID1, "vagas"));
		} catch (Exception e) {			
			fail();
		}
		
		//atributo vazio ou null
		try {		
			caronaBusiness.getAtributoCarona(caronaID1, "");
			caronaBusiness.getAtributoCarona(caronaID1, null);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.ATRIBUTO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}
		
		//carona vazia ou null
		try {		
			caronaBusiness.getAtributoCarona("", "origem");
			caronaBusiness.getAtributoCarona(null, "destino");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.IDENTIFICADOR_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}
		
		//carona que nao existe
		try {		
			caronaBusiness.getAtributoCarona("carona1", "origem");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.ITEM_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}
		
		//atributo que nao existe
		try {		
			caronaBusiness.getAtributoCarona(caronaID1, "atributo");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.ATRIBUTO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}	
	}
	
	@Test
	public void testGetTrajeto(){
		idSessao = "Mark";
		origem = "Campina Grande";
		destino = "João Pessoa";
		data = "23/06/2013";
		hora="16:00";
		vagas = 3;	
		
		String caronaID1 = "";
		
		try {
			usuarioBusiness.criarUsuario(idSessao, idSessao, "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao(idSessao, idSessao);
			caronaID1 = caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, hora, vagas);
			Assert.assertEquals(origem+" - "+destino, caronaBusiness.getTrajeto(caronaID1));
		} catch (Exception e) {			
			fail();
		}
		
		//carona vazia ou null
		try {		
			caronaBusiness.getTrajeto(null);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.TRAJETO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}
		
		//carona invalida
		try {		
			caronaBusiness.getTrajeto("");
			caronaBusiness.getTrajeto("Carona1");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.TRAJETO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}		
				
		

	}

	@Test
	public void testGetCarona(){
		//public CaronaDomain getCarona(String idCarona);
		idSessao = "Mark";
		origem = "Campina Grande";
		destino = "João Pessoa";
		data = "23/06/2013";
		hora="16:00";
		vagas = 3;	
		
		String caronaID1 = "";
		//carona null			
		try {		
			caronaBusiness.getCarona(null);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}
		
		//carona vazia			
		try {		
			caronaBusiness.getCarona("");
			caronaBusiness.getCarona(caronaID1);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.CARONA_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}
		
		//carona existente		
		try {
			usuarioBusiness.criarUsuario(idSessao, idSessao, "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao(idSessao, idSessao);
			caronaID1 = caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, hora, vagas);
			Assert.assertEquals(origem+" - "+destino, caronaBusiness.getTrajeto(caronaID1));
		} catch (Exception e) {			
			fail();
		}
	}
}
