package de.saphijaga.spoozer.web.authentication;

import de.saphijaga.spoozer.web.domain.request.ChangePasswordRequest;
import de.saphijaga.spoozer.web.domain.request.RegisterUserRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by xce35l5 on 30.11.2015.
 */
public class PasswordChangeMatchesValidator implements ConstraintValidator<PasswordChangeMatches, Object> {
    @Override
    public void initialize(PasswordChangeMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        ChangePasswordRequest request = (ChangePasswordRequest) obj;
        return request.getPassword().equals(request.getPassword2());
    }
}