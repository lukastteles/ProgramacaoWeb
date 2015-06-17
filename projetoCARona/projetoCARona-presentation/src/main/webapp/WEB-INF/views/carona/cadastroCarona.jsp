<%@ include file="/WEB-INF/views/includeTags.jsp"%>

<script>
      function caronaNormal( vagas, cidade, minCaroneiros, dataVolta, tipo ) {
        var divV = document.getElementById( vagas );
        var divC = document.getElementById( cidade );
        var divMC = document.getElementById( minCaroneiros );
        var divDV = document.getElementById( dataVolta );
        var inputTipo = document.getElementById( tipo );
        
        divV.style.visibility = "visible";
        divC.style.visibility = "hidden";
        divMC.style.visibility = "hidden";
        divDV.style.visibility = "hidden";
        inputTipo.setAttribute("value", "C");
      }
</script>

<script>
      function caronaMunicipal( vagas, cidade, minCaroneiros, dataVolta, tipo ) {
        var divV = document.getElementById( vagas );
        var divC = document.getElementById( cidade );
        var divMC = document.getElementById( minCaroneiros );
        var divDV = document.getElementById( dataVolta );
        var inputTipo = document.getElementById( tipo );
        
        divV.style.visibility = "visible";
        divC.style.visibility = "visible";
        divMC.style.visibility = "hidden";
        divDV.style.visibility = "hidden";
        inputTipo.setAttribute("value", "M");
      }
 </script>
 
 <script>
      function caronaRelampago( vagas, cidade, minCaroneiros, dataVolta, tipo ) {
        var divV = document.getElementById( vagas );
        var divC = document.getElementById( cidade );
        var divMC = document.getElementById( minCaroneiros );
        var divDV = document.getElementById( dataVolta );
        var inputTipo = document.getElementById( tipo );
        
        divV.style.visibility = "hidden";
        divC.style.visibility = "hidden";
        divMC.style.visibility = "visible";
        divDV.style.visibility = "visible";
        inputTipo.setAttribute("value", "R");
      }
 </script>
<div id="service">
	
	<div class="col-md-10 col-md-offset-1">	
			
			<h3>Cadastrar Carona</h3>
			<a href="#" onclick="caronaNormal('divVagas', 'divCidade', 'divMinCaroneiros', 'divDataVolta', 'tipo');">Normal</a>
			<a href="#" onclick="caronaMunicipal('divVagas', 'divCidade', 'divMinCaroneiros', 'divDataVolta', 'tipo');">Municipal</a>
			<a href="#" onclick="caronaRelampago('divVagas', 'divCidade', 'divMinCaroneiros', 'divDataVolta', 'tipo');">Relâmpago</a>
			<form:form id="form_carona" modelAttribute="carona" method="post">
				<form:input id="tipo" path="tipoCarona" type="text" class="form-control" style="visibility:hidden" />
				<div class="row">
				<div class="col-md-6">
						
				<div>
					<form:input path="origem" type="text" class="form-control" placeholder="Origem" />
					<form:errors path="origem" cssClass="error" />	
				</div>
					
					<div>
						<form:input path="destino" type="text" class="form-control" placeholder="Destino"/>
						<form:errors path="destino" cssClass="error" />
					</div>
					<div>
						<form:input path="data" type="text" class="form-control" placeholder="Data"/>
						<form:errors path="data" cssClass="error" />
					</div>
					<div>
						<form:input path="hora" type="text" class="form-control" placeholder="Hora"/>
						<form:errors path="hora" cssClass="error" />
					</div>
				</div>
				<div class="col-md-6">
					
					<div id="divVagas">
						<form:input path="vagas" type="text" class="form-control" placeholder="Vagas"/>
						<form:errors path="vagas" cssClass="error" />
					</div>
					<div id="divCidade" style="visibility:hidden">
						<form:input path="cidade" type="text" class="form-control" placeholder="Cidade"/>
						<form:errors path="cidade" cssClass="error" />
					</div>
					<div id="divDataVolta"  style="visibility:hidden">
						<form:input path="dataVolta" type="text" class="form-control" placeholder="Data Volta"/>
						<form:errors path="dataVolta" cssClass="error" />
					</div>
					<div id="divMinCaroneiros"  style="visibility:hidden">
						<form:input path="minimoCaroneiros" type="text" class="form-control" placeholder="Minimo de Caroneiros"/>
						<form:errors path="minimoCaroneiros" cssClass="error" />
					</div>
					<br/>
				</div>
					<div class="col-md-12 ">
						<input type="submit" value="Casdastrar" class="btn btn-primary"  >
					</div>
				
				</div>	
				</form:form>	
				<br/><br/><br/>
	</div>
</div><!-- service -->