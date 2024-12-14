// LocationServiceTest.java
package com.ozyegin.carRental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ozyegin.carRental.dto.LocationInputDTO;
import com.ozyegin.carRental.dto.LocationOutputDTO;
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
        LocationInputDTO locationInputDTO = new LocationInputDTO();
        locationInputDTO.setName(locationName);

        LocationOutputDTO result = locationService.addLocation(locationInputDTO);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(locationName, result.getName());
    }

    // TEST GET LOCATION BY CODE
    @Test
    void testGetLocationByCode() {
        Location location = new Location();
        location.setName("Taksim Square");
        location.setCode(40); // Ensure the code is set
        location = locationRepository.save(location);

        LocationOutputDTO result = locationService.getLocationByCode(String.valueOf(location.getCode()));

        assertNotNull(result);
        assertEquals(location.getCode(), result.getCode()); // Compare the code field
        assertEquals(location.getName(), result.getName());
    }

    // TEST GET LOCATION BY CODE - NOT FOUND
    @Test
    void testGetLocationByCodeNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            locationService.getLocationByCode("999");
        });
    }

    // TEST DELETE LOCATION
    @Test
    void testDeleteLocation() {
        Location location = new Location();
        location.setName("Taksim Square");
        location = locationRepository.save(location);

        final Integer locationId = location.getId();
        locationService.deleteLocation(locationId.toString());

        assertThrows(IllegalArgumentException.class, () -> {
            locationService.getLocationByCode(locationId.toString());
        });
    }
}