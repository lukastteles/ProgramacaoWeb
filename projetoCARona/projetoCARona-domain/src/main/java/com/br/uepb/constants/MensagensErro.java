package com.br.uepb.constants;

/**
 * Classe para definir mensagens de erro padrão
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 20/04/2015
 */

public class MensagensErro {
	
	//Mensagens para Carona	
	public static final String CARONA_INVALIDA = "Carona Inválida";
	public static final String ORIGEM_INVALIDA = "Origem inválida";
	public static final String DESTINO_INVALIDO = "Destino inválido";	
	public static final String ATRIBUTO_INVALIDO = "Atributo inválido";
	public static final String IDENTIFICADOR_INVALIDO = "Identificador do carona é inválido";
	public static final String TRAJETO_INVALIDO = "Trajeto Inválida";
	public static final String MINIMO_CARONEIROS_INVALIDO = "Minimo Caroneiros inválido";
	
	public static final String IDENTIFICADOR_NAO_INFORMADO = "Identificador da carona não informado";
	public static final String TIPO_CARONA_INVALIDO = "Tipo da carona inválido";
	public static final String CARONA_NAO_IDENTIFICADA ="Carona não identificada para o usuário informado";
	public static final String CARONA_REJEITADA ="A carona foi rejeitada por falta de vaga";
	
	public static final String CARONA_INEXISTENTE = "Carona Inexistente";	
	public static final String ITEM_INEXISTENTE = "Item inexistente";
	public static final String ATRIBUTO_INEXISTENTE = "Atributo inexistente";
	public static final String TRAJETO_INEXISTENTE = "Trajeto Inexistente";
	public static  final String CIDADE_INEXISTENTE = "Cidade inexistente";
	
	
	//Mensagens para Sessao e Usuario
	public static final String SESSAO_INVALIDA = "Sessão inválida";
	public static final String SESSAO_INEXISTENTE = "Sessão inexistente";
	public static final String USUARIO_INEXISTENTE = "Usuário inexistente";
	public static final String USUARIO_JA_EXISTE = "Já existe um usuário com este login";
	public static final String USUARIO_SEM_VAGA_NA_CARONA = "Usuário não possui vaga na carona.";
	public static final String EMAIL_JA_EXISTE = "Já existe um usuário com este email";
	
	public static final String LOGIN_INVALIDO = "Login inválido";
	public static final String SENHA_INVALIDA = "Senha inválida";
	public static final String NOME_INVALIDO = "Nome inválido";
	public static final String ENDERECO_INVALIDO = "Endereco inválido";
	public static final String EMAIL_INVALIDO = "Email inválido";
	public static final String DATA_INVALIDA = "Data inválida";
	public static final String HORA_INVALIDA = "Hora inválida";
	public static final String VAGA_INVALIDA = "Vaga inválida";
	
	//Review vaga na carona
	public static final String FALTOU = "faltou";
	public static final String NAO_FALTOU = "não faltou";
	
	//Review carona
	public static final String SEGURA_TRANQUILA = "segura e tranquila";
	public static final String NAO_FUNCIONOU = "não funcionou";
	
	public static final String OPCAO_INVALIDA = "Opção inválida.";
	
	//Mensagens para PontosEncontro
	public static final String PONTO_INVALIDO = "Ponto Inválido";	
	
	//Mensagens para SolicitacaoVaga
	public static final String SOLICITACAO_INVALIDA = "Solicitação inválida";
	public static final String SOLICITACAO_INEXISTENTE = "Solicitação inexistente";
	public static final String VAGAS_OCUPADAS = "Todas as vagas já foram ocupadas!";
	
	//Indices
	public static final String INDICE_INVALIDO = "Indice não encontrado";
	
	//Carona Preferencial
	public static final String USUARIO_NAO_PREFERENCIAL = "Usuário não está na lista preferencial da carona";
	
	//Envio de email
	public static final String EMAIL_NAO_ENVIADO = "Problemas no envio do email. Email nao enviado";
	
}
