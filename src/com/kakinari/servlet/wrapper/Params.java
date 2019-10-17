/**
 * 
 */
package com.kakinari.servlet.wrapper;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Takashi Kakinari
 *
 */
public class Params extends ArrayList<String> {
	private static final long serialVersionUID = -9065013142287151504L;

	/**
	 * 
	 */
	public Params() {
	}

	/**
	 * @param initialCapacity
	 */
	public Params(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * @param String[]
	 */
	public Params(String data) {
		super();
			add(data);
	}

	/**
	 * @param String[]
	 */
	public Params(String[] data) {
		super();
		for(String d : data)
			add(d);
	}

	/**
	 * @param c
	 */
	public Params(Collection<? extends String> c) {
		super(c);
	}

	public String getFirst() {
		return size() == 0 ? null : get(0);
	}

	@Override
	public String[] toArray() {
		return super.toArray(new String[0]);
	}

	public void addAll(String[] data) {
		for(String d : data)
			add(d);
	}
}
