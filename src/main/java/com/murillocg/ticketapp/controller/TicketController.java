package com.murillocg.ticketapp.controller;


import com.murillocg.ticketapp.entity.Ticket;
import com.murillocg.ticketapp.enums.Status;
import com.murillocg.ticketapp.model.NewTicketRequest;
import com.murillocg.ticketapp.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TicketController {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @PostMapping("/tickets")
    public void createTicket(@RequestBody @Validated NewTicketRequest newTicketRequest) {

        Ticket newTicket = new Ticket();
        newTicket.setStatus(Status.OPEN);
        newTicket.setMessage(newTicketRequest.message());
        newTicket.setSubject(newTicketRequest.subject());

        ticketRepository.save(newTicket);
        //TODO: notify admin that a ticket has been created
    }

    @PostMapping("/tickets/{id}/rate")
    public ResponseEntity<Void> rateSolvedTicket(@PathVariable Long id) {
        Ticket ticketToRate = ticketRepository.getReferenceById(id);
        //TODO: are you the user that has created this ticket?
        if (ticketToRate.getStatus() != Status.CLOSED) {
            return ResponseEntity.badRequest().build();
        }
        ticketToRate.setRating(5);
        ticketRepository.save(ticketToRate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tickets")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        //implement page, and filters for id and subject
        return ResponseEntity.ok().body(tickets);
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
