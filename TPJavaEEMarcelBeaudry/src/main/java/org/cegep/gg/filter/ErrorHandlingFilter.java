package org.cegep.gg.filter;

import jakarta.annotation.Priority;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// Filtre pour gérer les exceptions non-gérer par le code
@WebFilter("/*")
@Priority(1) // Utilise ce filtre en premier pour gérer les exceptions.
public class ErrorHandlingFilter extends HttpFilter implements Filter {
       

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            // Continue with the next filter or target servlet
            chain.doFilter(request, response);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();

            // Forward the request to the error page
            httpRequest.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(httpRequest, httpResponse);
        }
    }

}
