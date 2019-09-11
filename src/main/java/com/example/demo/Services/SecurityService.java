package com.example.demo.Services;

import com.example.demo.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SecurityService {

    private final UserDetailsService userDetailsService;


    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    @Autowired
    public SecurityService(@Qualifier(value = "myUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void autoLogin(String username, String password, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        logger.warn("Password: " + userDetails.getPassword());
        logger.warn("Authorities: " + userDetails.getAuthorities().toString());

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password,userDetails.getAuthorities());
        token.setDetails(new WebAuthenticationDetails(request));
        //wrong here

        SecurityContextHolder.getContext().setAuthentication(token);
        logger.warn("Token auth is: " +(token.isAuthenticated()));
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }
}
