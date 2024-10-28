package org.cegep.gg.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.cegep.gg.model.Product;
import jakarta.servlet.ServletException;

public class ProductService {
    private DataSource dataSource;
    
    public ProductService(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public List<Product> getAllProducts() throws ServletException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("image_url"));
                product.setCategory(rs.getString("category"));
                products.add(product);
            }
            return products;
            
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des produits", e);
        }
    }
    
    public List<String> getAllCategories() throws ServletException {
        List<String> categories = new ArrayList<>();
        String query = "SELECT DISTINCT category FROM products";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
            return categories;
            
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des catégories", e);
        }
    }
    
    public void testConnection() throws ServletException {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("Test de connexion réussi !");
            
            String query = "SELECT COUNT(*) FROM products";
            try (PreparedStatement ps = conn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Nombre de produits dans la base : " + rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du test de connexion", e);
        }
    }
}