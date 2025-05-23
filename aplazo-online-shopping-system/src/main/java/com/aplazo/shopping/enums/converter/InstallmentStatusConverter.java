/**
 * 
 */
package com.aplazo.shopping.enums.converter;

import java.util.stream.Stream;

import com.aplazo.shopping.enums.InstallmentStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Class to cast ENUM to String and String to ENUM.
 * @author CesarSalazar
 */
@Converter(autoApply = true)
public class InstallmentStatusConverter implements AttributeConverter<InstallmentStatus, String> {

	@Override
	public String convertToDatabaseColumn(InstallmentStatus attribute) {
		if(attribute == null) {
			return null;
		}
		return attribute.getInstallmentValue();
	}

	@Override
	public InstallmentStatus convertToEntityAttribute(String installmentStatus) {
		if(installmentStatus == null) {
			return null;
		}
		return Stream.of(InstallmentStatus.values())
				.filter(value -> value.getInstallmentValue().equals(installmentStatus)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
	
}
