<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="sidebar">
    <nav>
        <ul>
            <c:forEach var="category" items="${categories}" varStatus="status">
                <li>
                    <a href="${pageContext.request.contextPath}/products?categoryId=${category.id}">${category.name}</a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</div>