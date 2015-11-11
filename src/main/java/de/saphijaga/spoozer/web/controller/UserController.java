package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.response.GetUserDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by samuel on 16.10.15.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @MessageMapping("/getUserDetails")
    @SendToUser("/setUserDetails")
    public GetUserDetailsResponse getUserDetails(UserDetails user) {
        return new GetUserDetailsResponse(user);
    }

    @MessageMapping("/saveUserDetails")
    @SendToUser("/setUserDetails")
    public GetUserDetailsResponse saveUserDetails(UserDetails user) {
        // TODO save UserDetails
        return new GetUserDetailsResponse(user);
    }
}