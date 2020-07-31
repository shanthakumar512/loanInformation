package com.rabobank.loaninformation.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.loaninformation.exceptions.LoanInformationNotFoundException;
import com.rabobank.loaninformation.exceptions.LoanNumberAlreadyExixtsException;
import com.rabobank.loaninformation.model.LoanInformation;
import com.rabobank.loaninformation.requestdto.LoanInformationRequest;
import com.rabobank.loaninformation.services.LoanInformationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/loanInfo")
public class LoanInformationController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoanInformationController.class);

	@Autowired
	LoanInformationService loanInformationService;
	
	@ApiOperation(value = "Add Loan information the System ", response = LoanInformation.class, tags = "addLoanInformation")
	@ApiResponses(value = { 
	            @ApiResponse(code = 200, message = "Success| Loan Information added successfully"),
	            @ApiResponse(code = 401, message = "not authorized!"), 
	            @ApiResponse(code = 403, message = "forbidden!!!"),
	            @ApiResponse(code = 404, message = "not found!!!") })

	@PostMapping("/addLoanInfo")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<LoanInformation>> addLoanInformation(@Valid @RequestBody LoanInformationRequest loanInformationRequest) throws LoanNumberAlreadyExixtsException, LoanInformationNotFoundException {
		logger.info("Entered addLoanInformation method {}", loanInformationRequest);
		loanInformationService.addLoanInformation(loanInformationRequest);
		logger.info("Loan Information entered successfully {}", loanInformationRequest.getLoanNumber());
		List<LoanInformation> loanInformationList =	loanInformationService.findLoanInfoByLoanUserEmail(loanInformationRequest.getLoanUserEmail());
		return  new ResponseEntity<>(loanInformationList, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Update the loan information in the System ", response = LoanInformation.class, tags = "updateLoanInformation")
	@ApiResponses(value = { 
	            @ApiResponse(code = 200, message = "Success|OK"),
	            @ApiResponse(code = 401, message = "not authorized!"), 
	            @ApiResponse(code = 403, message = "forbidden!!!"),
	            @ApiResponse(code = 404, message = "not found!!!") })
	
	@PostMapping("/updateLoanInfo")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<LoanInformation> updateLoanInformation(@Valid @RequestBody LoanInformationRequest loanInformationRequest) throws LoanInformationNotFoundException {	
		logger.info("Entered updateLoanInformation method {}", loanInformationRequest);
		LoanInformation loanInfo=loanInformationService.updateLoanInformation(loanInformationRequest);
		logger.info("Updated Loan Information successfully : {}", loanInformationRequest);
		return  new ResponseEntity<>(loanInfo, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get the loan information for given Loan number from the System ", response = LoanInformation.class, tags = "getLoanInfoByLoanNumber")
	@ApiResponses(value = { 
	            @ApiResponse(code = 200, message = "Success|OK"),
	            @ApiResponse(code = 401, message = "not authorized!"), 
	            @ApiResponse(code = 403, message = "forbidden!!!"),
	            @ApiResponse(code = 404, message = "not found!!!") })
	
	@GetMapping("/getLoanInfo/{loanNumber}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<LoanInformation> getLoanInfoByLoanNumber(@PathVariable String loanNumber) throws LoanInformationNotFoundException{
		logger.info("Entered getLoanInfoByLoanNumber method {}", loanNumber);
		LoanInformation loanInfo= loanInformationService.findLoanInfoByLoanNum(loanNumber);
		logger.info("Loan Infomation returned {}", loanInfo.getLoanNumber());
		return new ResponseEntity<>(loanInfo, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get the loan information for given Loan number from the System ", response = LoanInformation.class, tags = "getLoanInfoByLoanUserEmail")
	@ApiResponses(value = { 
	            @ApiResponse(code = 200, message = "Success|OK"),
	            @ApiResponse(code = 401, message = "not authorized!"), 
	            @ApiResponse(code = 403, message = "forbidden!!!"),
	            @ApiResponse(code = 404, message = "not found!!!") })
	@GetMapping("/getLoanInfoByEmail/{loanUserEmail}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<LoanInformation>> getLoanInfoByLoanUserEmail(@PathVariable String loanUserEmail) throws LoanInformationNotFoundException{
		logger.info("Entered getLoanInfoByLoanUserEmail method {}", loanUserEmail);
		List<LoanInformation> loanInfo= loanInformationService.findLoanInfoByLoanUserEmail(loanUserEmail);
		logger.info("The size of Loan information returned for given Loan User email{}", loanInfo.size());
		return new ResponseEntity<>(loanInfo, HttpStatus.OK);
	}
	
}
