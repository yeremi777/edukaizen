package com.kokuu.edukaizen.common.validators;

import org.springframework.beans.BeanWrapperImpl;

import com.kokuu.edukaizen.common.annotations.IsEqualTo;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<IsEqualTo, Object> {
    private String message;
    private String field;
    private String fieldToMatch;

    @Override
    public void initialize(IsEqualTo constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.field = constraintAnnotation.field();
        this.fieldToMatch = constraintAnnotation.fieldToMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(this.field);
        Object fieldToMatchValue = new BeanWrapperImpl(value).getPropertyValue(this.fieldToMatch);

        boolean isValid = fieldValue.equals(fieldToMatchValue);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.message)
                    .addPropertyNode(this.fieldToMatch)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
