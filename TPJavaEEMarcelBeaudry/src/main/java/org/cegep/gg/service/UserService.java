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
	
	public boolean updateUser(User user) {
		String sql = "UPDATE user SET prenom = ?, nom = ?, date_de_naissance = ?, telephone = ?, courriel = ?, mot_de_passe = ?,"
				+ " adresse = ?, ville = ?, province = ?, code_postal = ?, pays = ?,"
				+ " adresse_livraison = ?, ville_livraison = ?, province_livraison = ?, code_postal_livraison = ?, pays_livraison = ? WHERE courriel = ?";
		
		// Check if email doesn't exists
        if (!emailExists(user.getEmail())) {
            return false;
        }
        
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
			pstmt.setString(17, user.getEmail());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean signup(User user) {
	    String sqlUser = "INSERT INTO user (prenom, nom, date_de_naissance, telephone, courriel, mot_de_passe,"
	            + " adresse, ville, province, code_postal, pays,"
	            + " adresse_livraison, ville_livraison, province_livraison, code_postal_livraison, pays_livraison)"
	            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	            
	    String sqlRole = "INSERT INTO roles (courriel, role) VALUES (?, ?)";

	    Connection conn = null;
	    try {
	        conn = dataSource.getConnection();
	        conn.setAutoCommit(false);  // Start transaction

	        // Check if email exists
	        if (emailExists(user.getEmail())) {
	            return false;
	        }

	        // Insert user
	        try (PreparedStatement pstmtUser = conn.prepareStatement(sqlUser)) {
	            pstmtUser.setString(1, user.getPrenom());
	            pstmtUser.setString(2, user.getNom());
	            pstmtUser.setObject(3, user.getDate_de_naissance());
	            pstmtUser.setString(4, user.getTelephone());
	            pstmtUser.setString(5, user.getEmail());
	            pstmtUser.setString(6, user.getPassword());
	            pstmtUser.setString(7, user.getAdresse_client());
	            pstmtUser.setString(8, user.getVille_client());
	            pstmtUser.setString(9, user.getProvince_client());
	            pstmtUser.setString(10, user.getCode_postal_client());
	            pstmtUser.setString(11, user.getPays_client());
	            pstmtUser.setString(12, user.getAdresse_livraison());
	            pstmtUser.setString(13, user.getVille_livraison());
	            pstmtUser.setString(14, user.getProvince_livraison());
	            pstmtUser.setString(15, user.getCode_postal_livraison());
	            pstmtUser.setString(16, user.getPays_livraison());
	            
	            pstmtUser.executeUpdate();
	        }

	        // Insert role
	        try (PreparedStatement pstmtRole = conn.prepareStatement(sqlRole)) {
	            pstmtRole.setString(1, user.getEmail());
	            pstmtRole.setString(2, "MEMBRE"); 
	            
	            pstmtRole.executeUpdate();
	        }

	        conn.commit();  // Commit transaction
	        return true;

	    } catch (SQLException e) {
	        if (conn != null) {
	            try {
	                conn.rollback();  // Rollback on error
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        e.printStackTrace();
	        return false;
	    } finally {
	        if (conn != null) {
	            try {
	                conn.setAutoCommit(true);  // Reset auto-commit
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
    
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE courriel = ?";
        
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, email);
			try (ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					User user = new User();
					user.setPrenom(rs.getString("prenom"));
					user.setNom(rs.getString("nom"));
					user.setDate_de_naissance(rs.getDate("date_de_naissance").toLocalDate());
					user.setTelephone(rs.getString("telephone"));
					user.setEmail(rs.getString("courriel"));
					user.setPassword(rs.getString("mot_de_passe"));
					user.setAdresse_client(rs.getString("adresse"));
					user.setVille_client(rs.getString("ville"));
					user.setProvince_client(rs.getString("province"));
					user.setCode_postal_client(rs.getString("code_postal"));
					user.setPays_client(rs.getString("pays"));
					user.setAdresse_livraison(rs.getString("adresse_livraison"));
					user.setVille_livraison(rs.getString("ville_livraison"));
					user.setProvince_livraison(rs.getString("province_livraison"));
					user.setCode_postal_livraison(rs.getString("code_postal_livraison"));
					user.setPays_livraison(rs.getString("pays_livraison"));
					return user;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    private boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE courriel = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()){
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
