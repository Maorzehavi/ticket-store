package com.maorzehavi.eventsservice.service;

import com.maorzehavi.eventsservice.model.dto.request.EventRequest;
import com.maorzehavi.eventsservice.model.dto.response.EventLocationResponse;
import com.maorzehavi.eventsservice.model.dto.response.EventResponse;
import com.maorzehavi.eventsservice.repository.EventRepository;
import com.maorzehavi.eventsservice.model.entity.Event;
import com.maorzehavi.eventsservice.service.client.LocationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    @LoadBalanced
    private final LocationClient locationClient;


    public Optional<List<EventResponse>> getAllEvents() {
        return Optional.of(eventRepository.findAll().stream().map(this::mapToDTO).toList());
    }

    public Optional<EventLocationResponse> getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("No event found for id: " + id)
                );
        var location = locationClient.getLocation(event.getLocationId());
        var obj = EventLocationResponse.builder()
                .event(mapToDTO(event))
                .location(location)
                .build();
        return Optional.of(obj);
    }

    public Optional<EventLocationResponse> createEvent(EventRequest event) {
        Event eventToSave = mapToEntity(event);
        var location = locationClient.getLocation(eventToSave.getLocationId());
        if (event.getTicketsToSell()>location.getCapacity()){
            throw new RuntimeException(location.getName()+" has only "+location.getCapacity()+" capacity");
        }
        Event savedEvent = eventRepository.save(eventToSave);
        var obj = EventLocationResponse.builder()
                .event(mapToDTO(savedEvent))
                .location(location)
                .build();
        return Optional.of(obj);
    }

    public Optional<EventResponse> updateEvent(Long id, EventRequest event) {
        Event eventToUpdate = eventRepository.findById(id)
                .orElseThrow();
        eventToUpdate.setName(event.getName());
        eventToUpdate.setDescription(event.getDescription());
        eventToUpdate.setTicketPrice(event.getTicketPrice());
        var location = locationClient.getLocation(eventToUpdate.getLocationId());
        if (event.getTicketsToSell()>location.getCapacity()){
            throw new RuntimeException(location.getName()+" has only "+location.getCapacity()+" capacity");
        }
        eventToUpdate.setTicketsToSell(event.getTicketsToSell());
        Event updatedEvent = eventRepository.save(eventToUpdate);
        return Optional.of(mapToDTO(updatedEvent));
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }


    public Event mapToEntity(EventRequest event) {
        return Event.builder()
                .name(event.getName())
                .description(event.getDescription())
                .date(event.getDate())
                .ticketPrice(event.getTicketPrice())
                .ticketsToSell(event.getTicketsToSell())
                .locationId(event.getLocationId())
                .build();
    }

    public EventResponse mapToDTO(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .date(event.getDate())
                .ticketPrice(event.getTicketPrice())
                .ticketsToSell(event.getTicketsToSell())
                .build();
    }

}
