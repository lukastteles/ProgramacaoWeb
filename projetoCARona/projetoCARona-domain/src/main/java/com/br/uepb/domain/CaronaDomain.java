package com.br.uepb.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.br.uepb.constants.MensagensErro;
import com.br.uepb.exceptions.ProjetoCaronaException;
import com.br.uepb.validator.ValidarCampos;

/**
 * Classe de domínio que define o modelo para a Carona
 * @author Luana Janaina / Lukas Teles
 * @version 0.1
 * @since 19/04/2015
 */

@Entity
@Table(name="CARONAS")
public class CaronaDomain {

	final static Logger logger = Logger.getLogger(CaronaDomain.class);
	
	/** Id da sessão */ //TODO: Deve ser gerado automaticamente
	//@NotNull(message = "O ID da Sessao não pode ser null")
	@JoinTable(name="USUARIOS", joinColumns= @JoinColumn(name = "login") )
	@Column(nullable=false)
	private String idSessao;
	
	/** Id da carona */ //TODO: Deve ser gerado automaticamente
	//@NotNull(message = "O ID da Carona não pode ser null")
	@Id
	@Column(name="id")
	private String id;

	/** Local de origem da carona */ 
	@NotNull(message = "O local de origem da carona não pode ser null")
	@Column(nullable=false)
	private String origem;
	
	/** Local de destino da carona */ 
	@NotNull(message = "O local de Destino da Carona não pode ser null")
	@Column(nullable=false)
	private String destino;
	
	/** Horário da carona */ 
	@NotNull(message = "O Horario da Carona não pode ser null")
	@Pattern(regexp = "d{2}\\:\\d{2}", message="Hora: preencha no formato hh:mm")
	@Column(nullable=false)
	private String hora;
	
	/** Dia da carona */ 	
	@NotNull(message = "O dia da Carona não pode ser null")
	@Pattern(regexp = "d{2}\\/\\d{2}\\/\\d{4}", message="Data: preencha no formato dd/mm/yyyy")
	@Column(nullable=false)
	private String data;
	
	/** Quantidade de vagas disponívels para a carona */ 
	@NotNull(message = "A quantidade de vagas na Carona não pode ser nula")
	@Column(nullable=false)
	private int vagas;
	
	/** Lista de pontos de encontro da carona */
	@OneToMany(mappedBy="idCarona")
	//@JoinColumn(table="PONTOS_ENCONTRO", name = "idCarona",  referencedColumnName="id")	
	@Cascade(CascadeType.ALL)
	//private Collection<PontoDeEncontroDomain> pontoDeEncontro = new ArrayList<PontoDeEncontroDomain>();
	private List<PontoDeEncontroDomain> pontoDeEncontro = new ArrayList<PontoDeEncontroDomain>();
	//private ArrayList<PontoDeEncontroDomain> pontoDeEncontro = new ArrayList<PontoDeEncontroDomain>();
	
	/**
	 * Método construtor de CaronaDomain
	 * @param idSessao Armazena o login do usuário
	 * @param idCarona Id da carona
	 * @param origem Local de origem da carona
	 * @param destino Local de destino da carona
	 * @param data Data que a carona irá acontecer
	 * @param hora Horário em que a carona irá sair
	 * @param vagas Quantidade de vagas disponíveis para a carona
	 * @throws Exception Lança exceção caso algum dos campos informados esteja vazio, null ou inválido
	 */
	public CaronaDomain(String idSessao, String idCarona, String origem, String destino, String data, String hora, int vagas) throws Exception { 
		setID(idCarona);
		setIdSessao(idSessao);
		setOrigem(origem);
		setDestino(destino);
		setData(data);
		setHora(hora);
		setVagas(vagas);
	}
	
	/**
	 * Método para retornar o id da sessão
	 * @return idSessao
	 */
	public String getIdSessao(){
		return idSessao;
	}
	
	/**
	 * Método para informar o id da sessão
	 * @param idSessao id da sessão
	 * @throws Exception Lança exceção se o idSessao for null ou vazio 
	 */
	private void setIdSessao(String idSessao) throws Exception{
		if ( (idSessao == null) || (idSessao.trim().equals("")) ){
			logger.debug("setIdSessao() Exceção: "+MensagensErro.SESSAO_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.SESSAO_INVALIDA);
		}
		
		this.idSessao = idSessao;
	}
	
	/**
	 * Método para retornar o id da carona
	 * @return Id da Carona
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Método para informar o id da carona
	 * @param id Id da carona
	 * @throws Exception Lança exceção se o id da carona informado for null ou vazio
	 */
	public void setID(String id) throws Exception {
		if ( (id == null) || (id.trim().equals("")) ){
			logger.debug("setId() Exceção: "+MensagensErro.IDENTIFICADOR_NAO_INFORMADO);
			throw new ProjetoCaronaException(MensagensErro.IDENTIFICADOR_NAO_INFORMADO);
		}
		
		this.id = id;
	}
	
	/**
	 * Método para retornar o local de origem da carona
	 * @return Origem da carona
	 */
	public String getOrigem() {
		return origem;
	}
	
	/**
	 * Método para informar o local de origem da carona
	 * @param origem Origem da carona
	 * @throws Exception Lança exceção de o local de origem informado for null ou vazio
	 */
	public void setOrigem(String origem) throws Exception {
		if ( (origem == null) || (origem.trim().equals("")) ){
			logger.debug("setOrigem() Exceção: "+MensagensErro.ORIGEM_INVALIDA);
			throw new ProjetoCaronaException(MensagensErro.ORIGEM_INVALIDA);
		}
		this.origem = origem;
	}
	
