<!DOCTYPE html>
<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<html>
<body>
	<div id="service">
		<div class="row">
  			<div class="col-md-8">
				<div class="row tile">
                	<h4 class="col-md-offset-1 panel-title"> Caronas Recentes </h4>	
            	</div>
            	
    			<c:forEach items="${listaCaronas}" var="carona">
    				<a class="label-corAzul message-inner" href="carona.html?id=${carona.idCarona}">
						<div class="qa-message-list" id="wallmessages">
							<div class="message-item">		
								<div class="message-inner">
									<div class="message-head clearfix">
										<div class="avatar pull-left">
											<img src="../images/user1.png" class="img-responsive" alt="">
										</div>
										<div class="user-detail">
											<div class="col-md-8">
												<span class="glyphicon glyphicon-user"></span>
												<b class="label-corVerde handle">Motorista: </b> ${carona.nomeMotorista}<br>
			                               		<span class="glyphicon glyphicon-calendar"></span>
			                               		<b class="label-corVerde handle">Data de saída: </b> ${carona.data}
			                               		<c:if test="${not empty carona.dataVolta}">
				        	               			<b class="label-corVerde handle"> | Data da volta: </b> ${carona.dataVolta}
				                          		</c:if> <br>
			                               		<span class="glyphicon glyphicon-map-marker"></span>
			                               		<b class="label-corVerde">Origem: </b> ${carona.origem}
			                                   	<b class="label-corVerde"> | Destino: </b> ${carona.destino}<br>
			                                   	<c:if test="${not empty carona.cidade}">
		    											<span class="glyphicon glyphicon-globe"></span><b class="label-corVerde"> Cidade: </b> ${carona.cidade}
												</c:if>
											</div>
											<div class="col-md-4">
												<b>Carona ${carona.tipoCarona}</b><br>
												<span class="background-corVerde badge">${carona.vagas}</span> Vagas Disponíveis
											</div>
										</div>
									</div>		
								</div>
							</div>
						</div>
					</a>
				</c:forEach>
				
  			</div>
  			
  			
  			<!-- Barra de Notificacoes -->
  			<div class="col-md-4">
  				<div class="panel panel-default widget">
            		<div class="panel-heading">
                		<h3 class="panel-title"> Notificacoes Recentes </h3>	
            		</div>
            		<div class="panel-body">
                		<ul class="list-group">
                		
                			<!-- Solicitacoes Pendentes -->
		                	<c:forEach items="${listaSolicitacoes}" var="solicitacao">
	                    		<li class="list-group-item">
	                            	<a href="carona.html?id=${solicitacao.idCarona}">
	                        		<div class="row">
	                        				<div class="col-md-3">
												<div class="profile-userpic">
													 <img src="../images/user1.png" class="img-responsive" alt="">
												</div>
											</div>
	                            			<div class="col-md-9">
	                                			<div class="comment-text">
	                                				<b class="label-corVerde">${solicitacao.idUsuario}</b><br>
	                                				<b>Solicitou uma vaga na sua carona</b><br>
	                                			</div>
	                            			</div>
	                        			</div>
	                        		</a>
	                    		</li>
                    		</c:forEach>
                    		
		                	<c:forEach items="${listaSugestoes}" var="sugestao">
			                	<li class="list-group-item">
			                		<a href="carona.html?id=${sugestao.idCarona}">
		                        		<div class="row">
		                        			<div class="col-md-3">
		                            			
													<div class="profile-userpic">
														 <img src="../images/user1.png" class="img-responsive" alt="">
													</div>
												
											</div>
		                            		<div class="col-md-9">
		                                		<div class="comment-text">
		                                			<b class="label-corVerde">${sugestao.idUsuario}</b><br>
		                                			<b>Sugeriu um Ponto de Encontro </b>
		                                		</div>
		                            		</div> 
		                        		</div>
	                        		</a>
	                    		</li>
		                	</c:forEach>
		                	
		                	<c:forEach items="${listaInteresses}" var="interesse">
			                	<li class="list-group-item">
			                		<a href="carona.html?id=${interesse.id}">
		                        		<div class="row">
		                        			<div class="col-md-3">
												<div class="profile-userpic">
													 <img src="../images/user1.png" class="img-responsive" alt="">
												</div>
											</div>
		                            		<div class="col-md-9">
		                                		<div class="comment-text">
		                                			<b class="label-corVerde">${interesse.idSessao}</b><br>
		                                			<b>Cadastrou uma nova carona</b><br>
		                                			<b>De ${interesse.origem} - ${interesse.destino} - ${interesse.data}, ${interesse.hora}</b>
		                                		</div>
		                            		</div> 
		                        		</div>
	                        		</a>
	                    		</li>
		                	</c:forEach>
		                	<!-- SOLICITACAO DE AMIZADE 
                    		<li class="list-group-item">
                        		<div class="row">
                        			<div class="col-md-3">
                            			<a href="#">
											<div class="profile-userpic">
												 <img src="../images/user1.png" class="img-responsive" alt="">
											</div>
										</a>
									</div>
                            		<div class="col-md-9">
                                		<div class="comment-text">
                                			<b class="label-corVerde">Fulano de Tal</b><br>
                                			<b>Solicitou sua amizade</b><br>
                                			<div class="action">
			                                    <button type="button" class="btn btn-primary btn-xs" title="Edit">
			                                        <span class="glyphicon glyphicon-ok"></span> Aceitar
			                                    </button>
			                                    <button type="button" class="btn btn-danger btn-xs" title="Delete">
			                                        <span class="glyphicon glyphicon-trash"></span> Recusar
			                                    </button>
			                                </div>	
                                		</div>
                            		</div>
                        		</div>
                    		</li> 
                    		 -->
                    		
                    	</ul>
            		</div>
        		</div>	
  				
  			</div>
		</div>	
	</div>
</body>
</html>