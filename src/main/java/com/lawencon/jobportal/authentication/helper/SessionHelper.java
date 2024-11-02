package com.lawencon.jobportal.authentication.helper;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.lawencon.jobportal.authentication.model.UserPrinciple;
import com.lawencon.jobportal.persistent.entity.User;

@Component
public class SessionHelper {
    public static User getLoginUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrinciple) {
            return ((UserPrinciple) principal).getUser();
        } else {
            return null;
        }
    }
    public static UserPrinciple getUserPrinciple(){
        return ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
