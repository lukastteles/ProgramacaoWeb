<!DOCTYPE html>
<%@ include file="/WEB-INF/views/includeTags.jsp"%>

<table border="" class="table table-striped table-hover table-custom">
	<tr>
		<th id="sl"><spring:message code="home.user.id"/></th>
		<th id="enterprise"><spring:message code="home.user.cpf"/></th>
		<th id="action"><spring:message code="home.user.action"/></th>
	</tr>
	<c:forEach items="${lstUsersAsParameter}" var="user">
		<tr>
			<td headers="sl">${user.nome}</td>
			<td headers="enterprise">${user.cpf}</td>
			<td headers="action"><a href="#" onclick="openAjaxCall('${user.nome}');"><spring:message code="home.user.action.delete"/></a></td>
		</tr>
	</c:forEach>
</table>