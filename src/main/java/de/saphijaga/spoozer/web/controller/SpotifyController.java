package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.service.spotify.Spotify;
import de.saphijaga.spoozer.core.service.AccountService;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.service.spotify.SpotifyApi;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.authentication.Session;
import de.saphijaga.spoozer.web.details.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by samuel on 12.11.15.
 */
@Controller
public class SpotifyController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountController accountController;

    @Autowired
    private SpotifyApi api;

    @RequestMapping("/spotify/login")
    public String login(HttpSession session) throws IOException {
        String state = UUID.randomUUID().toString();
        session.setAttribute(Spotify.STATE, state);
        String redirectUrl = (String) session.getAttribute("serverurl");
        return "redirect:" + api.getLoginURL(redirectUrl, state);
    }

    @RequestMapping("/spotify/callback")
    public String spotifyCallback(@RequestParam(required = false) String code, @RequestParam String state, HttpSession session, UserDetails user) throws Exception {
        String sessionState = (String) session.getAttribute(Spotify.STATE);
        if (state.equals(sessionState)) {
            session.removeAttribute(Spotify.STATE);
            api.login(user, code, (String) session.getAttribute(Session.SERVER_URL));
            // TODO ErrorHandling
            accountController.sendGetUserAccountsResponse(user);
            return "scripts/spotify_callback";
        }
        throw new IllegalArgumentException("state is wrong");
    }


    @MessageMapping("/spotify/logout")
    public void logout(UserDetails user) throws IOException {
        Optional<AccountDetails> account = accountService.getAccount(user, StreamingService.SPOTIFY);
        if (account.isPresent()) {
            accountService.deleteAccount(user, account.get());
            accountController.sendGetUserAccountsResponse(user);
        }
    }
}