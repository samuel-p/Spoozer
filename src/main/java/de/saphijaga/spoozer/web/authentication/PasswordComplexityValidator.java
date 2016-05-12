package de.saphijaga.spoozer.web.authentication;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by samuel on 16.10.15.
 */
public class PasswordComplexityValidator implements ConstraintValidator<ComplexPassword, String> {
    @Override
    public void initialize(ComplexPassword constraintAnnotation) {
        // not used
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password.matches(".*[a-z]+.*") && password.matches(".*[A-Z]+.*") && password.matches(".*[0-9]+.*") && password.length() >= 8;
    }
}