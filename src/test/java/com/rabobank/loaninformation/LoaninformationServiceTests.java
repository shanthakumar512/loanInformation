package com.rabobank.loaninformation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.rabobank.loaninformation.exceptions.LoanInformationNotFoundException;
import com.rabobank.loaninformation.exceptions.LoanNumberAlreadyExixtsException;
import com.rabobank.loaninformation.model.LoanInformation;
import com.rabobank.loaninformation.repository.LoanInformationRepository;
import com.rabobank.loaninformation.requestdto.LoanInformationRequest;
import com.rabobank.loaninformation.services.LoanInformationServiceImpl;

@SpringBootTest(classes = {LoaninformationApplication.class, H2JpaConfig.class })
@ActiveProfiles("test")
class LoaninformationServiceTests {
	
	@Autowired
	LoanInformationRepository loanInformationRepository;
	
	@Autowired
	LoanInformationServiceImpl loanInformationservice;
	
	private String email="abc@gmail.c";
	
	private String loanNUmber="ABC12345";
	
	private String active="ACTIVE";
	
	private String originAccount="ACB123476";

	private String loannum;
	
	
	@Test
	@Rollback(false)
	void addLoanInfoTest( ) throws LoanNumberAlreadyExixtsException {
		final LoanInformationRequest loanInformation = new LoanInformationRequest();
		loanInformation.setLoanUserEmail(email);
		loanInformation.setLoanAmount(1234568);
		loanInformation.setLoanNumber("LOAN895");
		loanInformation.setLoanTerm(45);
		loanInformation.setLoanStatus(active);
		loanInformation.setLoanMgtFees(7895);
		loanInformation.setOriginationAccount(originAccount);
		loanInformationservice.addLoanInformation(loanInformation);
	}

	@Test
	@Rollback(false)
	final void testaddloaninfoWithAlreadyExisingLoanNum() throws LoanNumberAlreadyExixtsException{
		
		LoanInformationRequest loanInformation1 = new LoanInformationRequest();
		loanInformation1.setLoanUserEmail(email);
		loanInformation1.setLoanAmount(1234568);
		loanInformation1.setLoanNumber(loanNUmber);
		loanInformation1.setLoanTerm(45);
		loanInformation1.setLoanStatus(active);
		loanInformation1.setLoanMgtFees(7895);
		loanInformation1.setOriginationAccount(originAccount);
		loanInformationservice.addLoanInformation(loanInformation1);
		Assertions.assertThrows(LoanNumberAlreadyExixtsException.class,()->loanInformationservice.addLoanInformation(loanInformation1));
	}
	
	@Test()
	@Rollback(false)
	final void testUpdateloaninfo() throws LoanNumberAlreadyExixtsException, LoanInformationNotFoundException {
		
		final LoanInformationRequest loanInformation = new LoanInformationRequest();
		loanInformation.setLoanUserEmail(email);
		loanInformation.setLoanAmount(1234568);
		loanInformation.setLoanNumber("ABC123");
		loanInformation.setLoanTerm(45);
		loanInformation.setLoanStatus(active);
		loanInformation.setLoanMgtFees(7895);
		loanInformation.setOriginationAccount(originAccount);
		loanInformationservice.addLoanInformation(loanInformation);

		LoanInformationRequest loanInformation1 = new LoanInformationRequest();
		loanInformation1.setLoanUserEmail(email);
		loanInformation1.setLoanAmount(1234568);
		loanInformation1.setLoanNumber("ABC123");
		loanInformation1.setLoanTerm(45);
		loanInformation1.setLoanStatus(active);
		loanInformation1.setLoanMgtFees(7895);
		loanInformation1.setOriginationAccount("ACB1234");
		loanInformationservice.updateLoanInformation(loanInformation1);
	}
	
	@Test()
	@Rollback(false)
	final void testforGivenLoanNum() {

		LoanInformationRequest loanInformation1 = new LoanInformationRequest();
		loanInformation1.setLoanUserEmail(email);
		loanInformation1.setLoanAmount(1234568);
		loanInformation1.setLoanNumber("ABC123435436");
		loanInformation1.setLoanTerm(45);
		loanInformation1.setLoanStatus(active);
		loanInformation1.setLoanMgtFees(7895);
		loanInformation1.setOriginationAccount("ACB1234");
		Assertions.assertThrows(LoanInformationNotFoundException.class,()->loanInformationservice.updateLoanInformation(loanInformation1));	
	}
	
	@Test
	@Rollback(false)
	void findByLoanNumTest() throws LoanInformationNotFoundException  {
		LoanInformation loanInfo = loanInformationservice.findLoanInfoByLoanNum(loanNUmber);
		 assertEquals(loanInfo.getLoanNumber(), loanNUmber);
		 List<LoanInformation> loanInfoEmail = loanInformationservice.findLoanInfoByLoanUserEmail(email);
		 assertNotNull(loanInfoEmail.size());
	}
	
	@Test
	@Rollback(false)
	void findByNotExistingLoanNumTest() {
		Assertions.assertThrows(LoanInformationNotFoundException.class, ()->loanInformationservice.findLoanInfoByLoanNum("LOAN1RBL2"));
	}
	
	@Test
	@Rollback(false)
	void findByExistingLoanNumTest() {
		Assertions.assertThrows(LoanInformationNotFoundException.class, ()->loanInformationservice.findLoanInfoByLoanNum(loannum));
	}
	
	
	@Test
	@Rollback(false)
	void findByLastNameTest() throws LoanInformationNotFoundException {
		List<LoanInformation> loanInfolist = loanInformationservice.findLoanInfoByLoanUserEmail(email);
		
		 assertEquals(loanInfolist.size(), loanInformationRepository.findByLoanUserEmail(email).get().size());
	}
	
	
	
}
