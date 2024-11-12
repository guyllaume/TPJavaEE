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

    @Resource(name="jdbc/cegep_gg_bd_tp")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            productService = new ProductService(dataSource);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'initialisation du ProductService", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            forwardToDashboard(request, response);
            return;
        }

        try {
            switch (path) {
                case "/categories":
                    displayCategories(request, response);
                    break;
                case "/produits":
                    displayProducts(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (Exception e) {
            handleError(response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        try {
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
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (Exception e) {
            handleError(response, e);
        }
    }

    private void forwardToDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
    }

    private void displayCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Can be removed since filter does it
    	List<Category> categories = productService.getAllCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/views/admin/categories.jsp").forward(request, response);
    }

    private void displayProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productService.getAllProducts();
        //Can be removed since filter does it
        List<Category> categories = productService.getAllCategories();

        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/views/admin/produits.jsp").forward(request, response);
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("nom");
        String description = request.getParameter("description");

        productService.addCategory(name, description);
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }

    private void editCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("nom");
        String description = request.getParameter("description");

        productService.updateCategory(id, name, description);
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        productService.deleteCategory(id);
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }


	 // Product CRUD Methods
	 private void addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
	     String name = request.getParameter("nom");
	     String categoryId = request.getParameter("categorie");
	     String price = request.getParameter("prix");
	     String imagePath = uploadImage(request);
	
	     productService.addProduct(name, categoryId, price, imagePath);
	     response.sendRedirect(request.getContextPath() + "/admin/produits");
	 }
	
	 private void editProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
	     int productId = Integer.parseInt(request.getParameter("id"));
	     String name = request.getParameter("nom");
	     String categoryId = request.getParameter("categorie");
	     String price = request.getParameter("prix");
	     String imagePath = uploadImage(request); // Update image if a new file is uploaded
	
	     productService.updateProduct(productId, name, categoryId, price, imagePath);
	     response.sendRedirect(request.getContextPath() + "/admin/produits");
	 }
	
	 private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
	     int productId = Integer.parseInt(request.getParameter("id"));
	
	     productService.deleteProduct(productId);
	     response.sendRedirect(request.getContextPath() + "/admin/produits");
	 }
	
	 // Utility method to handle file upload (e.g., for product images)
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
	         System.out.println("uploadFile: " + uploadFile);
	         System.out.println("uploadFile.exists(): " + uploadFile.exists());
	         System.out.println("uploadDir: " + uploadDir);
	         
	         // Save the uploaded file
	         try (InputStream input = filePart.getInputStream()) {
	             Files.copy(input, uploadFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	         }
	         return fileName;
	     }
	     return null; // or return existing image path if no new image is uploaded
	 }

    private void handleError(HttpServletResponse response, Exception e) throws IOException {
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