import domain.Customer;
import domain.MovieRental;
import service.RentalInfoService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AcceptanceTestSuit {

  private RentalInfoService<String> rentalInfoService;

  public AcceptanceTestSuit(RentalInfoService<String> rentalInfoService) {
    this.rentalInfoService = rentalInfoService;
  }

  static class SlipItem {
    String film;
    BigDecimal price;

    public SlipItem(String film, BigDecimal price) {
      this.film = film;
      this.price = price;
    }
  }

  public void runTests() {
    twoRentalsAcceptanceTest();
    noRentalsAcceptanceTest();
    singleNewRentalAcceptanceTest();
    complicatedRentalsAcceptanceTest();
  }

  public void twoRentalsAcceptanceTest() {
    List<MovieRental> rentals = Arrays.asList(
        new MovieRental("F001", 3),
        new MovieRental("F002", 1)
    );
    List<SlipItem> slipItems = Arrays.asList(
        new SlipItem("You've Got Mail", new BigDecimal("3.50")),
        new SlipItem("Matrix", new BigDecimal("2.00"))
    );

    acceptanceTest(
        "C. U. Stomer",
        rentals,
        slipItems,
        new BigDecimal("5.5"),
        2
    );
  }

  public void noRentalsAcceptanceTest() {
    acceptanceTest(
        "Helen Kroos",
        Collections.emptyList(),
        Collections.emptyList(),
        new BigDecimal("0.00"),
        0
    );
  }

  public void singleNewRentalAcceptanceTest() {
    List<MovieRental> rentals = Collections.singletonList(new MovieRental("F004", 5));
    List<SlipItem> slipItems = Collections.singletonList(new SlipItem("Fast & Furious X", new BigDecimal("15.00")));

    acceptanceTest(
        "Clark Smith",
        rentals,
        slipItems,
        new BigDecimal("15.00"),
        2
    );
  }

  public void complicatedRentalsAcceptanceTest() {
    List<MovieRental> rentals = Arrays.asList(
        new MovieRental("F002", 7),
        new MovieRental("F003", 4),
        new MovieRental("F004", 2)
    );
    List<SlipItem> slipItems = Arrays.asList(
        new SlipItem("Matrix", new BigDecimal("9.50")),
        new SlipItem("Cars", new BigDecimal("3.00")),
        new SlipItem("Fast & Furious X", new BigDecimal("6.00"))
    );

    acceptanceTest(
        "Mary Claire",
        rentals,
        slipItems,
        new BigDecimal("18.5"),
        3
    );
  }

  void acceptanceTest(String customerName,
                      List<MovieRental> rentals,
                      List<SlipItem> slipItems,
                      BigDecimal totalAmount,
                      int frequentPoints) {

    String resultSlip = rentalInfoService.formStatement(new Customer(customerName, rentals));

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
                                         BigDecimal totalAmount,
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
