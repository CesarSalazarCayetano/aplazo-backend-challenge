/**
 * 
 */
package com.aplazo.shopping.enums;

/**
 * Enum to describe the installment status.
 * @author CesarSalazar
 */
public enum InstallmentStatus {

	NEXT("next installment to be paid"),
    PENDING("installment still pending to be paid"),
    ERROR("error on payment, considered unpaid");
	
	private String installmentValue;

	private InstallmentStatus(String installmentValue) {
		this.installmentValue = installmentValue;
	}

	public String getInstallmentValue() {
		return installmentValue;
	}
	
}
