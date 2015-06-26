<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div id="service">
	<div class="row">
  		<div class="col-md-8 col-md-offset-1">
            <div class="panel-body tile-pesquisa">
            	<b class="label-corVerde handle">Informe os valores para a pesquisa:</b><br>
            	<form:form modelAttribute="caronaDomainViewModel" class="form-inline" method="post">	
	            	<div class="form-group form-group-sm"> 
	              		<form:input path="origem"  type="text" class="form-control" placeholder="Origem"/>
	            		<form:input path="destino"  type="text" class="form-control" placeholder="Destino"/>
	              		<form:input path="cidade"  type="text" class="form-control" placeholder="Cidade"/>
	            	</div>
	            	
		  			<form:button type="submit" class="btn btn-primary btn-xs" title="Edit">
		  				<span class="glyphicon glyphicon-ok"></span> Localizar
		  			</form:button>
		  			
	            </form:form>
            </div>
            
  			<div class="row tile">
                <h4 class="col-md-offset-1 panel-title"> Pesquisa de Caronas</h4>	
            </div>
            
            <c:if test="${empty listaCaronas}">
            	<div class="user-detail">
	           		</b> Nenhum dado encontrado para a pesquisa<br>
	           	</div>
			</c:if>
			
            <c:forEach items="${listaCaronas}" var="carona">				
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
			                           	<b class="label-corVerde handle">Data da saida: </b> ${carona.data}
			  							<c:if test="${not empty carona.dataVolta}">
			        	               		<b class="label-corVerde handle"> | Data da volta: </b> ${carona.dataVolta}
			                          	</c:if>
			                            <br>
			                            <span class="glyphicon glyphicon-map-marker"></span>
			                            <b class="label-corVerde">Origem: </b> ${carona.origem}
			                            <b class="label-corVerde"> | Destino: </b> ${carona.destino}<br>
			                            <c:if test="${not empty carona.cidade}">
		    								<span class="glyphicon glyphicon-globe"></span><b class="label-corVerde"> Cidade: </b> ${carona.cidade}
										</c:if>
										<div class="">
											<br>
											<hr-2>
				  							<a href="carona.html?id=${carona.idCarona}"><span class="fui-search"></span> <b>Detalhar Carona</b></a>
				  						</div>
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
            </c:forEach>
  		
  		</div>
  	</div>
	 
<!-- 
	<div class="container">
		<div class="col-md-3">
			<div class="panel panel-default" >
				<div class="panel-heading">Localizar CARonas</div> 
	  			<div class="panel-body">
	          		<form:form modelAttribute="caronaDomainViewModel" class="form-inline" method="post">	
	            		<div class="form-group">	            				
	              			Origem: 
	              			<form:input path="origem"  type="text" class="form-control" placeholder="Origem"/>
	            		</div>
	            		<div class="form-group">	            				
	              			Destino: 
	              			<form:input path="destino"  type="text" class="form-control" placeholder="Destino"/>
	            		</div>
	            		<div class="form-group">	            				
	              			Cidade: 
	              			<form:input path="cidade"  type="text" class="form-control" placeholder="Cidade"/>
	            		</div>
	            		<p><p>
		  				<form:button type="submit" class="btn btn-primary btn-lg btn-block">Localizar</form:button>
	            	</form:form>
	  			
	  			</div> 
	  		</div> 
		</div> 
		 
		<div class="col-md-9">
	  		<div class="col-md-12">  				 				  	
	  			<ul class="list-group">
				    <li class="list-group-item">
				    	<span class="badge">${totalCaronas}</span>
				    	Caronas Encontradas ${filtoConsulta}
				    </li>
				</ul>
				<c:forEach items="${listaCaronas}" var="carona">
					<ul class="list-group">
					   	<li href="#" class="list-group-item list-group-pesquisa">
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
				  					<div class="col-md-4">
				  						<a href="carona.html?id=${carona.idCarona}"><span class="fui-search"></span> <b>Detalhar Carona</b></a>
				  					</div>
				  					
								</div>
							</div>
					   	</li>
					</ul>  					
				</c:forEach>  						  			
	  		</div>
		</div>
	</div>
-->
</div><!-- service -->