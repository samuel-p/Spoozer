package de.saphijaga.spoozer.test.data;

import de.saphijaga.spoozer.persistence.domain.Track;
import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.web.details.UserDetails;

/**
 * Created by samuel on 17.05.16.
 */
public class TestUserFactory {
    public static final String TEST_ID = "bf2a2b0a-adca-4458-bf62-9464548736ce";
    public static final String TEST_USERNAME = "TestUser";
    public static final String TEST_PASSWORD = "Test1234";
    public static final String TEST_ENCODED_PASSWORD = "ENCODED_PASSWORD";
    public static final String TEST_EMAIL = "test@spoozer.de";

    public static User testUserWithoutId() {
        User user = new User();
        user.setUsername(TEST_USERNAME);
        user.setPassword(TEST_ENCODED_PASSWORD);
        user.setEmail(TEST_EMAIL);
        return user;
    }

    public static User testUser() {
        User user = testUserWithoutId();
        user.setId(TEST_ID);
        user.getHistory().addSong(new Track());
        return user;
    }

    public static UserDetails testUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(TEST_ID);
        userDetails.setUsername(TEST_USERNAME);
        userDetails.setEmail(TEST_EMAIL);
        return userDetails;
    }
}