package service.calculation;

import java.math.BigDecimal;

public interface RentalCalculationStrategy {
  BigDecimal calculate(int rentalDays);
}
