package com.ozyegin.carRental;

import com.ozyegin.carRental.model.Car;
import com.ozyegin.carRental.model.Reservation;
import com.ozyegin.carRental.repository.CarRepository;
import com.ozyegin.carRental.repository.ReservationRepository;
import com.ozyegin.carRental.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CarServiceTests {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CarService carService;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
        reservationRepository.deleteAll();
    }

    @Test
    void testSearchAvailableCars() {
        Car car = new Car();
        car.setBrand("Mercedes");
        car.setModel("Maybach");
        car.setType("Standard");
        car.setTransmissionType("Automatic");
        car.setStatus("AVAILABLE");
        carRepository.save(car);

        List<Car> availableCars = carService.searchAvailableCars("Standard", "Automatic");
        assertEquals(1, availableCars.size());
        assertEquals("Mercedes", availableCars.get(0).getBrand());
    }

    @Test
    void testGetAllRentedCars() {
        Car car = new Car();
        car.setBrand("Mercedes");
        car.setModel("Maybach");
        car.setStatus("LOANED");
        carRepository.save(car);

        List<Car> rentedCars = carService.getAllRentedCars();
        assertEquals(1, rentedCars.size());
        assertEquals("Mercedes", rentedCars.get(0).getBrand());
    }

    @Test
    void testDeleteCar() {
        Car car = new Car();
        car.setBarcode("999999");
        carRepository.save(car);

        String result = carService.deleteCar("999999");
        assertEquals("Car deleted successfully", result);
    }

    @Test
    void testDeleteCarWithActiveReservations() {
        Car car = new Car();
        car.setBarcode("999999");
        carRepository.save(car);

        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservationRepository.save(reservation);

        assertThrows(IllegalStateException.class, () -> carService.deleteCar("999999"));
    }

    @Test
    void testDeleteCarNotFound() {
        assertThrows(IllegalArgumentException.class, () -> carService.deleteCar("999999"));
    }
}