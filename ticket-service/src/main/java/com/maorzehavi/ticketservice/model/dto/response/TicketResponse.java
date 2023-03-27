package com.maorzehavi.ticketservice.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketResponse {

    Long id;

    Long eventId;

    Double price;

    Boolean isAvailable;
}
