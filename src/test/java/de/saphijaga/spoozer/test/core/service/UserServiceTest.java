package de.saphijaga.spoozer.test.core.service;

import de.saphijaga.spoozer.core.handler.UserHandler;
import de.saphijaga.spoozer.core.service.TrackService;
import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.persistence.domain.User;
import de.saphijaga.spoozer.persistence.service.UserPersistenceService;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.ChangePasswordRequest;
import de.saphijaga.spoozer.web.domain.request.RegisterUserRequest;
import de.saphijaga.spoozer.web.domain.request.SaveUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static de.saphijaga.spoozer.test.data.TestUserFactory.*;
import static java.util.Optional.of;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by samuel on 16.05.16.
 */
public class UserServiceTest {
    private static final String TEST_NAME = "TestName";
    private static final String TEST_CHANGE_PASSWORD = "NewPassword";
    private static final String TEST_CHANGE_ENCODED_PASSWORD = "NEW_ENCODED_PASSWORD";

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
        initMocks(this);

        when(passwordEncoder.encode(TEST_PASSWORD)).thenReturn(TEST_ENCODED_PASSWORD);
        when(passwordEncoder.matches(TEST_PASSWORD, TEST_ENCODED_PASSWORD)).thenReturn(true);
        when(passwordEncoder.encode(TEST_CHANGE_PASSWORD)).thenReturn(TEST_CHANGE_ENCODED_PASSWORD);

        when(userPersistenceService.saveUser(testUserWithoutId())).thenReturn(of(testUser()));
        when(userPersistenceService.getUser(TEST_ID)).thenReturn(of(testUser()));
        when(userPersistenceService.getUserByUsername(TEST_USERNAME)).thenReturn(of(testUser()));
        when(userPersistenceService.saveUser(testUserWithName())).thenReturn(of(testUserWithName()));
        when(userPersistenceService.saveUser(testUserWithChangedPassword())).thenReturn(of(testUserWithChangedPassword()));
    }

    @Test
    public void shouldRegisterUser() throws Exception {
        Optional<UserDetails> result = userService.registerUser(testRegisterUserRequest());

        verify(passwordEncoder).encode(TEST_PASSWORD);
        verify(userPersistenceService).saveUser(testUserWithoutId());

        assertThat(result, is(of(testUserDetails())));
    }

    @Test
    public void shouldReturnUserDetailsById() throws Exception {
        Optional<UserDetails> result = userService.getUserDetails(TEST_ID);

        verify(userPersistenceService).getUser(TEST_ID);

        assertThat(result, is(of(testUserDetails())));
    }

    @Test
    public void shouldReturnUserDetailsByUsername() throws Exception {
        Optional<UserDetails> result = userService.getUserDetailsByUsername(TEST_USERNAME);

        verify(userPersistenceService).getUserByUsername(TEST_USERNAME);

        assertThat(result, is(of(testUserDetails())));
    }

    @Test
    public void shouldSaveUserDetails() throws Exception {
        Optional<UserDetails> result = userService.saveUserDetails(testUserDetails(), testSaveUserRequest());

        verify(userPersistenceService).getUser(TEST_ID);
        verify(userPersistenceService).saveUser(testUserWithName());

        assertThat(result, is(of(testUserDetailsWithName())));
    }

    @Test
    public void shouldChangeUserPassword() throws Exception {
        Optional<UserDetails> result = userService.changeUserPassword(testUserDetails(), testChangePasswordRequest());

        verify(passwordEncoder).encode(TEST_CHANGE_PASSWORD);
        verify(userPersistenceService).getUser(TEST_ID);
        verify(userPersistenceService).saveUser(testUserWithChangedPassword());

        assertThat(result, is(of(testUserDetails())));
    }

    @Test
    public void shouldNotChangeUserPassword() throws Exception {
        Optional<UserDetails> result = userService.changeUserPassword(testUserDetails(), testChangePasswordRequestWithoutOldPassword());

        verify(passwordEncoder, never()).encode(any());
        verify(userPersistenceService).getUser(TEST_ID);
        verify(userPersistenceService, never()).saveUser(any());

        assertThat(result, is(of(testUserDetails())));
    }

    private RegisterUserRequest testRegisterUserRequest() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername(TEST_USERNAME);
        request.setPassword(TEST_PASSWORD);
        request.setPassword2(TEST_PASSWORD);
        request.setEmail(TEST_EMAIL);
        return request;
    }

    private SaveUserRequest testSaveUserRequest() {
        SaveUserRequest request = new SaveUserRequest();
        request.setId(TEST_ID);
        request.setName(TEST_NAME);
        request.setUsername(TEST_USERNAME);
        request.setEmail(TEST_EMAIL);
        return request;
    }

    private ChangePasswordRequest testChangePasswordRequest() {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setOldpassword(TEST_PASSWORD);
        request.setPassword(TEST_CHANGE_PASSWORD);
        request.setPassword2(TEST_CHANGE_PASSWORD);
        return request;
    }

    private ChangePasswordRequest testChangePasswordRequestWithoutOldPassword() {
        ChangePasswordRequest request = testChangePasswordRequest();
        request.setOldpassword(null);
        return request;
    }

    private User testUserWithName() {
        User user = testUser();
        user.setName(TEST_NAME);
        return user;
    }

    private UserDetails testUserDetailsWithName() {
        UserDetails userDetails = testUserDetails();
        userDetails.setName(TEST_NAME);
        return userDetails;
    }

    private User testUserWithChangedPassword() {
        User user = testUser();
        user.setPassword(TEST_CHANGE_ENCODED_PASSWORD);
        return user;
    }
}