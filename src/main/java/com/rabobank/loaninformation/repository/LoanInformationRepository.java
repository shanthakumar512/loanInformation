/**
 * 
 */
package com.rabobank.loaninformation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rabobank.loaninformation.model.LoanInformation;


/**
 * @author Admin
 *
 */
@Repository
public interface LoanInformationRepository extends JpaRepository<LoanInformation, Long> {
	
	Optional<LoanInformation> findByLoanNumber(String loanNumber);
	
	Boolean existsByLoanNumber(String loanNumber);
	
	Optional<List<LoanInformation>> findByLoanUserEmail(String loanUserEmail);
	
}
