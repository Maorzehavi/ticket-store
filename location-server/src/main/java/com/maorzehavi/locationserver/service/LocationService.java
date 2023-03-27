package com.maorzehavi.locationserver.service;

import com.maorzehavi.locationserver.model.dto.request.LocationRequest;
import com.maorzehavi.locationserver.model.dto.response.LocationResponse;
import com.maorzehavi.locationserver.model.entity.Location;
import com.maorzehavi.locationserver.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public Optional<List<LocationResponse>> getAllLocations(){
        var locations = locationRepository.findAll().stream().map(this::mapToDto).toList();
        return Optional.of(locations);
    }

    public Optional<LocationResponse> getLocationById(Long id){
        return locationRepository.findById(id).map(this::mapToDto);
    }

    public Optional<LocationResponse> createLocation(LocationRequest locationDto){
        var location = locationRepository.save(mapToEntity(locationDto));
        return Optional.of(mapToDto(location));
    }

    public Optional<LocationResponse> updateLocation(Long id, LocationRequest locationDto){
        var location = locationRepository.findById(id).map(l -> {
            l.setName(locationDto.getName());
            l.setAddress(locationDto.getAddress());
            l.setCity(locationDto.getCity());
            l.setCapacity(locationDto.getCapacity());
            return locationRepository.save(l);
        }).orElseThrow(
                () -> new RuntimeException("Location not found")
        );
        return Optional.of(mapToDto(location));
    }

    public void deleteLocation(Long id){
        locationRepository.deleteById(id);
    }

    public LocationResponse mapToDto(Location location){
        return LocationResponse.builder()
                .id(location.getId())
                .name(location.getName())
                .address(location.getAddress())
                .city(location.getCity())
                .capacity(location.getCapacity())
                .build();
    }

    public Location mapToEntity(LocationRequest locationDto){
        return Location.builder()
                .name(locationDto.getName())
                .address(locationDto.getAddress())
                .city(locationDto.getCity())
                .capacity(locationDto.getCapacity())
                .build();
    }

}
