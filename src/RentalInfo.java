import dao.Dao;
import domain.Customer;
import domain.Movie;
import domain.MovieRental;

public class RentalInfo {
  private final Dao<String, Movie> movieDao;

  public RentalInfo(Dao<String, Movie> movieDao) {
    this.movieDao = movieDao;
  }

  public String formStatement(Customer customer) {
    double totalAmount = 0;
    int frequentEnterPoints = 0;
    StringBuilder result = new StringBuilder("Rental Record for " + customer.getName() + "\n");
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
      result.append("\t")
          .append(movieDao.get(r.getMovieId()).getTitle())
          .append("\t")
          .append(thisAmount)
          .append("\n");
      totalAmount = totalAmount + thisAmount;
    }
    // add footer lines
    result.append("Amount owed is ")
        .append(totalAmount)
        .append("\n");
    result.append("You earned ")
        .append(frequentEnterPoints)
        .append(" frequent points\n");

    return result.toString();
  }
}
