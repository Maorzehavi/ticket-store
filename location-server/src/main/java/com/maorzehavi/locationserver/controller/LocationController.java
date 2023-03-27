package com.maorzehavi.locationserver.controller;

import com.maorzehavi.locationserver.model.dto.request.LocationRequest;
import com.maorzehavi.locationserver.model.dto.response.LocationResponse;
import com.maorzehavi.locationserver.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;


    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No locations found")
        ));
    }

    @GetMapping("{id}")
    public ResponseEntity<LocationResponse> getLocationById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getLocationById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No location found for id: " + id)
        ));
    }


    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(@RequestBody LocationRequest locationDto) {
        var createdLocation = locationService.createLocation(locationDto).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create location")
        );
        URI location = URI.create(String.format("/api/v1/locations/%s", createdLocation.getId()));
        return ResponseEntity.created(location).body(createdLocation);
    }

    @PutMapping("{id}")
    public ResponseEntity<LocationResponse> updateLocation(@PathVariable Long id, @RequestBody LocationRequest locationDto) {
        return ResponseEntity.ok(locationService.updateLocation(id, locationDto).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update location")
        ));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }



}
