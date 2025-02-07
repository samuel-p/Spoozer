package de.saphijaga.spoozer.web.authentication;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by samuel on 16.10.15.
 */

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordComplexityValidator.class)
@Documented
public @interface ComplexPassword {
    String message() default "Password to weak";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
