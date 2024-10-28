package org.cegep.gg.controlleur;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cegep.gg.model.Product;
import org.cegep.gg.service.ProductService;
import javax.sql.DataSource;

@WebServlet(urlPatterns = {"", "/index"})
public class ProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        System.out.println("Initialisation de ProductServlet...");
        try {
            DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
            if (dataSource == null) {
                throw new ServletException("DataSource est null dans ProductServlet");
            }
            productService = new ProductService(dataSource);
            // Test de connexion au démarrage
            List<Product> testProducts = productService.getAllProducts();
            System.out.println("Test initial : " + testProducts.size() + " produits trouvés");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation du ProductServlet:");
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Début de doGet dans ProductServlet");
        try {
            // Vérification du DataSource
            if (productService == null) {
                throw new ServletException("ProductService n'est pas initialisé");
            }

            // Récupération des produits
            System.out.println("Récupération des produits...");
            List<Product> products = productService.getAllProducts();
            System.out.println("Nombre de produits récupérés : " + products.size());

            // Récupération des catégories
            System.out.println("Récupération des catégories...");
            List<String> categories = productService.getAllCategories();
            System.out.println("Nombre de catégories récupérées : " + categories.size());

            // Définition des attributs
            request.setAttribute("products", products);
            request.setAttribute("categories", categories);

            // Forward vers la JSP
            System.out.println("Forward vers products.jsp...");
            String jspPath = "/WEB-INF/views/products.jsp";
            System.out.println("Chemin JSP : " + jspPath);
            request.getRequestDispatcher(jspPath).forward(request, response);
            System.out.println("Forward terminé");

        } catch (Exception e) {
            System.err.println("Erreur dans doGet:");
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Erreur serveur</h1>");
            response.getWriter().println("<p>Type d'erreur : " + e.getClass().getName() + "</p>");
            response.getWriter().println("<p>Message : " + e.getMessage() + "</p>");
            response.getWriter().println("<h2>Stack Trace:</h2><pre>");
            e.printStackTrace(response.getWriter());
            response.getWriter().println("</pre></body></html>");
        }
    }
}