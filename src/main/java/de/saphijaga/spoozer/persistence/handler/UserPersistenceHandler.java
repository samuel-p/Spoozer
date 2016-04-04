package de.saphijaga.spoozer.persistence.handler;

import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.persistence.repository.UserRepository;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

/**
 * Created by samuel on 15.10.15.
 */
@Component
public class UserPersistenceHandler implements UserPersistenceService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> saveUser(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString());
        }
        if (user.getHistory().getId() == null) {
            user.getHistory().setId(UUID.randomUUID().toString());
        }

        User savedUser = userRepository.save(user);
        return ofNullable(savedUser);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public Optional<User> getUser(String id) {
        return ofNullable(userRepository.findOne(id));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return ofNullable(userRepository.findByUsernameIgnoreCase(username));
    }
}