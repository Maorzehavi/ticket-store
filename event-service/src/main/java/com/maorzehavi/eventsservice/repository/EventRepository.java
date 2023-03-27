package com.maorzehavi.eventsservice.repository;

import com.maorzehavi.eventsservice.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event,Long> {

    @Query("select e.ticketPrice from Event e where e.id = ?1")
    Optional<Integer> getTicketPriceById(Long id);
}
