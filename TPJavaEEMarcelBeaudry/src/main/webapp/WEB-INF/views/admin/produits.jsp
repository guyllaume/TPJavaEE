<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin-Produit</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesAdminGestion.css">
<script>
function editProduct(id, categorie, name, prix) {
    document.getElementById("categorie").value = categorie;
    document.getElementById("nom").value = name;
    document.getElementById("prix").value = prix;
    document.getElementById("image").value = "";
	document.getElementById("submit").innerHTML = "Modifier";
    document.getElementById("form").action = "/TPJavaEEMarcelBeaudry/admin/produit/edit?id=" + id;
}
</script>
</head>
<body>

    <%@ include file="../../components/header.jsp" %>
    <div class="content">
    	<div class="ajout">
    		<h1>Gestion de produit :</h1>
    		<form id="form" action="<c:url value='/admin/produit/add' />" method="post" enctype="multipart/form-data">
    			<div class="form-group">
	    			<label for="categorie">Categorie</label>
	    			<select id="categorie" name="categorie">
	    				<c:forEach items="${categories}" var="categorie">
	    					<option value="${categorie.id}">${categorie.name}</option>
	    				</c:forEach>
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
    			<button id="submit" type="submit">Enregistrer</button>
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
	            <c:forEach items="${products}" var="produit">
	            		<tr>
	            			<td>
				            <c:forEach items="${categories}" var="category">
				                <c:if test="${category.id == produit.category_id}">
				                    ${category.name}
				                </c:if>
				            </c:forEach>
				            </td>
	            			<td>${produit.name}</td>
	            			<td>${produit.price}$</td>
	            			<td>${produit.imageUrl}</td>
	            			<td class="categorie-options">
							    <!-- Modifier Button -->
							    <button class="table-button" type="button" onclick="editProduct('${produit.id}', '${produit.category_id}', '${produit.name}', '${produit.price}')">
							        Modifier
							    </button>
							
							    <!-- Supprimer Button within a Form for POST Request -->
							    <form action="<c:url value='/admin/produit/delete?id=${produit.id}' />" method="POST" style="display:inline;">
							        <button class="table-button" type="submit" onclick="return confirm('Are you sure you want to delete this product?');">
							            Supprimer
							        </button>
							    </form>
							</td>
	            		</tr>
	            </c:forEach>
            </table>
        </div>
    </div>

</body>
</html>