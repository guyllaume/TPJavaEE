package org.cegep.gg.controlleur;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.sql.DataSource;

import org.cegep.gg.model.Category;
import org.cegep.gg.model.Product;
import org.cegep.gg.service.ProductService;

@MultipartConfig
@WebServlet("/admin/*")
public class AdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductService productService;

    @Resource(name = "jdbc/cegep_gg_bd_tp")
    private DataSource dataSource;

	// Initialisation du service
    @Override
    public void init() throws ServletException {
        super.init();
        if (dataSource == null) {
            throw new ServletException("DataSource est null dans AdminController");
        }
        productService = new ProductService(dataSource);
    }

    // Méthode pour traiter les requêtes GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
	        String path = request.getPathInfo();
	
	        if (path == null || path.equals("/")) {
	            forwardToDashboard(request, response);
	            return;
	        }

            switch (path) {
                case "/categories":
                    displayCategories(request, response);
                    break;
                case "/produits":
                    displayProducts(request, response);
                    break;
                default: // 404
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (Exception e) {
            handleError(request ,response, e); // A revoir
        }
    }

    // Méthode pour traiter les requêtes POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Gestion des erreurs
        try {
        	String path = request.getPathInfo();
            switch (path) {
                case "/categorie/add":
                    addCategory(request, response);
                    break;
                case "/categorie/edit":
                    editCategory(request, response);
                    break;
                case "/categorie/delete":
                    deleteCategory(request, response);
                    break;
                case "/produit/add":
                    addProduct(request, response);
                    break;
                case "/produit/edit":
                    editProduct(request, response);
                    break;
                case "/produit/delete":
                    deleteProduct(request, response);
                    break;
                default: // 404
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (Exception e) {
            handleError(request ,response, e); // A revoir
        }
    }

    // Redirige vers la page du tableau de bord
    private void forwardToDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
    }

    // Affiche la liste des catégories
    private void displayCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = productService.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/views/admin/categories.jsp").forward(request, response);
    }

    // Affiche la liste des produits
    private void displayProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productService.getAllProducts();
        List<Category> categories = productService.getAllCategories();

        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/views/admin/produits.jsp").forward(request, response);
    }

    // Ajoute une nouvelle catégorie
    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("nom");
        String description = request.getParameter("description");

        productService.addCategory(name, description);
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }

    // Modifie une catégorie existante
    private void editCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("nom");
        String description = request.getParameter("description");

        productService.updateCategory(id, name, description);
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }

    // Supprime une catégorie
    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));

        productService.deleteCategory(id);
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }

    // Ajoute un nouveau produit
    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("nom");
        String categoryId = request.getParameter("categorie");
        String price = request.getParameter("prix");
        try {
			Double.parseDouble(price);
		} catch (NumberFormatException e) {
			request.setAttribute("error", "Le prix doit etre un nombre");
			displayProducts(request, response);
			return;
		}
        String imagePath = uploadImage(request);

        productService.addProduct(name, categoryId, price, imagePath);
        response.sendRedirect(request.getContextPath() + "/admin/produits");
    }

    // Modifie un produit existant
    private void editProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int productId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("nom");
        String categoryId = request.getParameter("categorie");
        String price = request.getParameter("prix");
        try {
			Double.parseDouble(price);
		} catch (NumberFormatException e) {
			request.setAttribute("error", "Le prix doit etre un nombre");
			displayProducts(request, response);
			return;
		}
        String imagePath = uploadImage(request);

        productService.updateProduct(productId, name, categoryId, price, imagePath);
        response.sendRedirect(request.getContextPath() + "/admin/produits");
    }

    // Supprime un produit
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int productId = Integer.parseInt(request.getParameter("id"));

        productService.deleteProduct(productId);
        response.sendRedirect(request.getContextPath() + "/admin/produits");
    }

    // Télécharge une image et retourne le chemin d'accès
    private String uploadImage(HttpServletRequest request) throws Exception {
        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadDir = "C:\\uploads\\TPJavaEEMarcelBeaudry";
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            File uploadFile = new File(uploadDir, fileName);

            // Enregistre le fichier téléchargé
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, uploadFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            return fileName;
        }
        return null;
    }

    // Gère les erreurs et affiche des informations utiles
    private void handleError(HttpServletRequest request ,HttpServletResponse response, Exception e) throws ServletException, IOException {
        // Log the error details for debugging purposes
        e.printStackTrace();

        // Forward the request to the generalized error page
        request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
    }
}
