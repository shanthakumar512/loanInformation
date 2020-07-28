/**
 * 
 */
package com.rabobank.loaninformation.services;

import java.util.List;

import com.rabobank.loaninformation.model.LoanInformation;
import com.rabobank.loaninformation.requestdto.LoanInformationRequest;
import com.rabobank.userinformation.exceptions.LoanInformationNotFoundException;
import com.rabobank.userinformation.exceptions.LoanNumberAlreadyExixtsException;


/**
 * @author Admin
 *
 */
public interface LoanInformationService {
	
	public LoanInformation addLoanInformation(LoanInformationRequest loanInformationRequest) throws LoanNumberAlreadyExixtsException;
	
	public LoanInformation updateLoanInformation(LoanInformationRequest loanInformationRequest) throws LoanInformationNotFoundException;
	
	public LoanInformation findLoanInfoByLoanNum(String loanNumber) throws LoanInformationNotFoundException;
	
	public List<LoanInformation> findLoanInfoByLoanUserEmail(String loanUserEmail) throws LoanInformationNotFoundException;
	
}
