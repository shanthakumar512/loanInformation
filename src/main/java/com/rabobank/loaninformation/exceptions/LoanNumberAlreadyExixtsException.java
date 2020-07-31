/**
 * 
 */
package com.rabobank.loaninformation.exceptions;

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
}
