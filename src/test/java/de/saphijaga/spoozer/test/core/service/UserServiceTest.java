package de.saphijaga.spoozer.test.core.service;

import de.saphijaga.spoozer.core.handler.UserHandler;
import de.saphijaga.spoozer.core.service.TrackService;
import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import de.saphijaga.spoozer.web.details.UserDetails;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static de.saphijaga.spoozer.test.data.TestUserFactory.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by samuel on 16.05.16.
 */
public class UserServiceTest {
    @Mock
    private UserPersistenceService userPersistenceService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TrackService trackService;

    @InjectMocks
    private UserService userService = new UserHandler();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(passwordEncoder.encode(TEST_PASSWORD)).thenReturn(TEST_ENCODED_PASSWORD);

        when(userPersistenceService.saveUser(testUserWithoutId())).thenReturn(testUserOptional());
    }

    @Test
    public void shouldRegisterUser() throws Exception {
        Optional<UserDetails> result = userService.registerUser(testRegisterUserRequest());

        verify(userPersistenceService).saveUser(testUserWithoutId());

        assertThat(result, Matchers.is(testUserDetailsOptional()));
    }
}