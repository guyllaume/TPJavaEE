<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CardRoyalty - Erreur</title>
    <style>
        .error-container {
            text-align: center;
            margin-top: 50px;
        }
        .error-message {
            color: red;
            font-size: 1.2em;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>Une erreur s'est produite</h1>
        <p class="error-message">${errorMessage}</p>
        <a href="/">Retour à l'accueil</a>
    </div>
</body>
</html>