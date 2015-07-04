<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div id="service">
	<div class="container">
		<div class="col-md-7"><!--###DETALHES CARONA###-->
		<div class="panel panel-default">
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
			  							
			        </div>
			    </div><!--###DETALHES CARONA###-->
			     <c:if test="${avalia == false }">
			    <div class="panel panel-default"><!-- ###SOLICITACOES### -->
	  				<div class="panel-heading">
	  					<div class="row">
	  						<div class="col-md-12">
	  							Solicitações
	  						</div>
			           	</div>
	  				</div>
	  				<div class="panel-body">
	  					<c:if test="${numSolicitacoes == 0}">
	  						Não há solicitações para essa carona
	  					</c:if>
	  					<c:if test="${numSolicitacoes != 0}">
						<c:forEach items="${solicitacoes}" var="solicitacao">
						<div class="row">
							<div class="col-md-5">
								<b>${solicitacao.idUsuario}</b>
							</div>
							<div class="col-md-1" style="border-left:1px solid #112;height:45px"></div>
							<div class="col-md-6">
								<c:if test="${solicitacao.foiAceita == true}">
									<div class="row">
										<div class="col-md-12">
											<dl class="palette">
												<span class="label label-success">Aceito</span>&nbsp;&nbsp;
												<a href="desfazerSolicitacao.html?id=${carona.idCarona}&idSolicitacao=${solicitacao.id}">
												<u>Desfazer</u>
												</a>
											</dl>
										</div>
									</div>
								</c:if>
								<c:if test="${solicitacao.foiAceita == false}">
									<dl class="palette">
									<a href="aceitarSolicitacao.html?id=${carona.idCarona}&idSolicitacao=${solicitacao.id}">
										<u>Aceitar</u>
									</a>
									&nbsp;&nbsp;
									<a href="recusarSolicitacao.html?id=${carona.idCarona}&idSolicitacao=${solicitacao.id}">
										<u>Recusar</u>
									</a>
									</dl>
								</c:if>
							</div>
						</div>
						<hr>
						</c:forEach>
						</c:if>		
			        </div>
			    </div><!-- ###SOLICITACOES### -->
			    </c:if>
			     <c:if test="${avalia == true }">
			     	<div class="panel panel-default"><!-- ###AVALIA_VAGAS### -->
	  				<div class="panel-heading">
	  					<div class="row">
	  						<div class="col-md-12">
	  							Avaliação das pessoas na carona
	  						</div>
			           	</div>
	  				</div>
	  				<div class="panel-body">
	  					<c:if test="${numSolicitacoes == 0}">
	  						ninguem participou dessa carona =(
	  					</c:if>
	  					<c:if test="${numSolicitacoes != 0}">
						<c:forEach items="${solicitacoes}" var="solicitacao">
						<div class="row">
							<div class="col-md-1">
								<div class="profile-userpic">
									<img src="../images/user1.png" class="img-responsive" alt="">
								</div>
							</div>
							<div class="col-md-2">
								<b>${solicitacao.idUsuario}</b>
							</div>
								<c:if test="${solicitacao.reviewVaga == null }">
								<a href="faltou.html?id=${carona.idCarona}&idCaroneiro=${solicitacao.idUsuario}">								
									<div class="col-md-3">
										<span class="badge">faltou</span>
										<span data-toggle="tooltip" data-placement="left" title="faltou" class="glyphicon glyphicon-thumbs-down"></span>
									</div>
								</a>
								<div class="col-md-1" style="border-left:1px solid #112;height:25px"></div>
								<a href="participou.html?id=${carona.idCarona}&idCaroneiro=${solicitacao.idUsuario}">
									<div class="col-md-3">
          								<span data-toggle="tooltip" data-placement="left" title="participou" class="glyphicon glyphicon-thumbs-up"></span>
          								<span class="badge">participou</span>
									</div>
								</a>
								</c:if>
								<c:if test="${solicitacao.reviewVaga != null }">
									<c:if test="${solicitacao.reviewVaga == 'faltou' }">
										<div class="col-md-5">
										<p>faltou</p>
										<a><span data-toggle="tooltip" data-placement="left" title="faltou" class="glyphicon glyphicon-thumbs-down"></span></a>
										</div>
									</c:if>
									<c:if test="${solicitacao.reviewVaga == 'não faltou' }">
										<div class="col-md-5">
										<p>não faltou</p>
										<a><span data-toggle="tooltip" data-placement="left" title="não faltou" class="glyphicon glyphicon-thumbs-up"></span></a>
										</div>
									</c:if>
								</c:if>
						</div>
						<hr>
						</c:forEach>
						</c:if>		
			        </div>
			    </div><!-- ###AVALIA_VAGAS### -->
			     </c:if>
			</div>
			
			<div class="col-md-5"><!-- ###PONTOS### -->
				<div class="panel panel-default">
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
									<div class="row">
										<div class="col-md-12">
											<dl class="palette">
												<span class="label label-success">Aceito</span>&nbsp;&nbsp;
												<a href="desistirPonto.html?id=${carona.idCarona}&ponto=${ponto.pontoDeEncontro}&idPonto=${ponto.idSugestao}">
												<u>Desistir</u>
												</a>
											</dl>
										</div>
									</div>
								</c:if>
								<c:if test="${ponto.foiAceita == false}">
									<dl class="palette">
									<a href="acitarPonto.html?id=${carona.idCarona}&ponto=${ponto.pontoDeEncontro}&idPonto=${ponto.idSugestao}">
									
										<u>Aceitar</u>
									
									</a>
									&nbsp;&nbsp;
									<a href="recusarPonto.html?id=${carona.idCarona}&ponto=${ponto.pontoDeEncontro}&idPonto=${ponto.id}">
									
										<u>Recusar</u>
									
									</a>
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
							<form:input path="ponto"  type="text" class="form-control" placeholder="Adicionar ponto de encontro"/>
							<form:button type="submit" class="btn btn-primary">Adicionar</form:button>
						</form:form>			
						</c:if>
			        </div>
			    </div>
			</div>
	</div>
</div>