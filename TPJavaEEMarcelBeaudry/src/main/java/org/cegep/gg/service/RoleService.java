package org.cegep.gg.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.cegep.gg.model.Role;

import jakarta.servlet.ServletException;

public class RoleService {

	private DataSource dataSource;

	public RoleService(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * Récupère un rôle en fonction de sa description.
	 * @param description La description du rôle (exemple: "Administrateur", "Utilisateur").
	 * @return Un objet Role correspondant à la description ou null si aucun rôle trouvé.
	 * @throws ServletException En cas d'erreur SQL ou de connexion.
	 */
	public Role getRoleByDescription(String description) throws ServletException {
	    // Requête SQL pour récupérer le rôle en fonction de sa description
	    String sql = "SELECT * FROM roles WHERE role = ?";
	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        // Paramètre de la requête
	        pstmt.setString(1, description);
	        
	        // Exécution de la requête
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                // Création et remplissage de l'objet Role à partir des résultats
	                Role role = new Role();
	                role.setId(rs.getInt("id")); // ID du rôle
	                role.setRole(rs.getString("role")); // Description du rôle
	                return role;
	            }
	        }
	    } catch (SQLException e) {
	        // Gestion des erreurs SQL ou de connexion
	        throw new ServletException("Erreur lors de la récupération du rôle par description", e);
	    }
	    // Retourne null si aucun rôle correspondant n'est trouvé
	    return null;
	}

}
