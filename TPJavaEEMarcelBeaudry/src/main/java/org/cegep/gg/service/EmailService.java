package org.cegep.gg.service;

import org.cegep.gg.model.Cart;
import org.cegep.gg.model.CartItem;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Properties;

public class EmailService {
    private final Session mailSession;

    /**
     * Initialise le service d'envoi d'email avec une session configurée.
     * Utilise un serveur SMTP pour l'authentification et l'envoi.
     */
    public EmailService() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        final String username = "noreply.guyllaume@gmail.com";
        final String password = "dcxdxviuhhnawlec";

        // Création de la session mail avec authentification
        mailSession = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    /**
     * Envoie un email de confirmation de compte après inscription.
     * @param toEmail L'email du destinataire.
     * @param name Le nom de l'utilisateur inscrit.
     */
    public void sendConfirmationAccount(String toEmail, String name) {
        try {
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("noreply.guyllaume@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Welcome to CardRoyalty!");

            // Contenu HTML du message
            String htmlContent = String.format("""
                <html>
                <body>
                    <h2>Bienvenue chez CardRoyalty, %s!</h2>
                    <p>Merci d'avoir choisi CardRoyalty.</p>
                    <p>Vous pouvez maintenant :</p>
                    <ul>
                        <li>Visiter notre catalogue</li>
                        <li>Faire des achats</li>
                        <li>Modifier votre profil</li>
                    </ul>
                    <p>Si vous avez des questions, n'hésitez pas à nous contacter.</p>
                </body>
                </html>
                """, name);

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);

        } catch (MessagingException e) {
            // Log l'erreur mais ne stoppe pas le processus
            e.printStackTrace();
        }
    }
    
    /**
     * Envoie un email de confirmation de commande avec les détails.
     * @param request La requête HTTP contenant les données de la commande.
     */
    public void sendCheckoutConfirmation(HttpServletRequest request) {
    	// Récupération des informations du formulaire
    	String prenom = request.getParameter("prenom");
    	String nom = request.getParameter("nom");
    	String telephone = request.getParameter("telephone");
    	String courriel = request.getParameter("courriel");
    	String adresse_client = request.getParameter("adresse_client");
    	String ville_client = request.getParameter("ville_client");
    	String province_client = request.getParameter("province_client");
    	String pays_client = request.getParameter("pays_client");
    	String code_postal_client = request.getParameter("code_postal_client");
    	String adresse_shipping = request.getParameter("adresse_shipping");
    	String ville_shipping = request.getParameter("ville_shipping");
    	String province_shipping = request.getParameter("province_shipping");
    	String pays_shipping = request.getParameter("pays_shipping");
    	String code_postal_shipping = request.getParameter("code_postal_shipping");
    	String date_livraison = request.getParameter("date_livraison");
		String cardNumber = request.getParameter("credit_card").replace(" ", "");
		String lastFourNumber = cardNumber.substring(cardNumber.length()-4);
		String expDate = request.getParameter("date_exp");
		
    	
    	Cart cart = (Cart) request.getSession().getAttribute("cart");
    	Double totalAmountPaid = cart.getTotal();

        // Construction de la liste des produits en HTML
        StringBuilder productListHtml = new StringBuilder();
        productListHtml.append("<table style='width:100%; border-collapse: collapse;'>")
                       .append("<thead>")
                       .append("<tr>")
                       .append("<th style='border: 1px solid #ddd; padding: 8px;'>Nom</th>")
                       .append("<th style='border: 1px solid #ddd; padding: 8px;'>Prix</th>")
                       .append("<th style='border: 1px solid #ddd; padding: 8px;'>Quantité</th>")
                       .append("</tr>")
                       .append("</thead>")
                       .append("<tbody>");

        for (CartItem item : cart.getItems()) {
            productListHtml.append("<tr>")
                           .append(String.format("<td style='border: 1px solid #ddd; padding: 8px;'>%s</td>", item.getProduct().getName()))
                           .append(String.format("<td style='border: 1px solid #ddd; padding: 8px;'>%.2f $</td>", item.getTotalPrice()))
                           .append(String.format("<td style='border: 1px solid #ddd; padding: 8px;'>%d</td>", item.getQuantity()))
                           .append("</tr>");
        }

        productListHtml.append("</tbody></table>");
    	
        try {
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("noreply.guyllaume@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(courriel));
            message.setSubject("Welcome to CardRoyalty!");

            // Contenu HTML du message
            String htmlContent = String.format("""
                <html lang="en">
				<head>
				    <meta charset="UTF-8">
				    <meta name="viewport" content="width=device-width, initial-scale=1.0">
				    <title>Confirmation de commande</title>
				    <style>
				        body {
				            font-family: Arial, sans-serif;
				            line-height: 1.6;
				            background-color: #f9f9f9;
				            color: #333;
				        }
				        .container {
				            max-width: 600px;
				            margin: 20px auto;
				            background: #fff;
				            padding: 20px;
				            border-radius: 8px;
				            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
				        }
				        .header {
				            text-align: center;
				            padding: 10px 0;
				            border-bottom: 1px solid #ddd;
				        }
				        .content {
				            margin: 20px 0;
				        }
				        .content h2 {
				            margin-top: 0;
				            color: #6A2469;
				        }
				        .info-block {
				            margin-bottom: 20px;
				        }
				        .info-block h3 {
				            margin-bottom: 10px;
				            font-size: 16px;
				            color: #555;
				        }
				        .info-block p {
				            margin: 5px 0;
				            font-size: 14px;
				        }
				        .footer {
				            text-align: center;
				            font-size: 12px;
				            color: #aaa;
				            border-top: 1px solid #ddd;
				            padding: 10px 0;
				        }
				    </style>
				</head>
				<body>
				    <div class="container">
				        <div class="header">
				            <h1>Confirmation de votre commande</h1>
				        </div>
				        <div class="content">
				            <h2>Détails de votre commande</h2>
				            <div class="info-block">
				                <h3>Informations personnelles</h3>
				                <p><strong>Prénom:</strong> %s</p>
				                <p><strong>Nom:</strong> %s</p>
				                <p><strong>Téléphone:</strong> %s</p>
				                <p><strong>Courriel:</strong> %s</p>
				                <p><strong>Adresse:</strong> %s, %s, %s, %s - %s</p>
				            </div>
				            <div class="info-block">
				                <h3>Adresse de livraison</h3>
				                <p><strong>Adresse:</strong> %s</p>
				                <p><strong>Ville:</strong> %s</p>
				                <p><strong>Province:</strong> %s</p>
				                <p><strong>Pays:</strong> %s</p>
				                <p><strong>Code postal:</strong> %s</p>
				                <p><strong>Date de livraison:</strong> %s</p>
				            </div>
				            <div class="info-block">
                            <h3>Produits commandés</h3>
                            %s
            		        </div>
				            <div class="info-block">
				                <h3>Informations de paiement</h3>
				                <p><strong>Carte de crédit:</strong> **** **** **** %s</p>
				                <p><strong>Date d'expiration:</strong> %s</p>
				                <p><strong>CC:</strong> ***</p>
				                <p><strong>Montant chargé:</strong> %.2f</p>
				            </div>
				        </div>
				        <div class="footer">
				            <p>Merci pour votre commande! Nous vous contacterons si nous avons besoin de plus d'informations.</p>
				        </div>
				    </div>
				</body>
				</html>
                """, prenom, nom, telephone, courriel,
                adresse_client, ville_client, province_client, pays_client, code_postal_client,
                adresse_shipping, ville_shipping, province_shipping, pays_shipping, code_postal_shipping, date_livraison,
                productListHtml.toString(), lastFourNumber, expDate, totalAmountPaid);

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
