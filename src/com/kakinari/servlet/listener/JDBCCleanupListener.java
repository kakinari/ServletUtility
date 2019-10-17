package com.kakinari.servlet.listener;

import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
// import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class JDBCCleanupListener
 * When you extends this class as the WebListener, you need to set annotation "@WebListener"
 *
 */
// @WebListener
public abstract class JDBCCleanupListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public JDBCCleanupListener() {
     }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
    	// unregister all JDBC drivers.
    	for (Driver driver : Collections.list(DriverManager.getDrivers())) {
    		try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	// Shutdown AbandonedConnectionCleanupThread.
    	try {
			Class<?> cleanupThread = Class.forName("com.mysql.jdbc.AbandonedConnectionCleanupThread");
			Method method = null;
			if (cleanupThread != null)
				method  = cleanupThread.getMethod("shutdown");
			if (method != null)
				method.invoke(null);
    	} catch (Throwable e) {
			e.printStackTrace();
		}
     }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	// do Nothing.
    }
	
}
