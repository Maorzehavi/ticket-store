package com.maorzehavi.ticketservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "event-service")
public interface EventClient {

    @GetMapping("/api/v1/events/{id}/price")
    Integer getTotalTickets(@PathVariable Long id);

    @GetMapping("/api/v1/events/{id}")
    EventDto getById(@PathVariable Long id);
}
