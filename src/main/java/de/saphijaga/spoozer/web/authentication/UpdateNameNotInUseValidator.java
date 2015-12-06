package de.saphijaga.spoozer.web.authentication;

import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.SaveUserRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

/**
 * Created by xce35l5 on 20.11.2015.
 */
public class UpdateNameNotInUseValidator implements ConstraintValidator<UpdateNameNotInUse, Object> {
    @Autowired
    private UserService userService;

    @Override
    public void initialize(UpdateNameNotInUse constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        SaveUserRequest saveUserRequest = (SaveUserRequest) obj;
        Optional<UserDetails> user = userService.getUserDetailsByUsername(saveUserRequest.getUsername());
        return user.isPresent() && user.get().getId().equals(saveUserRequest.getId());
    }


}
