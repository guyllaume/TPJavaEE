<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin-Dashboard</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesAdmin.css">
</head>
<body>

    <%@ include file="../../components/header.jsp" %>
   	<div class="dashboard">
   		<button onclick="location.href = '<c:url value='/admin/categories' />'">Gestion de catégorie</button>
   		<button onclick="location.href = '<c:url value='/admin/produits' />'">Gestion de produit</button>
   	</div>

</body>
</html>