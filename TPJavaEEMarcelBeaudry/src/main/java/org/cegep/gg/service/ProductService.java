package org.cegep.gg.service;

import org.cegep.gg.model.Category;
import org.cegep.gg.model.Product;

import jakarta.servlet.ServletException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final DataSource dataSource;

    /**
     * Constructeur du service avec injection du DataSource.
     * @param dataSource La source de données utilisée pour les connexions à la base.
     */
    public ProductService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Récupère tous les produits depuis la base de données.
     * @return Une liste d'objets Product.
     * @throws ServletException En cas d'erreur SQL ou autre problème.
     */
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

    /**
     * Récupère toutes les catégories depuis la base de données.
     * @return Une liste d'objets Category.
     * @throws ServletException En cas d'erreur SQL ou autre problème.
     */
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

    /**
     * Récupère un produit par son ID.
     * @param id L'ID du produit.
     * @return Le produit correspondant ou null s'il n'existe pas.
     * @throws SQLException En cas d'erreur SQL.
     * @throws ServletException 
     */
    public Product getProductById(long id) throws ServletException {
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
        }catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération du produit par ID", e);
        }
    }

    /**
     * Ajoute une nouvelle catégorie dans la base de données.
     * @param name Le nom de la catégorie.
     * @param description La description de la catégorie.
     * @throws ServletException 
     */
    public void addCategory(String name, String description) throws ServletException {
        String query = "INSERT INTO categories (name, description) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, description);
            ps.executeUpdate();
        } catch (SQLException e) {
        	throw new ServletException("Erreur lors de l'ajout de la catégorie", e);
        }
    }

    /**
     * Met à jour une catégorie existante.
     * @param id L'ID de la catégorie.
     * @param name Le nouveau nom de la catégorie.
     * @param description La nouvelle description.
     * @throws ServletException 
     */
    public void updateCategory(int id, String name, String description) throws ServletException {
        String query = "UPDATE categories SET name = ?, description = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
        	throw new ServletException("Erreur lors de l'update de la catégorie", e);
        }
    }

    /**
     * Supprime une catégorie par son ID.
     * @param id L'ID de la catégorie à supprimer.
     * @throws ServletException 
     */
    public void deleteCategory(int id) throws ServletException {
        String query = "DELETE FROM categories WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
        	throw new ServletException("Erreur lors de la suppression de la catégorie", e);
        }
    }

    /**
     * Ajoute un nouveau produit dans la base de données.
     * @param name Le nom du produit.
     * @param categoryId L'ID de la catégorie du produit.
     * @param price Le prix du produit.
     * @param imagePath Le chemin de l'image du produit.
     * @throws ServletException 
     */
    public void addProduct(String name, String categoryId, String price, String imagePath) throws ServletException {
        String query = "INSERT INTO products (name, categories_id, price, image_url) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, Integer.parseInt(categoryId));
            ps.setDouble(3, Double.parseDouble(price));
            ps.setString(4, imagePath);
            ps.executeUpdate();
        } catch (SQLException e) {
        	throw new ServletException("Erreur lors de l'ajout du produit", e);
        }
    }

    /**
     * Met à jour un produit existant.
     * @param productId L'ID du produit à mettre à jour.
     * @param name Le nouveau nom du produit.
     * @param categoryId L'ID de la nouvelle catégorie.
     * @param price Le nouveau prix.
     * @param imagePath Le nouveau chemin de l'image.
     * @throws ServletException 
     */
    public void updateProduct(int productId, String name, String categoryId, String price, String imagePath) throws ServletException {
        String query = "UPDATE products SET name = ?, categories_id = ?, price = ?, image_url = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, Integer.parseInt(categoryId));
            ps.setDouble(3, Double.parseDouble(price));
            ps.setString(4, imagePath);
            ps.setInt(5, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
        	throw new ServletException("Erreur lors de l'update du produit", e);
        }
    }

    /**
     * Supprime un produit par son ID.
     * @param productId L'ID du produit à supprimer.
     * @throws ServletException 
     */
    public void deleteProduct(int productId) throws ServletException {
        String query = "DELETE FROM products WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
        	throw new ServletException("Erreur lors de la suppression du produit", e);
        }
    }

    /**
     * Récupère les produits par ID de catégorie.
     * @param categoryId L'ID de la catégorie.
     * @return Une liste de produits appartenant à cette catégorie.
     * @throws ServletException 
     */
    public List<Product> getProductsByCategoryId(long categoryId) throws ServletException {
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
        }catch (SQLException e) {
			throw new ServletException("Erreur lors de la récupération des produits par catégorie", e);
		}
        return products;
    }

	// méthode searchProducts pour la barre de recherche
	public List<Product> searchProducts(String query) throws ServletException {
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
	    }catch (SQLException e) {
		    throw new ServletException("Erreur lors de la recherche des produits", e);
	    }
	    return products;
	}
}
