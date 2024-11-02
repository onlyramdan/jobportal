package com.lawencon.jobportal.authentication.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lawencon.jobportal.authentication.service.JwtService;
import com.lawencon.jobportal.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
   
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
    FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    String token = null;
    String userName = null;

      if(authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring((7));
        userName = jwtService.extractUserName(token);
      }

      if(userName != null) {
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userName);
        if(jwtService.validateToken(token, userDetails)) {
          SecurityContext context = SecurityContextHolder.createEmptyContext();
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          context.setAuthentication(authToken);
          SecurityContextHolder.setContext(context);
        };
      }
      filterChain.doFilter(request, response);
    }
}
