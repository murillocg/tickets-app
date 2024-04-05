package com.murillocg.ticketapp.security;

import com.murillocg.ticketapp.entity.User;
import com.murillocg.ticketapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
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

    public void checkCurrentUserIsOwner(User owner) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        if (!currentUser.equals(owner)) {
            throw new AccessDeniedException("Access denied");
        }
    }

}
