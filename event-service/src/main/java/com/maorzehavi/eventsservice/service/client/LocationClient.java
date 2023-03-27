package com.maorzehavi.eventsservice.service.client;

import com.maorzehavi.eventsservice.model.dto.response.LocationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "location-service")
public interface LocationClient {

    @GetMapping("/api/v1/location/{id}")
    LocationResponse getLocation(@PathVariable Long id);

}
