package de.saphijaga.spoozer.test.persistence.service;

import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static de.saphijaga.spoozer.test.data.TestUserFactory.*;
import static java.util.Optional.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by samuel on 17.05.16.
 */
public class UserPersistenceServiceIntegrationTest extends MongoIntegrationTest {
    @Autowired
    private UserPersistenceService userService;

    @Test
    public void shouldReturnUserById() throws Exception {
        assertThat(userService.getUser(TEST_ID), is(testUserOptional()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowErrorForNullId() throws Exception {
        userService.getUser(null);
    }

    @Test
    public void shouldReturnNullForEmptyId() throws Exception {
        assertThat(userService.getUser(""), is(empty()));
    }

    @Test
    public void shouldReturnUserByUsername() throws Exception {
        assertThat(userService.getUserByUsername(TEST_USERNAME), is(testUserOptional()));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowErrorForNullUsername() throws Exception {
        userService.getUserByUsername(null);
    }

    @Test
    public void shouldReturnNullForEmptyUsername() throws Exception {
        assertThat(userService.getUserByUsername(""), is(empty()));
    }

    @Test
    public void shouldSaveNewUserAndAddId() throws Exception {
        User create = testUserWithoutId();
        Optional<User> created = userService.saveUser(create);
        assertTrue(created.isPresent());
        assertThat(created.get(), is(create));
        assertThat(findUser(created.get().getId()), is(created.get()));
    }

    @Test
    public void shouldSaveEditedUser() throws Exception {
        User edit = findUser(TEST_ID);
        edit.setName("Spoozer Test User");
        Optional<User> edited = userService.saveUser(edit);
        assertTrue(edited.isPresent());
        assertThat(edited.get(), is(edit));
        assertThat(findUser(TEST_ID), is(edited.get()));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        User delete = findUser(TEST_ID);
        userService.deleteUser(delete);
        assertNull(findUser(TEST_ID));
    }
}