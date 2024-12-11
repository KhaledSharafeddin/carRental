package com.ozyegin.carRental.service;

import com.ozyegin.carRental.dto.LocationInputDTO;
import com.ozyegin.carRental.dto.LocationOutputDTO;
import com.ozyegin.carRental.model.Location;
import com.ozyegin.carRental.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public LocationOutputDTO addLocation(LocationInputDTO locationInputDTO) {
        Location location = new Location();
        location.setCode(locationInputDTO.getCode());
        location.setName(locationInputDTO.getName());
        location.setAddress(locationInputDTO.getAddress());
        location = locationRepository.save(location);
        return mapToOutputDTO(location);
    }

    public LocationOutputDTO getLocationByCode(String code) {
        Location location = locationRepository.findById(Integer.valueOf(code)).orElseThrow(() -> new IllegalArgumentException("Location not found"));
        return mapToOutputDTO(location);
    }

    public void deleteLocation(String code) {
        locationRepository.deleteById(Integer.valueOf(code));
    }

    private LocationOutputDTO mapToOutputDTO(Location location) {
        LocationOutputDTO locationOutputDTO = new LocationOutputDTO();
        locationOutputDTO.setCode(location.getCode());
        locationOutputDTO.setName(location.getName());
        locationOutputDTO.setAddress(location.getAddress());
        return locationOutputDTO;
    }
}