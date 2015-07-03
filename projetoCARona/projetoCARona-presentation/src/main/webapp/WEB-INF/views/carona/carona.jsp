<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div id="service">
	<div class="container">
		<div class="col-md-7">
		<div class="panel panel-default"><!--###DETALHES CARONA###-->
	  				<div class="panel-heading">
	  					<div class="row">
	  						<div class="col-md-4">
	  							Detalhes da Carona
	  						</div>
			           	</div>
	  				</div>
	  				<div class="panel-body">
						
						<div class="row">			  				
                				<div class="col-md-8">
			  						<span class="glyphicon glyphicon-map-marker"></span> De ${carona.origem} - para ${carona.destino}  (${carona.tipoCarona})<hr>
			  						<span class="fui-user"></span> <b>Motorista:</b> ${carona.nomeMotorista} <br>			  															
									<c:if test="${not empty carona.cidade}">
    									<span class="glyphicon glyphicon-globe"></span> <b>Cidade:</b> ${carona.cidade}<br>
									</c:if>		
			  					</div> 
			  					<div class="col-md-4">
			  						<span class="badge">${carona.vagas}</span> Vagas Disponíveis<hr>
			  						<span class="fui-calendar"></span> <b>Saída:</b> ${carona.data} <br>
			  						<c:if test="${not empty carona.dataVolta}">
			  							<span class="fui-calendar"></span> <b>Volta:</b> ${carona.dataVolta} <br>
									</c:if>				
			  					</div>
			  				</div>
			  				<div class="row">
				  				<div class="col-md-12">
				  					<hr>
				  					<c:if test="${avalia == false }">
				  					<div class="col-md-6">
				  						<c:if test="${carona.solicitouVaga == false}">
			  								<a  href="solicitarVagaCarona.html?id=${carona.idCarona}" ><span class="fui-plus-circle"></span> <b>Solicitar Vaga</b></a>
										</c:if>
										<c:if test="${carona.solicitouVaga == true}">
			  								<a href="desistirVagaCarona.html?id=${carona.idCarona}&idSolicitacao=${carona.idSolicitacao}"><span class="glyphicon glyphicon-minus-sign"></span> <b>Desistir Vaga</b></a>
										</c:if>					  							
				  					</div>
				  					</c:if>
								</div>
						</div>			
			        </div>
			    </div><!--###DETALHES CARONA###-->
			    <c:if test="${avalia == false }">
			    <c:if test="${carona.vagaAceita == true }">
			    	<div class="panel panel-default"><!-- ###VAGAS### -->
	  				<div class="panel-heading">
	  					<div class="row">
	  						<div class="col-md-12">
	  							Pessoas na carona
	  						</div>
			           	</div>
	  				</div>
	  				<div class="panel-body">
	  					<c:if test="${numSolicitacoes == 0}">
	  						Ainda não há mais pessoas nessa carona
	  					</c:if>
	  					<c:if test="${numSolicitacoes != 0}">
						<c:forEach items="${solicitacoes}" var="solicitacao">
						<div class="row">
							<div class="col-md-3">
								<div class="profile-userpic">
									<img src="../images/user1.png" class="img-responsive" alt="">
								</div>
							</div>
							<div class="col-md-5">
								<b>${solicitacao.idUsuario}</b>
							</div>
						</div>
						<hr>
						</c:forEach>
						</c:if>		
			        </div>
			    </div><!-- ###VAGAS### -->
			    </c:if>
			    </c:if>
			    <c:if test="${avalia == true }">
			    <c:if test="${carona.vagaAceita == true }">
			    	<div class="panel panel-default"><!-- ###AVALIACAO### -->
	  				<div class="panel-heading">
	  					<div class="row">
	  						<div class="col-md-12">
	  							Avalie a carona!
	  						</div>
			           	</div>
	  				</div>
	  				<div class="panel-body">
	  					<div class="row">
	  								<a href="naoFuncionou.html?id=${carona.idCarona}">								
									<div class="col-md-3">
										<span class="badge">não funcionou</span>
										<span data-toggle="tooltip" data-placement="left" title="não funcionou" class="glyphicon glyphicon-thumbs-down"></span>
									</div>
									</a>
									<div class="col-md-1" style="border-left:1px solid #112;height:25px"></div>
									<a href="seguraETraquila.html?id=${carona.idCarona}">
									<div class="col-md-4">
          								<span data-toggle="tooltip" data-placement="left" title="segura e tranquila" class="glyphicon glyphicon-thumbs-up"></span>
          								<span class="badge">segura e tranquila</span>
									</div>
									</a>	
								</div>		
			        </div>
			    </div><!-- ###AVALIACAO### -->
			    </c:if>
			    </c:if>
			    
			    <c:if test="${minhaAvaliacao == 'segura e tranquila' }">
			    	<div class="panel panel-default"><!-- ###AVALIACAO### -->
	  				<div class="panel-heading">
	  					<div class="row">
	  						<div class="col-md-12">
	  							Sua avaliação
	  						</div>
			           	</div>
	  				</div>
	  				<div class="panel-body">
	  					<div class="row">
							<div class="col-md-4">
   								<a><span data-toggle="tooltip" data-placement="left" title="segura e tranquila" class="glyphicon glyphicon-thumbs-up"></span></a>
     							<p>segura e tranquila</p>
							</div>
						</div>		
			        </div>
			    </div><!-- ###AVALIACAO### -->
			    </c:if>
			    <c:if test="${minhaAvaliacao == 'não funcionou' }">
			    	<div class="panel panel-default"><!-- ###AVALIACAO### -->
	  				<div class="panel-heading">
	  					<div class="row">
	  						<div class="col-md-12">
	  							Sua avaliação
	  						</div>
			           	</div>
	  				</div>
	  				<div class="panel-body">
	  					<div class="row">
							<div class="col-md-4">
   								<a><span data-toggle="tooltip" data-placement="left" title="segura e tranquila" class="glyphicon glyphicon-thumbs-down"></span></a>
     							<p>Não funcionou</p>
							</div>
						</div>		
			        </div>
			    </div><!-- ###AVALIACAO### -->
			    </c:if>
			    
			</div>
			
			<div class="col-md-5"><!-- ###PONTOS### -->
				<div class="panel panel-default"><!--###MEUS AMIGOS###-->
	  				<div class="panel-heading">
	  					<div class="row">
	  						<div class="col-md-12">
	  							Pontos Sugeridos
	  						</div>
			           	</div>
	  				</div>
	  				<div class="panel-body">
	  					<c:if test="${numPontos == 0}">
	  						Não há pontos sugeridos para essa carona
	  					</c:if>
	  					<c:if test="${numPomtos != 0}">
						<c:forEach items="${listaPontos}" var="ponto">
						<div class="row">
							<div class="col-md-5"><!-- pontoDeEncontro -->
								<b>${ponto.pontoDeEncontro}</b>
							</div>
							<div class="col-md-1" style="border-left:1px solid #112;height:45px"></div>
							<div class="col-md-6">
								<c:if test="${ponto.foiAceita == true}">
									<dl class="palette">
									<span class="label label-success">Aceito</span>
									</dl>
								</c:if>
								<c:if test="${ponto.foiAceita == false}">
									<dl class="palette">
									<span class="label label-danger">Aguardando Aprovação</span>
									</dl>
								</c:if>
							</div>
						</div>
						<hr>
						</c:forEach>
						</c:if>
						<c:if test="${avalia == false }">
						<form:form modelAttribute="ponto" class="form-inline" method="POST">
							<form:input path="idCarona" value="${carona.idCarona}" type="text" class="form-control" style="visibility:hidden" />
							<form:input path="ponto"  type="text" class="form-control" placeholder="Sugerir ponto de encontro"/>
							<form:button type="submit" class="btn btn-primary">Sugerir</form:button>
						</form:form>			
						</c:if>
			        </div>
			    </div>
			</div>
	</div>
</div>