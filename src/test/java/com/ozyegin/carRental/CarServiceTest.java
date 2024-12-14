// CarServiceTest.java
package com.ozyegin.carRental;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.ozyegin.carRental.model.Car;
import com.ozyegin.carRental.repository.CarRepository;
import com.ozyegin.carRental.repository.ReservationRepository;
import com.ozyegin.carRental.service.CarService;

@SpringBootTest
@ActiveProfiles("test")
public class CarServiceTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CarService carService;

    @BeforeEach
    void setUp() {
        reservationRepository.deleteAll();
        carRepository.deleteAll();
    }

    // TEST SEARCH AVAILABLE CARS
    @Test
    void testSearchAvailableCars() {
        Car car = new Car();
        car.setBrand("Mercedes");
        car.setModel("Maybach");
        car.setCarType("Standard");
        car.setTransmissionType("Automatic");
        car.setStatus("AVAILABLE");
        carRepository.save(car);

        List<Car> availableCars = carService.searchAvailableCars("Standard", "Automatic");
        assertNotNull(availableCars);
        assertEquals(1, availableCars.size());
        assertEquals("Mercedes", availableCars.get(0).getBrand());
    }

    // TEST GET ALL RENTED CARS
    @Test
    void testGetAllRentedCars() {
        Car car = new Car();
        car.setBrand("Mercedes");
        car.setModel("Maybach");
        car.setStatus("LOANED");
        carRepository.save(car);

        List<Car> rentedCars = carService.getAllRentedCars();
        assertNotNull(rentedCars);
        assertEquals(1, rentedCars.size());
        assertEquals("Mercedes", rentedCars.get(0).getBrand());
    }

    // TEST DELETE CAR
    @Test
    void testDeleteCar() {
        Car car = new Car();
        car.setBarcode("999999");
        carRepository.save(car);

        String result = carService.deleteCar("999999");
        assertEquals("Car deleted successfully", result);
        assertTrue(carRepository.findByBarcode("999999").isEmpty());
    }
}