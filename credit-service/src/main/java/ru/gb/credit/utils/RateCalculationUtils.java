package ru.gb.credit.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;

/**
 * Утилиты расчёта суммы по процентам
 */
public class RateCalculationUtils {

	/**
	 * Расчёт суммы по процентам за один день
	 */
	public static BigDecimal calculateRateForOneDay(BigDecimal rate, BigDecimal sumOnAccount) {

		BigDecimal percent = new BigDecimal("100");
		BigDecimal daysInYear = BigDecimal.valueOf(Year.now().length());

		return rate.divide(percent, 10, RoundingMode.HALF_UP).divide(daysInYear, 10, RoundingMode.HALF_UP).multiply(sumOnAccount).setScale(10, RoundingMode.HALF_UP);
	}
}
