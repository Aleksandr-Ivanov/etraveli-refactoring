package service;

import dao.Dao;
import domain.Customer;
import domain.Movie;
import domain.MovieRental;

import java.math.BigDecimal;
import java.util.function.BiFunction;

import static java.lang.System.lineSeparator;

public class StringRentalInfoService implements RentalInfoService<String> {
  private final Dao<String, Movie> movieDao;

  public StringRentalInfoService(Dao<String, Movie> movieDao) {
    this.movieDao = movieDao;
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

      BiFunction<Integer, BigDecimal, BigDecimal> extendedRentalCalculator = (basePeriodDays, dailyPrice) -> {
        if (rentalDays > basePeriodDays) {
          return dailyPrice.multiply(BigDecimal.valueOf(rentalDays - basePeriodDays));
        }
        return BigDecimal.ZERO;
      };

      // determine amount for each movie
      BigDecimal rentalPrice = BigDecimal.ZERO;
      switch (movieCode) {
        case REGULAR:
          BigDecimal basePriceForRegular = new BigDecimal("2.00");
          BigDecimal extendedPriceForRegular = extendedRentalCalculator.apply(2, new BigDecimal("1.50"));
          rentalPrice = basePriceForRegular.add(extendedPriceForRegular);
          break;
        case NEW:
          rentalPrice = new BigDecimal("3.00").multiply(new BigDecimal(rentalDays));
          break;
        case CHILDREN:
          BigDecimal basePriceForChildren = new BigDecimal("1.50");
          BigDecimal extendedPriceForChildren = extendedRentalCalculator.apply(3, new BigDecimal("1.50"));
          rentalPrice = basePriceForChildren.add(extendedPriceForChildren);
      }

      //add frequent bonus points
      frequentEnterPoints++;
      // add bonus for a two day new release rental
      if (movieCode == Movie.Code.NEW && rentalDays > 2) {
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
