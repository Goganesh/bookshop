package com.goganesh.bookshop.webapi.client.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NoPathTraversalValidator implements ConstraintValidator<NoPathTraversal, String> {

    private static final Pattern PATTERN = Pattern.compile("[-_a-zA-Z_0-9]+");

    @Override
    public void initialize(NoPathTraversal constraintAnnotation) {
        // Nothing to do
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return PATTERN.matcher(value).matches();
    }

}
