package org.cegep.gg.context.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;


import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;


@WebListener("context listener")
public class ContextListener implements ServletContextListener {


    public void contextDestroyed(ServletContextEvent event)  { 
        // First deregister JDBC drivers
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (Exception e) {
                // Log the error but continue
                e.printStackTrace();
            }
        }

        // Then shutdown MySQL AbandonedConnectionCleanupThread
        try {
            AbandonedConnectionCleanupThread.checkedShutdown();
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
        }
    	
    }

    public void contextInitialized(ServletContextEvent event)  { 
    }
	
}
