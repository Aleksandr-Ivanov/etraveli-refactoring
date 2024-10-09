package service;

import domain.Customer;

public interface RentalInfoService<T> {
  T formStatement(Customer customer);
}
