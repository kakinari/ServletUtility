package com.kakinari.servlet.wrapper;

import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class LocalParam extends TreeMap<String, Params> {
	private static final long serialVersionUID = 1979763167903081057L;
	
	public LocalParam() {
		super();
	}

	public LocalParam(Comparator<? super String> comparator) {
		super(comparator);
	}

	public LocalParam(Map<? extends String, ? extends Params> m) {
		super(m);
	}

	public LocalParam(SortedMap<String, ? extends Params> m) {
		super(m);
	}

	public boolean has(String name) {
		return containsKey(name);
	}

	public String getFirst(String name) {
		return has(name) ? get(name).getFirst() : null;
	}

	public Map<String, String[]> getParameterMap(Map<String, String[]> parameterMap) {
		HashMap<String, String[]> ret = new HashMap<String,String[]>(parameterMap);
		for(String key : keySet())
			ret.put(key, ((Params)get(key)).toArray(new String[0]));
		return ret;
	}

	public Enumeration<String> getParameterNames(Map<String, String[]> parameterMap) {
		return Collections.enumeration(getParameterMap(parameterMap).keySet());
	}

	public void setParameter(String name, String value) {
		Params param = remove(name);
		if (param == null)
			param = new Params(value);
		put(name, param);
	}

	public void setParameterValues(String name, String[] value) {
		Params param = remove(name);
		if (param == null)
			param = new Params(value);
		put(name, param);
	}

	public void addParameter(String name, String value) {
		Params param = remove(name);
		if (param == null)
			param = new Params(value);
		else
			param.add(value);
		put(name, param);
	}

	public void addParameterValues(String name, String[] value) {
		Params param = remove(name);
		if (param == null)
			param = new Params(value);
		put(name, param);
	}
}
