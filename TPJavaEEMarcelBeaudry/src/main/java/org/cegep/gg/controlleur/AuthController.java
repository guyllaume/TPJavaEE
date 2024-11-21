package org.cegep.gg.controlleur;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.cegep.gg.model.User;
import org.cegep.gg.service.EmailService;
import org.cegep.gg.service.UserService;

@WebServlet("/auth/*")
public class AuthController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserService userService;
    private EmailService emailService;

    @Resource(name = "jdbc/cegep_gg_bd_tp")
    private DataSource dataSource;

    // Initialisation des services nécessaires
    @Override
    public void init() throws ServletException {
        super.init();
        if (dataSource == null) {
            throw new ServletException("DataSource est null dans AuthController");
        }
        userService = new UserService(dataSource);
        emailService = new EmailService();
    }

    // Gestion des requêtes GET
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        // Définir un chemin par défaut pour éviter les erreurs
        if (path == null) {
            path = "error";
        }

        switch (path) {
            case "/signup":
                // Rediriger vers la page d'inscription
                request.getRequestDispatcher("/WEB-INF/views/inscription.jsp").forward(request, response);
                break;

            case "/membre/profile":
                // Récupérer les informations de l'utilisateur connecté
                User user = userService.getUserByEmail(request.getUserPrincipal().getName());
                if (user != null) {
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("/WEB-INF/views/member/profile.jsp").forward(request, response);
                } else {
                    // Si l'utilisateur n'est pas trouvé
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
                break;

            default:
                // Si le chemin n'est pas reconnu
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    // Gestion des requêtes POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        // Définir un chemin par défaut pour éviter les erreurs
        if (path == null) {
            path = "error";
        }

        switch (path) {
            case "/signup":
                // Gestion de l'inscription
                signup(request, response);
                break;

            case "/membre/updateProfile":
                // Mise à jour du profil utilisateur
                updateUser(request, response);
                break;

            default:
                // Si le chemin n'est pas reconnu
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    // Met à jour les informations d'un utilisateur
    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        User user = validateUser(request, response, errors);

        if (!errors.isEmpty()) {
            // Retourner à la page du profil avec les erreurs
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/views/member/profile.jsp").forward(request, response);
            return;
        }

        if (userService.updateUser(user)) {
            // Mise à jour réussie
            request.getSession().setAttribute("validationMessage", "Vos modifications ont bien été enregistrées");
            Map<String, String> userDetails = new HashMap<>();
            userDetails.put("prenom", user.getPrenom());
            userDetails.put("nom", user.getNom());
            request.getSession().setAttribute("userDetails", userDetails);
            response.sendRedirect(request.getContextPath() + "/index");
        } else {
            // Échec de la mise à jour
            errors.put("email", "Cet email existe déjà");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/views/member/profile.jsp").forward(request, response);
        }
    }

    // Inscrit un nouvel utilisateur
    private void signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        User user = validateUser(request, response, errors);

        if (!errors.isEmpty()) {
            // Retourner à la page d'inscription avec les erreurs
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/views/inscription.jsp").forward(request, response);
            return;
        }

        if (userService.signup(user)) {
            // Envoie un email de confirmation
            emailService.sendConfirmationAccount(user.getEmail(), user.getPrenom() + " " + user.getNom());

            // Connecte automatiquement l'utilisateur
            request.login(user.getEmail(), user.getPassword());
            request.getSession().setAttribute("validationMessage", "Votre compte a été créé avec succès");
            response.sendRedirect(request.getContextPath() + "/index");
        } else {
            // Échec de l'inscription
            errors.put("email", "Cet email existe déjà");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/views/inscription.jsp").forward(request, response);
        }
    }

    // Valide les informations d'un utilisateur avant de les enregistrer
    private User validateUser(HttpServletRequest request, HttpServletResponse response, Map<String, String> errors) throws ServletException, IOException {

		String prenom = request.getParameter("prenom");
		String nom = request.getParameter("nom");
		LocalDate date_de_naissance = LocalDate.parse(request.getParameter("date_de_naissance"));
		String telephone = request.getParameter("telephone");
		String email = request.getParameter("courriel");
		String confirmEmail = request.getParameter("confirmationCourriel");
		String password = request.getParameter("mot_de_passe");
		String adresse_client = request.getParameter("adresse_client");
		String ville_client = request.getParameter("ville_client");
		String province_client = request.getParameter("province_client");
		String code_postal_client = request.getParameter("code_postal_client");
		String pays_client = request.getParameter("pays_client");
		String adresse_livraison = request.getParameter("adresse_shipping");
		String ville_livraison = request.getParameter("ville_shipping");
		String province_livraison = request.getParameter("province_shipping");
		String code_postal_livraison = request.getParameter("code_postal_shipping");
		String pays_livraison = request.getParameter("pays_shipping");
		
		if (prenom == null || prenom.trim().length() < 2 || prenom.trim().length() > 45) {
			errors.put("prenom", "Le prenom doit contenir entre 2 et 45 caractères");
		}

		if (nom == null || nom.trim().length() < 2 || nom.trim().length() > 45) {
			errors.put("nom", "Le nomasd doit contenir entre 2 et 45 caractères");
		}
		
		if(date_de_naissance == null) {
			errors.put("date_de_naissance", "La date de naissance est obligatoire");
		}
		
		if (telephone == null) {
			errors.put("telephone", "Le telephone est obligatoire");
		}else {
			telephone = telephone.replaceAll("[^0-9]", "");
			if(telephone.length() != 10) {
				errors.put("telephone", "Le telephone doit contenir 10 chiffres");
			}
		}
		
		if (email == null) {
			errors.put("courriel", "Le courriel est obligatoire");
		}else if(!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			errors.put("courriel", "Le courriel est invalide");
		}
		
		if (confirmEmail == null) {
			errors.put("confirmationCourriel", "La confirmation du courriel est obligatoire");
		}else if(!email.equals(confirmEmail)) {
			errors.put("confirmationCourriel", "Les courriels ne correspondent pas");
		}
		
		if (password == null || password.trim().length() < 8 || password.trim().length() > 30) {
			errors.put("mot_de_passe", "Le mot de passe doit contenir entre 8 et 30 caractères");
		}
		
		if (adresse_client == null || adresse_client.trim().length() < 2 || adresse_client.trim().length() > 100) {
			errors.put("adresse_client", "L'adresse client doit contenir entre 2 et 100 caractères");
		}
		
		if (ville_client == null || ville_client.trim().length() < 2 || ville_client.trim().length() > 100) {
			errors.put("ville_client", "La ville client doit contenir entre 2 et 100 caractères");
		}
		
		if (province_client == null || province_client.trim().length() < 2 || province_client.trim().length() > 100) {
			errors.put("province_client", "La province client doit contenir entre 2 et 100 caractères");
		}
		
		if (code_postal_client == null || 
				!code_postal_client.trim().matches("^[ABCEGHJ-NPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z]\\s?\\d[ABCEGHJ-NPRSTV-Z]\\d$")) {
			errors.put("code_postal_client", "Le code postal doit etre A1A 1A1 ou A1A1A1");
		}
		
		if (pays_client == null || pays_client.trim().length() < 2 || pays_client.trim().length() > 50) {
			errors.put("pays_client", "Le pays client doit contenir entre 2 et 50 caractères");
		}
		
		if (adresse_livraison == null || adresse_livraison.trim().length() < 2 || adresse_livraison.trim().length() > 45) {
			errors.put("adresse_shipping", "L'adresse shipping doit contenir entre 2 et 45 caractères");
		}
		
		if (ville_livraison == null || ville_livraison.trim().length() < 2 || ville_livraison.trim().length() > 45) {
			errors.put("ville_shipping", "La ville shipping doit contenir entre 2 et 45 caractères");
		}
		
		if (province_livraison == null || province_livraison.trim().length() < 2 || province_livraison.trim().length() > 45) {
			errors.put("province_shipping", "La province shipping doit contenir entre 2 et 45 caractères");
		}
		
		if (code_postal_livraison == null || 
				!code_postal_livraison.trim().matches("^[ABCEGHJ-NPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z]\\s?\\d[ABCEGHJ-NPRSTV-Z]\\d$")) {
			errors.put("code_postal_shipping", "Le code postal doit etre A1A 1A1 ou A1A1A1");
		}
		
		if (pays_livraison == null || pays_livraison.trim().length() < 2 || pays_livraison.trim().length() > 45) {
			errors.put("pays_shipping", "Le pays shipping doit contenir entre 2 et 45 caractères");
		}
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			return null;
		}
		
		code_postal_client = code_postal_client.toUpperCase().replace(" ", "");
		code_postal_livraison = code_postal_livraison.toUpperCase().replace(" ", "");
		
		return new User(prenom, nom, date_de_naissance, telephone, email, password, adresse_client, ville_client, province_client, code_postal_client, pays_client, adresse_livraison, ville_livraison, province_livraison, code_postal_livraison, pays_livraison);
	}
}
