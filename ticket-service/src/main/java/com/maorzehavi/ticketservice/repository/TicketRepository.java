package com.maorzehavi.ticketservice.repository;

import com.maorzehavi.ticketservice.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<List<Ticket>> findByEventId(Long eventId);
}
