package com.project.karrot.dto;

import com.project.karrot.domain.Location;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationRequestDto {

    private Long locationId;
    private String address;

}
