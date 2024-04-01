package com.murillocg.ticketapp.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TicketRatingRequest(
        @NotNull
        @Min(0)
        @Max(5)
        Integer rating
) {
}
