package de.saphijaga.spoozer.test.application.config;

import de.saphijaga.spoozer.core.handler.*;
import de.saphijaga.spoozer.persistence.handler.AccountPersistenceHandler;
import de.saphijaga.spoozer.persistence.handler.PlaylistPersistenceHandler;
import de.saphijaga.spoozer.persistence.handler.UserPersistenceHandler;
import de.saphijaga.spoozer.service.spotify.SpotifyApi;
import de.saphijaga.spoozer.service.utils.ApiService;
import de.saphijaga.spoozer.web.controller.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by samuel on 06.12.15.
 */
@Configuration
public class TestSpoozerConfig {
    /* PERSISTENCE: */

    @Bean
    public AccountPersistenceHandler accountPersistenceHandler() {
        return new AccountPersistenceHandler();
    }

    @Bean
    public PlaylistPersistenceHandler playlistPersistenceHandler() {
        return new PlaylistPersistenceHandler();
    }

    @Bean
    public UserPersistenceHandler userPersistenceHandler() {
        return new UserPersistenceHandler();
    }

    /* CORE: */

    @Bean
    public AccountAccessHandler accountAccessHandler() {
        return new AccountAccessHandler();
    }

    @Bean
    public AccountHandler accountHandler() {
        return new AccountHandler();
    }

    @Bean
    public PlaylistHandler playlistHandler() {
        return new PlaylistHandler();
    }

    @Bean
    public SecurityUserDetailsHandler securityUserDetailsHandler() {
        return new SecurityUserDetailsHandler();
    }

    @Bean
    public UserHandler userHandler() {
        return new UserHandler();
    }

    /* SERVICE: */

    @Bean
    public ApiService apiService() {
        return new ApiService();
    }

    @Bean
    public SpotifyApi spotifyApi() {
        return new SpotifyApi();
    }

    /* WEB: */

    @Bean
    public AccountController accountController() {
        return new AccountController();
    }

    @Bean
    public AuthenticationController authenticationController() {
        return new AuthenticationController();
    }

    @Bean
    public CsrfController csrfController() {
        return new CsrfController();
    }

    @Bean
    public PlaylistController playlistController() {
        return new PlaylistController();
    }

    @Bean
    public SpoozerController spoozerController() {
        return new SpoozerController();
    }

    @Bean
    public SpotifyController spotifyController() {
        return new SpotifyController();
    }

    @Bean
    public TrackController trackController() {
        return new TrackController();
    }

    @Bean
    public UserController userController() {
        return new UserController();
    }
}