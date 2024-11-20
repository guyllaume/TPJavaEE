<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member-Achat</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesAchat.css">
</head>
<body>

    <%@ include file="../../components/header.jsp" %>
    <div class="main-container">
        <%@ include file="../../components/sidebar.jsp" %>
        <div class="content">
		<h1>Votre commande:</h1>
		<form action="<c:url value='/checkout' />" method="post">
		<div class="grid-content">
			<div class="personalInfo">
			    <div class="form-group">
					<label for="date_livraison">Date de livraison</label>
					<input type="date" id="date_livraison" name="date_livraison" value="" required>
				</div>
			    <div class="form-group">
					<label for="prenom">Prénom</label>
					<input type="text" id="prenom" name="prenom" value="${not empty param.prenom ? param.prenom : user.prenom}" required readonly>
				</div>
			    <div class="form-group">
				<label for="nom">Nom</label>
				<input type="text" id="nom" name="nom" value="${not empty param.nom ? param.nom : user.nom}" required readonly>
				</div>
			    <div class="form-group">
				<label for="telephone">Telephone</label>
				<input type="tel" id="telephone" name="telephone" value="${not empty param.telephone ? param.telephone : user.telephone}" required readonly>
				</div>
			    <div class="form-group">
				<label for="courriel">Courriel</label>
				<input type="email" id="courriel" name="courriel" value="${not empty param.courriel ? param.courriel : user.email}" required readonly>
				</div>
			    <div class="form-group">
				<label for="adresse_client">Adresse</label>
				<input type="text" id="adresse_client" name="adresse_client" value="${not empty param.adresse_client ? param.adresse_client : user.adresse_client}" required readonly>
				</div>
			    <div class="form-group">
				<label for="ville_client">Ville</label>
				<input type="text" id="ville_client" name="ville_client" value="${not empty param.ville_client ? param.ville_client : user.ville_client}" required readonly>
				</div>
			    <div class="form-group">
				<label for="province_client">Province</label>
				<input type="text" id="province_client" name="province_client" value="${not empty param.province_client ? param.province_client : user.province_client}" required readonly>
				</div>
			    <div class="form-group">
				<label for="pays_client">Pays</label>
				<input type="text" id="pays_client" name="pays_client" value="${not empty param.pays_client ? param.pays_client : user.pays_client}" required readonly>
				</div>
			    <div class="form-group">
				<label for="code_postal_client">Code postal</label>
				<input type="text" id="code_postal_client" name="code_postal_client" value="${not empty param.code_postal_client ? param.code_postal_client : user.code_postal_client}" required readonly>
				</div>
			</div>
			<div class="shippingInfo">
			    <div class="form-group">
				<label for="adresse_shipping">Adresse de livraison</label>
				<input type="text" id="adresse_shipping" name="adresse_shipping" value="${not empty param.adresse_shipping ? param.adresse_shipping : user.adresse_livraison}" required readonly>
				</div>
			    <div class="form-group">
				<label for="ville_shipping">Ville</label>
				<input type="text" id="ville_shipping" name="ville_shipping" value="${not empty param.ville_shipping ? param.ville_shipping : user.ville_livraison}" required readonly>
				</div>
			    <div class="form-group">
				<label for="province_shipping">Province</label>
				<input type="text" id="province_shipping" name="province_shipping" value="${not empty param.province_shipping ? param.province_shipping : user.province_livraison}" required readonly>
				</div>
			    <div class="form-group">
				<label for="pays_shipping">Pays</label>
				<input type="text" id="pays_shipping" name="pays_shipping" value="${not empty param.pays_shipping ? param.pays_shipping : user.pays_livraison}" required readonly>
				</div>
			    <div class="form-group">
				<label for="code_postal_shipping">Code postal</label>
				<input type="text" id="code_postal_shipping" name="code_postal_shipping" value="${not empty param.code_postal_shipping ? param.code_postal_shipping : user.code_postal_livraison}" required readonly>
				</div>
			</div>
			<div class="creditInfo">
			    <div class="form-group">
				<label for="credit_card">Carte de crédit</label>
				<input type="text" id="credit_card" name="credit_card" value="" required>
				</div>
			    <div class="form-group">
				<label for="date_exp">Date exp</label>
				<input type="text" id="date_exp" name="date_exp" value="" required>
				</div>
			    <div class="form-group">
				<label for="cc">CC</label>
				<input type="text" id="cc" name="cc" value="" required>
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
			<button type="submit" class="submit_btn">Envoyer ma commande</button>
		</form>
		</div>
		
    </div>
	<%@ include file="../../components/footer.jsp" %>
	

<script>
// Function to calculate a date at least 3 days in the future
function setMinDate() {
    const input = document.getElementById("date_livraison");
    const today = new Date();
    today.setDate(today.getDate() + 3); // Add 3 days to the current date

    // Format the date to YYYY-MM-DD
    const minDate = today.toISOString().split("T")[0];

    // Set the min attribute
    input.min = minDate;
}

// Set the minimum date when the page loads
window.onload = setMinDate;
</script>
</body>
</html>