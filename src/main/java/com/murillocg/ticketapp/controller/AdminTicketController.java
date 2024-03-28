package com.murillocg.ticketapp.controller;

import com.murillocg.ticketapp.entity.Ticket;
import com.murillocg.ticketapp.enums.TicketStatus;
import com.murillocg.ticketapp.model.AdminTicketDTO;
import com.murillocg.ticketapp.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Transactional
@RestController
public class AdminTicketController {

    private final TicketRepository ticketRepository;

    @Autowired
    public AdminTicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/api/admin/tickets")
    @Transactional(readOnly = true)
    public ResponseEntity<List<AdminTicketDTO>> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();

        List<AdminTicketDTO> ticketsResponse = tickets.stream().map(AdminTicketDTO::new).toList();
        //implement page, and filters for id and subject
        return ResponseEntity.ok().body(ticketsResponse);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/api/admin/tickets/{id}/close")
    public ResponseEntity<Void> closeTicket(@PathVariable Long id) {
        //TODO: What if the ticket does not exist?
        Ticket ticket = ticketRepository.getReferenceById(id);
        if (ticket.getStatus() == TicketStatus.CLOSED) {
            return ResponseEntity.badRequest().build();
        }

        ticket.setStatus(TicketStatus.CLOSED);
        ticketRepository.save(ticket);
        return ResponseEntity.ok().build();
    }

}
