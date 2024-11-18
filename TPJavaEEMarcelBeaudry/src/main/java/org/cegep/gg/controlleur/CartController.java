package org.cegep.gg.controlleur;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.cegep.gg.model.Cart;
import org.cegep.gg.service.CartService;
import org.json.JSONObject;
import javax.sql.DataSource;

@WebServlet("/cart/*")
public class CartController extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        System.out.println("Initialisation de CartController...");
        DataSource dataSource = (DataSource) getServletContext().getAttribute("dataSource");
        if (dataSource == null) {
            throw new ServletException("DataSource est null dans CartController");
        }
        cartService = new CartService(dataSource);
        System.out.println("CartService initialisé avec succès");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("CartController: Traitement GET");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        
        // Créer un nouveau panier si n'existe pas
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
            System.out.println("Nouveau panier créé");
        }
        
        // Forward vers la page panier
        String jspPath = "/WEB-INF/views/cart.jsp";
        System.out.println("Forward vers: " + jspPath);
        request.getRequestDispatcher(jspPath).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("CartController: Traitement POST");
        System.out.println("Received POST with params: " + request.getQueryString());
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
            System.out.println("Nouveau panier créé dans POST");
        }

        String action = request.getParameter("action");
        System.out.println("Action demandée: " + action);
        JSONObject jsonResponse = new JSONObject();

        try {
            switch (action) {
                case "add":
                    String productIdStr = request.getParameter("productId");
                    String quantityStr = request.getParameter("quantity");
                    
                    if (productIdStr == null || productIdStr.trim().isEmpty() ||
                        quantityStr == null || quantityStr.trim().isEmpty()) {
                        throw new IllegalArgumentException("L'ID du produit et la quantité ne peuvent pas être vides.");
                    }

                    long productId = Long.parseLong(productIdStr);
                    int quantity = Integer.parseInt(quantityStr);
                    cartService.addToCart(cart, productId, quantity);
                    
                    jsonResponse.put("success", true);
                    jsonResponse.put("message", "Produit ajouté au panier");
                    jsonResponse.put("cartSize", cartService.getTotalItems(cart));
                    System.out.println("Produit " + productId + " ajouté au panier");
                    break;

                // Répétez des contrôles similaires pour les cas "update" et "remove" si nécessaire

                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    jsonResponse.put("error", "Action inconnue: " + action);
                    System.err.println("Action inconnue demandée: " + action);
                    break;
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse.put("error", "Format de numéro invalide: " + e.getMessage());
            System.err.println("Erreur de format de numéro: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.put("error", e.getMessage());
            System.err.println("Erreur dans CartController: " + e.getMessage());
            e.printStackTrace();
        }

        // Envoyer la réponse JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

}