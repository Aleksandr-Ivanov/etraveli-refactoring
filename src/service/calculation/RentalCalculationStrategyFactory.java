package service.calculation;

import domain.Movie;

import java.math.BigDecimal;

public class RentalCalculationStrategyFactory {
  public static final String NEW_BASE_PRICE = "3.00";
  public static final int REGULAR_BASE_PERIOD_DAYS = 2;
  public static final String REGULAR_BASE_PRICE = "2.00";
  public static final String REGULAR_EXTENSION_DAILY_PRICE = "1.50";
  public static final int CHILDREN_BASE_PERIOD_DAYS = 3;
  public static final String CHILDREN_BASE_PRICE = "1.50";
  public static final String CHILDREN_EXTENSION_DAILY_PRICE = "1.50";

  private static RentalCalculationStrategyFactory instance = new RentalCalculationStrategyFactory();

  private RentalCalculationStrategyFactory() {}

  public static RentalCalculationStrategyFactory getInstance() {
    return instance;
  }

  public RentalCalculationStrategy getRentalCalculationStrategy(Movie.Code code) {
    // determine amount for each movie
    switch (code) {
      case NEW:
        return rentalDays -> new BigDecimal(NEW_BASE_PRICE).multiply(new BigDecimal(rentalDays));
      case REGULAR:
        return new BasePeriodPlusExtensionRentalCalculationStrategy(
            REGULAR_BASE_PERIOD_DAYS,
            new BigDecimal(REGULAR_BASE_PRICE),
            new BigDecimal(REGULAR_EXTENSION_DAILY_PRICE)
        );
      case CHILDREN:
        return new BasePeriodPlusExtensionRentalCalculationStrategy(
            CHILDREN_BASE_PERIOD_DAYS,
            new BigDecimal(CHILDREN_BASE_PRICE),
            new BigDecimal(CHILDREN_EXTENSION_DAILY_PRICE)
        );
      default:
        throw new IllegalStateException("Can't make calculations, because found not defined film code: " + code);
    }
  }
}
