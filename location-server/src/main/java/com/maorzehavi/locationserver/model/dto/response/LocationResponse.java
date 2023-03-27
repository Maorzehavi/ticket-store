package com.maorzehavi.locationserver.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LocationResponse {
    private Long id;

    private String name;

    private String address;

    private String city;

    private Integer capacity;
}
