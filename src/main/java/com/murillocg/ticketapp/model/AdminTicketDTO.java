package com.murillocg.ticketapp.model;

import com.murillocg.ticketapp.entity.Ticket;
import com.murillocg.ticketapp.enums.TicketStatus;

import java.time.LocalDateTime;

public record AdminTicketDTO(Long id, LocalDateTime createdDate, String username, TicketStatus status, String subject, String message) {

    public AdminTicketDTO(Ticket entity) {
        this(entity.getId(),
                entity.getCreatedDate(),
                String.join(" ", entity.getUser().getFirstName(), entity.getUser().getLastName()),
                entity.getStatus(),
                entity.getSubject(),
                entity.getMessage());
    }
}
