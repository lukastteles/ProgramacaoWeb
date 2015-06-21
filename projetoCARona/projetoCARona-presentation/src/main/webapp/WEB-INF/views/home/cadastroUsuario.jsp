<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div id="service">
	<div class="container">
		<div class="col-md-5">
			<div class="row logoCustom">
				<a style="padding-top: 20px;" class="" href="home.html"><img src="../images/CARona_LogoMiniAzul.png"></a>
			</div>
			<div>
				<h5>Cadastro de Usuário</h5><p></p>
			</div>
		</div>
	
		<div class="col-md-7">
			<div class="login-form">				
				<form:form modelAttribute="usuarioDomain" method="post">					
					
					<div class="form-group">
						<form:input path="login" type="text" class="form-control" placeholder="Login"/>
						<form:errors path="login" cssClass="error" />
					</div>
	
		  			<div class="form-group">
						<form:input path="senha" type="password" class="form-control" placeholder="Senha"/>					
						<form:errors path="senha" cssClass="error" />
		  			</div>
		  			
			  		<div class="form-group">
						<form:input path="perfil.nome"  type="text" class="form-control" placeholder="Nome"/>
						<form:errors path="perfil.nome" cssClass="error" />		
			  		</div>
			  		
			  		<div class="form-group">
						<form:input path="perfil.endereco" type="text" class="form-control" placeholder="Endereço"/>	
						<form:errors path="perfil.endereco" cssClass="error" />	
			  		</div>  
			  		
			  		<div class="form-group">    			
						<form:input path="perfil.email" type="text" class="form-control" placeholder="Email"/>	
						<form:errors path="perfil.email" cssClass="error" />			
			  		</div>
			  		
		  			<form:button type="submit" class="btn btn-primary btn-lg btn-block">Entrar</form:button>			
				</form:form>
			</div>
			
		</div> <!-- /content -->	
	</div> <!-- /account-container -->
</div>
