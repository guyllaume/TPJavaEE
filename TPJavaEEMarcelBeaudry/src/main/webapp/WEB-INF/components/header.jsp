<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="header">
    <div class="logo">
        <img alt="Logo Carte" src="<c:url value='${request.contextPath}/images/logo.jpeg'/>">
    </div>
    <c:choose>
    	<c:when test="${pageContext.request.isUserInRole('ADMIN')}">
    		<div class="mod-Admin">Module Administrateur</div>
			<a href="<c:url value='/admin'/>">Admin</a>
		</c:when>
		<c:otherwise>
		    <div class="nav-links">
		        <a href="<c:url value='/index'/>">Accueil</a> - 
		        <a href="<c:url value='/contact'/>">contacter nous</a> - 
		        <a href="<c:url value='/fr'/>">fr</a>
		    </div>
		    <div class="search">
		        <form action="${pageContext.request.contextPath}/search" method="GET" class="search-form">
				    <input type="text" name="query" placeholder="Rechercher des produits..." class="search-input">
				    <button type="submit" class="search-button">Rechercher</button>
				</form>

		    </div>
		    <div class="panier">
                <a href="${pageContext.request.contextPath}/cart" class="cart-link">
                    <img alt="Panier" src="${pageContext.request.contextPath}/images/panier.png">
                    <span class="cart-count">${not empty sessionScope.cart ? sessionScope.cart.itemCount : 0}</span>
                </a>
                <c:choose>
                    <c:when test="${empty pageContext.request.userPrincipal}">
                        <a href="${pageContext.request.contextPath}/login">Se Connecter</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/auth/membre/profile">
                            ${sessionScope.userDetails.prenom} ${sessionScope.userDetails.nom}
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
		</c:otherwise>
    </c:choose>
</div>