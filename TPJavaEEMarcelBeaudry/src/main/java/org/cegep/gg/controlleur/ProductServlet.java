package org.cegep.gg.controlleur;

import java.io.IOException;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.cegep.gg.model.Category;
import org.cegep.gg.model.Product;
import org.cegep.gg.service.ProductService;
import javax.sql.DataSource;


@WebServlet(name = "ProductServlet", urlPatterns = {"", "/index","/products", "/filterProducts" })
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductService productService;

    @Resource(name="jdbc/cegep_gg_bd_tp")
    private DataSource dataSource;
    
    @Override
    public void init() throws ServletException {
        System.out.println("Initialisation de ProductServlet...");
    	super.init();
    	if (dataSource == null) {
    		throw new ServletException("DataSource est null dans ProductServlet");
    	}
        productService = new ProductService(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String categoryIdStr = request.getParameter("categoryId");
            List<Product> products;
            List<Category> categories = productService.getAllCategories();

            if (categoryIdStr != null) {
                long categoryId = Long.parseLong(categoryIdStr);
                products = productService.getProductsByCategoryId(categoryId);
            } else {
                products = productService.getAllProducts();
            }

            request.setAttribute("products", products);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error handling products display", e);
        }
    }

}