<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Résultats de recherche</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesProducts.css">
</head>
<body>
<%@ include file="../components/header.jsp" %>
    <div class="main-container">
        <%@ include file="../components/sidebar.jsp" %>
        <div class="content">
            <h1 class="resultat-title">Résultats de recherche</h1>
            <main class="product-grid">
            	<c:if test="${not empty error}">
					<div class="errors">
						<div class="error-message">${error}</div>
					</div>
				</c:if>
                <c:forEach items="${products}" var="product">
				    <div class="product-card">
				        <img src="${pageContext.request.contextPath}/uploads/${product.imageUrl}" alt="${product.name}">
				        <h3>${product.name}</h3>
				        <p>${product.categoryDescription}</p>  <!-- Affichage de la description de la catégorie -->
				        <p class="price">${product.price} $ </p>
				        <button class="add-to-cart" data-product-id="${product.id}">Ajouter au panier</button>
				    </div>
				</c:forEach>

            </main>
        </div>
    </div>
    <%@ include file="../components/footer.jsp" %>

    <script>
    document.addEventListener('click', function(event) {
        if (event.target.classList.contains('add-to-cart')) {
            const productId = event.target.getAttribute('data-product-id');
            if (productId) {
                addToCart(productId);
            } else {
                alert('Erreur : ID du produit manquant');
            }
        }
    });

    function addToCart(productId) {
        const params = new URLSearchParams();
        params.append('action', 'add');
        params.append('productId', productId);
        params.append('quantity', '1');

        fetch(`${pageContext.request.contextPath}/cart`, {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded', 'Accept': 'application/json'},
            body: params
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('Produit ajouté au panier !');
                window.location.reload();
            } else {
                alert('Erreur : ' + data.error);
            }
        })
        .catch(error => {
            console.error('Erreur lors de l\'ajout au panier :', error);
            alert('Une erreur est survenue lors de l\'ajout au panier: ' + error.message);
        });
    }
    </script>
</body>
</html>
