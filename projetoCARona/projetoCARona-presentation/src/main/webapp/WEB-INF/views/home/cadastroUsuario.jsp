<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div id="service">
	<div class="container">
		<div class="col-md-7 ">				
			<form:form modelAttribute="usuarioVieModel" method="post">					
				<div class="form-group">
	    			<form:input path="login" type="text" class="form-control" placeholder="Insira o Login" />
					<form:errors path="login" cssClass="error" />					
	  			</div>
	  			<div class="form-group">
					<form:input path="senha" type="password" class="form-control" placeholder="Insira a Senha"/>					
					<form:errors path="senha" cssClass="error" />
	  			</div>
	  			
		  		<div class="form-group">
					<form:input path="nome"  type="text" class="form-control" placeholder="Insira o Nome"/>
					<form:errors path="nome" cssClass="error" />		
		  		</div>
		  		
		  		<div class="form-group">
					<form:input path="endereco" type="text" class="form-control" placeholder="Insira o Endereço"/>	
					<form:errors path="endereco" cssClass="error" />	
		  		</div>  
		  		
		  		<div class="form-group">    			
					<form:input path="email" type="text" class="form-control" placeholder="Insira o Email"/>	
					<form:errors path="email" cssClass="error" />			
		  		</div>
	  			
	  			<form:button type="submit" class="btn btn-default">Submit</form:button>			
			</form:form>
		
			
		</div> <!-- /content -->	
	</div> <!-- /account-container -->
</div>
