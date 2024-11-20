package org.cegep.gg.service;

import org.cegep.gg.model.User;

import javax.sql.DataSource;
import jakarta.servlet.ServletException;

import java.sql.*;

public class UserService {

    private final DataSource dataSource;

    /**
     * Constructeur du service utilisateur avec injection du DataSource.
     * @param dataSource La source de données utilisée pour les connexions à la base.
     */
    public UserService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Met à jour un utilisateur dans la base de données.
     * @param user L'utilisateur à mettre à jour.
     * @return true si la mise à jour a réussi, false sinon.
     * @throws ServletException En cas d'erreur SQL.
     */
    public boolean updateUser(User user) throws ServletException {
        String sql = "UPDATE user SET prenom = ?, nom = ?, date_de_naissance = ?, telephone = ?, courriel = ?, mot_de_passe = ?," +
                " adresse = ?, ville = ?, province = ?, code_postal = ?, pays = ?," +
                " adresse_livraison = ?, ville_livraison = ?, province_livraison = ?, code_postal_livraison = ?, pays_livraison = ? WHERE courriel = ?";

        if (!emailExists(user.getEmail())) {
            return false; // Retourne false si l'email n'existe pas
        }

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Paramètres de la requête
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

            // Exécution de la mise à jour
            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la mise à jour de l'utilisateur", e);
        }
    }

    /**
     * Inscrit un nouvel utilisateur dans la base de données.
     * @param user L'utilisateur à inscrire.
     * @return true si l'inscription a réussi, false sinon.
     * @throws ServletException En cas d'erreur SQL ou de connexion.
     */
    public boolean signup(User user) throws ServletException {
        String sqlUser = "INSERT INTO user (prenom, nom, date_de_naissance, telephone, courriel, mot_de_passe," +
                " adresse, ville, province, code_postal, pays," +
                " adresse_livraison, ville_livraison, province_livraison, code_postal_livraison, pays_livraison)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String sqlRole = "INSERT INTO roles (courriel, role) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false); // Démarre une transaction

            if (emailExists(user.getEmail())) {
                return false; // Retourne false si l'email existe déjà
            }

            // Insertion de l'utilisateur
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

            // Insertion du rôle
            try (PreparedStatement pstmtRole = conn.prepareStatement(sqlRole)) {
                pstmtRole.setString(1, user.getEmail());
                pstmtRole.setString(2, "MEMBRE");
                pstmtRole.executeUpdate();
            }

            conn.commit(); // Valide la transaction
            return true;

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'inscription de l'utilisateur", e);
        }
    }

    /**
     * Récupère un utilisateur en fonction de son email.
     * @param email L'email de l'utilisateur.
     * @return L'utilisateur correspondant ou null s'il n'existe pas.
     * @throws ServletException En cas d'erreur SQL.
     */
    public User getUserByEmail(String email) throws ServletException {
        String sql = "SELECT * FROM user WHERE courriel = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
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
            throw new ServletException("Erreur lors de la récupération de l'utilisateur", e);
        }
        return null;
    }

    /**
     * Vérifie si un email existe déjà dans la base de données.
     * @param email L'email à vérifier.
     * @return true si l'email existe, false sinon.
     * @throws ServletException En cas d'erreur SQL.
     */
    private boolean emailExists(String email) throws ServletException {
        String sql = "SELECT COUNT(*) FROM user WHERE courriel = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la vérification de l'email", e);
        }
        return false;
    }
}
