<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><tiles:getAsString name="title" /></title>

<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/logo15.png">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
</head>
<body onload="slideA()">

	<div class="header">
	<tiles:insertAttribute name="header"/>
	</div>
	
	<div class="content">
		<div class="Cleft">
		<tiles:insertAttribute name="menu" />
		</div>
		
		<div class="Cright">
		<tiles:insertAttribute name="body"/>
		</div>
	</div>
	
	<div class="footer">
	<tiles:insertAttribute name="footer"  />
	</div>
</body>
</html>