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
		
		User user = new User(prenom, nom, date_de_naissance, telephone, email, password, role.getId(), adresse_client, ville_client, province_client, code_postal_client, pays_client, adresse_livraison, ville_livraison, province_livraison, code_postal_livraison, pays_livraison);

		if(userService.signup(user)) {
			response.sendRedirect(request.getContextPath() + "/products.jsp");
		}else {
			request.getRequestDispatcher("/inscription.jsp").forward(request, response);
		}
	}

}
