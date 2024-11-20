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

    @Override
    public void init() throws ServletException {
    	super.init();
        if (dataSource == null) {
            throw new ServletException("DataSource est null dans CheckoutController");
        }
        userService = new UserService(dataSource);
        emailService = new EmailService();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		showCheckoutPage(request, response);
	}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Vérifie les informations de la commande
        if (verificationDesInformations(request, response)) {
            // Envoie une confirmation par email
            emailService.sendCheckoutConfirmation(request);

            // Vide le panier après la commande
            request.getSession().removeAttribute("cart");

            // Ajoute un message de validation dans la session
            request.getSession().setAttribute("validationMessage", "Votre commande a bien été envoyée. Veuillez vérifier votre boîte mail pour les détails.");
            response.sendRedirect(request.getContextPath() + "/index");
        } else {
            // Renvoie à la page de checkout si les informations sont invalides
            showCheckoutPage(request, response);
        }
    }

    // Affiche la page de checkout
    private void showCheckoutPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        // Vérifie si le panier est vide
        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        Principal userPrincipal = request.getUserPrincipal();

        // Vérifie si l'utilisateur est connecté
        if (userPrincipal == null) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        String email = userPrincipal.getName(); // Récupère l'email de l'utilisateur connecté

        if (email == null || email.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        User user = userService.getUserByEmail(email);

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Si tout est valide, prépare les données pour la page de checkout
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/views/member/checkout.jsp").forward(request, response);
    }

    // Vérifie les informations de la carte de crédit et autres champs obligatoires
    private boolean verificationDesInformations(HttpServletRequest request, HttpServletResponse response) {
        String cardRegex = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|6(?:011|5[0-9]{2})[0-9]{12}|(?:2131|1800|35\\d{3})\\d{11})$";
        String expDateRegex = "^(0[1-9]|1[0-2])\\/([0-9]{2}|[0-9]{4})$";
        String ccRegex = "^[0-9]{3,4}$";

        // Récupère les champs du formulaire
        String cardNumber = request.getParameter("credit_card");
        String expDate = request.getParameter("date_exp");
        String cc = request.getParameter("cc");

        Map<String, String> errors = new HashMap<>();

        // Valide le numéro de carte
        if (!cardNumber.matches(cardRegex)) {
            errors.put("cardNumberError", "Le numéro de carte est invalide.");
        }

        // Valide la date d'expiration
        if (!expDate.matches(expDateRegex)) {
            errors.put("expDateError", "La date d'expiration est invalide.");
        }

        // Valide le code CC
        if (!cc.matches(ccRegex)) {
            errors.put("ccError", "Le CC est invalide.");
        }

        // Vérifie si des erreurs ont été détectées
        if (errors.isEmpty()) {
            return true;
        }

        // Envoie les erreurs à la vue
        request.setAttribute("errors", errors);
        return false;
    }
}
