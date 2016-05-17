package de.saphijaga.spoozer.test.persistence.service;

import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static de.saphijaga.spoozer.test.data.TestUserFactory.TEST_ID;
import static de.saphijaga.spoozer.test.data.TestUserFactory.testUserOptional;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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
}