/**
 * 
 */
package com.aplazo.shopping.enums.converter;

import java.util.stream.Stream;

import com.aplazo.shopping.enums.LoanStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Class to cast a ENUM to String and String to ENUM
 * @author CesarSalazar
 */
@Converter(autoApply = true)
public class LoanStatusConverter implements AttributeConverter<LoanStatus, String> {

	@Override
	public String convertToDatabaseColumn(LoanStatus attribute) {
		if(attribute == null) {
			return null;
		}
		return attribute.getLoanValue();
	}

	@Override
	public LoanStatus convertToEntityAttribute(String loanStatus) {
		if(loanStatus == null) {
			return null;
		}
		return Stream.of(LoanStatus.values())
				.filter(value -> value.getLoanValue().equals(loanStatus)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
	
}
