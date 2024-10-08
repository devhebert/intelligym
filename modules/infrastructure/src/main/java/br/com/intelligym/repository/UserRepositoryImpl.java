package br.com.intelligym.repository;

import br.com.intelligym.model.user.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryImpl extends MongoRepository<User, UUID>, UserRepository {
    default List<User> all() {
        return findAll();
    }
    Optional<UserDetails> findByUsername(String username);
    Optional<User> findById(UUID id);
}
