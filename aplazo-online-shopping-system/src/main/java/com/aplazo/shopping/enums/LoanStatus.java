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

	LoanStatus(String string) {}

	public String getLoanValue() {
		return loanValue;
	}
	
	
}
