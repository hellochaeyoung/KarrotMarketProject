package com.project.karrot.src.location;

import com.project.karrot.src.location.dto.LocationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationResponseDto find(Long id) {
        Location location = locationRepository.findById(id).orElseThrow();

        return new LocationResponseDto(location);
    }

    public List<LocationResponseDto> findByAddressAll(String address) {
        List<Location> list = locationRepository.findByAddressContains(address).orElseGet(ArrayList::new);

        return list.stream()
                .map(LocationResponseDto::new)
                .collect(Collectors.toList());
    }

    public LocationResponseDto findByAddress(String address) {
        Location location = locationRepository.findByAddress(address).orElseThrow();

        return new LocationResponseDto(location);
    }
}
