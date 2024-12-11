package com.ozyegin.carRental;

import com.ozyegin.carRental.model.Location;
import com.ozyegin.carRental.repository.LocationRepository;
import com.ozyegin.carRental.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

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