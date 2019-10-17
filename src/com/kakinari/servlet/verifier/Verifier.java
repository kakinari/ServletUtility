package com.kakinari.servlet.verifier;

import com.kakinari.servlet.exception.IllegalParameterException;

public class Verifier {
	private int value = -1;
	private String[]  name;

	public Verifier(String key, String[] name) throws IllegalParameterException {
		if (key == null)
			throw new IllegalParameterException();
		try {
			this.name = name;
			int p = Integer.parseInt(key);
			if (p < 0 || p > name.length)
				throw new IllegalParameterException();	
			this.value = p;
		} catch (NumberFormatException e) {
			for (int i = 0; i<name.length; i++) {
				if (name[i].equals(key)) {
					this.value = i;
					break;
				}
			}
			if (this.value == -1)
				throw new IllegalParameterException();
		}
	}
	public int getValue() {
		return this.value;
	}
	public String getName() {
		return name[this.value];
	}
}
