package com.maorzehavi.ticketservice.controller;

import com.maorzehavi.ticketservice.model.dto.request.TicketRequest;
import com.maorzehavi.ticketservice.model.dto.response.TicketResponse;
import com.maorzehavi.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getTickets().orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No tickets found")
                )
        );
    }

    @GetMapping("event/{eventId}")
    public ResponseEntity<List<TicketResponse>> getTicketsByEventId(@PathVariable Long eventId) {
        return ResponseEntity.ok(ticketService.getTicketsByEventId(eventId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No tickets found for event id: " + eventId)
        ));
    }

    @GetMapping("{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No ticket found for id: " + id)
        ));
    }

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@RequestBody TicketRequest ticketDto) {
        var createdTicket = ticketService.createTicket(ticketDto).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create ticket")
        );
        URI location = URI.create(String.format("/api/v1/tickets/%s", createdTicket.getId()));
        return ResponseEntity.created(location).body(createdTicket);
    }

    @PutMapping("{id}")
    public ResponseEntity<TicketResponse> updateTicket(@PathVariable Long id, @RequestBody TicketRequest ticketDto) {
        return ResponseEntity.ok(ticketService.updateTicket(id, ticketDto).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update ticket")
        ));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

}
