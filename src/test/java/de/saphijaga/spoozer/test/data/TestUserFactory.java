package de.saphijaga.spoozer.test.data;

import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.RegisterUserRequest;

import java.util.Optional;

import static java.util.Optional.of;

/**
 * Created by samuel on 17.05.16.
 */
public class TestUserFactory {
    public static final String TEST_ID = "bf2a2b0a-adca-4458-bf62-9464548736ce";
    public static final String TEST_USERNAME = "TestUser";
    public static final String TEST_PASSWORD = "Test1234";
    public static final String TEST_ENCODED_PASSWORD = "ENCODED_PASSWORD";
    public static final String TEST_EMAIL = "test@spoozer.de";

    public static RegisterUserRequest testRegisterUserRequest() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername(TEST_USERNAME);
        request.setPassword(TEST_PASSWORD);
        request.setPassword2(TEST_PASSWORD);
        request.setEmail(TEST_EMAIL);
        return request;
    }

    public static User testUserWithoutId() {
        User user = new User();
        user.setUsername(TEST_USERNAME);
        user.setPassword(TEST_ENCODED_PASSWORD);
        user.setEmail(TEST_EMAIL);
        return user;
    }

    public static User testUser() {
        User user = new User();
        user.setId(TEST_ID);
        user.setUsername(TEST_USERNAME);
        user.setPassword(TEST_ENCODED_PASSWORD);
        user.setEmail(TEST_EMAIL);
        return user;
    }

    public static Optional<User> testUserOptional() {
        return of(testUser());
    }

    public static UserDetails testUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(TEST_ID);
        userDetails.setUsername(TEST_USERNAME);
        userDetails.setEmail(TEST_EMAIL);
        return userDetails;
    }

    public static Optional<UserDetails> testUserDetailsOptional() {
        return of(testUserDetails());
    }
}