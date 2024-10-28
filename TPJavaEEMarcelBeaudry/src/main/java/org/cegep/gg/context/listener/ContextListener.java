package org.cegep.gg.context.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

@WebListener("context listener")
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("=== Démarrage de l'initialisation du contexte ===");
        ServletContext sc = event.getServletContext();
        
        try {
            // Configuration JNDI
            InitialContext initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            System.out.println("Contexte JNDI obtenu avec succès");
            
            // Récupération du DataSource
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/cegep_gg_bd_tp");
            System.out.println("DataSource obtenu avec succès: " + dataSource);
            
            // Test de connexion
            try (Connection conn = dataSource.getConnection()) {
                System.out.println("Test de connexion réussi!");
                System.out.println("URL de connexion: " + conn.getMetaData().getURL());
                System.out.println("Utilisateur: " + conn.getMetaData().getUserName());
            }
            
            // Stockage dans le contexte
            sc.setAttribute("dataSource", dataSource);
            System.out.println("DataSource stocké dans le contexte avec succès");
            
        } catch (Exception e) {
            System.err.println("!!! ERREUR CRITIQUE lors de l'initialisation !!!");
            System.err.println("Message d'erreur: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("=== Fin de l'initialisation du contexte ===");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("=== Début de la destruction du contexte ===");
        ServletContext sc = event.getServletContext();
        
        // Suppression du DataSource
        sc.removeAttribute("dataSource");
        System.out.println("DataSource retiré du contexte");

        // Désenregistrement des drivers
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println("Driver désenregistré : " + driver);
            } catch (Exception e) {
                System.err.println("Erreur lors du désenregistrement du driver : " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Nettoyage MySQL
        try {
            AbandonedConnectionCleanupThread.checkedShutdown();
            System.out.println("Nettoyage MySQL effectué");
        } catch (Exception e) {
            System.err.println("Erreur lors du nettoyage MySQL : " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("=== Fin de la destruction du contexte ===");
    }
}