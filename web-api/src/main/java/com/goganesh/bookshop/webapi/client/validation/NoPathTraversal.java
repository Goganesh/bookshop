package com.goganesh.bookshop.webapi.client.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoPathTraversalValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoPathTraversal {

    String message() default "contains illegal symbols";

    Class[] groups() default {};

    Class[] payload() default {};
}
