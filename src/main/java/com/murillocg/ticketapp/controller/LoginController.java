package com.murillocg.ticketapp.controller;

import com.murillocg.ticketapp.entity.User;
import com.murillocg.ticketapp.model.LoginRequest;
import com.murillocg.ticketapp.model.LoginResponseDTO;
import com.murillocg.ticketapp.repository.UserRepository;
import com.murillocg.ticketapp.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, TokenService tokenService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequest loginRequest) {
        LOGGER.info("Login requested with the following data: {}", loginRequest);
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());

        Authentication auth = authenticationManager.authenticate(authenticationRequest);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String accessToken = tokenService.generateToken(userDetails.getUsername());
        User user = userRepository.findOneByLogin(userDetails.getUsername());

        return ResponseEntity.ok(new LoginResponseDTO(user, accessToken));
    }

}
