package com.project.karrot.src.location.dto;

import com.project.karrot.src.location.Location;
import lombok.Getter;

@Getter
public class LocationResponseDto {

    private final Long locationId;
    private final String address;

    public LocationResponseDto(Location location) {
        this.locationId = location.getId();
        this.address = location.getAddress();
    }
}
