package org.cegep.gg.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import org.cegep.gg.model.Category;
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
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("image_url"));
                product.setCategory_id(rs.getLong("categories_id"));
                products.add(product);
            }
            return products;
            
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des produits", e);
        }
    }
    
    public List<Category> getAllCategories() throws ServletException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                categories.add(category);
            }
            return categories;
            
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des catégories", e);
        }
    }
    
    public Product getProductById(long id) throws SQLException {
        String query = "SELECT * FROM products WHERE id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setLong(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getLong("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setImageUrl(rs.getString("image_url"));
                    product.setCategory_id(rs.getLong("categories_id"));
                    return product;
                }
                return null;
            }
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

	public void addCategory(String name, String description) {
		try (Connection conn = dataSource.getConnection()) {
			String query = "INSERT INTO categories (name, description) VALUES (?, ?)";
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setString(1, name);
				ps.setString(2, description);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateCategory(int id, String name, String description) {
		try (Connection conn = dataSource.getConnection()) {
			String query = "UPDATE categories SET name = ?, description = ? WHERE id = ?";
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setString(1, name);
				ps.setString(2, description);
				ps.setInt(3, id);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void deleteCategory(int id) {
		try (Connection conn = dataSource.getConnection()) {
			String query = "DELETE FROM categories WHERE id = ?";
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setInt(1, id);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void addProduct(String name, String categoryId, String price, String imagePath) {
		
		try (Connection conn = dataSource.getConnection()) {
			String query = "INSERT INTO products (name, categories_id, price, image_url) VALUES (?, ?, ?, ?)";
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setString(1, name);
				ps.setInt(2, Integer.parseInt(categoryId));
				ps.setDouble(3, Double.parseDouble(price));
				ps.setString(4, imagePath);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void updateProduct(int productId, String name, String categoryId, String price, String imagePath) {
		try (Connection conn = dataSource.getConnection()) {
			String query = "UPDATE products SET name = ?, categories_id = ?, price = ?, image_url = ? WHERE id = ?";
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setString(1, name);
				ps.setInt(2, Integer.parseInt(categoryId));
				ps.setDouble(3, Double.parseDouble(price));
				ps.setString(4, imagePath);
				ps.setInt(5, productId);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void deleteProduct(int productId) {
		try (Connection conn = dataSource.getConnection()) {
			String query = "DELETE FROM products WHERE id = ?";
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setInt(1, productId);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Product> getProductsByCategoryId(long categoryId) throws SQLException {
	    List<Product> products = new ArrayList<>();
	    String query = "SELECT * FROM products WHERE categories_id = ?";

	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setLong(1, categoryId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Product product = new Product();
	                product.setId(rs.getLong("id"));
	                product.setName(rs.getString("name"));
	                product.setPrice(rs.getDouble("price"));
	                product.setImageUrl(rs.getString("image_url"));
	                product.setCategory_id(rs.getLong("categories_id"));
	                products.add(product);
	            }
	        }
	    }
	    return products;
	}
	// méthode searchProducts pour la barre de recherche
	public List<Product> searchProducts(String query) throws SQLException {
	    List<Product> products = new ArrayList<>();
	    String sql = "SELECT p.*, c.description AS category_description FROM products p " +
	                 "JOIN categories c ON p.categories_id = c.id " +
	                 "WHERE p.name LIKE ? OR c.description LIKE ?";
	    
	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, "%" + query + "%");
	        ps.setString(2, "%" + query + "%");
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Product product = new Product();
	                product.setId(rs.getLong("id"));
	                product.setName(rs.getString("name"));
	                product.setPrice(rs.getDouble("price"));
	                product.setImageUrl(rs.getString("image_url"));
	                product.setCategory_id(rs.getLong("categories_id"));
	                product.setCategoryDescription(rs.getString("category_description"));
	                products.add(product);
	            }
	        }
	    }
	    return products;
	}




}