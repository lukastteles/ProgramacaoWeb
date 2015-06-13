<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div id="service">
	<div class="container">
	<div class="col-md-6 col-md-offset-3">
		<div class="jumbotron">
			
				<form:form modelAttribute="sessaoDomain" method="post">
					<c:if test="login != null">
						<form:errors path="*" cssClass="error" title="ERRO" />
					</c:if>
					<div>
						<form:input path="login" type="text" class="form-control" placeholder="Login" />
						<form:errors path="login" cssClass="error" />	
					</div>
					<br/>
					<div>
						<form:input path="senha" type="password" class="form-control" placeholder="Senha"/>
						<form:errors path="senha" cssClass="error" />
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