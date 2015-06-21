<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div id="service">
	<div class="container">
		<ul class="list-group">
					   	<li href="#" class="list-group-item list-group-pesquisa">
			  				<div class="row">			  				
                				<div class="col-md-8">
			  						<span class="glyphicon glyphicon-map-marker"></span> De ${carona.origem} - ${carona.destino}  (${carona.tipoCarona})<hr>
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
				  					
				  					<div class="col-md-3">
				  						<c:if test="${carona.solicitouVaga == false}">
			  								<a  href="carona.html?id=${carona.idCarona}&vaga=s" ><span class="fui-plus-circle"></span> <b>Solicitar Vaga</b></a>
										</c:if>
										<c:if test="${carona.solicitouVaga == true}">
			  								<a href="solicitarVagaCarona.html?id=${carona.idCarona}"><span class="glyphicon glyphicon-minus-sign"></span> <b>Desistir Vaga</b></a>
										</c:if>					  							
				  					</div>
				  					<div class="col-md-5">
			  						<a href="sugerirPonto.html"><span class="glyphicon glyphicon-map-marker"></span> <b>Sugerir Ponto de Encontro</b></a>
				  					</div>
								</div>
							</div>
					   	</li>
					</ul>
	</div>
</div>