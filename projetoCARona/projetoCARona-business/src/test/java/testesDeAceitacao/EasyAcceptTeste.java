package testesDeAceitacao;

import easyaccept.EasyAccept;

public class EasyAcceptTeste {
	
	public static void main(String[] args) {
		args = new String[] {"com.br.uepb.business.FacadeTestBusiness", "src/test/resources/userStories/US01.txt"};
		EasyAccept.main(args);
	}
}
