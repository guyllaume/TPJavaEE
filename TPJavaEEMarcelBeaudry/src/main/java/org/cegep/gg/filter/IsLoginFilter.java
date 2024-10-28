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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

@WebFilter("/*")
public class IsLoginFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/cegep_gg_bd_tp")
    private DataSource dataSource;
       

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        Principal userPrincipal = req.getUserPrincipal();
        
        if (userPrincipal != null && session.getAttribute("userDetails") == null) {
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                     "SELECT prenom, nom FROM user WHERE courriel = ?")) {
                     
                stmt.setString(1, userPrincipal.getName());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Map<String, String> userDetails = new HashMap<>();
                        userDetails.put("prenom", rs.getString("prenom"));
                        userDetails.put("nom", rs.getString("nom"));
                        session.setAttribute("userDetails", userDetails);
                    }
                }
            } catch (SQLException e) {
            	e.printStackTrace();
            }
        }

        chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
