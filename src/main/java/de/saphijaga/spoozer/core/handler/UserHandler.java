package de.saphijaga.spoozer.core.handler;

import de.saphijaga.spoozer.core.service.TrackService;
import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.persistence.domain.HTrack;
import de.saphijaga.spoozer.persistence.domain.History;
import de.saphijaga.spoozer.persistence.domain.Properties;
import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.AddHTrackRequest;
import de.saphijaga.spoozer.web.domain.request.ChangePasswordRequest;
import de.saphijaga.spoozer.web.domain.request.RegisterUserRequest;
import de.saphijaga.spoozer.web.domain.request.SaveUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Created by samuel on 16.10.15.
 */
@Component
public class UserHandler implements UserService {
    @Autowired
    private UserPersistenceService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TrackService trackService;

    @Override
    public Optional<UserDetails> registerUser(RegisterUserRequest request) {
        User user1 = toUser(request);
        Optional<User> user = userService.saveUser(user1);
        return toUserDetails(user);
    }

    private User toUser(RegisterUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        return user;
    }

    @Override
    public Optional<UserDetails> getUserDetails(String id) {
        Optional<User> user = userService.getUser(id);
        return toUserDetails(user);
    }

    @Override
    public Optional<UserDetails> getUserDetailsByUsername(String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return toUserDetails(user);
    }

    private Optional<UserDetails> toUserDetails(Optional<User> user) {
        if (!user.isPresent()) {
            return empty();
        }
        UserDetails details = new UserDetails();
        details.setId(user.get().getId());
        details.setUsername(user.get().getUsername());
        details.setEmail(user.get().getEmail());
        details.setName(user.get().getName());
        return of(details);
    }

    @Override
    public Optional<UserDetails> saveUserDetails(UserDetails userDetails, SaveUserRequest request) {
        Optional<User> user = userService.getUser(userDetails.getId());
        if (user.isPresent()) {
            updateUser(user.get(), request);
            user = userService.saveUser(user.get());
        }
        return toUserDetails(user);
    }

    @Override
    public Optional<UserDetails> changeUserPassword(UserDetails userDetails, ChangePasswordRequest changePasswordRequest) {
        Optional<User> user = userService.getUser(userDetails.getId());
        if (user.isPresent() && passwordEncoder.matches(changePasswordRequest.getOldpassword(), user.get().getPassword())) {
            user.get().setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
            user = userService.saveUser(user.get());
        }
        return toUserDetails(user);
    }

    @Override
    public void addSongToHistory(UserDetails userDetails, AddHTrackRequest request) {
        trackService.addTrackToHistory(userDetails, request);
    }

    @Override
    public void cutUserHistory(UserDetails userDetails, int limit) {
        Optional<User> user = userService.getUser(userDetails.getId());
        user.ifPresent(u -> u.getHistory().cutListtoLimit(limit));
    }

    @Override
    public void clearUserHistory(UserDetails userDetails) {
        Optional<User> user = userService.getUser(userDetails.getId());
        user.ifPresent(u -> {
            u.getHistory().clearHistory();
            userService.saveUser(u);
        });
    }

    @Override
    public Map<Date, TrackDetails> getHistoryMap(UserDetails userDetails) {
        Map<Date, TrackDetails> historyMap = new HashMap<>();
        Optional<User> user = userService.getUser(userDetails.getId());
        for (HTrack track : user.map(User::getHistory).map(History::getSongs).orElse(emptyList())) {
            trackService.getTrack(track.getTrack().getId()).ifPresent(t -> historyMap.put(track.getDate(), t));
        }
        return historyMap;
    }

    private void updateUser(User user, SaveUserRequest request) {
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
    }

    @Override
    public void saveProperties(UserDetails userDetails, Map<String, Object> properties) {
        Optional<User> user = userService.getUser(userDetails.getId());
        if (user.isPresent()) {
            user.get().getProperties().addList(properties);
            userService.saveUser(user.get());
        }
    }

    @Override
    public Map<String, Object> getProperties(UserDetails user) {
        return userService.getUser(user.getId()).map(User::getProperties).map(Properties::getList).orElse(new HashMap<>());
    }
}