/**
 * 
 */
package com.kakinari.servlet.exception;

/**
 * @author tkakinari
 *
 */
public class NoTypeFunctionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NoTypeFunctionException() {
		super();
	}

	/**
	 * @param message
	 */
	public NoTypeFunctionException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NoTypeFunctionException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoTypeFunctionException(String message, Throwable cause) {
		super(message, cause);
	}

}
