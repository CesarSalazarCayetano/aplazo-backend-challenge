/**
 * 
 */
package com.aplazo.shopping.util;

import java.time.temporal.ValueRange;

/**
 * @author CesarSalazar
 */
public class RangeUtils {

	private RangeUtils() {}
	
	/**
	 * Get the range between 2 Integer values.
	 * Recibes two Strings that are be parsed as Integer.
	 * @param valueToDeterminate Integer value between ranges.
	 * @param fromRange first range.
	 * @param toRange second range.
	 * @return return a int if is into the range, otherwise null.
	 */
	public static Integer getRangeFromString(Integer valueToDeterminate, String fromRange, String toRange) {
		ValueRange range = ValueRange.of(Integer.parseInt(fromRange), Integer.parseInt(toRange));
		if (range.isValidIntValue(valueToDeterminate)) {
			return valueToDeterminate;
		}
		return null;
	}
}
