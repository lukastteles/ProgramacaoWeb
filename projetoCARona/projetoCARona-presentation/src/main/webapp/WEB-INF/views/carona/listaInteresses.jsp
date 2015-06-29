<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div id="service">
	<div class="container">
		<div>
			<a href="cadastroInteresse.html"><span class="fui-plus"></span>&nbsp;Cadastrar interresse</a>
		</div>
		<br/>
		<div class="panel panel-default">
  						<div class="panel-heading">
  							<div class="row">
  							<div class="col-md-4">
  								Meus Interesses em Caronas
  							</div>
  							<div class="col-md-offset-11">
  								<span class="badge">${totalInteresses}</span>
  							</div>
		            		</div>
  						</div>
  						<div class="panel-body">
							<c:choose>
  							<c:when test="${totalInteresses == 0}">
  								<a>Não há Interesses em caronas para mostrar</a>
  							</c:when>
  							<c:otherwise>
							<c:forEach items="${listaInteresses}" var="interesse">	
			  				<dl class="palette palette-clouds">
			  				<div class="row">
			  					<div class="col-md-5">
			  						<a><span class="glyphicon glyphicon-map-marker"></span><a>
			  						<small>&nbsp;De ${interesse.origem} - ${interesse.destino} </small>
			  					</div>
			  					<div class="col-md-5">
			  						<span class="fui-calendar"></span>
			  						<small>&nbsp;Data:<b>${interesse.data}</b>&nbsp;|&nbsp;${interesse.hora}&nbsp;-&nbsp;${interesse.horaFim}</small>		  						
			  					</div>
			  					<div class="col-md-2">
			  							<a href="apagaInteresse.html?id=${interesse.id}"  data-toggle="tooltip" title="Apagar"><span class="fui-cross"></span></a>
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