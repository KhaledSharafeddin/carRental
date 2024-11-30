package com.ozyegin.carRental.service;
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
    public Location addLocation(String name) {
        Location location = new Location();
        location.setName(name);
        return locationRepository.save(location);
    }
}
