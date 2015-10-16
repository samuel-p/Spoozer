package de.saphijaga.spoozer.persistence.service;

import de.saphijaga.spoozer.persistence.domain.User;

import java.util.Optional;

/**
 * Created by samuel on 15.10.15.
 */
public interface UserPersistenceService {
    Optional<User> saveUser(User user);

    void deleteUser(User user);

    Optional<User> getUser(String id);

    Optional<User> getUserByUsername(String username);
}