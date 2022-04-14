package com.project.karrot.src.location.dto;

import com.project.karrot.src.location.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LocationResponseDto {

    private Long locationId;
    private String address;

    public LocationResponseDto(Location location) {
        this.locationId = location.getId();
        this.address = location.getAddress();
    }
}
