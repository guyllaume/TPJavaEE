<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin-Categories</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesAdminGestion.css">
</head>
<body>

    <%@ include file="../../components/header.jsp" %>
    <div class="content">
    	<div class="ajout">
    		<h1>Gestion de catégorie :</h1>
    		<form action="<c:url value='/admin/categorie/add' />">
    			<div class="form-group">
	    			<label for="nom">Nom</label>
	    			<input id="nom" name="nom" type="text" required>
    			</div>
    			<div class="form-group">
	    			<label for="description">Description</label>
	    			<input id="description" name="description" type="text" required>
    			</div>
    			<button type="submit">Enregistrer</button>
    		</form>
    	</div>
        <div class="table-container">
            <table>
            	<tr>
           			<th>Nom</th>
           			<th>Description</th>
           			<th>Action</th>
           		</tr>
<%-- 	            <c:forEach items="${categories}" var="categorie"> --%>
<!-- 	            		<tr> -->
<%-- 	            			<td>${categorie.name}</td> --%>
<%-- 	            			<td>${categorie.description}</td> --%>
<!-- 	            			<td class="categorie-options"> -->
<%-- 	            				<a href="<c:url value='/admin/categorie/edit/${categorie.id}'/>">Modifier</a> --%>
<%-- 	            				<a href="<c:url value='/admin/categorie/delete/${categorie.id}'/>">Supprimer</a> --%>
<!-- 	            			</td> -->
<!-- 	            		</tr> -->
<%-- 	            </c:forEach> --%>
				<tr>
           			<td>abc</td>
           			<td>met dans abc</td>
           			<td class="options">
           				<a href="<c:url value='/admin/categorie/edit/1'/>">Modifier</a>
           				<a href="<c:url value='/admin/categorie/delete/1'/>">Supprimer</a>
           			</td>
				</tr>
				<tr>
           			<td>abc</td>
           			<td>met dans abc</td>
           			<td class="options">
           				<a href="<c:url value='/admin/categorie/edit/1'/>">Modifier</a>
           				<a href="<c:url value='/admin/categorie/delete/1'/>">Supprimer</a>
           			</td>
				</tr>
				<tr>
           			<td>abc</td>
           			<td>met dans abc</td>
           			<td class="options">
           				<a href="<c:url value='/admin/categorie/edit/1'/>">Modifier</a>
           				<a href="<c:url value='/admin/categorie/delete/1'/>">Supprimer</a>
           			</td>
				</tr>
            </table>
        </div>
    </div>

</body>
</html>