import java.util.HashMap;

public class RentalInfo {

  public String statement(Customer customer) {
    HashMap<String, Movie> movies = new HashMap<>();
    movies.put("F001", new Movie("You've Got Mail", "regular"));
    movies.put("F002", new Movie("Matrix", "regular"));
    movies.put("F003", new Movie("Cars", "childrens"));
    movies.put("F004", new Movie("Fast & Furious X", "new"));

    double totalAmount = 0;
    int frequentEnterPoints = 0;
    StringBuilder result = new StringBuilder("Rental Record for " + customer.getName() + "\n");
    for (MovieRental r : customer.getRentals()) {
      double thisAmount = 0;

      // determine amount for each movie
      if (movies.get(r.getMovieId()).getCode().equals("regular")) {
        thisAmount = 2;
        if (r.getDays() > 2) {
          thisAmount = ((r.getDays() - 2) * 1.5) + thisAmount;
        }
      }
      if (movies.get(r.getMovieId()).getCode().equals("new")) {
        thisAmount = r.getDays() * 3;
      }
      if (movies.get(r.getMovieId()).getCode().equals("childrens")) {
        thisAmount = 1.5;
        if (r.getDays() > 3) {
          thisAmount = ((r.getDays() - 3) * 1.5) + thisAmount;
        }
      }

      //add frequent bonus points
      frequentEnterPoints++;
      // add bonus for a two day new release rental
      if ("new".equals(movies.get(r.getMovieId()).getCode()) && r.getDays() > 2) frequentEnterPoints++;

      //print figures for this rental
      result.append("\t")
          .append(movies.get(r.getMovieId()).getTitle())
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
