package org.cegep.gg.context.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener("context listener")
public class ContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();

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