<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div class="account-container register">	
	<div class="content">					
		<form:form modelAttribute="usuarioDomain" method="post">	
				
			<div class="form-group">
    			<label>Login: </label>
    			<form:input path="login" type="text" class="form-control" placeholder="Insira o Login" />
			
  			</div>
  			<div class="form-group">
    			<label>Senha: </label>
				<form:input path="senha" type="password" class="form-control" placeholder="Insira a Senha"/>
				
  			</div>
  			<div class="form-group">
    			<label>Nome: </label>
				<form:input path="perfil.nome" type="text" class="form-control" placeholder="Insira o Nome"/>
				
  			</div>
  			<div class="form-group">
    			<label>Endereço: </label>
				<form:input path="perfil.endereco" type="text" class="form-control" placeholder="Insira o Endereço"/>
				
  			</div>  
  			<div class="form-group">
    			<label>Email: </label>    			
				<form:input path="perfil.email" type="text" class="form-control" placeholder="Insira o Email"/>
				
  			</div>
  			
  			<form:button type="submit" class="btn btn-default">Submit</form:button>			
		</form:form>
	
		
	</div> <!-- /content -->	
</div> <!-- /account-container -->
<p></p>
