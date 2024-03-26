package com.murillocg.ticketapp.model;

import jakarta.validation.constraints.NotNull;

public record TicketRatingRequest(@NotNull Integer rating) {
}
