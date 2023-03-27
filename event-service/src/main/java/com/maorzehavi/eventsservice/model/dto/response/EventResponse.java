package com.maorzehavi.eventsservice.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EventResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private Double ticketPrice;
    private Integer ticketsToSell;

}
