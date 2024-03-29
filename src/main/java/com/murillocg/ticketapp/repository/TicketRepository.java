package com.murillocg.ticketapp.repository;

import com.murillocg.ticketapp.entity.Ticket;
import com.murillocg.ticketapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByUser(User user);

}
