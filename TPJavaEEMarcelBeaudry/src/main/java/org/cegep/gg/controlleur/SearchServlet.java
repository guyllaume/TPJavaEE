package org.cegep.gg.controlleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.cegep.gg.model.Product;
import org.cegep.gg.service.ProductService;
import javax.sql.DataSource;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
        if (dataSource == null) {
            throw new ServletException("DataSource est null dans SearchServlet");
        }
        productService = new ProductService(dataSource);
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        if (query == null || query.trim().isEmpty()) {
            request.setAttribute("error", "La requête de recherche ne peut pas être vide.");
            request.getRequestDispatcher("/searchResults.jsp").forward(request, response);
            return;
        }
        try {
            List<Product> products = productService.searchProducts(query.trim());
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/views/searchResults.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des produits", e);
        }
    }
}
