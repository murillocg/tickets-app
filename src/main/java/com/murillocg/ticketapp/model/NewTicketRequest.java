package com.murillocg.ticketapp.model;

import jakarta.validation.constraints.NotBlank;

public record NewTicketRequest(@NotBlank String subject, @NotBlank String message) {
}
