package com.aplazo.shopping.enums;

/**
 * Enum to validate the status of the loans.
 * @author CesarSalazar
 */
public enum LoanStatus {

	ACTIVE("Pending installment payments"),
    LATE("Installment payments with error"),
    COMPLETED("Installments are paid");
	
	private String loanValue;

	private LoanStatus(String loanValue) {
		this.loanValue = loanValue;
	}

	public String getLoanValue() {
		return loanValue;
	}
	
	
}
