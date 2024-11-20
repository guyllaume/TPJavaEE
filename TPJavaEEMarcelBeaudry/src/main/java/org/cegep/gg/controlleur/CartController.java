package org.cegep.gg.controlleur;

import java.io.IOException;
import jakarta.annotation.Resource;
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
	private static final long serialVersionUID = 1L;

	private CartService cartService;

    @Resource(name = "jdbc/cegep_gg_bd_tp")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        System.out.println("Initialisation de CartController...");
        if (dataSource == null) {
            throw new ServletException("DataSource est null dans CartController");
        }
        cartService = new CartService(dataSource);
        System.out.println("CartService initialisé avec succès");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleViewCart(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Action non spécifiée.");
            return;
        }

        try {
            switch (action) {
                case "add":
                    handleAddToCart(request, response);
                    break;
                case "remove":
                    handleRemoveFromCart(request, response);
                    break;
                case "clear":
                    handleClearCart(request, response);
                    break;
                case "update":
                    handleUpdateQuantity(request, response);
                    break;
                default:
                    sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Action inconnue: " + action);
            }
        } catch (Exception e) {
            handleException(response, e);
        }
    }

 // Gère l'affichage du panier
    private void handleViewCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        getOrCreateCart(session);

        // Redirige vers la page du panier
        String jspPath = "/WEB-INF/views/cart.jsp";
        System.out.println("Forward vers: " + jspPath);
        request.getRequestDispatcher(jspPath).forward(request, response);
    }

    // Gère l'ajout d'un produit au panier
    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Cart cart = getOrCreateCart(session);

        String productIdStr = request.getParameter("productId");
        String quantityStr = request.getParameter("quantity");

        // Vérifie que les paramètres nécessaires sont fournis
        if (productIdStr == null || quantityStr == null) {
            throw new IllegalArgumentException("L'ID du produit et la quantité sont requis.");
        }

        long productId = Long.parseLong(productIdStr);
        int quantity = Integer.parseInt(quantityStr);

        // Ajoute le produit au panier
        cartService.addToCart(cart, productId, quantity);

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", true);
        jsonResponse.put("message", "Produit ajouté au panier");
        jsonResponse.put("cartSize", cartService.getTotalItems(cart));

        // Envoie une réponse JSON
        sendJsonResponse(response, jsonResponse);
    }

    // Gère la suppression d'un produit du panier
    private void handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Cart cart = getOrCreateCart(session);

        String productIdStr = request.getParameter("productId");

        // Vérifie que l'ID du produit est fourni
        if (productIdStr == null) {
            throw new IllegalArgumentException("L'ID du produit est requis.");
        }

        long productId = Long.parseLong(productIdStr);

        // Supprime le produit du panier
        cartService.removeFromCart(cart, productId);

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", true);
        jsonResponse.put("message", "Produit retiré du panier");

        // Envoie une réponse JSON
        sendJsonResponse(response, jsonResponse);
    }

    // Gère le vidage complet du panier
    private void handleClearCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Cart cart = getOrCreateCart(session);

        // Vide le panier
        cartService.clearCart(cart);

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", true);
        jsonResponse.put("message", "Panier vidé");

        // Envoie une réponse JSON
        sendJsonResponse(response, jsonResponse);
    }

    // Gère la mise à jour de la quantité d'un produit dans le panier
    private void handleUpdateQuantity(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Cart cart = getOrCreateCart(session);

        String productIdStr = request.getParameter("productId");
        String newQuantityStr = request.getParameter("quantity");

        // Vérifie que les paramètres nécessaires sont fournis
        if (productIdStr == null || newQuantityStr == null) {
            throw new IllegalArgumentException("L'ID du produit et la nouvelle quantité sont requis.");
        }

        long productId = Long.parseLong(productIdStr);
        int newQuantity = Integer.parseInt(newQuantityStr);

        // Met à jour la quantité du produit dans le panier
        cartService.updateQuantity(cart, productId, newQuantity);

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("success", true);
        jsonResponse.put("message", "Quantité mise à jour pour le produit " + productId);

        // Envoie une réponse JSON
        sendJsonResponse(response, jsonResponse);
    }

    // Récupère le panier à partir de la session ou en crée un nouveau
    private Cart getOrCreateCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
            System.out.println("Nouveau panier créé");
        }
        return cart;
    }

    // Envoie une réponse JSON
    private void sendJsonResponse(HttpServletResponse response, JSONObject jsonResponse) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
    }

    // Envoie une réponse d'erreur JSON
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("error", message);
        sendJsonResponse(response, jsonResponse);
    }

    // Gère les exceptions et envoie une réponse d'erreur
    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("error", e.getMessage());
        e.printStackTrace();
        sendJsonResponse(response, jsonResponse);
    }

}
