<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div id="service">
	<div class="container">
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
		<form:form modelAttribute="interresse" class="form-inline" method="post">
			<div class="form-group">
				<form:input path="origem" type="text" class="form-control" placeholder="Origem"/>
				<form:errors path="origem" cssClass="error" />
			</div>
			<div class="form-group">
				<form:input path="destino" type="text" class="form-control" placeholder="Destino"/>
				<form:errors path="destino" cssClass="error" />
			</div>
			<div class="form-group">
				<form:input path="origem" type="text" class="form-control" placeholder="Origem"/>
				<form:errors path="origem" cssClass="error" />
			</div>
			<div class="form-group">
				<form:input path="data" type="text" class="form-control" placeholder="Data"/>
				<form:errors path="data" cssClass="error" />
			</div>
			<div class="form-group">
				<form:input path="horaInicio" type="text" class="form-control" placeholder="Hora de Inicio"/>
				<form:errors path="horaInicio" cssClass="error" />
			</div>
			<div class="form-group">
				<form:input path="horaFim" type="text" class="form-control" placeholder="Hora de Fim"/>
				<form:errors path="horaFim" cssClass="error" />
			</div>
		</form:form>
		</div>
		</div>
	</div>
</div>
