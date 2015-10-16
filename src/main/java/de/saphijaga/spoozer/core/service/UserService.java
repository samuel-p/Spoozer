package de.saphijaga.spoozer.core.service;

import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.RegisterUserRequest;

import java.util.Optional;

/**
 * Created by samuel on 16.10.15.
 */
public interface UserService {
    Optional<UserDetails> registerUser(RegisterUserRequest request);

    Optional<UserDetails> getUserDetails(String id);

    Optional<UserDetails> getUserDetailsByUsername(String username);
}