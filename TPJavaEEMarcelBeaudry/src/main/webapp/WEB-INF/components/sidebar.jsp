<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="sidebar">
    <nav>
        <ul>
            <c:forEach var="category" items="${categories}" varStatus="status">
                <li>
                    <a href="?category=${category}">${category}</a>
                </li>
            </c:forEach>
            <li><a href="<c:url value='/'/>">TestCategory1</a></li>
            <li><a href="<c:url value='/'/>">TestCategory2</a></li>
            <li><a href="<c:url value='/'/>">TestCategory3</a></li>
        </ul>
    </nav>
</div>