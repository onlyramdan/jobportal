package com.lawencon.jobportal.validation.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MetaDatas {
    MetaData[] value() default{};
}
