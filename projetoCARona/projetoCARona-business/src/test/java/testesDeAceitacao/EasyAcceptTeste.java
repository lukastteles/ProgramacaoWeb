package testesDeAceitacao;

import easyaccept.EasyAccept;

public class EasyAcceptTeste {
	
	public static void main(String[] args) {
		
		args = new String[] {"com.br.uepb.business.FacadeTestBusiness", "src/test/resources/userStories/US01.txt",  
																		"src/test/resources/userStories/US02.txt",
																		"src/test/resources/userStories/US03.txt",
																		"src/test/resources/userStories/US04.txt",
																		"src/test/resources/userStories/US05.txt",
																		"src/test/resources/userStories/US06.txt",
																		"src/test/resources/userStories/US07.txt",
																		"src/test/resources/userStories/US08.txt",
																		"src/test/resources/userStories/US09.txt",
																		"src/test/resources/userStories/US10.txt",
																		"src/test/resources/userStories/US11.txt",
																		"src/test/resources/userStories/US12.txt",
																		"src/test/resources/userStories/US13.txt",
																		"src/test/resources/userStories/US14.txt"};
		EasyAccept.main(args);
	}
}
