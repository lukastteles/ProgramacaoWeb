<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<div id="service">
	<div class="container">
		<div class="col-md-5">
			<div class="row logoCustom">
				<a style="padding-top: 20px;" class="" href="home.html"><img src="../images/CARona_LogoMiniAzul.png"></a>
			</div>
			<div>	
				<h5>Acesso ao Sistema</h5><p></p>
			</div>
		</div>
		
		<div class="col-md-6">
			<div class="login-form">
				<form:form modelAttribute="sessaoDomain" method="post">
	            	<div class="form-group">
	            		<form:input path="login" type="text" class="form-control login-field" placeholder="Login" />
						<form:errors path="login" cssClass="error" />
	            	</div>
	            		
	            	<div class="form-group">
						<form:input path="senha" type="password" class="form-control login-field" placeholder="Senha"/>
						<form:errors path="login" cssClass="error" />
	            	</div>
	
		  			<form:button type="submit" class="btn btn-primary btn-lg btn-block">Entrar</form:button>
	            	<!--  <a class="login-link" href="#">Lost your password?</a>  -->
	            </form:form>
	      	</div>	
		</div>
		<div class="col-md-1"></div>
		
	</div>
</div><!-- service -->

<script>


</script>