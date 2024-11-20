package org.cegep.gg.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;
    private double total;

    public Cart() {
        items = new ArrayList<>();
        total = 0.0;
    }

    public void addItem(Product product, int quantity) {
        // Vérifier si le produit existe déjà
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                calculateTotal();
                return;
            }
        }
        
        // Sinon ajouter nouveau produit
        items.add(new CartItem(product, quantity));
        calculateTotal();
    }

    public void removeItem(long productId) {
        items.removeIf(item -> item.getProduct().getId() == productId);
        calculateTotal();
    }

    public void updateQuantity(long productId, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                item.setQuantity(quantity);
                calculateTotal();
                return;
            }
        }
    }

    private void calculateTotal() {
        total = items.stream()
                    .mapToDouble(CartItem::getTotalPrice)
                    .sum();
    }

    // Getters
    public List<CartItem> getItems() { 
        return items; 
    }

    public double getTotal() { 
        return total; 
    }

    public int getItemCount() { 
        return items.stream()
                .mapToInt(CartItem::getQuantity) 
                .sum();
    }
}