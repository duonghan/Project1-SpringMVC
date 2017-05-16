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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/js/header.js"></script>
</head>
<body onload="slideA()">


	
	<table width=100%>
		
		<tr>
			<td colspan="2" class="header"><tiles:insertAttribute
					name="header" /></td>
		</tr>
		<tr>
		<!-- còn đây là chia tỉ lệ hiển thị -->
			<td width="20%" nowrap="nowrap" class="Cleft"><tiles:insertAttribute
					name="menu" /></td>
			<td width="80%"><tiles:insertAttribute name="body" /></td>
		</tr>
		<tr>
			<td colspan="2"><tiles:insertAttribute name="footer" /></td>
		</tr>
	</table>
</body>
</html>