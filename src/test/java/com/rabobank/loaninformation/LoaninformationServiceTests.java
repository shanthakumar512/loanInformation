package com.rabobank.loaninformation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.rabobank.loaninformation.model.LoanInformation;
import com.rabobank.loaninformation.repository.LoanInformationRepository;
import com.rabobank.loaninformation.requestdto.LoanInformationRequest;
import com.rabobank.loaninformation.services.LoanInformationServiceImpl;
import com.rabobank.userinformation.exceptions.LoanInformationNotFoundException;
import com.rabobank.userinformation.exceptions.LoanNumberAlreadyExixtsException;

@SpringBootTest(classes = {LoaninformationApplication.class, H2JpaConfig.class })
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class LoaninformationServiceTests {
	
	@Autowired
	LoanInformationRepository loanInformationRepository;
	
	@Autowired
	LoanInformationServiceImpl loanInformationservice;
	
	private String email="abc@gmail.c";
	
	private String loanNUmber="ABC12345";
	
	private String active="ACTIVE";
	
	private String originAccount="ACB123476";
	
	
	@Test
	@Rollback(false)
	void addLoanInfoTest( ) throws LoanNumberAlreadyExixtsException {
		
		final LoanInformationRequest loanInformation = new LoanInformationRequest();
		loanInformation.setLoanUserEmail(email);
		loanInformation.setLoanAmount(1234568);
		loanInformation.setLoanNumber(loanNUmber);
		loanInformation.setLoanTerm(45);
		loanInformation.setLoanStatus(active);
		loanInformation.setLoanMgtFees(7895);
		loanInformation.setOriginationAccount(originAccount);
		loanInformationservice.addLoanInformation(loanInformation);
	}

	@Test
	@Rollback(false)
	final void testaddloaninfoWithAlreadyExisingLoanNum(){
		
		LoanInformationRequest loanInformation1 = new LoanInformationRequest();
		loanInformation1.setLoanUserEmail(email);
		loanInformation1.setLoanAmount(1234568);
		loanInformation1.setLoanNumber(loanNUmber);
		loanInformation1.setLoanTerm(45);
		loanInformation1.setLoanStatus(active);
		loanInformation1.setLoanMgtFees(7895);
		loanInformation1.setOriginationAccount(originAccount);
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
	}
	
	@Test
	@Rollback(false)
	void findByNotExistingLoanNumTest() {
		Assertions.assertThrows(LoanInformationNotFoundException.class, ()->loanInformationservice.findLoanInfoByLoanNum(loanNUmber));
	}
	
	@Test
	@Rollback(false)
	void findByLastNameTest() throws LoanInformationNotFoundException {
		List<LoanInformation> loanInfolist = loanInformationservice.findLoanInfoByLoanUserEmail(email);
		
		 assertEquals(loanInfolist.size(), loanInformationRepository.findByLoanUserEmail(email).get().size());
	}
	
	
	
}
