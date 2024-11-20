<%@ page isErrorPage="true" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CardRoyalty - Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesError.css">
</head>
<body>

<%@ include file="../components/header.jsp" %>
    <div class="main-container">
        <div class="error-container">
            <h1>Une erreur s'est produite</h1>
			<p class="error-details">
			    <c:set var="statusCode" value="${requestScope['jakarta.servlet.error.status_code']}" />
			    <c:set var="errorMessage" value="${requestScope['jakarta.servlet.error.message']}" />
			    <c:set var="requestUri" value="${requestScope['jakarta.servlet.error.request_uri']}" />
			
			    <p>Status Code: <strong><c:out value="${statusCode != null ? statusCode : 'Unknown'}" /></strong></p>
			    <p>Message: <strong><c:out value="${errorMessage != null ? errorMessage : 'No message available'}" /></strong></p>
			    <p>Requested URI: <strong><c:out value="${requestUri != null ? requestUri : 'Unknown'}" /></strong></p>
			
			    <c:if test="${not empty exception}">
			        <h2>Exception Details:</h2>
			        <pre><c:out value="${exception.message}" /></pre>
			    </c:if>
			</p>
            <a href="${pageContext.request.contextPath}">Retour à l'accueil</a>
        </div>
    </div>
<%@ include file="../components/footer.jsp" %>
</body>
</html>
