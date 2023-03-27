package com.maorzehavi.eventsservice.controller;

import com.maorzehavi.eventsservice.model.dto.request.EventRequest;
import com.maorzehavi.eventsservice.model.dto.response.EventLocationResponse;
import com.maorzehavi.eventsservice.model.dto.response.EventResponse;
import com.maorzehavi.eventsservice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {

    public final EventService eventService;


    @GetMapping
    public ResponseEntity<List<EventResponse>> getEvents() {
        try {
        return ResponseEntity.ok(eventService.getAllEvents().orElseThrow());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<EventLocationResponse> getEventById(@PathVariable Long id) {
        try {
        return ResponseEntity.ok(eventService.getEventById(id).orElseThrow());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<EventLocationResponse> createEvent(@RequestBody EventRequest eventDTO) {
        try {
            var createdEvent = eventService.createEvent(eventDTO).orElseThrow();
            URI location = URI.create(String.format("/api/v1/events/%s", createdEvent.getEvent().getId()));
            return ResponseEntity.created(location).body(createdEvent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long id, @RequestBody EventRequest eventDTO) {
        try {
            return ResponseEntity.ok(eventService.updateEvent(id, eventDTO).orElseThrow());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
