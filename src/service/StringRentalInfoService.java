package service;

import dao.Dao;
import domain.Customer;
import domain.Movie;
import domain.MovieRental;

import java.util.function.ToDoubleBiFunction;

import static java.lang.System.lineSeparator;

public class StringRentalInfoService implements RentalInfoService<String> {
  private final Dao<String, Movie> movieDao;

  public StringRentalInfoService(Dao<String, Movie> movieDao) {
    this.movieDao = movieDao;
  }

  public String formStatement(Customer customer) {
    double totalAmount = 0;
    int frequentEnterPoints = 0;

    StatementBuilder resultBuilder = new StatementBuilder();
    resultBuilder.appendCustomer(customer.getName());

    for (MovieRental rental : customer.getRentals()) {
      double rentalPrice = 0;

      Movie movie = movieDao.get(rental.getMovieId());
      Movie.Code movieCode = movie.getCode();
      int rentalDays = rental.getDays();

      ToDoubleBiFunction<Integer, Double> extendedRentalCalculator = (basePeriodDays, dailyPrice) -> {
        if (rentalDays > basePeriodDays) {
          return (rentalDays - basePeriodDays) * dailyPrice;
        }
        return 0.0;
      };

      // determine amount for each movie
      switch (movieCode) {
        case REGULAR:
          rentalPrice = 2 + extendedRentalCalculator.applyAsDouble(2, 1.5);
          break;
        case NEW:
          rentalPrice = rentalDays * 3;
          break;
        case CHILDREN:
          rentalPrice = 1.5 + extendedRentalCalculator.applyAsDouble(3, 1.5);
      }

      //add frequent bonus points
      frequentEnterPoints++;
      // add bonus for a two day new release rental
      if (movieCode == Movie.Code.NEW && rentalDays > 2) {
        frequentEnterPoints++;
      }

      //print figures for this rental
      resultBuilder.appendRentalItem(movie.getTitle(), rentalPrice);
      totalAmount += rentalPrice;
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

    StatementBuilder appendRentalItem(String title, double price) {
      stringBuilder.append("\t")
          .append(title)
          .append("\t")
          .append(String.format("%.2f", price))
          .append(lineSeparator());
      return this;
    }

    StatementBuilder appendTotalAmount(double totalAmount) {
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
