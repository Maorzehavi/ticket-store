package com.maorzehavi.ticketservice.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "location-service")
public interface LocationClient {

    @GetMapping("/api/v1/location/{id}/capacity")
    Integer getLocationCapacity(@PathVariable Long id);

}
