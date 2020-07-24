/**
 * 
 */
package com.rabobank.loaninformation.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabobank.loaninformation.model.LoanInformation;
import com.rabobank.loaninformation.repository.LoanInformationRepository;
import com.rabobank.loaninformation.requestdto.LoanInformationRequest;
import com.rabobank.userInformation.exceptions.LoanInformationNotFoundException;
import com.rabobank.userInformation.exceptions.LoanNumberAlreadyExixtsException;


/**
 * @author Admin
 *
 */
@Service
public class LoanInformationServiceImpl implements LoanInformationService {
	
	
	@Autowired
	LoanInformationRepository loanInformationRepository;
	
	public void addLoanInformation(LoanInformationRequest addLoanInformationRequest) throws LoanNumberAlreadyExixtsException {
		
		if(!loanInformationRepository.existsByLoanNumber(addLoanInformationRequest.getLoanNumber())) {
			LoanInformation loanInformation= new LoanInformation(addLoanInformationRequest.getLoanUserEmail(),addLoanInformationRequest.getLoanNumber(), addLoanInformationRequest.getLoanAmount(), addLoanInformationRequest.getLoanTerm(), addLoanInformationRequest.getLoanStatus(), addLoanInformationRequest.getLoanMgtFees(),
					addLoanInformationRequest.getOriginationAccount(), addLoanInformationRequest.getOriginationDate());
			loanInformationRepository.save(loanInformation);
		} else {
			 throw new LoanNumberAlreadyExixtsException("Loan information already exists for loan Number :"+addLoanInformationRequest.getLoanNumber());
		}
	}

	@Override
	public void updateLoanInformation(LoanInformationRequest loanInformationRequest) throws LoanInformationNotFoundException{
		Optional<LoanInformation> loanInfo= loanInformationRepository.findByLoanNumber(loanInformationRequest.getLoanNumber());
		LoanInformation loanInformation =null;
		if(loanInfo.isPresent()) {
			 loanInformation =loanInfo.get();
		} else {
			throw new LoanInformationNotFoundException("Loan Information not found for Loan Number"+loanInformationRequest.getLoanNumber());
		}
		loanInformation.setLoanUserEmail(loanInformationRequest.getLoanUserEmail());
		loanInformation.setLoanAmount(loanInformationRequest.getLoanAmount());
		loanInformation.setLoanNumber(loanInformationRequest.getLoanNumber());
		loanInformation.setLoanTerm(loanInformationRequest.getLoanTerm());
		loanInformation.setLoanStatus(loanInformationRequest.getLoanStatus());
		loanInformation.setLoanMgtFees(loanInformationRequest.getLoanMgtFees());
		loanInformation.setOriginationAccount(loanInformationRequest.getOriginationAccount());
		
	}

	@Override
	public LoanInformation findLoanInfoByLoanNum(String loanNumber) throws LoanInformationNotFoundException {
		LoanInformation loanInfo=loanInformationRepository.findByLoanNumber(loanNumber).
				orElseThrow(()-> new LoanInformationNotFoundException("Loan Information not found for Loan Number :"+loanNumber));
		return  loanInfo;
	}

	@Override
	public List<LoanInformation> findLoanInfoByLoanUserEmail(String loanUserEmail)
			throws LoanInformationNotFoundException {
		List<LoanInformation> loanInfo=loanInformationRepository.findByLoanUserEmail(loanUserEmail).
				orElseThrow(()-> new LoanInformationNotFoundException("Loan Information not found for Email :"+loanUserEmail));
		return loanInfo;
	}

}
