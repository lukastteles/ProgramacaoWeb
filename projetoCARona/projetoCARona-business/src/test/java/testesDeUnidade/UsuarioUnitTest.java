package testesDeUnidade;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.br.uepb.business.UsuarioBusiness;
import com.br.uepb.constants.MensagensErro;
import com.br.uepb.dao.impl.CaronaDAOImpl;
import com.br.uepb.dao.impl.InteresseEmCaronaDAOImpl;
import com.br.uepb.dao.impl.PontoDeEncontroDAOImpl;
import com.br.uepb.dao.impl.SessaoDAOImpl;
import com.br.uepb.dao.impl.SolicitacaoVagaDAOImpl;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class UsuarioUnitTest {
	
	private UsuarioBusiness usuarioBusiness;
	
	//Parâmetros
	private String login;
	private String senha;
	private String nome;
	private String endereco;
	private String email;
	
	@Before
	public void iniciaBusiness(){
		usuarioBusiness = new UsuarioBusiness();
		
		//limpa os dados antes de iniciar
		SolicitacaoVagaDAOImpl.getInstance().apagaSolicitacoes();
		PontoDeEncontroDAOImpl.getInstance().apagaPontosEncontro();
		CaronaDAOImpl.getInstance().apagaCaronas();		
		UsuarioDAOImpl.getInstance().apagaUsuarios();
		SessaoDAOImpl.getInstance().apagaSessoes();
		InteresseEmCaronaDAOImpl.getInstance().apagaInteresses();
		
		//define parametros default
		login = "mark";
		senha = "m@rk";
		nome="Mark Zuckerberg";
		endereco = "Palo Alto, California";
		email = "mark@facebook.com";
	}
	
	@Test
	public void testeCadastraUsuario(){
		//cadastra um usuario
		try {
			usuarioBusiness.criarUsuario(login, senha, nome, endereco, email);
		} catch (ProjetoCaronaException e) {
			fail();
		} catch (Exception e) {
			fail();
		}

		//Asserts Corretos
		try {
			Assert.assertEquals("mark", usuarioBusiness.getAtributoUsuario(login, "login"));
			Assert.assertEquals("m@rk", usuarioBusiness.getAtributoUsuario(login, "senha"));
			Assert.assertEquals("Mark Zuckerberg", usuarioBusiness.getAtributoUsuario(login, "nome"));
			Assert.assertEquals("Palo Alto, California", usuarioBusiness.getAtributoUsuario(login, "endereco"));
			Assert.assertEquals("mark@facebook.com", usuarioBusiness.getAtributoUsuario(login, "email"));
		} catch (ProjetoCaronaException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
		//Tentar pegar um atributo de um usuario inexistente
		try {
			usuarioBusiness.getAtributoUsuario("luan", "email");
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals(MensagensErro.USUARIO_INEXISTENTE, e.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//tentar criar usuario que já existe
		try {
			usuarioBusiness.criarUsuario(login, senha, nome, endereco, "email@email.com");
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals(MensagensErro.USUARIO_JA_EXISTE, e.getMessage());
		} catch (Exception e) {
			fail();
		}
		
		//tentar criar usuario com email que já existe
		try {
			usuarioBusiness.criarUsuario("luana", "luana", "Luana", "rua", email);
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals(MensagensErro.EMAIL_JA_EXISTE, e.getMessage());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testesExcecoesUsuario(){		
		/*
		 * Cria Usuario Teste
		 */
		try {
			usuarioBusiness.criarUsuario(login, senha, nome, endereco, email);
		} catch (ProjetoCaronaException e) {
			fail();
		}catch (Exception e) {
			fail();
		}
		
		/*
		 * Exceções de criarUsuario
		 */
		
		//Login Vazio
		try{
			usuarioBusiness.criarUsuario("", senha, nome, endereco, email);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.LOGIN_INVALIDO, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.criarUsuario(null, senha, nome, endereco, email);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.LOGIN_INVALIDO, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		//Senha Vazia
		try{
			usuarioBusiness.criarUsuario(login, "", nome, endereco, email);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.SENHA_INVALIDA, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.criarUsuario(login, null, nome, endereco, email);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.SENHA_INVALIDA, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		//Nome Vazio
		try{
			usuarioBusiness.criarUsuario(login, senha, "", endereco, email);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.NOME_INVALIDO, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.criarUsuario(login, senha, null, endereco, email);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.NOME_INVALIDO, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		//Endereço Vazio
		try{
			usuarioBusiness.criarUsuario(login, senha, nome, "", email);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.ENDERECO_INVALIDO, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.criarUsuario(login, senha, nome, null, email);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.ENDERECO_INVALIDO, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		//Email Vazio
		try{
			usuarioBusiness.criarUsuario(login, senha, nome, endereco, "");
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.EMAIL_INVALIDO, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.criarUsuario(login, senha, nome, endereco, null);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.EMAIL_INVALIDO, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		//Criar outro usuario com mesmo login
		try {
			usuarioBusiness.criarUsuario(login, "senha", "teste", "endereco", "email@email.com");
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals(MensagensErro.USUARIO_JA_EXISTE, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		//Criar outro usuario com mesmo email
		try {
			usuarioBusiness.criarUsuario("login", "senha", "teste", "endereco", email);
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals(MensagensErro.EMAIL_JA_EXISTE, e.getMessage());
		}catch (Exception e) {
			fail();
		}

		/*
		 * Exceções de getAtributoUsuario()
		 */
		
		//Atributo inválido
		try{
			usuarioBusiness.getAtributoUsuario(login, "Hakuna Matata");
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.ATRIBUTO_INEXISTENTE, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.getAtributoUsuario(login, "");
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.ATRIBUTO_INVALIDO, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.getAtributoUsuario(login, null);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.ATRIBUTO_INVALIDO, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		//login invalido
		try{
			usuarioBusiness.getAtributoUsuario("", "nome");
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.LOGIN_INVALIDO, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.getAtributoUsuario(null, "nome");
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.LOGIN_INVALIDO, e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.getAtributoUsuario("123", "nome");
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals(MensagensErro.USUARIO_INEXISTENTE, e.getMessage());
		}catch (Exception e) {
			fail();
		}
	}
	
}
