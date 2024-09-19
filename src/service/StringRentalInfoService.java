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

    StatementBuilder result = new StatementBuilder();
    result.appendCustomer(customer.getName());

    for (MovieRental r : customer.getRentals()) {
      double thisAmount = 0;

      // determine amount for each movie
      if (movieDao.get(r.getMovieId()).getCode().equals("regular")) {
        thisAmount = 2;
        if (r.getDays() > 2) {
          thisAmount = ((r.getDays() - 2) * 1.5) + thisAmount;
        }
      }
      if (movieDao.get(r.getMovieId()).getCode().equals("new")) {
        thisAmount = r.getDays() * 3;
      }
      if (movieDao.get(r.getMovieId()).getCode().equals("childrens")) {
        thisAmount = 1.5;
        if (r.getDays() > 3) {
          thisAmount = ((r.getDays() - 3) * 1.5) + thisAmount;
        }
      }

      //add frequent bonus points
      frequentEnterPoints++;
      // add bonus for a two day new release rental
      if ("new".equals(movieDao.get(r.getMovieId()).getCode()) && r.getDays() > 2) frequentEnterPoints++;

      //print figures for this rental
      result.appendRentalItem(movieDao.get(r.getMovieId()).getTitle(), thisAmount);
      totalAmount = totalAmount + thisAmount;
    }
    // add footer lines
    return result.appendTotalAmount(totalAmount)
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
