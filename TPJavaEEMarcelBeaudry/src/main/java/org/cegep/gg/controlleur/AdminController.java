package org.cegep.gg.controlleur;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.cegep.gg.model.Product;
import org.cegep.gg.model.User;
import org.cegep.gg.service.ProductService;


@WebServlet("/admin/*")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService;

    @Resource(name="jdbc/cegep_gg_bd_tp")
    private DataSource dataSource;

    public AdminController() {
        super();
    }
    
	@Override
	public void init() throws ServletException {
    	super.init();
        try {
            productService = new ProductService(dataSource);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation du ProductServlet:");
            e.printStackTrace();
            throw new ServletException(e);
        }
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		
		if(path == null || path.equals("/")) {
			request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
			return;
		}
		
		switch(path) {
		case "/categories":
	        try {
	            // Vérification du DataSource
	            if (productService == null) {
	                throw new ServletException("ProductService n'est pas initialisé");
	            }

	            // Récupération des catégories
	            System.out.println("Récupération des catégories...");
	            List<String> categories = productService.getAllCategories(); //TODO : utiliser le service avec la nouvelle BD
	            System.out.println("Nombre de catégories récupérées : " + categories.size());

	            // Définition des attributs
	            request.setAttribute("categories", categories);

	            // Forward vers la JSP
	            request.getRequestDispatcher("/WEB-INF/views/admin/categories.jsp").forward(request, response);
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
			break;
		case "/produits":
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
				request.getRequestDispatcher("/WEB-INF/views/admin/produits.jsp").forward(request, response);
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
			
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
