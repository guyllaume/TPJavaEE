package org.cegep.gg.controlleur;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.cegep.gg.model.Role;
import org.cegep.gg.model.User;
import org.cegep.gg.service.RoleService;
import org.cegep.gg.service.UserService;

@WebServlet("/auth/*")
public class AuthController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;
	private RoleService roleService;
	
	@Resource(name="jdbc/cegep_gg_bd_tp")
	private DataSource dataSource;
       
    public AuthController() {
        super();
    }
    
    //Initialisation du service
    @Override
    public void init() throws ServletException {
    	super.init();
        userService = new UserService(dataSource);
		roleService = new RoleService(dataSource);
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String path = request.getPathInfo();
		if(path == null) {
			path = "error";
		}
		
		switch(path) {
		case "/signup":
			request.getRequestDispatcher("/inscription.jsp").forward(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getPathInfo();
		if(path == null) {
			path = "error";
		}
		
		switch(path) {
		case "/signup":
			signup(request, response);
			break;
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}
	
	private void signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Role role = roleService.getRoleByDescription("MEMBRE");
        
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

        // Create a map to store validation errors
        Map<String, String> errors = new HashMap<>();
		
		if (prenom == null || prenom.trim().length() < 2 || prenom.trim().length() > 45) {
			errors.put("prenom", "Le prenom doit contenir entre 2 et 45 caractères");
		}

		if (nom == null || nom.trim().length() < 2 || nom.trim().length() > 45) {
			errors.put("nom", "Le prenom doit contenir entre 2 et 45 caractères");
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
			request.getRequestDispatcher("/inscription.jsp").forward(request, response);
			return;
		}
		
		code_postal_client = code_postal_client.toUpperCase().replace(" ", "");
		code_postal_livraison = code_postal_livraison.toUpperCase().replace(" ", "");
		User user = new User(prenom, nom, date_de_naissance, telephone, email, password, role.getId(), adresse_client, ville_client, province_client, code_postal_client, pays_client, adresse_livraison, ville_livraison, province_livraison, code_postal_livraison, pays_livraison);

		if(userService.signup(user)) {
			response.sendRedirect(request.getContextPath() + "/products.jsp");
		}else {
			errors.put("email", "Cet email existe déja");
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/inscription.jsp").forward(request, response);
		}
	}

}
