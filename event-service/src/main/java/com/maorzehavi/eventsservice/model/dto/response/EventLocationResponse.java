package com.maorzehavi.eventsservice.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EventLocationResponse {

    private EventResponse event;
    private LocationResponse location;
}
