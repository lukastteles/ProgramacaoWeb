<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>Projeto CARona</title>
  <%@ include file="/WEB-INF/views/imports.jsp"%>
  <%@ include file="/WEB-INF/views/includeTags.jsp"%>
</head>
<body>
	<header>
		<div style="width: 100%">
			<tiles:insertAttribute name="header" />
		</div>
	</header>
	<section>
		<div class="row" style="width: 100%;">
			<div class="col-md-2">
				<tiles:insertAttribute name="menuLateral" />
			</div>
			<div class="col-md-10">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
	</section>
	
    <footer>
		<div style="width: 100%">
		<tiles:insertAttribute name="footer" />
		</div>
	</footer>
</body>
</html>