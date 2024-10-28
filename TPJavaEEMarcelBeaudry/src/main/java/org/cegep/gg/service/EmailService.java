package org.cegep.gg.service;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService {
    private final Session mailSession;

    public EmailService() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        final String username = "noreply.guyllaume@gmail.com";
        final String password = "dcxdxviuhhnawlec";
        
        mailSession = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
    
    public void sendConfirmationAccount(String toEmail, String name) {
        try {
            Message message = new MimeMessage(mailSession);
            // Get from email from session properties (matches context.xml)
            message.setFrom(new InternetAddress("noreply.guyllaume@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Welcome to CardRoyalty!");

            // Create HTML content
            String htmlContent = String.format("""
                <html>
                <body>
                    <h2>Bienvenue CardRoyalty, %s!</h2>
                    <p>Merci d'avoir choisi CardRoyalty.</p>
                    <p>Vous pouvez maintenant utiliser CardRoyalty pour:</p>
                    <ul>
                        <li>Visiter notre catalogue</li>
                        <li>Faire des achats</li>
                        <li>Modifier votre profil</li>
                    </ul>
                    <p>Si vous avez des questions n'hesitez pas a nous contacter</p>
                </body>
                </html>
                """, name);

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);

        } catch (MessagingException e) {
            // Log the error but don't stop the signup process
            e.printStackTrace();
            // Optionally: log.error("Failed to send confirmation email", e);
        }
    }
}
