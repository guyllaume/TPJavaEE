package org.cegep.gg.controlleur;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	if(request.isUserInRole("ADMIN")) {
			response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
		}else {
			response.sendRedirect(request.getContextPath() + "/products.jsp");
		}
    }

}
