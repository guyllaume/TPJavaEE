<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Test Base de données</title>
</head>
<body>
    <h1>Test de connexion à la base de données</h1>
    
    <%
    try {
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:comp/env");
        DataSource ds = (DataSource) envContext.lookup("jdbc/cegep_gg_bd_tp");
        
        try (Connection conn = ds.getConnection()) {
            out.println("<p style='color:green'>Connexion réussie!</p>");
            out.println("<p>URL: " + conn.getMetaData().getURL() + "</p>");
            out.println("<p>User: " + conn.getMetaData().getUserName() + "</p>");
            
            // Test de lecture de la table products
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM products")) {
                if (rs.next()) {
                    out.println("<p>Nombre de produits: " + rs.getInt(1) + "</p>");
                }
            }
        }
        
    } catch (Exception e) {
        out.println("<p style='color:red'>Erreur: " + e.getMessage() + "</p>");
        out.println("<pre>");
        e.printStackTrace(new java.io.PrintWriter(out));
        out.println("</pre>");
    }
    %>
</body>
</html>