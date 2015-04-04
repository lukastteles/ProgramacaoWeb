package testesDeUnidade;

import junit.framework.Assert;

import org.junit.Test;

import com.br.uepb.business.CaronaBusiness;

public class CaronaUnitTest {
	
	//TODO: Gerar Documentacao
	CaronaBusiness caronaBusiness = new CaronaBusiness();
	
	@Test
	public void testeCadastraCarona(){
		//carona1ID=cadastrarCarona idSessao=${sessaoMark} origem="Campina Grande" destino="João Pessoa" data="23/06/2013" hora="16:00" vagas=3
		String idSessao = "Luana";
		String origem = "Campina Grande";
		String destino = "João Pessoa";
		String data = "23/06/2013";
		String hora="16:00";
		int vagas = 5;
				
		String idCarona = caronaBusiness.cadastrarCarona(idSessao, origem, destino, data, hora, vagas);
		
		Assert.assertNotNull(idCarona);		
	}
	
}
