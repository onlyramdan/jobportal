package com.lawencon.jobportal.validation.annotation;

import  java.lang.annotation.*;


@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SortProperty {
    String entityProperty();

}
