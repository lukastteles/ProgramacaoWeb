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
					<div class="qa-message-list" id="wallmessages">
						<div class="message-item">
							<div class="message-inner">
								<div class="message-head clearfix">
									<div class="avatar pull-left">
										<img src="../images/user1.png" class="img-responsive" alt="">
									</div>
									<div class="user-detail">
										<h5>${carona.nomeMotorista}</h5>
	                               		<span class="glyphicon glyphicon-calendar"></span>
	                               		<b class="label-corVerde handle">Data da carona: </b> ${carona.data}<br>
	                               		<span class="glyphicon glyphicon-map-marker"></span>
	                               		<b class="label-corVerde">Origem: </b> ${carona.origem}
	                                   	<b class="label-corVerde"> | Destino: </b> ${carona.destino}<br>
	                                   	<c:if test="${not empty carona.cidade}">
    										<span class="glyphicon glyphicon-globe"></span><b class="label-corVerde"> Cidade: </b> ${carona.cidade}
										</c:if>
									</div>
								</div>		
							</div>
						</div>
					</div>
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
                                			<b>Solicitou uma vaga na sua carona</b><br>
                                    		<!-- 
                                    		<b class="label-corVerde">Origem: </b> Campina grande<br>
                                    		<b class="label-corVerde">Destino: </b> Joao Pessoa<br>
                                    		<b class="label-corVerde">Data: </b> 13/04/2015 | <b class="label-corVerde">Hora: </b> 16:00
                                    		 -->	
                                		</div>
                            		</div>
                        		</div>
                    		</li>
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
                                			<b>Sugeriu um Ponto de Encontro </b><br>
                                			<!-- 
                                    		<b class="label-corVerde">Ponto: </b> Acude Velho<br>
                                    		 -->
                                		</div>
                            		</div> 
                        		</div>
                        		<!--
                        		<div class="row">
                        			<div class="col-md-12">
                        			<div class="comment-text">
                                		<span class="glyphicon glyphicon-calendar"></span>
                                		<b class="label-corVerde">Data da carona: </b> 12/05/2015, 19:00<br>
                                		<span class="glyphicon glyphicon-map-marker"></span>
                                		<b class="label-corVerde">Origem: </b> Campina grande
                                    	<b class="label-corVerde"> | Destino: </b> Joao Pessoa
                                    </div>
                                    </div>
                        		</div>  -->
                    		</li>
                    	</ul>
                		<a href="#" class="btn btn-primary btn-sm btn-block" role="button">Ver todas as notificacoes</a>
            		</div>
        		</div>	
  				
  			</div>
		</div>	
	</div>
</body>
</html>