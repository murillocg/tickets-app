package com.murillocg.ticketapp.service;

import com.murillocg.ticketapp.entity.User;
import com.murillocg.ticketapp.enums.UserRole;
import com.murillocg.ticketapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class UserDetailsSecurityService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsSecurityService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Authenticating {}", username);

        String lowercaseUsername = username.toLowerCase(Locale.ENGLISH);
        User user = userRepository.findOneByLogin(lowercaseUsername);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        logger.info("User {} authenticated successfully!", lowercaseUsername);
        List<GrantedAuthority> grantedAuthorities = user.getRole() == UserRole.USER ?
                List.of(new SimpleGrantedAuthority("ROLE_USER")) :
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorities);
    }

}
