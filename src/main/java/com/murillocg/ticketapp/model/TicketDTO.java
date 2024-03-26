package com.murillocg.ticketapp.model;

import com.murillocg.ticketapp.entity.Ticket;
import com.murillocg.ticketapp.enums.Status;

import java.time.LocalDateTime;

public record TicketDTO(Long id, LocalDateTime createdDate, Status status, String subject, String message) {

    public TicketDTO(Ticket entity) {
        this(entity.getId(),
                entity.getCreatedDate(),
                entity.getStatus(),
                entity.getSubject(),
                entity.getMessage());
    }

}
