package com.kakinari.servlet.exception;

public class IllegalParameterException extends Exception {

	private static final long serialVersionUID = -5584868182605043785L;

	public IllegalParameterException() {
		super();
	}
	public IllegalParameterException(String message) {
		super(message);
	}
	public IllegalParameterException(String message, Throwable cause) {
		super(message, cause);
	}
	public IllegalParameterException(Throwable cause) {
		super(cause);
	}
}
