package br.com.intelligym.repository;

import br.com.intelligym.model.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);
    List<Customer> all();
    Optional<Customer> findById(UUID id);
    void deleteById(UUID id);

    List<Customer> findAll();
}
