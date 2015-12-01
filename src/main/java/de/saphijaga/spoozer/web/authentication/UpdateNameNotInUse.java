package de.saphijaga.spoozer.web.authentication;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by xce35l5 on 20.11.2015.
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UpdateNameNotInUseValidator.class)
@Documented
public @interface UpdateNameNotInUse {
    String message() default "Username already in use";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}