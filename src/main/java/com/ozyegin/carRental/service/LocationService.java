package com.ozyegin.carRental.service;

import com.ozyegin.carRental.dto.LocationInputDTO;
import com.ozyegin.carRental.dto.LocationOutputDTO;
import com.ozyegin.carRental.model.Location;
import com.ozyegin.carRental.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public LocationOutputDTO addLocation(LocationInputDTO locationInputDTO) {
        Location location = new Location();
        location.setName(locationInputDTO.getName());
        location = locationRepository.save(location);

        LocationOutputDTO locationOutputDTO = new LocationOutputDTO();
        locationOutputDTO.setId(location.getId());
        locationOutputDTO.setName(location.getName());

        return locationOutputDTO;
    }

    public LocationOutputDTO getLocationByCode(String code) {
        Integer locationCode = Integer.valueOf(code);
        Optional<Location> location = locationRepository.findByCode(locationCode);
        if (location.isPresent()) {
            Location loc = location.get();
            LocationOutputDTO dto = new LocationOutputDTO();
            dto.setId(loc.getId());
            dto.setName(loc.getName());
            dto.setCode(loc.getCode()); // Set the code field
            return dto;
        } else {
            throw new IllegalArgumentException("Location not found");
        }
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