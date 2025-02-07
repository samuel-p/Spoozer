package de.saphijaga.spoozer.persistence.repository;

import de.saphijaga.spoozer.persistence.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by samuel on 14.10.15.
 */
public interface UserRepository extends CrudRepository<User, String> {
    User findByUsernameIgnoreCase(String username);
}