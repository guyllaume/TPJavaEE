<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CardRoyalty - Connexion</title>
<link >
</head>
<body>
    <%@ include file="../components/header.jsp" %>
    <div class="main-container">
        <%@ include file="../components/sidebar.jsp" %>
		<div class="content">
			<h1>Connexion</h1>
			<form action="${pageContext.request.contextPath}/j_security_check" method="post">
				<label for="j_username">Courriel</label>
				<input type="email" id="j_username" name="j_username" required>
				<br>
				<label for="j_password">Mot de passe</label>
				<input type="password" id="j_password" name="j_password" required>
				<br>
				<input type="submit" value="OK">
				<input type="button" value="Créer un compte" onclick="location.href='<c:url value='/auth/signup' />';">
			</form>
		    <c:if test="${param.error != null}">
		        <div class="error-message">
		            Nom d'utilisateur ou mot de passe incorrect.
		        </div>
		    </c:if>
		</div>
    </div>
	<%@ include file="../components/footer.jsp" %>
</body>
</html>