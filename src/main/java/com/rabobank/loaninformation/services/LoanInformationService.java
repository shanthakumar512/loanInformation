/**
 * 
 */
package com.rabobank.loaninformation.services;

import java.util.List;

import com.rabobank.loaninformation.exceptions.LoanInformationNotFoundException;
import com.rabobank.loaninformation.exceptions.LoanNumberAlreadyExixtsException;
import com.rabobank.loaninformation.model.LoanInformation;
import com.rabobank.loaninformation.requestdto.LoanInformationRequest;


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
