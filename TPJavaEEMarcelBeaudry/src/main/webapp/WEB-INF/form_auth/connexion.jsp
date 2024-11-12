<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="org.cegep.gg.service.ProductService" %>
<%@ page import="org.cegep.gg.model.Category" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>

<% 
/*Creates a context to use JNDI to connect to the database: works similarly to context.xml  */
    InitialContext ctx = new InitialContext();
    DataSource dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/cegep_gg_bd_tp");
    ProductService productService = new ProductService(dataSource);
    List<Category> categories = productService.getAllCategories();
    request.setAttribute("categories", categories);
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CardRoyalty - Connexion</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesConnexion.css">
</head>
<body>
    <%@ include file="../components/header.jsp" %>
    <div class="main-container">
        <%@ include file="../components/sidebar.jsp" %>
		<div class="content">
			<h1>Connexion</h1>
			<form action="${pageContext.request.contextPath}/j_security_check" method="post">
			    <div class="form-group">
				<label for="j_username">Courriel</label>
				<input type="email" id="j_username" name="j_username" required>
				</div>
			    <div class="form-group">
				<label for="j_password">Mot de passe</label>
				<input type="password" id="j_password" name="j_password" required>
				</div>
				<div class="button-group">
				<button type="submit" class="submit_btn">OK</button>
				<button type="button" class="submit_btn" onclick="location.href='<c:url value='/auth/signup' />';">Créer un compte</button>
				</div>
			    <c:if test="${param.error != null}">
			        <div class="error-message">
			            Nom d'utilisateur ou mot de passe incorrect.
			        </div>
			    </c:if>
			</form>
		</div>
    </div>
	<%@ include file="../components/footer.jsp" %>
</body>
</html>