package org.cegep.gg.filter;

import jakarta.annotation.Resource;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import java.io.IOException;
import java.util.List;

import javax.sql.DataSource;

import org.cegep.gg.model.Category;
import org.cegep.gg.service.ProductService;

@WebFilter("/*")
public class GetCategoriesFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;

	private ProductService productService;

    @Resource(name = "jdbc/cegep_gg_bd_tp")
    private DataSource dataSource;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	super.init(filterConfig);
        if (dataSource == null) {
            throw new ServletException("DataSource est null dans AuthController");
        }
        this.productService = new ProductService(dataSource);
    }

    //Set les categories pour toutes les pages puisque le sidebar est présent sur toute les pages ou presque.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        List<Category> categories = productService.getAllCategories();
        request.setAttribute("categories", categories);

        chain.doFilter(request, response);
    }

}
