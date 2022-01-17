package com.project.karrot.service;

import com.project.karrot.domain.Location;
import com.project.karrot.repository.LocationRepository;

import java.util.List;
import java.util.Optional;

public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Optional<List<Location>> findByName(String name) {
        return locationRepository.findByName(name);
    }
}
