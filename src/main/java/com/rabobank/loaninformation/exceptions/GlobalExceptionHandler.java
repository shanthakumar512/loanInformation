package com.rabobank.loaninformation.exceptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.rabobank.loaninformation.model.ErrorResponse;


/**
 * 
 * @author Shanthakumar
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler   extends ResponseEntityExceptionHandler{
	Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(LoanInformationNotFoundException.class)
	public @ResponseBody ResponseEntity<ErrorResponse> handleFileException( LoanInformationNotFoundException exception) {
    	 List<String> details = new ArrayList<>();
         details.add(exception.getLocalizedMessage());
    	ErrorResponse errorResponse = new ErrorResponse("Loan Information not found for Loan Number", details);
    	
		log.error("LoanInformationNotFoundException: {}", details);
		return ResponseEntity.badRequest().body(errorResponse);
	}
    
    @ExceptionHandler(LoanNumberAlreadyExixtsException.class)
   	public @ResponseBody ResponseEntity<ErrorResponse> handleFileException( LoanNumberAlreadyExixtsException exception) {
       	 List<String> details = new ArrayList<>();
            details.add(exception.getLocalizedMessage());
       	ErrorResponse errorResponse = new ErrorResponse("Loan information already exists for loan Number", details);
       	
   		log.error("Loan information already exists for loan Number: {}", details);
   		return ResponseEntity.badRequest().body(errorResponse);
   	}
    
    @ResponseStatus()
   	public final @ResponseBody  ResponseEntity<ErrorResponse> handleResponseStatusException(final ResponseStatusException exception,
   			final HttpServletRequest request) {
       	
       	 List<String> details = new ArrayList<>();
            details.add(exception.getLocalizedMessage());
            ErrorResponse error = new ErrorResponse(exception.getStatus().toString(), details);
            log.error(exception.getLocalizedMessage());
            return new ResponseEntity<>(error, exception.getStatus());
   	}    
       
}