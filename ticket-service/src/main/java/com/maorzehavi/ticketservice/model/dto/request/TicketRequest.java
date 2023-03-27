package com.maorzehavi.ticketservice.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketRequest {

    Long eventId;

    Double price;

    Boolean isAvailable;
}
