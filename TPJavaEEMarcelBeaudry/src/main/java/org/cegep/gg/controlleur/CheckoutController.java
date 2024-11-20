package org.cegep.gg.controlleur;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.cegep.gg.model.Cart;
import org.cegep.gg.model.User;
import org.cegep.gg.service.EmailService;
import org.cegep.gg.service.UserService;


@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;
	private EmailService emailService;
	
	@Resource(name="jdbc/cegep_gg_bd_tp")
	private DataSource dataSource;
       
    public CheckoutController() {
        super();
    }

    @Override
    public void init() throws ServletException {
    	super.init();
        userService = new UserService(dataSource);
        emailService = new EmailService();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		showCheckoutPage(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(verificationDesInformations(request, response)) {
			emailService.sendCheckoutConfirmation(request);
			request.getSession().removeAttribute("cart");
			request.getSession().setAttribute("validationMessage", "Votre commande a bien été envoyé. Veuillez verifier votre boite mail pour les details.");
			response.sendRedirect(request.getContextPath() + "/index");
		}else {
			showCheckoutPage(request, response);
		}
	}
	
	private void showCheckoutPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute("cart");

		// Check if cart is empty
		if (cart == null || cart.getItems().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/cart");
			return;
		}
		
		Principal userPrincipal = request.getUserPrincipal();
		
		// Check if user is connected
		if (userPrincipal == null) {
			response.sendRedirect(request.getContextPath() + "/cart");
			return;
		}
		
		String email = userPrincipal.getName(); // This will return the email 
		
		if(email == null || email.isEmpty()){
			response.sendRedirect(request.getContextPath() + "/cart");
			return;
		}
		
		User user = userService.getUserByEmail(email);
		
		if(user == null){
			response.sendRedirect(request.getContextPath() + "/cart");
			return;
		}
		
		//Everything is valid here
		request.setAttribute("user", user);
		request.getRequestDispatcher("/WEB-INF/views/member/checkout.jsp").forward(request, response);
	}
	
	private boolean verificationDesInformations(HttpServletRequest request, HttpServletResponse response) {
        String cardRegex = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|6(?:011|5[0-9]{2})[0-9]{12}|(?:2131|1800|35\\d{3})\\d{11})$";
        String expDateRegex = "^(0[1-9]|1[0-2])\\/([0-9]{2}|[0-9]{4})$";
        String ccRegex = "^[0-9]{3,4}$";
        
		String cardNumber = request.getParameter("credit_card");
		String expDate = request.getParameter("date_exp");
		String cc = request.getParameter("cc");
		
		Map<String, String> errors = new HashMap<>();
		
		if (!cardNumber.matches(cardRegex)) {
			errors.put("cardNumberError", "Le numéro de carte est invalide.");
		}
		
		if (!expDate.matches(expDateRegex)) {
			errors.put("expDateError", "La date d'expiration est invalide.");
		}
		
		if (!cc.matches(ccRegex)) {
			errors.put("ccError", "Le CC est invalide.");
		}
		
		if (errors.isEmpty()) {
			return true;
		}
		request.setAttribute("errors", errors);
		return false;
        
	}

}
