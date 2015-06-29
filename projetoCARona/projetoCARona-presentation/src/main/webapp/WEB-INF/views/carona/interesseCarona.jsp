<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div id="service">
	<div class="container">
		<div class="panel panel-default">
  					<div class="panel-heading">
  						<div class="row">
  							<div class="col-md-4">
  								Cadastrar Interesse em carona
  							</div>
		            	</div>
  					</div>
  					<div class="panel-body">
		<form:form modelAttribute="interesse" class="form-inline" method="post">
			<div class="form-group">
				<form:input path="origem" type="text" class="form-control" placeholder="Origem"/>
				<form:errors path="origem" cssClass="error" />
			</div>
			<br/><br/>
			<div class="form-group">
				<form:input path="destino" type="text" class="form-control" placeholder="Destino"/>
				<form:errors path="destino" cssClass="error" />
			</div>
			<br/><br/>
			<div class="form-group">
				<form:input path="data" type="text" class="form-control" placeholder="Data"/>
				<form:errors path="data" cssClass="error" />
			</div>
			<br/><br/>
			<div class="form-group">
				<form:input path="hora" type="text" class="form-control" placeholder="Hora de Inicio"/>
				<form:errors path="hora" cssClass="error" />
			</div>
			<br/><br/>
			<div class="form-group">
				<form:input path="horaFim" type="text" class="form-control" placeholder="Hora de Fim"/>
				<form:errors path="horaFim" cssClass="error" />
			</div>
			<br/><br/>
			<input type="submit" value="Casdastrar" class="btn btn-primary"  >
		</form:form>
		</div>
		</div>
	</div>
</div>
