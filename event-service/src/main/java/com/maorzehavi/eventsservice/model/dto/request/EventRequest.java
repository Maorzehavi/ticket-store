package com.maorzehavi.eventsservice.model.dto.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EventRequest {
    private String name;
    private String description;
    private LocalDate date;
    private Integer ticketsToSell;
    private Double ticketPrice;
    private Long locationId;

}
