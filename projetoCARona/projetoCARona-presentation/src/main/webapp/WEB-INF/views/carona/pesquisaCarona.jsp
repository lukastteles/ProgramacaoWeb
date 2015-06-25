<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div id="service">
	<div class="container">
		<div class="col-md-3">
			<div class="panel panel-default" >
				<div class="panel-heading">Localizar CARonas</div> <!-- panel-heading -->
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
	  			
	  			</div> <!-- panel-body -->
	  		</div> <!-- panel panel-default -->
		</div> <!-- col-md-3 -->
		
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
		</div> <!-- col-md-9 --> 	
	</div><!-- container -->
</div><!-- service -->