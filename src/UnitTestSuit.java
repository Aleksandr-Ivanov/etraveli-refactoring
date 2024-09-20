import domain.Customer;
import domain.MovieRental;

import java.util.ArrayList;
import java.util.List;

public class UnitTestSuit {

  public void runTests() {
    testProvidedRentalsListChangesAreNotAffectingCustomer();
  }

  private static void testProvidedRentalsListChangesAreNotAffectingCustomer() {
    List<MovieRental> list = new ArrayList<>();
    list.add(new MovieRental("F001", 1));
    list.add(new MovieRental("F002", 4));

    Customer curtisJameson = new Customer("Curtis Jameson", list);

    list.clear();

    if (curtisJameson.getRentals().size() != 2) {
      throw new AssertionError("Customer rentals list was leaked!");
    }
  }
}
