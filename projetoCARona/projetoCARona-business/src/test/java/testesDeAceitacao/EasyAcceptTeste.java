package testesDeAceitacao;

import easyaccept.EasyAccept;

public class EasyAcceptTeste {
	
	public static void main(String[] args) {
		args = new String[] {"com.br.uepb.business.FacadeTestBusiness", "src/test/resources/userStories/US07.txt"};
		EasyAccept.main(args);
	}
}
