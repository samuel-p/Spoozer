package de.saphijaga.spoozer.core.service;

import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.AddHTrackRequest;
import de.saphijaga.spoozer.web.domain.request.ChangePasswordRequest;
import de.saphijaga.spoozer.web.domain.request.RegisterUserRequest;
import de.saphijaga.spoozer.web.domain.request.SaveUserRequest;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * Created by samuel on 16.10.15.
 */
public interface UserService {
    Optional<UserDetails> registerUser(RegisterUserRequest request);

    Optional<UserDetails> getUserDetails(String id);

    Optional<UserDetails> getUserDetailsByUsername(String username);

    Optional<UserDetails> saveUserDetails(UserDetails user, SaveUserRequest request);

    Optional<UserDetails> changeUserPassword(UserDetails user, ChangePasswordRequest changePasswordRequest);

    void addSongToHistory(UserDetails user, AddHTrackRequest request);

    void cutUserHistory(UserDetails user, int limit);

    void clearUserHistory(UserDetails user);

    Map<Date, TrackDetails> getHistoryMap(UserDetails user);

    void saveProperties(UserDetails userDetails, Map<String, Object> properties);

    Map<String, Object> getProperties(UserDetails user);
}