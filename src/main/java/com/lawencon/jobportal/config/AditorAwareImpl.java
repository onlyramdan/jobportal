package com.lawencon.jobportal.config;

import java.util.Optional;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportal.authentication.helper.SessionHelper;

public class AditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            try{
                return Optional.of(InetAddress.getLocalHost().getHostAddress());    
            } catch (UnknownHostException e){
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unkhown host");
            }
        } else {
            return Optional.of(SessionHelper.getLoginUser() == null ?  "System" : SessionHelper.getLoginUser().getUsername());
        }
    }
    
}
