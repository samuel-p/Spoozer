package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.web.authentication.Session;
import de.saphijaga.spoozer.web.details.HistoryDetails;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.AddHTrackRequest;
import de.saphijaga.spoozer.web.domain.request.ChangePasswordRequest;
import de.saphijaga.spoozer.web.domain.request.SaveUserRequest;
import de.saphijaga.spoozer.web.domain.response.GetUserDetailsResponse;
import de.saphijaga.spoozer.web.domain.response.HistoryResponse;
import de.saphijaga.spoozer.web.domain.response.PropertiesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

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
    @SendToUser("/savedUserDetails")
    public GetUserDetailsResponse saveUserDetails(HttpSession session, UserDetails user, @Valid @Payload SaveUserRequest saveUserRequest) {
        if (user.getId().equals(saveUserRequest.getId())) {
            Optional<UserDetails> userDetails = userService.saveUserDetails(user, saveUserRequest);
            session.setAttribute(Session.USER, userDetails.orElse(user));
            return new GetUserDetailsResponse(userDetails.orElse(user));
        }
        return new GetUserDetailsResponse(user);
    }

    @MessageMapping("/savePassword")
    @SendToUser("/savedPassword")
    public GetUserDetailsResponse changePassword(UserDetails user, @Payload @Valid ChangePasswordRequest changePasswordRequest) {
        if (userService.getUserDetails(user.getId()).isPresent()) {
            userService.changeUserPassword(user, changePasswordRequest);
        }
        return new GetUserDetailsResponse(user);
    }

    @MessageMapping("/addHTrack")
    public void addHistoryTrack(UserDetails user, @Payload AddHTrackRequest addHTrackRequest) {
        if (userService.getUserDetails(user.getId()).isPresent())
            userService.addSongToHistory(user, addHTrackRequest);
    }

    @MessageMapping("/getHistory")
    @SendToUser("/setHistory")
    public HistoryResponse getHistory(UserDetails user) {
        Map<Date, TrackDetails> history = userService.getHistoryMap(user);
        HistoryDetails historyDetails = new HistoryDetails();
        historyDetails.setHistory(history);
        return new HistoryResponse(historyDetails);
    }

    @MessageMapping("/saveProperties")
    public void saveProperties(UserDetails user, @Payload Map<String, Object> properties) {
        userService.saveProperties(user, properties);
    }

    @MessageMapping("/getProperties")
    @SendToUser("/setProperties")
    public PropertiesResponse getProperties(UserDetails user) {
        Map<String, Object> properties = userService.getProperties(user);
        return new PropertiesResponse(properties);
    }

    @MessageMapping("/clearHistory")
    @SendToUser("/setHistory")
    public HistoryResponse clearHistory(UserDetails user){
        userService.clearUserHistory(user);
        Map<Date, TrackDetails> history = userService.getHistoryMap(user);
        HistoryDetails historyDetails = new HistoryDetails();
        historyDetails.setHistory(history);
        return new HistoryResponse(historyDetails);
    }
}