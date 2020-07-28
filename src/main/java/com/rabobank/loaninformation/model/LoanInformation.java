/**
 * 
 */
package com.rabobank.loaninformation.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Shanthakumar
 *
 */
@Entity
@Table(	name = "loan_information", uniqueConstraints = { 
		@UniqueConstraint(columnNames = "loanNumber")})
public class LoanInformation implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6738193567057311895L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 50)
	private String loanUserEmail;
	
	@NotBlank
	@Size(max = 20)
	private String loanNumber;
	
	/**
	 * @return the loanUserEmail
	 */
	public String getLoanUserEmail() {
		return loanUserEmail;
	}
	/**
	 * @param loanUserEmail the loanUserEmail to set
	 */
	public void setLoanUserEmail(String loanUserEmail) {
		this.loanUserEmail = loanUserEmail;
	}
	@NotNull
	private double loanAmount;
	
	@NotNull
	private double loanTerm;
	
	@NotBlank
	@Size(max = 20)
	private String loanStatus;
	
	@NotNull
	private double loanMgtFees;
	
	@NotBlank
	@Size(max = 20)
	private String originationAccount;
	
	
	private Date originationDate;

	/**
	 * @return the loanNumber
	 */
	public String getLoanNumber() {
		return loanNumber;
	}
	/**
	 * @param loanNumber the loanNumber to set
	 */
	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}
	/**
	 * @return the loanAmount
	 */
	public double getLoanAmount() {
		return loanAmount;
	}
	/**
	 * @param loanAmount the loanAmount to set
	 */
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	public LoanInformation() {
		
	}

	/**
	 * @return the loanTerm
	 */
	public double getLoanTerm() {
		return loanTerm;
	}
	/**
	 * @param loanTerm the loanTerm to set
	 */
	public void setLoanTerm(double loanTerm) {
		this.loanTerm = loanTerm;
	}
	/**
	 * @param loanNumber
	 * @param loanAmount
	 * @param loanTerm
	 * @param loanStatus
	 * @param loanMgtFees
	 * @param originationAccount
	 * @param originationDate
	 * @param loanUser
	 */
	public LoanInformation(String loanUserEmail, @NotBlank @Size(max = 20) String loanNumber, @NotNull  double loanAmount,
			@NotNull double loanTerm, @NotBlank String loanStatus,
			@NotNull double loanMgtFees, @NotBlank @Size(max = 20) String originationAccount,
			 Date originationDate) {
		this.loanUserEmail=loanUserEmail;
		this.loanNumber = loanNumber;
		this.loanAmount = loanAmount;
		this.loanTerm = loanTerm;
		this.loanStatus = loanStatus;
		this.loanMgtFees = loanMgtFees;
		this.originationAccount = originationAccount;
		this.originationDate = originationDate;
	}
	/**
	 * @return the loanStatus
	 */
	public String getLoanStatus() {
		return loanStatus;
	}
	/**
	 * @param loanStatus the loanStatus to set
	 */
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	/**
	 * @return the loanMgtFees
	 */
	public double getLoanMgtFees() {
		return loanMgtFees;
	}
	/**
	 * @param loanMgtFees the loanMgtFees to set
	 */
	public void setLoanMgtFees(double loanMgtFees) {
		this.loanMgtFees = loanMgtFees;
	}
	/**
	 * @return the originationAccount
	 */
	public String getOriginationAccount() {
		return originationAccount;
	}
	/**
	 * @param originationAccount the originationAccount to set
	 */
	public void setOriginationAccount(String originationAccount) {
		this.originationAccount = originationAccount;
	}
	/**
	 * @return the originationDate
	 */
	public Date getOriginationDate() {
		return originationDate;
	}
	/**
	 * @param originationDate the originationDate to set
	 */
	public void setOriginationDate(Date originationDate) {
		this.originationDate = originationDate;
	}


}
