package com.murillocg.ticketapp.model;

import com.murillocg.ticketapp.entity.User;

public record LoginResponseDTO(String login, String firstName, String lastName, String role, String accessToken) {

    public LoginResponseDTO(User user, String accessToken) {
        this(user.getLogin(), user.getFirstName(), user.getLastName(), user.getRole().toString(), accessToken);
    }
}
