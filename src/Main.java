import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  static class SlipItem {
    String film;
    double price;

    public SlipItem(String film, double price) {
      this.film = film;
      this.price = price;
    }
  }

  public static void main(String[] args) {
    String customerName = "C. U. Stomer";
    List<MovieRental> rentals = Arrays.asList(
        new MovieRental("F001", 3),
        new MovieRental("F002", 1)
    );
    List<SlipItem> slipItems = Arrays.asList(
        new SlipItem("You've Got Mail", 3.5),
        new SlipItem("Matrix", 2.0)
    );
    double totalAmount = 5.5;
    int frequentPoints = 2;

    acceptanceTest(customerName, rentals, slipItems, totalAmount, frequentPoints);

    System.out.println("Success");
  }

  private static void acceptanceTest(String customerName,
                                     List<MovieRental> rentals,
                                     List<SlipItem> slipItems,
                                     double totalAmount,
                                     int frequentPoints) {

    String resultSlip = new RentalInfo().statement(new Customer(customerName, rentals));

    String expectedSlip = formExpectedSlip(customerName, slipItems, totalAmount, frequentPoints);

    if (!resultSlip.equals(expectedSlip)) {
      throw new AssertionError(String.format(
          "%nExpected: %n%s%n%n" +
          "Got: %n%s",
          expectedSlip,
          resultSlip
      ));
    }
  }

  private static String formExpectedSlip(String customerName,
                                         List<SlipItem> slipItems,
                                         double totalAmount,
                                         int frequentPoints) {
    String joinedSlipItems = slipItems.stream()
        .map(item -> String.format("\t%s\t%.2f%n", item.film, item.price))
        .collect(Collectors.joining());

    return String.format(
        "Rental Record for %s%n" +
            "%s" +
            "Amount owed is %.2f%n" +
            "You earned %d frequent points%n",
        customerName,
        joinedSlipItems,
        totalAmount,
        frequentPoints
    );
  }
}
