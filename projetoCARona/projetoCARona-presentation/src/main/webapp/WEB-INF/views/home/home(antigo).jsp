<!DOCTYPE html>
<%@ include file="/WEB-INF/views/includeTags.jsp"%>
<html>
<body>

	<form:form modelAttribute="usuarioDomain" method="post">
		<form:errors path="*" cssClass="errorblock" element="div" />

		<table class="table table-striped table-hover table-custom">
			<tr>
				<th id="property"></th>
				<th id="value"></th>
			</tr>
			<tr>
				<td headers="property"><spring:message code="home.user.id"/></td>
				<td headers="value">
					<td><form:input path="nome" placeholder="Nome"/></td>
					<td><form:errors path="nome" cssClass="error" /></td>
				<td headers="property"><spring:message code="home.user.cpf"/></td>
				<td headers="value">
					<td><form:input path="cpf" placeholder="CPF"/></td>
					<td><form:errors path="cpf" cssClass="error" /></td>
					
				<td><input type="submit" value="<spring:message code="home.user.add.button"/>" onclick="teste();"/></td>
				
			</tr>
		</table>
	</form:form>
	
	<div id ="contentUsers">
		<c:set var="lstUsersAsParameter" value="${usuarioDomain.lstUsers}" scope="request" />
		<jsp:include page="conteudoUsuario.jsp" />
	</div>
	
<!-- 	<table border="" class="table table-striped table-hover table-custom"> -->
<!-- 		<tr> -->
<%-- 			<th id="sl"><spring:message code="home.user.id"/></th> --%>
<%-- 			<th id="enterprise"><spring:message code="home.user.cpf"/></th> --%>
<%-- 			<th id="action"><spring:message code="home.user.action"/></th> --%>
<!-- 		</tr> -->
<%-- 		<c:forEach items="${usuarioDomain.lstUsers}" var="user"> --%>
<!-- 			<tr> -->
<%-- 				<td headers="sl">${user.nome}</td> --%>
<%-- 				<td headers="enterprise">${user.cpf}</td> --%>
<%-- 				<td headers="action"><a href="#" onclick="openAjaxCallWOTimeout('${user.nome}');"><spring:message code="home.user.action.delete"/></a></td> --%>
	
<!-- 			</tr> -->
<%-- 		</c:forEach> --%>
<!-- 	</table> -->

	<!-- Start JS Custom Page -->
<script>

function teste(){
	alert("Funcao Funcionando");
	return true;
}

</script>
<script src="../js/views/home/home.js"
		type="text/javascript" charset="utf-8"></script>
	<!-- End JS Custom -->
</body>
</html>