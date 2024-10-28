<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CardRoyalty - Products</title>
    <style>
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 1rem;
            background-color: #f8f9fa;
        }
        
        .container {
            display: flex;
            margin: 2rem;
        }
        
        .categories {
            width: 200px;
            margin-right: 2rem;
        }
        
        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 2rem;
            flex: 1;
        }
        
        .product-card {
            border: 1px solid #ddd;
            padding: 1rem;
            border-radius: 8px;
            text-align: center;
        }
        
        .product-card img {
            max-width: 100%;
            height: auto;
        }
        
        .price {
            color: #007bff;
            font-weight: bold;
            font-size: 1.2rem;
        }
        
        .add-to-cart {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 0.5rem 1rem;
            border-radius: 4px;
            cursor: pointer;
        }
        
        .add-to-cart:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>CardRoyalty</h1>
        <div class="search">
            <input type="text" placeholder="Rechercher une carte...">
        </div>
        <div class="user-menu">
            <c:if test="${empty sessionScope.user}">
                <a href="login">Connexion</a>
            </c:if>
            <c:if test="${not empty sessionScope.user}">
                <span>Bonjour, ${sessionScope.user.prenom}</span>
                <a href="panier">Panier</a>
                <a href="logout">Déconnexion</a>
            </c:if>
        </div>
    </div>

    <div class="container">
        <aside class="categories">
            <h2>Catégories</h2>
            <ul>
                <c:forEach items="${categories}" var="category">
                    <li><a href="?category=${category}">${category}</a></li>
                </c:forEach>
            </ul>
        </aside>

        <main class="product-grid">
            <c:forEach items="${products}" var="product">
                <div class="product-card">
                    <img src="${product.imageUrl}" alt="${product.name}" 
                         onerror="this.src='https://via.placeholder.com/200x200?text=Image+non+disponible'">
                    <h3>${product.name}</h3>
                    <p>${product.description}</p>
                    <p class="price">${product.price} $ </p>
                    <button class="add-to-cart" data-product-id="${product.id}">Ajouter au panier</button>
                </div>
            </c:forEach>
        </main>
    </div>

    <script>
        document.querySelectorAll('.add-to-cart').forEach(button => {
            button.addEventListener('click', function() {
                const productId = this.getAttribute('data-product-id');
                // TODO: Implémenter l'ajout au panier
                alert('Produit ajouté au panier !');
            });
        });
    </script>
</body>
</html>