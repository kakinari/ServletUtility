/**
 * 
 */
package com.kakinari.servlet.wrapper;

import java.util.ArrayList;
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
		String rname[] = getRealName(name).split(":");
		String val = super.getParameter(rname[0]);
		if (val == null) return null;
		switch (rname.length) {
		case 1:
			return val;
		case 2:
			return val.substring(Integer.valueOf(rname[1]));
		case 3:
			return val.substring(Integer.valueOf(rname[1]), Integer.valueOf(rname[2]));
		default:
			return null;
		}
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> orig = super.getParameterMap();
		if (orig == null || this.keymap == null)
			return orig;
		Map<String, String[]> retval = new HashMap<String, String[]>();
		ArrayList<String> rlist = new ArrayList<String>();
		for (Entry<String, String> entry : keymap.entrySet()) {
			String key = entry.getValue();
			if (key != null && key.contains(":"))
				key = key.split(":")[0];
			if(orig.containsKey(key)) {
				if (key.equals(entry.getValue())) {
					retval.put(entry.getKey(), orig.remove(entry.getValue()));
				} else {
					if (! rlist.contains(key))
						rlist.add(key);
					retval.put(entry.getKey(), orig.get(entry.getValue()));
				}
			}
		}
		for (String k : rlist)
			orig.remove(k);
		retval.putAll(orig);
		return retval;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(getParameterMap().keySet());
	}

	@Override
	public String[] getParameterValues(String name) {
		String rname[] = getRealName(name).split(":");
		String[] vals = super.getParameterValues(rname[0]);
		if (vals == null) return null;
		ArrayList<String> ret = new ArrayList<String>();
		switch (rname.length) {
		case 1:
			return vals;
		case 2:
			for (String val : vals)
				ret.add(val.substring(Integer.valueOf(rname[1])));
			return ret.toArray(new String[] {});
		case 3:
			for (String val : vals)
				ret.add(val.substring(Integer.valueOf(rname[1]), Integer.valueOf(rname[2])));
			return ret.toArray(new String[] {});
		default:
			return null;
		}
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
