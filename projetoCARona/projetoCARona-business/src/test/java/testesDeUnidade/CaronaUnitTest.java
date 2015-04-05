package testesDeUnidade;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import com.br.uepb.business.CaronaBusiness;
import com.br.uepb.domain.CaronaDomain;

public class CaronaUnitTest {
	
	//TODO: Gerar Documentacao
	CaronaBusiness caronaBusiness = new CaronaBusiness();
	
	//Cadastra Carona
	@Test
	public void testesCadastraCarona1(){
		String idSessao = "Luana";
		String origem = "Campina Grande";
		String destino = "João Pessoa";
		String data = "23/06/2013";
		String hora="16:00";
		int vagas = 5;
				
		String carona1 = caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, hora, vagas);		
		Assert.assertNotNull(carona1);
	}
	
	//Cadastra Carona e verifica retorno dos atributos 
	@Test
	public void testeCadastraCarona2(){		
		String idSessao = "Mark";
		String origem = "Campina Grande";
		String destino = "João Pessoa";
		String data = "23/06/2013";
		String hora="16:00";
		int vagas = 3;
				
		String carona2 = caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, hora, vagas);		
		Assert.assertNotNull(carona2);
		
		//Verificacoes dos atributos
		try {
			Assert.assertEquals(origem, caronaBusiness.getAtributoCarona(carona2, "origem"));
			Assert.assertEquals(destino, caronaBusiness.getAtributoCarona(carona2, "destino"));
			Assert.assertEquals(data, caronaBusiness.getAtributoCarona(carona2, "data"));
			Assert.assertEquals(""+vagas, caronaBusiness.getAtributoCarona(carona2, "vagas"));
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeCadastraCarona3(){		
		String idCarona3 = caronaBusiness.cadastrarCarona("Luana", "Campina Grande", "João Pessoa", "12/03/2015", "12:30", 3);
		String idCarona4 = caronaBusiness.cadastrarCarona("Luana", "Rio de Janeiro", "São Paulo", "15/03/2015", "10:00", 4);
		String idCarona5 = caronaBusiness.cadastrarCarona("Luana", "Campina Grande", "João Pessoa", "25/13/2026", "15:30", 2);
		String idCarona6 = caronaBusiness.cadastrarCarona("Luana", "João Pessoa", "Logoa Seca", "25/13/2016", "12:30", 2);
		
		Assert.assertNotNull(idCarona3);
		Assert.assertNotNull(idCarona4);
		Assert.assertNotNull(idCarona5);
		Assert.assertNotNull(idCarona6);
	
		//ArrayList<CaronaDomain> caronaResultado = new ArrayList<CaronaDomain>();
		
		ArrayList<CaronaDomain> carona;
		try {
			carona = caronaBusiness.localizarCarona("Luana", "São Francisco", "Palo Alto");
			Assert.assertTrue(carona.isEmpty());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		//carona = caronaBusiness.localizarCarona("Luana", "Campina Grande", "João Pessoa");
		
		//Assert.assertTrue(carona.isEmpty());

//expect {} localizarCarona idSessao=${sessaoMark} origem="São Francisco" destino="P"
//expect {${carona2ID}} localizarCarona idSessao=${sessaoMark} origem="Rio de Janeiro" destino="São Paulo"
//expect {${carona3ID}} localizarCarona idSessao=${sessaoMark} origem="João Pessoa" destino="Campina Grande"
		
	}
	
	//procura Carona que não foi cadastrada
	@Test
	public void testeLocalizarCarona1(){
		//Teste para localizar uma carona que não existe
		String idSessao = "Luana";
		String origem = "Campina Grande";
		String destino = "João Pessoa";
			
		ArrayList<CaronaDomain> caronaList;
		try {
			caronaList = caronaBusiness.localizarCarona(idSessao, origem, destino);
			Assert.assertTrue(caronaList.isEmpty());	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
