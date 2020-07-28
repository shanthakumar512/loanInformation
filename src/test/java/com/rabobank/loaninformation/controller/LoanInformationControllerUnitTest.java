/**
 * 
 */
package com.rabobank.loaninformation.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.rabobank.loaninformation.H2JpaConfig;
import com.rabobank.loaninformation.LoaninformationApplication;
import com.rabobank.loaninformation.model.LoanInformation;
import com.rabobank.loaninformation.repository.LoanInformationRepository;
import com.rabobank.loaninformation.requestdto.LoanInformationRequest;
import com.rabobank.loaninformation.services.LoanInformationServiceImpl;
import com.rabobank.userinformation.exceptions.LoanInformationNotFoundException;
import com.rabobank.userinformation.exceptions.LoanNumberAlreadyExixtsException;

/**
 * @author Shanthakumar
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LoaninformationApplication.class, H2JpaConfig.class },
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.DEFAULT)
public class LoanInformationControllerUnitTest {
	
		private String loanNumber="ABC123454";
	
		private String active="ACTIVE";
	
		private String originAccount="ACB123476";

		@Autowired
		private TestRestTemplate restTemplate;
		
		@Autowired
		LoanInformationRepository loanInformationRepository;
		
		@Autowired
		LoanInformationServiceImpl loanInformationservice;

		@LocalServerPort
		private int port;

		private String getRootUrl() {
			return "http://localhost:" + port;
		}
		@Test
		@Rollback(false)
		public void testCreateLoanInfo() {
			LoanInformationRequest loanInformation1 = new LoanInformationRequest();
			loanInformation1.setLoanUserEmail("abc@gmail.c");
			loanInformation1.setLoanAmount(1234568);
			loanInformation1.setLoanNumber(loanNumber);
			loanInformation1.setLoanTerm(45);
			loanInformation1.setLoanStatus(active);
			loanInformation1.setLoanMgtFees(7895);
			loanInformation1.setOriginationAccount("ACB1235R5");
			
			ResponseEntity<LoanInformationRequest[]> postResponse = restTemplate.postForEntity(getRootUrl() + "/loanInfo/addLoanInfo", loanInformation1, LoanInformationRequest[].class);
			
			assertNotNull(postResponse);
			assertNotNull(postResponse.getBody());
		}

		@Test
		@Rollback(false)
		public void testUpdateLoanInfo() {
			LoanInformationRequest loanInformation1 = new LoanInformationRequest();
			loanInformation1.setLoanUserEmail("abc@gmail.c");
			loanInformation1.setLoanAmount(1234568);
			loanInformation1.setLoanNumber("ABC1234S");
			loanInformation1.setLoanTerm(45);
			loanInformation1.setLoanStatus(active);
			loanInformation1.setLoanMgtFees(7895);
			loanInformation1.setOriginationAccount("ACB1235R5");
			
			
			ResponseEntity<?> updatedEmployee = restTemplate.postForEntity(getRootUrl() + "/loanInfo/updateLoanInfo", loanInformation1, LoanInformationRequest.class);
			assertNotNull(updatedEmployee);
		}
		@Test
		@Rollback(false)
		public void testsGetLoanInfrmationFromLoanNumber() {
			 Map<String, String> uriVariables = new HashMap<>();
			    uriVariables.put("loanNumber", loanNumber);

			ResponseEntity<LoanInformationRequest> response = restTemplate.getForEntity(getRootUrl() + "loanInfo/getLoanInfo/{loanNumber}", LoanInformationRequest.class,
			        uriVariables);
			
			assertNotNull(response.getBody());
		}
		
		@Test
		@Rollback(false)
	public void testsGetLoanInfrmationFromEmail() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(null, headers);
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("loanNumber", loanNumber);
		uriVariables.put("loanUserEmail", "abc@gmail.c");
		
		ResponseEntity<LoanInformationRequest> responseLoanNum = restTemplate.getForEntity(getRootUrl() + "loanInfo/getLoanInfo/{loanNumber}", LoanInformationRequest.class,
		        uriVariables);
		assertNotNull(responseLoanNum.getBody());
		ResponseEntity<LoanInformationRequest[]> responseEmail = restTemplate.exchange(getRootUrl() + "loanInfo/getLoanInfoByEmail/{loanUserEmail}", HttpMethod.GET, entity, LoanInformationRequest[].class, uriVariables);
		assertNotNull(responseEmail.getBody());
		}
		
		
		@Test
		@Rollback(false)
		final void testaddloaninfo() throws LoanNumberAlreadyExixtsException {
			final LoanInformationRequest loanInformation = new LoanInformationRequest();
			loanInformation.setLoanUserEmail("abc@gmail.c");
			loanInformation.setLoanAmount(1234568);
			loanInformation.setLoanNumber("ABC12345");
			loanInformation.setLoanTerm(45);
			loanInformation.setLoanStatus(active);
			loanInformation.setLoanMgtFees(7895);
			loanInformation.setOriginationAccount(originAccount);
			loanInformationservice.addLoanInformation(loanInformation);
			
			LoanInformationRequest loanInformation1 = new LoanInformationRequest();
			loanInformation1.setLoanUserEmail("abc@gmail.c");
			loanInformation1.setLoanAmount(1234568);
			loanInformation1.setLoanNumber("ABC12345");
			loanInformation1.setLoanTerm(45);
			loanInformation1.setLoanStatus(active);
			loanInformation1.setLoanMgtFees(7895);
			loanInformation1.setOriginationAccount(originAccount);
			Assertions.assertThrows(LoanNumberAlreadyExixtsException.class,()->loanInformationservice.addLoanInformation(loanInformation1));
		}
		
		@Test()
		@Rollback(false)
		final void testaddloaninfoWithAlreadyExisingLoanNUm() {

			LoanInformationRequest loanInformation1 = new LoanInformationRequest();
			loanInformation1.setLoanUserEmail("abc@gmail.c");
			loanInformation1.setLoanAmount(1234568);
			loanInformation1.setLoanNumber(originAccount);
			loanInformation1.setLoanTerm(45);
			loanInformation1.setLoanStatus(active);
			loanInformation1.setLoanMgtFees(7895);
			loanInformation1.setOriginationAccount("ACB1234");
			Assertions.assertThrows(LoanInformationNotFoundException.class,()->loanInformationservice.updateLoanInformation(loanInformation1));		 	
		}
		
		@Test()
		@Rollback(false)
		final void testforGivenLoanNum() throws LoanNumberAlreadyExixtsException {

			LoanInformationRequest loanInformation1 = new LoanInformationRequest();
			loanInformation1.setLoanUserEmail("abc@gmail.c");
			loanInformation1.setLoanAmount(1234568);
			loanInformation1.setLoanNumber("ABC1234");
			loanInformation1.setLoanTerm(45);
			loanInformation1.setLoanStatus(active);
			loanInformation1.setLoanMgtFees(7895);
			loanInformation1.setOriginationAccount("ACB1234");
			loanInformationservice.addLoanInformation(loanInformation1);
		}
		
		@Test
		public void givenEmptyDBWhenFindOneByNameThenReturnEmptyOptional() {
			Optional<LoanInformation> foundloanInfo = loanInformationRepository.findByLoanNumber("ABC");

			assertFalse(foundloanInfo.isPresent());
		}

		@Test
		public void givenEmptyDBWhenFindByLoanNumThenReturnFalse() {
			assertFalse(loanInformationRepository.existsByLoanNumber("ABC"));
		}

		@Test
		public void givenEmptyDBWhenFindOneByNameThenReturnOptional() {

			LoanInformation loaninfo = new LoanInformation("abc@g.m", "LAN12374", 35356436, 33, active, 346, "2535", null);
			loanInformationRepository.save(loaninfo);
			Optional<LoanInformation> foundloanInfo = loanInformationRepository.findByLoanNumber("LAN12374");
			assertTrue(foundloanInfo.isPresent());
		}

		@Test
		public void givenEmptyDBWhenFindOneByNameThenReturnTrue() {
			Optional<LoanInformation> foundloanInfo = loanInformationRepository.findByLoanNumber("LAN12323");
			assertFalse(foundloanInfo.isPresent());
		}

	}

