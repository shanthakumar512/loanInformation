/**
 * 
 */
package com.rabobank.userInformation.exceptions;

/**
 * @author Admin
 *
 */
public class LoanInformationNotFoundException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3765418383626470747L;

	/**
	 * @param message
	 */
	public LoanInformationNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public LoanInformationNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LoanInformationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public LoanInformationNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
