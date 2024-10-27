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
        String sql = "INSERT INTO user (prenom, nom, date_de_naissance, telephone, courriel, mot_de_passe,"
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
			pstmt.setString(1, user.getPrenom());
			pstmt.setString(2, user.getNom());
			pstmt.setObject(3, user.getDate_de_naissance());
			pstmt.setString(4, user.getTelephone());
			pstmt.setString(5, user.getEmail());
			pstmt.setString(6, user.getPassword());
			pstmt.setString(7, user.getAdresse_client());
			pstmt.setString(8, user.getVille_client());
			pstmt.setString(9, user.getProvince_client());
			pstmt.setString(10, user.getCode_postal_client());
			pstmt.setString(11, user.getPays_client());
			pstmt.setString(12, user.getAdresse_livraison());
			pstmt.setString(13, user.getVille_livraison());
			pstmt.setString(14, user.getProvince_livraison());
			pstmt.setString(15, user.getCode_postal_livraison());
			pstmt.setString(16, user.getPays_livraison());
            
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
