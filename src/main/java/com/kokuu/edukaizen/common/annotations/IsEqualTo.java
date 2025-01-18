package com.kokuu.edukaizen.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kokuu.edukaizen.common.validators.FieldMatchValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = FieldMatchValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsEqualTo {
    String message() default "fields does not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field();

    String fieldToMatch();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        IsEqualTo[] value();
    }
}
