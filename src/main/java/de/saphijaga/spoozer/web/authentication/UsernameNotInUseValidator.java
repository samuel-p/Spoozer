package de.saphijaga.spoozer.web.authentication;

import de.saphijaga.spoozer.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by samuel on 16.10.15.
 */
public class UsernameNotInUseValidator implements ConstraintValidator<UsernameNotInUse, String> {
    @Autowired
    private UserService userService;

    @Override
    public void initialize(UsernameNotInUse constraintAnnotation) {
        // not used
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return !userService.getUserDetailsByUsername(username).isPresent();
    }
}