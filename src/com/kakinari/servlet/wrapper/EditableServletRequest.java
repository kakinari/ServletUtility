/**
 * 
 */
package com.kakinari.servlet.wrapper;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Takashi Kakinari
 *
 */
public class EditableServletRequest extends HttpServletRequestWrapper {
	private LocalParam localparam = null;

	/**
	 * @param request
	 */
	public EditableServletRequest(HttpServletRequest request) {
		super(request);
		localparam = new LocalParam();
	}

	@Override
	public String getParameter(String name) {
		if (localparam.has(name))
			return localparam.getFirst(name);
		return super.getParameter(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return localparam.getParameterMap(super.getParameterMap());
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return localparam.getParameterNames(super.getParameterMap());
	}

	@Override
	public String[] getParameterValues(String name) {
		if (localparam.has(name))
			return localparam.get(name).toArray(new String[0]);
		return super.getParameterValues(name);
	}

	public String[] getMargedParameterValues(String name) {
		ArrayList<String> al = new ArrayList<String>();
		String[] orig = super.getParameterValues(name);
		if (orig != null)
			for (String data : orig)
				al.add(data);
		if (localparam.has(name))
			for (String data : localparam.get(name).toArray(new String[0]))
				al.add(data);
		return al.size() == 0 ? null : al.toArray(new String[0]);
	}

	public void setParameter(Map<String,String> m) {
		for (String key : m.keySet())
			localparam.setParameter(key, m.get(key));
	}

	public void setParameter(String name, String value) {
		localparam.setParameter(name, value);
	}

	public void setParameterValues(String name, String[] value) {
		localparam.setParameterValues(name, value);
	}

	public void addParameter(String name, String value) {
		localparam.addParameter(name, value);
	}

	public void addParameterValues(String name, String[] value) {
		localparam.addParameterValues(name, value);
	}

	public String[] removeParameter(String name) {
		Params ret = localparam.remove(name);
		return ret == null ? null : ret.toArray(new String[0]);
	}
	
	public String getOriginalParameter(String name) {
		return super.getParameter(name);
	}

	public Map<String, String[]> getOriginalParameterMap() {
		return super.getParameterMap();
	}

	public Enumeration<String> getOriginalParameterNames() {
		return super.getParameterNames();
	}

	public String[] getOriginalParameterValues(String name) {
		return super.getParameterValues(name);
	}

}
