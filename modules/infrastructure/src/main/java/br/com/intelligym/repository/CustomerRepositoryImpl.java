package br.com.intelligym.repository;

import br.com.intelligym.model.customer.Customer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepositoryImpl extends MongoRepository<Customer, UUID>, CustomerRepository {
    default List<Customer> all() {
        return findAll();
    }
    Optional<Customer> findById(UUID id);
    void deleteById(UUID id);
}
