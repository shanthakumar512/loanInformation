package com.rabobank.loaninformation.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.loaninformation.model.LoanInformation;
import com.rabobank.loaninformation.requestdto.LoanInformationRequest;
import com.rabobank.loaninformation.services.LoanInformationService;
import com.rabobank.userInformation.exceptions.LoanInformationNotFoundException;
import com.rabobank.userInformation.exceptions.LoanNumberAlreadyExixtsException;


@RestController
@RequestMapping("/loanInfo")
public class LoanInformationController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoanInformationController.class);

	@Autowired
	LoanInformationService loanInformationService;


	@PostMapping("/addLoanInfo")
	public ResponseEntity<List<LoanInformation>> addLoanInformation(@Valid @RequestBody LoanInformationRequest loanInformationRequest) throws LoanNumberAlreadyExixtsException, LoanInformationNotFoundException {
		logger.info("Entered addLoanInformation method {}", loanInformationRequest);
		loanInformationService.addLoanInformation(loanInformationRequest);
		logger.info("Loan Information entered successfully {}", loanInformationRequest.getLoanNumber());
		List<LoanInformation> loanInformationList =	loanInformationService.findLoanInfoByLoanUserEmail(loanInformationRequest.getLoanUserEmail());
		return  new ResponseEntity<>(loanInformationList, HttpStatus.OK);
	}
	
	@PostMapping("/updateLoanInfo")
	public ResponseEntity<LoanInformation> updateLoanInformation(@Valid @RequestBody LoanInformationRequest loanInformationRequest) throws LoanInformationNotFoundException {	
		logger.info("Entered updateLoanInformation method {}", loanInformationRequest);
		LoanInformation loanInfo=loanInformationService.updateLoanInformation(loanInformationRequest);
		logger.info("Updated Loan Information successfully : {}", loanInformationRequest);
		return  new ResponseEntity<>(loanInfo, HttpStatus.OK);
	}
	
	@GetMapping("/getLoanInfo/{loanNumber}")
	public ResponseEntity<LoanInformation> getLoanInfoByLoanNumber(@PathVariable String loanNumber) throws LoanInformationNotFoundException{
		logger.info("Entered getLoanInfoByLoanNumber method {}", loanNumber);
		LoanInformation loanInfo= loanInformationService.findLoanInfoByLoanNum(loanNumber);
		logger.info("Loan Infomation returned {}", loanInfo.getLoanNumber());
		return new ResponseEntity<>(loanInfo, HttpStatus.OK);
	}
	
	@GetMapping("/getLoanInfoByEmail/{loanUserEmail}")
	public ResponseEntity<List<LoanInformation>> getLoanInfoByLoanUserEmail(@PathVariable String loanUserEmail) throws LoanInformationNotFoundException{
		logger.info("Entered getLoanInfoByLoanUserEmail method {}", loanUserEmail);
		List<LoanInformation> loanInfo= loanInformationService.findLoanInfoByLoanUserEmail(loanUserEmail);
		logger.info("The size of Loan information returned for given Loan User email{}", loanInfo.size());
		return new ResponseEntity<>(loanInfo, HttpStatus.OK);
	}
	
}
