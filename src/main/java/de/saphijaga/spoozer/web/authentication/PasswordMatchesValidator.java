package de.saphijaga.spoozer.web.authentication;

import de.saphijaga.spoozer.web.domain.request.RegisterUserRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {       
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        RegisterUserRequest user = (RegisterUserRequest) obj;
        return user.getPassword().equals(user.getPassword2());
    }     
}