package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.response.GetUsetDetailsResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by samuel on 16.10.15.
 */
@RestController
public class UserController {
    @MessageMapping("/getUserDetails")
    @SendTo("/setUserDetails")
    public GetUsetDetailsResponse getUserDetails(UserDetails user) {
        return new GetUsetDetailsResponse(user);
    }
}