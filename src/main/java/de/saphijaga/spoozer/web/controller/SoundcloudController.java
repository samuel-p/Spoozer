package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.core.service.AccountService;
import de.saphijaga.spoozer.service.StreamingService;
import de.saphijaga.spoozer.service.soundcloud.Soundcloud;
import de.saphijaga.spoozer.service.soundcloud.SoundcloudApi;
import de.saphijaga.spoozer.web.authentication.Session;
import de.saphijaga.spoozer.web.details.AccountDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.response.GetSoundcloudClientIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by samuel on 31.03.16.
 */
@Controller
public class SoundcloudController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountController accountController;

    @Autowired
    private SoundcloudApi api;

    @RequestMapping("/soundcloud/login")
    public String login(HttpSession session) throws IOException {
        String state = UUID.randomUUID().toString();
        session.setAttribute(Soundcloud.STATE, state);
        String redirectUrl = (String) session.getAttribute("serverurl");
        return  "redirect:" + api.getLoginURL(redirectUrl, state);
    }

    @RequestMapping("/soundcloud/callback")
    public String callback(@RequestParam(required = false) String code, @RequestParam String state, HttpSession session, UserDetails user) throws IOException {
        String sessionState = (String) session.getAttribute(Soundcloud.STATE);
        if (state.equals(sessionState)) {
            session.removeAttribute(Soundcloud.STATE);
            api.login(user, code, (String) session.getAttribute(Session.SERVER_URL));
            // TODO ErrorHandling
            accountController.sendGetUserAccountsResponse(user);
            return "scripts/soundcloud_callback";
        }
        throw new IllegalArgumentException("state is wrong");
    }


    @MessageMapping("/soundcloud/logout")
    public void logout(UserDetails user) throws IOException {
        Optional<AccountDetails> account = accountService.getAccount(user, StreamingService.SOUNDCLOUD);
        if (account.isPresent()) {
            accountService.deleteAccount(user, account.get());
            accountController.sendGetUserAccountsResponse(user);
        }
    }

    @MessageMapping("/getSoundcloudClientId")
    @SendToUser("/setSoundcloudClientId")
    public GetSoundcloudClientIdResponse getSoundcloudClientId() {
        return new GetSoundcloudClientIdResponse(Soundcloud.CLIENT_ID);
    }
}