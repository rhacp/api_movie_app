package com.rhacp.movie_app_api.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = RolePatternValidator.class)
public @interface RolePattern {

    String[] anyOf();

    String message() default "Role must not be null and one of the following: \"ROLE_USER\", \"ROLE_ADMIN\" or \"ROLE_USER,ROLE_ADMIN\".";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
