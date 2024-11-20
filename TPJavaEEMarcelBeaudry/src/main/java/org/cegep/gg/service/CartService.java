package org.cegep.gg.service;

import jakarta.servlet.ServletException;
import org.cegep.gg.model.Cart;
import org.cegep.gg.model.Product;
import org.cegep.gg.model.CartItem;
import javax.sql.DataSource;
import java.sql.SQLException;

public class CartService {
    private final ProductService productService;

    public CartService(DataSource dataSource) {
        this.productService = new ProductService(dataSource);
    }

    /**
     * Ajoute un produit au panier
     * @param cart Le panier
     * @param productId L'ID du produit à ajouter
     * @param quantity La quantité à ajouter
     * @throws ServletException Si le produit n'existe pas ou en cas d'erreur DB
     */
    public void addToCart(Cart cart, long productId, int quantity) throws ServletException {
        Product product = productService.getProductById(productId);
		if (product == null) {
		    throw new ServletException("Le produit avec l'ID " + productId + " n'existe pas");
		}
		cart.addItem(product, quantity);
		System.out.println("Produit " + product.getName() + " ajouté au panier. Quantité: " + quantity);
    }

    /**
     * Retire un produit du panier
     * @param cart Le panier
     * @param productId L'ID du produit à retirer
     */
    public void removeFromCart(Cart cart, long productId) {
        cart.removeItem(productId);
        System.out.println("Produit " + productId + " retiré du panier");
    }

    /**
     * Met à jour la quantité d'un produit dans le panier
     * @param cart Le panier
     * @param productId L'ID du produit à mettre à jour
     * @param quantity La nouvelle quantité
     * @throws ServletException Si la quantité est invalide
     */
    public void updateQuantity(Cart cart, long productId, int quantity) throws ServletException {
        if (quantity < 0) {
            throw new ServletException("La quantité ne peut pas être négative");
        }
        if (quantity == 0) {
            removeFromCart(cart, productId);
            return;
        }
        cart.updateQuantity(productId, quantity);
        System.out.println("Quantité mise à jour pour le produit " + productId + ". Nouvelle quantité: " + quantity);
    }

    /**
     * Vide le panier
     * @param cart Le panier à vider
     */
    public void clearCart(Cart cart) {
        cart.getItems().clear();
        System.out.println("Panier vidé");
    }

    /**
     * Calcule le nombre total d'articles dans le panier
     * @param cart Le panier
     * @return Le nombre total d'articles
     */
    public int getTotalItems(Cart cart) {
        return cart.getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    /**
     * Vérifie si le panier est vide
     * @param cart Le panier
     * @return true si le panier est vide, false sinon
     */
    public boolean isCartEmpty(Cart cart) {
        return cart.getItems().isEmpty();
    }
}