/**
 * 
 */
package com.rabobank.userInformation.exceptions;

/**
 * @author Shanthakumar
 *
 */
public class LoanNumberAlreadyExixtsException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public LoanNumberAlreadyExixtsException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public LoanNumberAlreadyExixtsException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LoanNumberAlreadyExixtsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public LoanNumberAlreadyExixtsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
