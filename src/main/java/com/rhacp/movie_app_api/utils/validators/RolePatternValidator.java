package com.rhacp.movie_app_api.utils.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class RolePatternValidator implements ConstraintValidator<RolePattern, String> {

    private String[] subset;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return false;

        return Arrays.asList(subset).contains(value.toLowerCase());
    }

    @Override
    public void initialize(RolePattern constraintAnnotation) {
        this.subset = constraintAnnotation.anyOf();
    }
}
