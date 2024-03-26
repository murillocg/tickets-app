package com.murillocg.ticketapp.controller;


import com.murillocg.ticketapp.entity.Ticket;
import com.murillocg.ticketapp.entity.User;
import com.murillocg.ticketapp.enums.Status;
import com.murillocg.ticketapp.model.NewTicketRequest;
import com.murillocg.ticketapp.model.TicketDTO;
import com.murillocg.ticketapp.model.TicketRatingRequest;
import com.murillocg.ticketapp.repository.TicketRepository;
import com.murillocg.ticketapp.security.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@RestController
public class TicketController {

    private final TicketRepository ticketRepository;

    private final AuthenticationService authenticationService;

    @Autowired
    public TicketController(TicketRepository ticketRepository, AuthenticationService authenticationService) {
        this.ticketRepository = ticketRepository;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/api/tickets")
    public void createTicket(@RequestBody @Valid NewTicketRequest newTicketRequest) {

        User currentUser = authenticationService.getCurrentUser();
        Ticket newTicket = new Ticket();
        newTicket.setCreatedDate(LocalDateTime.now());
        newTicket.setUser(currentUser);
        newTicket.setStatus(Status.OPEN);
        newTicket.setMessage(newTicketRequest.message());
        newTicket.setSubject(newTicketRequest.subject());

        ticketRepository.save(newTicket);
        //TODO: notify admin that a ticket has been created
    }

    @PostMapping("/api/tickets/{id}/rating")
    public ResponseEntity<Void> rateSolvedTicket(@PathVariable Long id,
                                                 @RequestBody @Valid TicketRatingRequest ticketRatingRequest) {
        Ticket ticketToRate = ticketRepository.getReferenceById(id);

        User currentUser = authenticationService.getCurrentUser();
        if (!ticketToRate.getUser().equals(currentUser)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (ticketToRate.getStatus() != Status.CLOSED) {
            return ResponseEntity.badRequest().build();
        }
        ticketToRate.setRating(ticketRatingRequest.rating());
        ticketRepository.save(ticketToRate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/tickets")
    @Transactional(readOnly = true)
    public ResponseEntity<List<TicketDTO>> getAllTickets(
            @RequestParam(required = false) Long id, @RequestParam(required = false) String subject) {

        User currentUser = authenticationService.getCurrentUser();

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Ticket exampleTicket = new Ticket();
        exampleTicket.setId(id);
        exampleTicket.setSubject(subject);
        exampleTicket.setUser(currentUser);
        Example<Ticket> ticketFilters = Example.of(exampleTicket, matcher);


        //TODO: List all tickets created by me
        List<Ticket> tickets = ticketRepository.findAll(ticketFilters);

        List<TicketDTO> ticketsResponse = tickets.stream().map(TicketDTO::new).toList();
        //TODO: Implement page, and filters for id and subject
        return ResponseEntity.ok().body(ticketsResponse);
    }


/*
• Eu como usuário desejo logar para abrir um chamado;
• Eu como usuário desejo pontuar meu atendimento do chamado que foi encerrado (pesquisa
de satisfação);
Eu como usuário desejo listar na aplicação WEB, com paginação, todos os meus chamados,
ou seja, somente os chamados que foram abertos por mim utilizando um filtro com um
único campo que deve encontrar um registro pelo assunto ou pelo ID - código do chamado.
 */

    /*
• Eu como usuário do gerenciador desejo realizar login;
• Eu como usuário do gerenciador desejo receber atualizações, em tempo real, de chamados
solicitados por usuários;
• Eu como usuário do gerenciador desejo finalizar um atendimento;
     */
}
