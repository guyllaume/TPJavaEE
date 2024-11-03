<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin-Produit</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesAdminGestion.css">
</head>
<body>

    <%@ include file="../../components/header.jsp" %>
    <div class="content">
    	<div class="ajout">
    		<h1>Gestion de produit :</h1>
    		<form action="<c:url value='/admin/produit/add' />">
    			<div class="form-group">
	    			<label for="categorie">Categorie</label>
	    			<select id="categorie" name="categorie">
<%-- 	    				<c:forEach items="${categories}" var="categorie"> --%>
<%-- 	    					<option value="${categorie.id}">${categorie.name}</option> --%>
<%-- 	    				</c:forEach> --%>
	    			</select>
    			</div>
    			<div class="form-group">
	    			<label for="nom">Nom</label>
	    			<input id="nom" name="nom" type="text" required>
    			</div>
    			<div class="form-group">
	    			<label for="prix">Prix</label>
	    			<input id="prix" name="prix" type="text" required>
    			</div>
    			<div class="form-group">
	    			<label for="image">Image</label>
	    			<input id="image" name="image" type="file" accept="image/*" required>
    			</div>
    			<button type="submit">Enregistrer</button>
    		</form>
    	</div>
        <div class="table-container">
            <table>
            	<tr>
            		<th>Catégorie</th>
           			<th>Nom</th>
           			<th>Prix</th>
           			<th>Image</th>
           			<th>Action</th>
           		</tr>
<%-- 	            <c:forEach items="${produits}" var="produit"> --%>
<!-- 	            		<tr> -->
<%-- 	            			<td>${produit.categorie}</td> --%>
<%-- 	            			<td>${produit.name}</td> --%>
<%-- 	            			<td>${produit.prix}$</td> --%>
<%-- 	            			<td>${produit.image}</td> --%>
<!-- 	            			<td class="options"> -->
<%-- 	            				<a href="<c:url value='/admin/produit/edit/${produit.id}'/>">Modifier</a> --%>
<%-- 	            				<a href="<c:url value='/admin/produit/delete/${produit.id}'/>">Supprimer</a> --%>
<!-- 	            			</td> -->
<!-- 	            		</tr> -->
<%-- 	            </c:forEach> --%>
				<tr>
           			<td>Categ</td>
           			<td>abc</td>
           			<td>12$</td>
           			<td>imagePath</td>
           			<td class="options">
           				<a href="<c:url value='/admin/produit/edit/1'/>">Modifier</a>
           				<a href="<c:url value='/admin/produit/delete/1'/>">Supprimer</a>
           			</td>
				</tr>
				<tr>
           			<td>Categ</td>
           			<td>abc</td>
           			<td>12$</td>
           			<td>imagePath</td>
           			<td class="options">
           				<a href="<c:url value='/admin/produit/edit/1'/>">Modifier</a>
           				<a href="<c:url value='/admin/produit/delete/1'/>">Supprimer</a>
           			</td>
				</tr>
				<tr>
           			<td>Categ</td>
           			<td>abc</td>
           			<td>12$</td>
           			<td>imagePath</td>
           			<td class="options">
           				<a href="<c:url value='/admin/produit/edit/1'/>">Modifier</a>
           				<a href="<c:url value='/admin/produit/delete/1'/>">Supprimer</a>
           			</td>
				</tr>
            </table>
        </div>
    </div>

</body>
</html>