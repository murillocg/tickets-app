package com.murillocg.ticketapp.model;

import com.murillocg.ticketapp.entity.Ticket;
import com.murillocg.ticketapp.enums.TicketStatus;

import java.time.LocalDateTime;

public record UserTicketDTO(Long id, LocalDateTime createdDate, TicketStatus status, String subject, String message) {

    public UserTicketDTO(Ticket entity) {
        this(entity.getId(),
                entity.getCreatedDate(),
                entity.getStatus(),
                entity.getSubject(),
                entity.getMessage());
    }

}
