package com.kakinari.servlet.listener;

import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
// import javax.servlet.annotation.WebListener;
import javax.servlet.ServletRegistration;

import com.kakinari.database.datatype.AbstractDBTable;
import com.kakinari.servlet.datatype.ServletInfo;

/**
 * Application Lifecycle Listener implementation class TomcatLoadingListener
 * When you extends this class as the WebListener, you need to set annotation "@WebListener"
 */
// @WebListener
public abstract class ServiceContentListener<T extends ServletInfo>
		extends AbstractDBTable<T>
		implements ServletContextListener {
/*
 *	 Constructor	
 */
	public ServiceContentListener() {
		super(new String[0]);
	}
/*
 * Context Destroyed callback	
 */
    public void contextDestroyed(ServletContextEvent event) {
    	//default do nothing.
    }

 /*
  * Context initialized callback
  */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		Map<String, ? extends ServletRegistration> list = event.getServletContext().getServletRegistrations();
		for (T serv : this)
			if (list.get(serv.getServletName()) == null)
				serv.addServlet(event.getServletContext());		
	}
}
