package service;

import dao.Dao;
import domain.Customer;
import domain.Movie;
import domain.MovieRental;

import static java.lang.System.*;

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
      double thisAmount = 0;
      Movie movie = movieDao.get(rental.getMovieId());
      Movie.Code movieCode = movie.getCode();

      // determine amount for each movie
      if (movieCode == Movie.Code.REGULAR) {
        thisAmount = 2;
        if (rental.getDays() > 2) {
          thisAmount = ((rental.getDays() - 2) * 1.5) + thisAmount;
        }
      }
      if (movieCode == Movie.Code.NEW) {
        thisAmount = rental.getDays() * 3;
      }
      if (movieCode == Movie.Code.CHILDREN) {
        thisAmount = 1.5;
        if (rental.getDays() > 3) {
          thisAmount = ((rental.getDays() - 3) * 1.5) + thisAmount;
        }
      }

      //add frequent bonus points
      frequentEnterPoints++;
      // add bonus for a two day new release rental
      if (movieCode == Movie.Code.NEW && rental.getDays() > 2) frequentEnterPoints++;

      //print figures for this rental
      resultBuilder.appendRentalItem(movie.getTitle(), thisAmount);
      totalAmount = totalAmount + thisAmount;
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