	/**
	 * Método para retornar o local de destino da carona
	 * @return Destino da carona
	 */
	public String getDestino() {
		return destino;
	}
	
	/**
	 * Método para informar o local de destino da carona
	 * @param destino Destino da carona
	 * @throws Exception Lança exceção se o local de destino informado for null ou vazio
	 */
	public void setDestino(String destino) throws Exception {
		if ( (destino == null) || (destino.trim().equals("")) ){
			logger.debug("setDestino() Exceção: "+MensagensErro.DESTINO_INVALIDO);
			throw new ProjetoCaronaException(MensagensErro.DESTINO_INVALIDO);
		}
		this.destino = destino;
	}
	
	/**
	 * Método para retornar o horário da saída da carona
	 * @return Horário da carona
	 */
	public String getHora() {
		return hora;
	}
	
	/**
	 * Método para informar o horário de saída da carona
	 * @param hora Horário da carona
	 * @throws Exception Lança exceção se o horário da carona informado for null, vazio ou estiver fora do padrão - hora:minuto(hh:mm)
	 */
	public void setHora(String hora) throws Exception {
		//validacao da Hora
		ValidarCampos validar = new ValidarCampos();
		validar.validarHora(hora);
		
		this.hora = hora;
	}
	
	/**
	 * Método para retornar a data de saída da carona
	 * @return Data da carona
	 */
	public String getData() {
		return data;
	}
	
	/**
	 * Método para informar a data de saída da carona
	 * @param data Data da carona
	 * @throws Exception Lança exceção se a data informada estiver null, vazia ou estiver fora do padrão - dia/mês/ano(dd/mm/yyyy)
	 */
	public void setData(String data) throws Exception {
		//validacao da Data
		ValidarCampos validar = new ValidarCampos();
		validar.validarData(data);
		
		this.data = data;
	}
	
	/**
	 * Método para retornar a quantidade de vagas disponíveis na carona
	 * @return Vagas disponíveis na carona
	 */
	public int getVagas() {
		return vagas;
	}
	
	/**
	 * Método para informar a quantidade de vagas disponíveis na carona
	 * @param vagas Vagas disponíveis na carona
	 */
	public void setVagas(int vagas) {
		this.vagas = vagas;
	}
	
	/**
	 * Método para aumentar a quantidade de vagas disponíves na carona
	 */
	public void aumentaVagas(){
		this.vagas++;
	}
	
	/**
	 * Método para diminuir a quantidade de vagas disponíves na carona
	 */
	public void diminuiVagas(){
		this.vagas--;
	}

	//Metodos para pontos de Encontro
	/**
	 * Método para adicionar um ponto de encontro para a carona
	 * @param ponto Ponto de encontro para a carona
	 */
	public void addPontoDeEncontro(PontoDeEncontroDomain ponto){
		pontoDeEncontro.add(ponto);
	}
	
	/**
	 * Método para retornar um conjunto de pontos de encontro pertencentes de uma sugestão de ponto de encontro da carona 
	 * @param idSugestao Id da sugestão de ponto de encontro  
	 * @return Lista de Pontos dos Encontro de uma sugestão de pontos de encontro da carona
	 */
	public List<PontoDeEncontroDomain> getPontoEncontro(String idSugestao) {
		List<PontoDeEncontroDomain> pontos = new ArrayList<PontoDeEncontroDomain>();
		for (PontoDeEncontroDomain ponto : pontoDeEncontro) {
			if(ponto.getIdSugestao().equals(idSugestao))
				pontos.add(ponto);
		}
		return pontos;
	}

	/**
	 * Método para retornar todos os pontos de encontro (aceitos ou não) da carona
	 * @return Lista de todos os pontos de encontro da carona
	 */
	//public ArrayList<PontoDeEncontroDomain> getTodosOsPontos(){
	public List<PontoDeEncontroDomain> getTodosOsPontos(){
		return pontoDeEncontro;
	}
	
	/**
	 * Método para retornar um ponto de encontro da carona
	 * @param ponto Nome do ponto de encontro
	 * @return Ponto de encontro da carona
	 * @throws Exception Lança exceção se o ponto informado não estiver adicionado lista de pontos de encontro da carona
	 */
	public PontoDeEncontroDomain getPontoEncontroByNome(String ponto) throws Exception{		
		for(PontoDeEncontroDomain pontoEncontro : pontoDeEncontro) {
			if(pontoEncontro.getPontoDeEncontro().equals(ponto)){ 
				return pontoEncontro;
			}
		}
		logger.debug("getPontoEncontroByNome() Exceção: "+MensagensErro.PONTO_INVALIDO);
		throw new ProjetoCaronaException(MensagensErro.PONTO_INVALIDO);
	}
	
	/**
	 * Método para informar um conjunto de pontos de encontro aceitos para a carona
	 * @return Lista dos pontos de encontro aceitos
	 */
	public List<PontoDeEncontroDomain> getPontoEncontroAceitos() {
		List<PontoDeEncontroDomain> pontosAceitos = new ArrayList<PontoDeEncontroDomain>();		
		for (PontoDeEncontroDomain ponto : pontoDeEncontro) {
			if(ponto.getFoiAceita() == true)
				pontosAceitos.add(ponto);
		}
		return pontosAceitos;
	}
	
	/**
	 * Método para verificar se um ponto de encontro existe 
	 * @param ponto Nome do ponto de encontro
	 * @return Retorna true se o ponto de encontro existir ou false no caso contrário 
	 */
	public Boolean pontoExiste(String ponto){
		for(PontoDeEncontroDomain pontoEncontro : pontoDeEncontro) {
			if(pontoEncontro.getPontoDeEncontro().equals(ponto)){ 
				return true;
			}
		}
		return false;
	}
}
