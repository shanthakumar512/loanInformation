package com.rabobank.loaninformation.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/loanInfo")
public class LoanInformationController {

	@Autowired
	LoanInformationService loanInformationService;


	@PostMapping("/addLoanInfo")
	public ResponseEntity<?> addLoanInformation(@Valid @RequestBody LoanInformationRequest loanInformationRequest) throws LoanNumberAlreadyExixtsException {
		loanInformationService.addLoanInformation(loanInformationRequest);
		return  new ResponseEntity<String>("Loan Information Added Successfully", HttpStatus.OK);
	}
	
	@PostMapping("/updateLoanInfo")
	public ResponseEntity<?> updateLoanInformation(@Valid @RequestBody LoanInformationRequest loanInformationRequest) throws LoanInformationNotFoundException {	
		loanInformationService.updateLoanInformation(loanInformationRequest);
		return  new ResponseEntity<String>("Loan Information updated Successfully", HttpStatus.OK);
	}
	
	@GetMapping("/getLoanInfo/{loanNumber}")
	public ResponseEntity<?> getLoanInfoByLoanNumber(@PathVariable String loanNumber) throws LoanInformationNotFoundException{
		LoanInformation loanInfo= loanInformationService.findLoanInfoByLoanNum(loanNumber);
		return new ResponseEntity<LoanInformation>(loanInfo, HttpStatus.OK);
	}
	
	@GetMapping("/getLoanInfoByEmail/{loanUserEmail}")
	public ResponseEntity<?> getLoanInfoByLoanUserEmail(@PathVariable String loanUserEmail) throws LoanInformationNotFoundException{
		List<LoanInformation> loanInfo= loanInformationService.findLoanInfoByLoanUserEmail(loanUserEmail);
		return new ResponseEntity<List<LoanInformation>>(loanInfo, HttpStatus.OK);
	}
	
}
