package com.ozyegin.carRental;

import com.ozyegin.carRental.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.ozyegin.carRental.repository.LocationRepository;
import com.ozyegin.carRental.service.LocationService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class LocationServiceTest {
    private LocationService locationService;
    private LocationRepository locationRepository;

    @BeforeEach
    void setUp() {
        locationRepository = Mockito.mock(LocationRepository.class);
        locationService = new LocationService(locationRepository);
    }
    @Test
    void testAddLocation() {
        int locationId = 1;
        String locationName = "Taksim Square";
        Location location = new Location();
        location.setId(locationId);
        location.setName(locationName);

        Mockito.when(locationRepository.save(any(Location.class))).thenReturn(location);
        Location result = locationService.addLocation(locationName);

        Mockito.verify(locationRepository).save(any(Location.class));
        assertNotNull(result);
        assertEquals(locationId, result.getId());
        assertEquals(locationName, result.getName());
    }
}
