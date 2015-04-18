package testesDeUnidade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

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
			
		} catch (Exception e) {
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

		//cadastrar carona com idSessao vazia
		try {
			caronaBusiness.cadastrarCarona("", origem, destino, data, hora, vagas);			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
		
		//cadastrar carona com idSessao null
		try {
			caronaBusiness.cadastrarCarona(null, origem, destino, data, hora, vagas);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//cadastrar carona com idSessao nas cadastrada
		try {
			caronaBusiness.cadastrarCarona("sessao1", origem, destino, data, hora, vagas);			
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
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
		
		//cadastrar carona com origem vazia
		try {
			caronaBusiness.cadastrarCarona(idSessao, "", destino, data, hora, vagas);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.ORIGEM_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//cadastrar carona com origem null
		try {
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

		//cadastrar carona com destino vazio
		try {
			caronaBusiness.cadastrarCarona(idSessao, origem, "", data, hora, vagas);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.DESTINO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//cadastrar carona com destino null
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
		
		//cadastrar carona com data vazia
		try {
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, "", hora, vagas);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.DATA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//cadastrar carona com data null
		try {
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, null, hora, vagas);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.DATA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//cadastrar carona com mes incorreto
		try {
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, "04/13/2015", hora, vagas);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.DATA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//cadastrar carona com dia incorreto
		try {
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, "31/02/2015", hora, vagas);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.DATA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
		
		//cadastrar carona com dia incorreto
		try {
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, "35/03/2015", hora, vagas);
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

		//cadastrar carona com hora vazia
		try {
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, "", vagas);				
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.HORA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//cadastrar carona com hora null
		try {
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, null, vagas);		
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.HORA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}		
		
		//cadastrar carona com hora incorreta
		try {
			caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, "8hs", vagas);	
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.HORA_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {
			fail();
		}	
		
		//cadastrar carona com hora incorreta
		try {
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
		
		//atributo vazio
		try {		
			caronaBusiness.getAtributoCarona(caronaID1, "");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.ATRIBUTO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}
		
		//atributo null
		try {		
			caronaBusiness.getAtributoCarona(caronaID1, null);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.ATRIBUTO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}
		
		//carona vazia
		try {		
			caronaBusiness.getAtributoCarona("", "origem");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.IDENTIFICADOR_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}
		
		//carona null
		try {		
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
		
		//carona null
		try {		
			caronaBusiness.getTrajeto(null);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.TRAJETO_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}
		
		//carona vazia
		try {		
			caronaBusiness.getTrajeto("");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.TRAJETO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}		
	
		//carona inexistente
		try {		
			caronaBusiness.getTrajeto("Carona1");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.TRAJETO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}		
	}

	@Test
	public void testGetCarona(){		
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

	@Test
	public void testGetCaronas(){
		String caronaID1 = "";
		String caronaID2 = "";
		String caronaID3 = "";
					
		ArrayList<CaronaDomain> listaCaronas = new ArrayList<CaronaDomain>();
		
		try {
			usuarioBusiness.criarUsuario("Mark", "Mark", "Mark", "Rua", "mark@gmail.com");
			sessaoBusiness.abrirSessao("Mark", "Mark");
			Assert.assertEquals(listaCaronas, caronaBusiness.getTodasCaronasUsuario("Mark"));
			caronaID1 = caronaBusiness.cadastrarCarona("Mark", "João Pessoa", "Campina Grande", "26/03/2015", "08:00", 5);
			caronaID2 = caronaBusiness.cadastrarCarona("Mark", "João Pessoa", "Araruna", "01/06/2015", "10:00", 3);
			caronaID3 = caronaBusiness.cadastrarCarona("Mark", "João Pessoa", "Piloes", "05/10/2015", "15:00", 2);
			
			listaCaronas.add(caronaBusiness.getCarona(caronaID1));
			listaCaronas.add(caronaBusiness.getCarona(caronaID2));
			listaCaronas.add(caronaBusiness.getCarona(caronaID3));
			
			Assert.assertEquals(listaCaronas, caronaBusiness.getTodasCaronasUsuario("Mark"));
			sessaoBusiness.encerrarSessao("Mark");
		} catch (Exception e) {
			fail();
		}
				
	}
	
	@Test
	public void testGetCaronaUsuario(){
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
		
		//retorna a carona 1
		try {		
			caronaBusiness.getCaronaUsuario("Mark", 1);			
		} catch (Exception e) {	
			fail();
		}
	
		//informar indice invalido
		try {		
			caronaBusiness.getCaronaUsuario("Mark", 2);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.INDICE_INVALIDO, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}
		
		//informar sessao vazia
		try {		
			caronaBusiness.getCaronaUsuario("", 1);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}

		//informar sessao null
		try {		
			caronaBusiness.getCaronaUsuario(null, 1);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}		
		
		//informar sessao que nao existe
		try {		
			caronaBusiness.getCaronaUsuario("Lukas", 1);
			caronaBusiness.getCaronaUsuario(null, 1);
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		} catch (Exception e) {	
			fail();
		}
	}

	@Test
	public void testLocalizarCarona(){
		idSessao = "Teles";
		
		String caronaID1 = "";
		String caronaID2 = "";
		String caronaID3 = "";
		String caronaID4 = "";
		String caronaID5 = "";
		String caronaID6 = "";
		try {
			usuarioBusiness.criarUsuario(idSessao, idSessao, "Teles", "Rua", "teles@gmail.com");
			sessaoBusiness.abrirSessao(idSessao, idSessao);
			caronaID1 = caronaBusiness.cadastrarCarona(idSessao, "Campina Grande", "Joao Pessoa", "12/05/2015", "10:00", 3);
			caronaID2 = caronaBusiness.cadastrarCarona(idSessao, "Campina Grande", "Araruna", "12/05/2015", "10:00", 3);
			caronaID3 = caronaBusiness.cadastrarCarona(idSessao, "Campina Grande", "Joao Pessoa", "12/05/2015", "10:00", 3);
			caronaID4 = caronaBusiness.cadastrarCarona(idSessao, "João Pessoa", "Araruna", "12/05/2015", "10:00", 3);
			caronaID5 = caronaBusiness.cadastrarCarona(idSessao, "Araruna", "Alagoa Nova", "12/05/2015", "10:00", 3);
			caronaID6 = caronaBusiness.cadastrarCarona(idSessao, "Araruna", "Joao Pessoa", "12/05/2015", "10:00", 3);
		} catch (Exception e) {			
			fail();
		}
		
		//localiza todas as caronas
		try {
			ArrayList<CaronaDomain> listTodascaronas = new ArrayList<CaronaDomain>();
			listTodascaronas.add(caronaBusiness.getCarona(caronaID1));
			listTodascaronas.add(caronaBusiness.getCarona(caronaID2));
			listTodascaronas.add(caronaBusiness.getCarona(caronaID3));
			listTodascaronas.add(caronaBusiness.getCarona(caronaID4));
			listTodascaronas.add(caronaBusiness.getCarona(caronaID5));
			listTodascaronas.add(caronaBusiness.getCarona(caronaID6));
			System.out.println();
			Assert.assertEquals(listTodascaronas, caronaBusiness.localizarCarona("Teles", "", "")); 		
		} catch (Exception e) {	
			fail();
		}
	
		//localiza todas as caronas de origem=Campina Grande
		try {
			ArrayList<CaronaDomain> listTodascaronas = new ArrayList<CaronaDomain>();
			listTodascaronas.add(caronaBusiness.getCarona(caronaID1));
			listTodascaronas.add(caronaBusiness.getCarona(caronaID2));
			listTodascaronas.add(caronaBusiness.getCarona(caronaID3));
			Assert.assertEquals(listTodascaronas, caronaBusiness.localizarCarona("Teles", "Campina Grande", "")); 	
		} catch (Exception e) {	
			fail();
		}
		
		//localiza todas as caronas de destino=Araruna
		try {
			ArrayList<CaronaDomain> listTodascaronas = new ArrayList<CaronaDomain>();
			listTodascaronas.add(caronaBusiness.getCarona(caronaID2));
			listTodascaronas.add(caronaBusiness.getCarona(caronaID4));
			Assert.assertEquals(listTodascaronas, caronaBusiness.localizarCarona("Teles", "", "Araruna")); 							
		} catch (Exception e) {	
			fail();
		}
		
		//localiza todas as caronas de Campina Grande a João Pessoa
		try {
			ArrayList<CaronaDomain> listTodascaronas = new ArrayList<CaronaDomain>();
			listTodascaronas.add(caronaBusiness.getCarona(caronaID1));
			listTodascaronas.add(caronaBusiness.getCarona(caronaID3));
			Assert.assertEquals(listTodascaronas, caronaBusiness.localizarCarona("Teles", "Campina Grande", "Joao Pessoa")); 							
		} catch (Exception e) {	
			fail();
		}
		
		//informar origem nao existente
		try {
			ArrayList<CaronaDomain> listTodascaronas = new ArrayList<CaronaDomain>();
			Assert.assertEquals(listTodascaronas, caronaBusiness.localizarCarona("Teles", "Campina", "Joao Pessoa")); 							
		} catch (Exception e) {	
			fail();
		}
		
		//informar login não cadastrado
		try {
			caronaBusiness.localizarCarona("Mark", "", "Joao Pessoa");
			System.out.println(caronaBusiness.localizarCarona("Mark", "", "Joao Pessoa"));
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INEXISTENTE, projetoCaronaErro.getMessage());
		}  catch (Exception e) {	
			fail();
		}
		
		//informar login não invalido 
		try {
			caronaBusiness.localizarCarona("", "", "Joao Pessoa");
			caronaBusiness.localizarCarona(null, "", "Joao Pessoa");
			System.out.println(caronaBusiness.localizarCarona("Mark", "", "Joao Pessoa"));
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		}  catch (Exception e) {	
			fail();
		}
		
		//informar login null
		try {
			caronaBusiness.localizarCarona(null, "", "Joao Pessoa");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.SESSAO_INVALIDA, projetoCaronaErro.getMessage());
		}  catch (Exception e) {	
			fail();
		}
		
		//informar origem invalida
		try {
			caronaBusiness.localizarCarona("Teles", "?", "Joao Pessoa");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.ORIGEM_INVALIDA, projetoCaronaErro.getMessage());
		}  catch (Exception e) {	
			fail();
		}
		
		//informar destino invalido
		try {
			caronaBusiness.localizarCarona("Teles", "Campina Grande", "()");
		} catch (ProjetoCaronaException projetoCaronaErro) {
			assertEquals(MensagensErro.DESTINO_INVALIDO, projetoCaronaErro.getMessage());
		}  catch (Exception e) {	
			fail();
		}
		
	}
	
}
