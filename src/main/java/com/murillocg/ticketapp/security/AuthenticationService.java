package com.murillocg.ticketapp.security;

import com.murillocg.ticketapp.entity.User;
import com.murillocg.ticketapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        UserDetails springSecurityUser =
                (UserDetails) securityContext
                        .getAuthentication().getPrincipal();

        return userRepository.findOneByLogin(springSecurityUser.getUsername());
    }

}
