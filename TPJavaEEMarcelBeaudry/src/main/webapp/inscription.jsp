<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CardRoyalty - Inscription</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesInscription.css">
</head>
<body>
    <%@ include file="WEB-INF/components/header.jsp" %>
    <div class="main-container">
        <%@ include file="WEB-INF/components/sidebar.jsp" %>
		<div class="content">
		<h1>Création - compte client</h1>
		<form action="<c:url value='/auth/signup' />" method="post">
		<div class="grid-content">
			<div class="personalInfo">
			    <div class="form-group">
					<label for="prenom">Prénom</label>
					<input type="text" id="prenom" name="prenom" value="${param.prenom}" required>
				</div>
			    <div class="form-group">
				<label for="nom">Nom</label>
				<input type="text" id="nom" name="nom" value="${param.nom}" required>
				</div>
			    <div class="form-group">
				<label for="date_de_naissance">Date de naissance</label>
				<input type="date" id="date_de_naissance" name="date_de_naissance" value="${param.date_de_naissance}" required>
				</div>
			    <div class="form-group">
				<label for="telephone">Telephone</label>
				<input type="tel" id="telephone" name="telephone" value="${param.telephone}" required>
				</div>
			    <div class="form-group">
				<label for="courriel">Courriel</label>
				<input type="email" id="courriel" name="courriel" value="${param.courriel}" required>
				</div>
			    <div class="form-group">
				<label for="confirmationCourriel">Confirmation courriel</label>
				<input type="email" id="confirmationCourriel" name="confirmationCourriel" value="${param.confirmationCourriel}" required>
				</div>
			    <div class="form-group">
				<label for="mot_de_passe">Mot de passe</label>
				<input type="password" id="mot_de_passe" name="mot_de_passe" required>
				</div>
			</div>
			<div class="adresseInfo">
			    <div class="form-group">
				<label for="adresse_client">Adresse</label>
				<input type="text" id="adresse_client" name="adresse_client" value="${param.adresse_client}" required>
				</div>
			    <div class="form-group">
				<label for="ville_client">Ville</label>
				<input type="text" id="ville_client" name="ville_client" value="${param.ville_client}" required>
				</div>
			    <div class="form-group">
				<label for="province_client">Province</label>
				<input type="text" id="province_client" name="province_client" value="${param.province_client}" required>
				</div>
			    <div class="form-group">
				<label for="pays_client">Pays</label>
				<input type="text" id="pays_client" name="pays_client" value="${param.pays_client}" required>
				</div>
			    <div class="form-group">
				<label for="code_postal_client">Code postal</label>
				<input type="text" id="code_postal_client" name="code_postal_client" value="${param.code_postal_client}" required>
				</div>
			</div>
			<div class="shippingInfo">
			    <div class="form-group">
				<label for="adresse_shipping">Adresse</label>
				<input type="text" id="adresse_shipping" name="adresse_shipping" value="${param.adresse_shipping}" required>
				</div>
			    <div class="form-group">
				<label for="ville_shipping">Ville</label>
				<input type="text" id="ville_shipping" name="ville_shipping" value="${param.ville_shipping}" required>
				</div>
			    <div class="form-group">
				<label for="province_shipping">Province</label>
				<input type="text" id="province_shipping" name="province_shipping" value="${param.province_shipping}" required>
				</div>
			    <div class="form-group">
				<label for="pays_shipping">Pays</label>
				<input type="text" id="pays_shipping" name="pays_shipping" value="${param.pays_shipping}" required>
				</div>
			    <div class="form-group">
				<label for="code_postal_shipping">Code postal</label>
				<input type="text" id="code_postal_shipping" name="code_postal_shipping" value="${param.code_postal_shipping}" required>
				</div>
			</div>
		</div>
		<c:if test="${not empty errors}">
		    <div class="errors">
		        <c:forEach items="${errors}" var="error">
		            <div class="error-message">- ${error.value}</div>
		        </c:forEach>
		    </div>
		</c:if>
			<button type="submit" class="submit_btn">Enregistrer</button>
		</form>
		</div>
    </div>
	<%@ include file="WEB-INF/components/footer.jsp" %>
</body>
</html>