package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.web.details.HistoryDetails;
import de.saphijaga.spoozer.web.details.TrackDetails;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.AddHTrackRequest;
import de.saphijaga.spoozer.web.domain.request.ChangePasswordRequest;
import de.saphijaga.spoozer.web.domain.request.SaveUserRequest;
import de.saphijaga.spoozer.web.domain.response.GetUserDetailsResponse;
import de.saphijaga.spoozer.web.domain.response.HistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by samuel on 16.10.15.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/getUserDetails")
    @SendToUser("/setUserDetails")
    public GetUserDetailsResponse getUserDetails(UserDetails user) {
        return new GetUserDetailsResponse(user);
    }

    @MessageMapping("/saveUserDetails")
    @SendToUser("/savedUserDetails")
    public GetUserDetailsResponse saveUserDetails(UserDetails user, @Valid @Payload SaveUserRequest saveUserRequest) {
        //        if (result.hasErrors()) {
        //            System.out.println("errors found");
                    /*Optional<ObjectError> error = result.getAllErrors().stream().filter(e -> e.getCode().equals(UpdateNameNotInUse.class.getSimpleName())).findAny();
                    if (error.isPresent()) {
                        result.addError(new FieldError("user", "nameError", error.get().getDefaultMessage()));
                    }
                    messagingTemplate.convertAndSendToUser(user.getUsername(), "/errorSaveUserDetails", result.getFieldErrors());*/
        //        } else {
        if (user.getId().equals(saveUserRequest.getId())) {
            userService.saveUserDetails(user, saveUserRequest);
        }
        //        }
        return new GetUserDetailsResponse(userService.getUserDetails(user.getId()).orElse(user));
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
    public void AddHistoryTrack(UserDetails user, @Payload AddHTrackRequest addHTrackRequest) {
        if (userService.getUserDetails(user.getId()).isPresent())
            userService.addSongToHistory(user, addHTrackRequest);
    }

    @MessageMapping("/getHistory")
    @SendToUser("/setHistory")
    public HistoryResponse getHistory(UserDetails user){
        Map<Date, TrackDetails> history = userService.getHistoryMap(user);
        HistoryDetails historyDetails = new HistoryDetails();
        historyDetails.setHistory(history);
        return new HistoryResponse(historyDetails);
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