package service.calculation;

import java.math.BigDecimal;

public class BasePeriodPlusExtensionRentalCalculationStrategy implements RentalCalculationStrategy {
  private final int basePeriodDays;
  private final BigDecimal basePrice;
  private final BigDecimal extensionDailyPrice;

  public BasePeriodPlusExtensionRentalCalculationStrategy(int basePeriodDays,
                                                          BigDecimal basePrice,
                                                          BigDecimal extensionDailyPrice) {
    this.basePeriodDays = basePeriodDays;
    this.basePrice = basePrice;
    this.extensionDailyPrice = extensionDailyPrice;
  }

  @Override
  public BigDecimal calculate(int rentalDays) {
    BigDecimal extensionTotal;
    if (rentalDays > basePeriodDays) {
      int extensionDays = rentalDays - basePeriodDays;
      extensionTotal = extensionDailyPrice.multiply(BigDecimal.valueOf(extensionDays));
    } else {
      extensionTotal = BigDecimal.ZERO;
    }

    return basePrice.add(extensionTotal);
  }
}
