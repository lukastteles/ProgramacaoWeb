<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div id="service">
	<div class="container">
		<div class="row"> <!-- ###1a linha### -->
			<div class="col-md-6">
			<div class="row"><!--###MEUS DADOS###-->
					<div class="panel panel-default">
  						<div class="panel-heading">
  							<div class="row">
  							<div class="col-md-4">
  								Meus dados
  							</div>
  							<div class="col-md-offset-11">
  								<a  href="aaa.html" data-toggle="tooltip" title="Editar"><span class="fui-new"></span></a>
		            		</div>
		            		</div>
  						</div>
  						<div class="panel-body">
							<div class="col-md-4">
		          				<div class="demo-download">
		            				<img src="../images/user1.png"/>
		            				</br>
		            				<a>${nome}</a>		            				
		            			</div>
		            		</div>
		            		
		            		<div>
		            			<div class="col-md-2">
		            				<p></p>
		            				Endereço:<br/>
		            				Email:<br/>      			
		            			</div>
		            		
		            			<div class="col-md-6">
		            				<p></p>
		            				<a>${endereco}</a><br/>
		            				<a>${email}</a><br/>	            			
		            			</div>
		            		</div>
		            	</div>
		            </div>
		    	</div>
			</div>
			<div class="col-md-6"> <!--###MINHA REPUTACAO###-->
				<div class="panel panel-default">
  						<div class="panel-heading">
  							<div class="row">
  							<div class="col-md-4">
  								Minha Reputação
  							</div>
		            		</div>
  						</div>
  						<div class="panel-body">
							<div class="row">
								<div class="col-md-6">
									Como motorista
								</div>
								<div class="col-md-6">
									Como caroneiro
								</div>
							</div>
							<div class="row">
							<div class="col-md-5">
							<div class="row">								
									<div class="col-md-6">
										<span data-toggle="tooltip" data-placement="left" title="Caronas que não funcionaram" class="glyphicon glyphicon-thumbs-down"></span>
										<span class="badge">${quantCaronasQueNaoFuncionaram}</span>
									</div>
									<div class="col-md-6">
										<span class="badge">${quantCaronasSegurasETranquilas}</span>
          								<span data-toggle="tooltip" data-placement="left" title="Caronas seguras e tranquilas" class="glyphicon glyphicon-thumbs-up"></span>
									</div>
									
							</div>
							</div>
							<div class="col-md-1" style="border-left:1px solid #112;height:84px"></div>
							
							<div class="col-md-6">
							<div class="row">
									
								
									<div class="col-md-6">
										<span data-toggle="tooltip" data-placement="left" title="Caronas que faltei" class="glyphicon glyphicon-thumbs-down "></span>
										<span class="badge">${quantCaronasQueFaltei}</span>
									</div>
									
									<div class="col-md-6">
										<span class="badge">${quantCaronasQueParticipei}</span>
										<span data-toggle="tooltip" data-placement="left" title="Caronas que participei" class="glyphicon glyphicon-thumbs-up"></span>
									</div>
							</div>	
							</div>
							</div>
		            	</div>
		            </div>
			</div>
		</div><!-- ###fim 1a linha### -->
				
		<div class="row"> <!-- ###2a linha### -->
			<div class="panel panel-default"><!--###MEUS AMIGOS###-->
  						<div class="panel-heading">
  							<div class="row">
  							<div class="col-md-4">
  								Meus Amigos
  							</div>
		            		</div>
  						</div>
  						<div class="panel-body">
							Você ainda não tem amigos =(     
							<a  href="aaa.html" >Conheça outras pessoas!</a>
							
		            	</div>
		            </div>
		</div> <!-- ###fim 2a linha### -->    	
		
		 
		<div class="row"> <!-- ###3a linha### -->
			
			<div class="col-md-6"><!-- Minhas Caronas -->
					<div class="panel panel-default">
  						<div class="panel-heading">
  							<div class="row">
  							<div class="col-md-4">
  								Minhas caronas
  							</div>
  							<div class="col-md-offset-11">
  								<span class="badge">${totalCaronas}</span>
  							</div>
		            		</div>
  						</div>
  						<div class="panel-body">
							<c:choose>
  							<c:when test="${totalCaronas == 0}">
  								<a>Não há caronas para mostrar</a>
  							</c:when>
  							<c:otherwise>
							<c:forEach items="${listaCaronas}" var="carona">	
			  				<dl class="palette palette-turquoise">
			  				<div class="row">
			  					<div class="col-md-12">
			  						<span class="glyphicon glyphicon-map-marker"></span><small>De ${carona.origem} - ${carona.destino}  (${carona.tipoCarona})</small>
			  						
			  					</div>
			  					
			  					<div class="col-md-6">
			  						<span class="fui-user"></span> <b>Motorista:</b><small>${carona.nomeMotorista}</small><br>
			  						<c:if test="${carona.cidade != null}">
			  							<span class="glyphicon glyphicon-globe"></span> <b>Cidade:</b> ${carona.cidade}<br>
			  						</c:if>	
			  					</div> 
			  					<div class="col-md-6">
			  						<span class="fui-calendar"></span> <b>Saída:</b> ${carona.data} <br>
			  						<c:if test="${carona.dataVolta != null}"> 
			  							<span class="fui-calendar"></span> <b>Volta:</b> ${carona.dataVolta} <br>
			  						</c:if> 			  										
			  					</div>
			  					
			  				</div>
							</dl>
							<p></p>	
						</c:forEach>
						</c:otherwise>
						</c:choose>
		        	</div>  	
		    	</div>     
			</div><!-- Fim Minhas caronas -->
		
			<div class="col-md-6">
				<div class="panel panel-default">
  						<div class="panel-heading">
  							<div class="row">
  							<div class="col-md-6">
  								Caronas que participei
  							</div>
  							<div class="col-md-offset-11">
  								<span class="badge">${totalCaronasParticipei}</span>
  							</div>
		            		</div>
  						</div>
  						<div class="panel-body">
							<c:choose>
  							<c:when test="${totalCaronasParticipei == 0}">
  								<a>Não há caronas para mostrar</a>
  							</c:when>
  							<c:otherwise>
							<c:forEach items="${listaCaronasParticipei}" var="carona">	
			  				<dl class="palette palette-turquoise">
			  				<div class="row">
			  					<div class="col-md-12">
			  						<span class="glyphicon glyphicon-map-marker"></span><small>De ${carona.origem} - ${carona.destino}  (${carona.tipoCarona})</small>
			  						
			  					</div>
			  					
			  					<div class="col-md-6">
			  						<span class="fui-user"></span> <b>Motorista:</b><small>${carona.nomeMotorista}</small><br>
			  						<c:if test="${carona.cidade != null}">
			  							<span class="glyphicon glyphicon-globe"></span> <b>Cidade:</b> ${carona.cidade}<br>
			  						</c:if>	
			  					</div> 
			  					<div class="col-md-6">
			  						<span class="fui-calendar"></span> <b>Saída:</b> ${carona.data} <br>
			  						<c:if test="${carona.dataVolta != null}"> 
			  							<span class="fui-calendar"></span> <b>Volta:</b> ${carona.dataVolta} <br>
			  						</c:if> 			  										
			  					</div>
			  					
			  				</div>
							</dl>
							<p></p>	
						</c:forEach>
						</c:otherwise>
						</c:choose>
		            	</div>
		            </div>
			</div>
		</div>
				
				
		         
		 
		 
		 
	</div>
</div>