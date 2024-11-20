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

	// This is a protected resource that can only be accessed by logged in users (Works because of FORMBASEAUTH)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	if(request.isUserInRole("ADMIN")) {
    		request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath() + "/index");
		}
    }

}
