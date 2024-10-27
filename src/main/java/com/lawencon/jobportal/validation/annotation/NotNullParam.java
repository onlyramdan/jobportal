package com.lawencon.jobportal.validation.annotation;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotBlank;


@NotBlank()
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNullParam {
    String messaege()  default "is required";
    String fieldName() default "";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};

}
