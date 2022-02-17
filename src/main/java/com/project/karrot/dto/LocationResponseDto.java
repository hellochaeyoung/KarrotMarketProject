package com.project.karrot.dto;

import com.project.karrot.domain.Location;
import lombok.Getter;

@Getter
public class LocationResponseDto {

    private final Long locationId;
    private final String address;

    public LocationResponseDto(Location location) {
        this.locationId = location.getLocationId();
        this.address = location.getAddress();
    }
}
