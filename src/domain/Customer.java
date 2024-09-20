package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer {
  private String name;
  private List<MovieRental> rentals;

  public Customer(String name, List<MovieRental> rentals) {
    this.name = name;
    this.rentals = new ArrayList<>(rentals);
  }

  public String getName() {
    return name;
  }

  public List<MovieRental> getRentals() {
    return Collections.unmodifiableList(rentals);
  }
}
