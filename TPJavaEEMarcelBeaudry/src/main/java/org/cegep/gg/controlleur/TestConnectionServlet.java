package org.cegep.gg.controlleur;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;

@WebServlet("/testdb")
public class TestConnectionServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h1>Test de connexion à la base de données</h1>");
        
        try {
            out.println("<p>Tentative de récupération du Context initial...</p>");
            Context initContext = new InitialContext();
            out.println("<p>Context initial obtenu.</p>");
            
            out.println("<p>Tentative de récupération du Context env...</p>");
            Context envContext = (Context) initContext.lookup("java:comp/env");
            out.println("<p>Context env obtenu.</p>");
            
            out.println("<p>Tentative de récupération du DataSource...</p>");
            DataSource ds = (DataSource) envContext.lookup("jdbc/cegep_gg_bd_tp");
            out.println("<p>DataSource obtenu: " + ds + "</p>");
            
            out.println("<p>Tentative de connexion...</p>");
            try (Connection conn = ds.getConnection()) {
                out.println("<p style='color:green'>Connexion réussie!</p>");
                out.println("<p>URL: " + conn.getMetaData().getURL() + "</p>");
                out.println("<p>User: " + conn.getMetaData().getUserName() + "</p>");
                out.println("<p>Database: " + conn.getCatalog() + "</p>");
            }
            
        } catch (Exception e) {
            out.println("<p style='color:red'>Erreur: " + e.getMessage() + "</p>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }
        
        out.println("</body></html>");
    }
}