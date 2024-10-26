package org.cegep.gg.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.cegep.gg.model.User;

public class UserService {
	
	private DataSource dataSource;

	public UserService(DataSource dataSource) {
		this.dataSource = dataSource;
	}
    
    public boolean signup(User user) {
        String sql = "INSERT INTO user (role_id, prenom, nom, date_de_naissance, telephone, courriel, mot_de_passe,"
        		+ " adresse, ville, province, code_postal, pays,"
        		+ " adresse_livraison, ville_livraison, province_livraison, code_postal_livraison, pays_livraison)"
        		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Check if email already exists
            if (emailExists(user.getEmail())) {
                return false;
            }
            
            // Set parameters
            pstmt.setInt(1, user.getRole_id());
			pstmt.setString(2, user.getPrenom());
			pstmt.setString(3, user.getNom());
			pstmt.setObject(4, user.getDate_de_naissance());
			pstmt.setString(5, user.getTelephone());
			pstmt.setString(6, user.getEmail());
			pstmt.setString(7, user.getPassword());
			pstmt.setString(8, user.getAdresse_client());
			pstmt.setString(9, user.getVille_client());
			pstmt.setString(10, user.getProvince_client());
			pstmt.setString(11, user.getCode_postal_client());
			pstmt.setString(12, user.getPays_client());
			pstmt.setString(13, user.getAdresse_livraison());
			pstmt.setString(14, user.getVille_livraison());
			pstmt.setString(15, user.getProvince_livraison());
			pstmt.setString(16, user.getCode_postal_livraison());
			pstmt.setString(17, user.getPays_livraison());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE courriel = ?";
        ResultSet rs = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	try {
        		if(rs != null)
        			rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        return false;
    }
}
