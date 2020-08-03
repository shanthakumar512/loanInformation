/**
 * 
 */
package com.rabobank.loaninformation.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabobank.loaninformation.exceptions.LoanInformationNotFoundException;
import com.rabobank.loaninformation.exceptions.LoanNumberAlreadyExixtsException;
import com.rabobank.loaninformation.model.LoanInformation;
import com.rabobank.loaninformation.repository.LoanInformationRepository;
import com.rabobank.loaninformation.requestdto.LoanInformationRequest;


/**
 * @author Admin
 *
 */
@Service
public class LoanInformationServiceImpl implements LoanInformationService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoanInformationServiceImpl.class);
	
	@Autowired
	LoanInformationRepository loanInformationRepository;
	
	public LoanInformation addLoanInformation(LoanInformationRequest addLoanInformationRequest) throws LoanNumberAlreadyExixtsException {
		logger.debug("Entered addLoanInformation() method {} ", addLoanInformationRequest);
		if(Boolean.FALSE.equals(loanInformationRepository.existsByLoanNumber(addLoanInformationRequest.getLoanNumber()))) {
			logger.info("Loan Information not already exixts in LoanRepository  for LoanNumber:{} ", addLoanInformationRequest.getLoanNumber());
			LoanInformation loanInformation= new LoanInformation(addLoanInformationRequest.getLoanUserEmail(),addLoanInformationRequest.getLoanNumber(), addLoanInformationRequest.getLoanAmount(), addLoanInformationRequest.getLoanTerm(), addLoanInformationRequest.getLoanStatus(), addLoanInformationRequest.getLoanMgtFees(),
					addLoanInformationRequest.getOriginationAccount());
			loanInformationRepository.save(loanInformation);
			return loanInformation;
		} else {
			 throw new LoanNumberAlreadyExixtsException("Loan information already exists for loan Number :"+addLoanInformationRequest.getLoanNumber());
		}
		
	}

	@Override
	public LoanInformation updateLoanInformation(LoanInformationRequest loanInformationRequest) throws LoanInformationNotFoundException{
		logger.info("Entered updateLoanInformation() method {} ", loanInformationRequest);

		LoanInformation loanInformation = loanInformationRepository.findByLoanNumber(loanInformationRequest.getLoanNumber()).
				orElseThrow(()->new LoanInformationNotFoundException("Loan Information not found for Loan Number"+loanInformationRequest.getLoanNumber()));
	
		loanInformation.setLoanUserEmail(loanInformationRequest.getLoanUserEmail());
		loanInformation.setLoanAmount(loanInformationRequest.getLoanAmount());
		loanInformation.setLoanNumber(loanInformationRequest.getLoanNumber());
		loanInformation.setLoanTerm(loanInformationRequest.getLoanTerm());
		loanInformation.setLoanStatus(loanInformationRequest.getLoanStatus());
		loanInformation.setLoanMgtFees(loanInformationRequest.getLoanMgtFees());
		loanInformation.setOriginationAccount(loanInformationRequest.getOriginationAccount());
		loanInformationRepository.save(loanInformation);
		return loanInformation;
		
	}

	@Override
	public LoanInformation findLoanInfoByLoanNum(String loanNumber) throws LoanInformationNotFoundException {
		logger.debug("Entered findLoanInfoByLoanNum method {}", loanNumber);
		LoanInformation loanInfo=loanInformationRepository.findByLoanNumber(loanNumber).
				orElseThrow(()-> new LoanInformationNotFoundException("Loan Information not found for Loan Number :"+loanNumber));
		logger.info("Search result for LoanNumber {}", loanInfo.getLoanUserEmail());
		return  loanInfo;
	}

	@Override
	public List<LoanInformation> findLoanInfoByLoanUserEmail(String loanUserEmail)
			throws LoanInformationNotFoundException {
		logger.info("Entered findLoanInfoByLoanNum method {}", loanUserEmail);
		List<LoanInformation> loanInfo=loanInformationRepository.findByLoanUserEmail(loanUserEmail).
				orElseThrow(()-> new LoanInformationNotFoundException("Loan Information not found for Email :"+loanUserEmail));
		logger.info("The size of LoanInformation result for LoanNumber {}", loanInfo.size());
		return loanInfo;
	}

}
