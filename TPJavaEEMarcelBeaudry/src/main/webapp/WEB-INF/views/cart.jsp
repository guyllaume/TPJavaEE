<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mon Panier - CardRoyalty</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesCart.css">
</head>
<body>
    <%@ include file="../components/header.jsp" %>
    <div class="main-container">
        <%@ include file="../components/sidebar.jsp" %>
        <div class="cart-container">
            <div class="cart-header">
                <h1>Mon Panier</h1>
                <c:if test="${not empty cart.items}">
                    <button class="btn btn-danger" onclick="clearCart()">Vider le panier</button>
                </c:if>
            </div>
            <c:choose>
                <c:when test="${empty cart.items}">
                    <div class="empty-cart">
                        <h2>Votre panier est vide</h2>
                        <p>Découvrez nos produits et ajoutez-les à votre panier</p>
                        <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Continuer mes achats</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <table class="cart-items">
                        <thead>
                            <tr>
                                <th>Produit</th>
                                <th>Prix unitaire</th>
                                <th>Quantité</th>
                                <th>Total</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${cart.items}" var="item">
                                <tr class="cart-item" data-product-id="${item.product.id}">
                                    <td>
                                        <img src="${pageContext.request.contextPath}/uploads/${item.product.imageUrl}" 
                                             alt="${item.product.name}"
                                             onerror="this.src='https://via.placeholder.com/100x120?text=Image+non+disponible'">
                                        <div>
                                            <h3>${item.product.name}</h3>
                                        </div>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="$"/>
                                    </td>
                                    <td>
                                        <input type="number" 
                                               class="quantity-input" 
                                               id="quantity-${item.product.id}" 
                                               value="${item.quantity}" 
                                               min="1">
                                        <button class="btn btn-primary" onclick="updateQuantity(${item.product.id}, document.getElementById('quantity-${item.product.id}').value)">Modifier</button>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="$"/>
                                    </td>
                                    <td>
                                        <button class="btn btn-danger" onclick="removeFromCart('${item.product.id}')">
                                            Supprimer
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach> <!-- Ensure this tag is properly closed -->
                        </tbody>
                    </table>
                    <div class="cart-summary">
                        <h2>Total : <fmt:formatNumber value="${cart.total}" type="currency" currencySymbol="$"/></h2>
                    </div>
                    <div class="cart-buttons">
                        <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
                            Continuer mes achats
                        </a>
                        <button class="btn btn-primary" onclick="checkout()">
                            Passer la commande
                        </button>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <%@ include file="../components/footer.jsp" %>

    <script>
	    function updateQuantity(productId, quantity) {
	        if (!quantity || quantity < 1) {
	            alert('Quantité invalide. Veuillez saisir une quantité positive.');
	            return;
	        }
	        let params = new URLSearchParams();
	        params.append('action', 'update');
	        params.append('productId', productId);
	        params.append('quantity', quantity);
	
	        fetch('${pageContext.request.contextPath}/cart', {
	            method: 'POST',
	            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
	            body: params
	        })
	        .then(response => response.json())
	        .then(data => {
	            if (data.success) {
	                window.location.reload(); // Recharger la page pour voir les modifications
	            } else {
	                alert('Erreur : ' + data.error);
	            }
	        })
	        .catch(error => {
	            console.error('Erreur lors de la mise à jour de la quantité :', error);
	            alert('Erreur lors de la mise à jour de la quantité');
	        });
	    }

        function removeFromCart(productId) {
        	console.log("Trying to remove product with ID:", productId);
            if (confirm('Voulez-vous vraiment retirer ce produit du panier ?')) {
                if (!productId) {
                    alert('Erreur : ID du produit manquant');
                    return;
                }
                let params = new URLSearchParams();
                params.append('action', 'remove');
                params.append('productId', productId);
                
                fetch('${pageContext.request.contextPath}/cart', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: params
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        const row = document.querySelector(`tr[data-product-id="${productId}"]`);
                        if (row) {
                            row.remove(); 
                        }
                        window.location.reload();
                    } else {
                        alert('Erreur : ' + data.error);
                    }
                })
                .catch(error => {
                    console.error('Erreur lors de la suppression du produit :', error);
                    alert('Erreur lors de la suppression du produit');
                });
            }
        }


        function clearCart() {
            if (confirm('Voulez-vous vraiment vider votre panier ?')) {
                fetch('${pageContext.request.contextPath}/cart', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: 'action=clear'
                })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        window.location.reload();
                    } else {
                        alert('Erreur : ' + data.error);
                    }
                });
            }
        }

        function checkout() {
        	window.location.href = "${pageContext.request.contextPath}/checkout";
        }
    </script>
</body>
</html>