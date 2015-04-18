package testesDeUnidade;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.br.uepb.business.UsuarioBusiness;
import com.br.uepb.dao.impl.UsuarioDAOImpl;
import com.br.uepb.exceptions.ProjetoCaronaException;

public class UsuarioUnitTest {
	
	private UsuarioBusiness usuarioBusiness;
	
	//Parâmetros
	String login;
	String senha;
	String nome;
	String endereco;
	String email;
	
	@Before
	public void iniciaBusiness(){
		usuarioBusiness = new UsuarioBusiness();
		
		//limpa os dados antes de iniciar
		UsuarioDAOImpl.getInstance().apagaUsuarios();
	}
	
	@Test
	public void testeCadastraUsuario(){
		
		login = "mark";
		senha = "m@rk";
		nome="Mark Zuckerberg";
		endereco = "Palo Alto, California";
		email = "mark@facebook.com";
		
		//
		//cadastra um usuario
		//
		try {
			usuarioBusiness.criarUsuario(login, senha, nome, endereco, email);
		} catch (ProjetoCaronaException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
		//
		//Asserts Corretos
		//
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
		
	}
	
	@Test
	public void testesExcecoesUsuario(){
		
		login = "mark";
		senha = "m@rk";
		nome="Mark Zuckerberg";
		endereco = "Palo Alto, California";
		email = "mark@facebook.com";
		
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
			Assert.assertEquals("Login inválido", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.criarUsuario(null, senha, nome, endereco, email);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals("Login inválido", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		//Senha Vazia
		try{
			usuarioBusiness.criarUsuario(login, "", nome, endereco, email);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals("Senha inválida", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.criarUsuario(login, null, nome, endereco, email);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals("Senha inválida", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		//Nome Vazio
		try{
			usuarioBusiness.criarUsuario(login, senha, "", endereco, email);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals("Nome inválido", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.criarUsuario(login, senha, null, endereco, email);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals("Nome inválido", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		//Endereço Vazio
		try{
			usuarioBusiness.criarUsuario(login, senha, nome, "", email);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals("Endereco inválido", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.criarUsuario(login, senha, nome, null, email);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals("Endereco inválido", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		//Email Vazio
		try{
			usuarioBusiness.criarUsuario(login, senha, nome, endereco, "");
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals("Email inválido", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.criarUsuario(login, senha, nome, endereco, null);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals("Email inválido", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		//Criar outro usuario com mesmo login
		try {
			usuarioBusiness.criarUsuario(login, "senha", "nome", "endereco", "email@email.com");
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals("Já existe um usuário com este login", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		//Criar outro usuario com mesmo email
		try {
			usuarioBusiness.criarUsuario("login", "senha", "nome", "endereco", email);
		} catch (ProjetoCaronaException e) {
			Assert.assertEquals("Já existe um usuário com este email", e.getMessage());
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
			Assert.assertEquals("Atributo inexistente", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.getAtributoUsuario(login, "");
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals("Atributo inválido", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.getAtributoUsuario(login, null);
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals("Atributo inválido", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		//login invalido
		try{
			usuarioBusiness.getAtributoUsuario("", "nome");
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals("Login inválido", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.getAtributoUsuario(null, "nome");
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals("Login inválido", e.getMessage());
		}catch (Exception e) {
			fail();
		}
		
		try{
			usuarioBusiness.getAtributoUsuario("123", "nome");
			fail();
		}catch(ProjetoCaronaException e){
			Assert.assertEquals("Usuário inexistente", e.getMessage());
		}catch (Exception e) {
			fail();
		}
	}

}
