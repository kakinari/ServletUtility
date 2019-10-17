package com.kakinari.servlet.datatype.interfaces;

import java.util.Map;

import javax.servlet.ServletContext;

public interface ServletInfoAccess {
	public String getServletName();
	public String getServletClass();
	public String[] getServletMap();
	public String[] getServletAuthKey();
	public Map<?,?> getServletParams();
	public Map<?,?> getServletAuth();
	public void addServlet(ServletContext context);	
}
