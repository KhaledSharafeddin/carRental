package com.ozyegin.carRental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ozyegin.carRental.model.Location;
import com.ozyegin.carRental.repository.LocationRepository;
import com.ozyegin.carRental.service.LocationService;

@SpringBootTest
@ActiveProfiles("test")
public class LocationServiceTest {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        locationRepository.deleteAll();
    }

    // TEST ADD LOCATION
    @Test
    void testAddLocation() {
        String locationName = "Taksim Square";
        Location result = locationService.addLocation(locationName);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(locationName, result.getName());
    }
}