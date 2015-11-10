package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.response.GetUserDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by samuel on 16.10.15.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @MessageMapping("/getUserDetails")
    @SendTo("/setUserDetails")
    public GetUserDetailsResponse getUserDetails(UserDetails user) {
        return new GetUserDetailsResponse(user);
    }

    @MessageMapping("/saveUserDetails")
    @SendTo("/setUserDetails")
    public GetUserDetailsResponse saveUserDetails(UserDetails user) {
        // TODO save UserDetails
        return getUserDetails(user);
    }
}