<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div id="service">
	<div class="container">
	<div class="col-md-6 col-md-offset-3">
		<div class="jumbotron">
			
				<form:form modelAttribute="sessaoDomain" method="post">
					
					<div>
						<form:input path="login" type="text" class="form-control" placeholder="Login" />	
					</div>
					<br/>
					<div>
						<form:input path="senha" type="password" class="form-control" placeholder="Senha"/>
					</div>
					<br/>
					<div class="col-md-12 ">
						<input type="submit" value="Entrar" class="btn btn-primary">
					</div>
					
				</form:form>
		</div>
	</div>
	</div>
</div><!-- service -->