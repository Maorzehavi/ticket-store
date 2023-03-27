package com.maorzehavi.ticketservice.service;

import com.maorzehavi.ticketservice.model.dto.request.TicketRequest;
import com.maorzehavi.ticketservice.model.dto.response.TicketResponse;
import com.maorzehavi.ticketservice.model.entity.Ticket;
import com.maorzehavi.ticketservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public Optional<List<TicketResponse>> getTickets() {
        var tickets = ticketRepository.findAll();
        return Optional.of(tickets.stream()
                .map(this::mapToDTO)
                .toList());
    }

    public Optional<List<TicketResponse>> getTicketsByEventId(Long eventId) {
        var tickets = ticketRepository.findByEventId(eventId).orElseThrow(
                () -> new RuntimeException("No tickets found for event id: " + eventId)
        );
        return Optional.of(tickets.stream()
                .map(this::mapToDTO)
                .toList());
    }

    public Optional<TicketResponse> getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId).map(this::mapToDTO);
    }

    public Optional<TicketResponse> createTicket(TicketRequest ticketDto) {
        var ticket = mapToEntity(ticketDto);
        return Optional.of(mapToDTO(ticketRepository.save(ticket)));
    }

    public Optional<TicketResponse> updateTicket(Long id, TicketRequest ticket){
        return ticketRepository.findById(id).map(
                ticketToUpdate -> {
                    ticketToUpdate.setEventId(ticket.getEventId());
                    ticketToUpdate.setPrice(ticket.getPrice());
                    ticketToUpdate.setIsAvailable(ticket.getIsAvailable());
                    return mapToDTO(ticketRepository.save(ticketToUpdate));
                }
        );
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    private Ticket mapToEntity(TicketRequest ticketDto) {
        return Ticket.builder()
                .eventId(ticketDto.getEventId())
                .price(ticketDto.getPrice())
                .isAvailable(ticketDto.getIsAvailable())
                .build();
    }

    private TicketResponse mapToDTO(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .eventId(ticket.getEventId())
                .price(ticket.getPrice())
                .isAvailable(ticket.getIsAvailable())
                .build();
    }


}
