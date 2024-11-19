<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mon Panier - CardRoyalty</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
		body {
		    margin: 0;
		    min-height: 100vh;
		    display: flex;
		    flex-direction: column;
		    background-color: #C0BEC4;
		}
		
		a{
		    text-decoration: none;
			color: white;
		}
		
		a:hover{
			text-decoration: underline;
			opacity: 0.8;
		}
		
		.header {
		    background-color: #6A2469;
		    color: white;
		    padding: 1.5rem 1rem;
		    display: flex;
		    justify-content: space-between;
			align-items: center;
		    gap: 2rem;
		}
		
		.header .search{
		    flex: 1;
		    display: flex;
		    justify-content: center;
		    gap: 10px;
		}
		
		.header .logo img{
			width: 100px;
			border-radius: 50%;
		}
		
		.header .logo img:hover{
			opacity: 0.8;
			cursor: pointer;
		}
		
		.header .panier{
			display: flex;
			gap: 10px;
			flex-direction: column;
			align-items: center;
		}
		.header .panier img:hover{
			opacity: 0.8;
			cursor: pointer;
		}
		
		
		.header .panier img{
			width: 40px;
		}
		
		.sidebar{
			background-color: #3A1541;
			padding: 1rem;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			gap: 1rem;
			width: 125px;
		}
		
		.sidebar ul{
			list-style-type: none;
			padding: 0;
			margin: 0;
		}
		
		.main-container {
		    flex: 1;
		    display: flex;
		    background-color: #C0BEC4;
		}
		
		.footer {
		    background-color: #010001;
		    color: white;
		    padding: 1rem;
			text-align: right;
		}
        .cart-container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .cart-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .cart-items {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        .cart-items th, .cart-items td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        .cart-items th {
            background-color: #6A2469;
            color: white;
        }

        .cart-item {
            display: grid;
            grid-template-columns: 100px 1fr auto auto auto;
            align-items: center;
            gap: 20px;
        }

        .cart-item img {
            width: 100px;
            height: 120px;
            object-fit: cover;
            border-radius: 4px;
        }

        .quantity-input {
            width: 60px;
            padding: 5px;
            text-align: center;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .cart-summary {
            text-align: right;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 4px;
            margin-top: 20px;
        }

        .cart-buttons {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }

        .btn {
            padding: 10px 20px;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            font-weight: bold;
        }

        .btn-primary {
            background-color: #6A2469;
            color: white;
        }

        .btn-secondary {
            background-color: #888;
            color: white;
        }

        .btn-danger {
            background-color: #dc3545;
            color: white;
        }

        .empty-cart {
            text-align: center;
            padding: 50px;
            color: #666;
        }
    </style>
</head>
<body>
    <%@ include file="../components/header.jsp" %>
    <div class="main-container">
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
	                alert('Quantité mise à jour avec succès');
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
                        alert('Produit retiré avec succès');
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
            // À implémenter plus tard
            alert('La fonction de paiement non disponible pour le moment');
        }
    </script>
</body>
</html>