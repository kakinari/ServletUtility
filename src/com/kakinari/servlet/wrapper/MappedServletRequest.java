/**
 * 
 */
package com.kakinari.servlet.wrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Takashi Kakinari
 *
 */
public class MappedServletRequest extends EditableServletRequest {
	private Map<String, String> keymap = null;

	/**
	 * @param request
	 */
	public MappedServletRequest(HttpServletRequest request, Map<String,String> keymap) {
		super(request);
		this.keymap  = keymap;
	}

	
	@Override
	public String getParameter(String name) {
		return super.getParameter(getRealName(name));
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> orig = super.getParameterMap();
		if (orig == null || this.keymap == null)
			return orig;
		Map<String, String[]> retval = new HashMap<String, String[]>();
		for (Entry<String, String> entry : keymap.entrySet()) {
			if(orig.containsKey(entry.getValue()))
				retval.put(entry.getKey(), orig.remove(entry.getValue()));
		}
		retval.putAll(orig);
		return retval;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(getParameterMap().keySet());
	}

	@Override
	public String[] getParameterValues(String name) {
		return super.getParameterValues(getRealName(name));
	}

	@Override
	public String[] getMargedParameterValues(String name) {
		return super.getMargedParameterValues(getRealName(name));
	}

	@Override
	public void setParameter(Map<String, String> m) {
		for (String key : m.keySet())
			super.setParameter(getRealName(key), m.get(key));
	}

	@Override
	public void setParameter(String name, String value) {
		super.setParameter(getRealName(name), value);
	}

	@Override
	public void setParameterValues(String name, String[] value) {
		super.setParameterValues(getRealName(name), value);
	}

	@Override
	public void addParameter(String name, String value) {
		super.addParameter(getRealName(name), value);
	}

	@Override
	public void addParameterValues(String name, String[] value) {
		super.addParameterValues(getRealName(name), value);
	}

	@Override
	public String[] removeParameter(String name) {
		return super.removeParameter(getRealName(name));
	}

	@Override
	public String getOriginalParameter(String name) {
		return super.getOriginalParameter(getRealName(name));
	}

	@Override
	public Map<String, String[]> getOriginalParameterMap() {
		Map<String, String[]> orig = super.getOriginalParameterMap();
		if (orig == null || this.keymap == null)
			return orig;
		Map<String, String[]> retval = new HashMap<String, String[]>();
		for (Entry<String, String> entry : keymap.entrySet()) {
			if(orig.containsKey(entry.getValue()))
				retval.put(entry.getKey(), orig.remove(entry.getValue()));
		}
		retval.putAll(orig);
		return retval;
	}

	@Override
	public Enumeration<String> getOriginalParameterNames() {
		return Collections.enumeration(getOriginalParameterMap().keySet());
	}

	@Override
	public String[] getOriginalParameterValues(String name) {
		return super.getOriginalParameterValues(getRealName(name));
	}

	public String getRealName(String name) {
		return keymap.containsKey(name) ? keymap.get(name) != null ? keymap.get(name) : name : name;
	}
}
