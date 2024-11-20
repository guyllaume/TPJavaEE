package org.cegep.gg.controlleur;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.cegep.gg.model.Product;
import org.cegep.gg.service.ProductService;
import javax.sql.DataSource;

/**
 * Servlet de gestion des recherches de produits.
 * Ce servlet permet de rechercher des produits en fonction d'une requête donnée.
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private ProductService productService;

    // Injection de la source de données via @Resource
    @Resource(name = "jdbc/cegep_gg_bd_tp")
    private DataSource dataSource;

    /**
     * Méthode d'initialisation du servlet.
     * Initialise le service produit avec la source de données.
     * @throws ServletException Si la source de données est introuvable.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        if (dataSource == null) {
            throw new ServletException("DataSource est null dans SearchServlet");
        }
        productService = new ProductService(dataSource);
    }

    /**
     * Gère les requêtes GET pour effectuer une recherche.
     * @param request La requête HTTP contenant les paramètres.
     * @param response La réponse HTTP.
     * @throws ServletException En cas d'erreur lors du traitement.
     * @throws IOException En cas d'erreur d'entrée/sortie.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupère le paramètre de recherche "query" depuis la requête.
        String query = request.getParameter("query");

        // Vérifie si la requête est vide ou nulle.
        if (query == null || query.trim().isEmpty()) {
            request.setAttribute("error", "La requête de recherche ne peut pas être vide.");
            // Redirige vers la page des résultats avec un message d'erreur.
            request.getRequestDispatcher("/searchResults.jsp").forward(request, response);
            return;
        }

        try {
            // Recherche des produits correspondant à la requête.
            List<Product> products = productService.searchProducts(query.trim());
            
            // Ajoute la liste des produits comme attribut pour la vue.
            request.setAttribute("products", products);
            
            // Redirige vers la page des résultats de recherche.
            request.getRequestDispatcher("/WEB-INF/views/searchResults.jsp").forward(request, response);

        } catch (ServletException e) {
            // Propagation de l'erreur si une exception est levée lors de la recherche.
            throw new ServletException("Erreur lors de la récupération des produits", e);
        }
    }
}
