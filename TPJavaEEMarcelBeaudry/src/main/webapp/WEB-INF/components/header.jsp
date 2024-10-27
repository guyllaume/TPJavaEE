<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header">
    <div class="logo">
        <a href="<c:url value='/'/>">LOGO</a>
    </div>
    <div class="nav-links">
        <a href="<c:url value='/accueil'/>">Accueil</a> - 
        <a href="<c:url value='/contact'/>">contacter nous</a> - 
        <a href="<c:url value='/fr'/>">fr</a>
    </div>
    <div class="search">
        Rechercher <input type="text">
    </div>
    <div class="panier">
        <a href="<c:url value='/panier'/>">Panier</a>
        <c:choose>
            <c:when test="${empty pageContext.request.userPrincipal}">
                <a href="<c:url value='/login'/>">se connecter</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='/profile'/>">Bonjour, ${sessionScope.user.name}</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>