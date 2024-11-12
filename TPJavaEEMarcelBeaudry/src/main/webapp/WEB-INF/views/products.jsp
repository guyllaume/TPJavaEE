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