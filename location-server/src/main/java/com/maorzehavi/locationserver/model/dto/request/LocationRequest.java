package com.maorzehavi.locationserver.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LocationRequest {

    private String name;

    private String address;

    private String city;

    private Integer capacity;
}
