package service;

import dao.Dao;
import domain.Customer;
import domain.Movie;
import domain.MovieRental;
import service.calculation.RentalCalculationStrategy;
import service.calculation.RentalCalculationStrategyFactory;

import java.math.BigDecimal;

import static java.lang.System.lineSeparator;

public class StringRentalInfoService implements RentalInfoService<String> {
  public static final int NEW_BONUS_PERIOD_DAYS = 2;

  private final Dao<String, Movie> movieDao;
  private final RentalCalculationStrategyFactory rentalCalculationStrategyFactory;

  public StringRentalInfoService(Dao<String, Movie> movieDao, RentalCalculationStrategyFactory rentalCalculationStrategyFactory) {
    this.movieDao = movieDao;
    this.rentalCalculationStrategyFactory = rentalCalculationStrategyFactory;
  }

  public String formStatement(Customer customer) {
    BigDecimal totalAmount = BigDecimal.ZERO;
    int frequentEnterPoints = 0;

    StatementBuilder resultBuilder = new StatementBuilder()
        .appendCustomer(customer.getName());

    for (MovieRental rental : customer.getRentals()) {
      Movie movie = movieDao.get(rental.getMovieId());
      Movie.Code movieCode = movie.getCode();
      int rentalDays = rental.getDays();

      // determine amount for each movie
      RentalCalculationStrategy calculationStrategy = rentalCalculationStrategyFactory.getRentalCalculationStrategy(movieCode);
      BigDecimal rentalPrice = calculationStrategy.calculate(rentalDays);

      //add frequent bonus points
      frequentEnterPoints++;
      // add bonus for a two day new release rental
      if (movieCode == Movie.Code.NEW && rentalDays > NEW_BONUS_PERIOD_DAYS) {
        frequentEnterPoints++;
      }

      //print figures for this rental
      resultBuilder.appendRentalItem(movie.getTitle(), rentalPrice);
      totalAmount = totalAmount.add(rentalPrice);
    }
    // add footer lines
    return resultBuilder.appendTotalAmount(totalAmount)
        .appendFrequentEnterPoints(frequentEnterPoints)
        .build();
  }

  static class StatementBuilder {
    private final StringBuilder stringBuilder = new StringBuilder();

    StatementBuilder appendCustomer(String name) {
      stringBuilder.append("Rental Record for ")
          .append(name)
          .append(lineSeparator());
      return this;
    }

    StatementBuilder appendRentalItem(String title, BigDecimal price) {
      stringBuilder.append("\t")
          .append(title)
          .append("\t")
          .append(String.format("%.2f", price))
          .append(lineSeparator());
      return this;
    }

    StatementBuilder appendTotalAmount(BigDecimal totalAmount) {
      stringBuilder.append("Amount owed is ")
          .append(String.format("%.2f", totalAmount))
          .append(lineSeparator());
      return this;
    }

    StatementBuilder appendFrequentEnterPoints(int frequentEnterPoints) {
      stringBuilder.append("You earned ")
          .append(frequentEnterPoints)
          .append(" frequent points")
          .append(lineSeparator());
      return this;
    }

    public String build() {
      return this.toString();
    }

    @Override
    public String toString() {
      return stringBuilder.toString();
    }
  }
}
