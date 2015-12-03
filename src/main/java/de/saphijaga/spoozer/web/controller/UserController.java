package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.web.authentication.PasswordMatches;
import de.saphijaga.spoozer.web.authentication.UpdateNameNotInUse;
import de.saphijaga.spoozer.web.authentication.ValidEmail;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.ChangePasswordRequest;
import de.saphijaga.spoozer.web.domain.request.SaveUserRequest;
import de.saphijaga.spoozer.web.domain.response.GetUserDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    @SendToUser("/getUserSave")
    public GetUserDetailsResponse saveUserDetails(UserDetails user, @Payload @Valid SaveUserRequest saveUserRequest) {
        if (user.getId().equals(saveUserRequest.getId()))
            userService.saveUserDetails(user, saveUserRequest);
        /*if(result.hasErrors()){
            Optional<ObjectError> error = result.getAllErrors().stream().filter(e -> e.getCode().equals(UpdateNameNotInUse.class.getSimpleName())).findAny();
            if (error.isPresent()) {
                result.addError(new FieldError("user", "username", error.get().getDefaultMessage()));
            }
            error = result.getAllErrors().stream().filter(e -> e.getCode().equals(ValidEmail.class.getSimpleName())).findAny();
            if (error.isPresent()) {
                result.addError(new FieldError("user", "email", error.get().getDefaultMessage()));
            }
        }*/
        return new GetUserDetailsResponse(user);
    }

    @MessageMapping("/changePassword")
    @SendToUser("/getPasswordChange")
    public GetUserDetailsResponse changePassword(UserDetails user, @Payload @Valid ChangePasswordRequest changePasswordRequest){
        if(userService.getUserDetails(user.getId()).isPresent()){
            userService.changeUserPassword(user, changePasswordRequest);
        }
        return new GetUserDetailsResponse(user);
    }
}