import domain.Customer;
import domain.MovieRental;

import java.util.ArrayList;
import java.util.List;

public class UnitTestSuit {

  public void runTests() {
    testProvidedRentalsListChangesAreNotAffectingCustomer();
    testReturnedRentalsListChangesAreNotAffectingCustomer();
  }

  private static void testProvidedRentalsListChangesAreNotAffectingCustomer() {
    List<MovieRental> list = new ArrayList<>();
    list.add(new MovieRental("F001", 1));
    list.add(new MovieRental("F002", 4));

    Customer customer = new Customer("Curtis Jameson", list);

    list.clear();

    if (customer.getRentals().size() != 2) {
      throw new AssertionError("Customer rentals list was leaked!");
    }
  }

  private static void testReturnedRentalsListChangesAreNotAffectingCustomer() {
    List<MovieRental> list = new ArrayList<>();
    list.add(new MovieRental("F002", 3));
    list.add(new MovieRental("F003", 7));

    Customer customer = new Customer("Ray Clayton", list);

    List<MovieRental> rentals = customer.getRentals();

    try {
      rentals.clear();
    } catch (UnsupportedOperationException ex) {
      System.out.println("UnitTestSuit INFO: Exception when clearing Customer rentals list was caught:" + ex + ". Execution is continuing normally.");
    }

    if (customer.getRentals().size() != 2) {
      throw new AssertionError("Customer rentals list was leaked!");
    }
  }
}
