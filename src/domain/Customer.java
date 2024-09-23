package domain;

import java.util.*;

public class Customer {
  private int id;
  private String name;
  private List<MovieRental> rentals;

  public Customer(String name, List<MovieRental> rentals) {
    this.name = name;
    this.rentals = new ArrayList<>(rentals);
    this.id = new Random().nextInt(Integer.MAX_VALUE);
  }

  public String getName() {
    return name;
  }

  public List<MovieRental> getRentals() {
    return Collections.unmodifiableList(rentals);
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Customer)) {
      return false;
    }
    Customer customer = (Customer) o;
    return id == customer.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
