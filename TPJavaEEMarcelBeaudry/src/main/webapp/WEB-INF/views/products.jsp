<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CardRoyalty - Products</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesProducts.css">
</head>
<body>
<%@ include file="../components/header.jsp" %>
    <div class="main-container">
        <%@ include file="../components/sidebar.jsp" %>
        <div class="content">
            <c:if test="${validationMessage != null || validationMessage != ''}">
                <p class="validation-message">${validationMessage}</p>
                <% session.removeAttribute("validationMessage"); %>
            </c:if>
            <main class="product-grid">
                <c:forEach items="${products}" var="product">
                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/uploads/${product.imageUrl}" alt="${product.name}" 
                             onerror="this.src='https://via.placeholder.com/200x200?text=Image+non+disponible'">
                        <h3>${product.name}</h3>
                        <p>
                        <c:forEach items="${categories}" var="category">
                            <c:if test="${category.id == product.category_id}">
                                ${category.description}
                            </c:if>
                        </c:forEach>
                        </p>
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
            console.log('ProductID:', productId); // Debug
            if (productId) {
                addToCart(productId);
            } else {
                console.error('Error: Product ID is missing');
                alert('Erreur : ID du produit manquant');
            }
        }
    });

    function addToCart(productId) {
        console.log('Sending request with productId:', productId); // Debug

        const params = new URLSearchParams();
        params.append('action', 'add');
        params.append('productId', productId);
        params.append('quantity', '1');

        fetch(`${pageContext.request.contextPath}/cart`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Accept': 'application/json'
            },
            body: params
        })
        .then(response => {
            console.log('Response status:', response.status); // Debug
            if (!response.ok) {
                throw new Error('Erreur de serveur: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log('Response data:', data); // Debug
            if (data.success) {
                // Trouver tous les boutons avec le même product-id et les mettre à jour
                document.querySelectorAll(`button[data-product-id="${productId}"]`).forEach(button => {
                    button.textContent = "Ajouté !";
                    button.style.backgroundColor = "#4CAF50";
                });

                setTimeout(() => {
                    document.querySelectorAll(`button[data-product-id="${productId}"]`).forEach(button => {
                        button.textContent = "Ajouter au panier";
                        button.style.backgroundColor = "";
                    });
                }, 2000);

                // Mettre à jour l'icône du panier ici si vous avez un élément pour cela
                updateCartCounter(data.cartSize);
                window.location.reload();
            } else {
                alert('Erreur : ' + data.error);
            }
        })
        .catch(error => {
            console.error('Fetch error:', error); // Debug
            alert('Une erreur est survenue lors de l\'ajout au panier: ' + error.message);
        });
    }

    function updateCartCounter(size) {
        const cartCounter = document.getElementById('cart-counter'); 
        if (cartCounter) {
            cartCounter.textContent = size;
        }
    }


    </script>
</body>
</html>
