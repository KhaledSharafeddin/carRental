package com.ozyegin.carRental;

import com.ozyegin.carRental.model.Car;
import com.ozyegin.carRental.model.Reservation;
import com.ozyegin.carRental.repository.CarRepository;
import com.ozyegin.carRental.repository.ReservationRepository;
import com.ozyegin.carRental.service.CarService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CarServiceTests {
    private CarRepository carRepository;
    private ReservationRepository reservationRepository;
    private CarService carService;

    @BeforeEach
    void setUp() {
        carRepository = Mockito.mock(CarRepository.class);
        reservationRepository = Mockito.mock(ReservationRepository.class);
        carService = new CarService(carRepository, reservationRepository);
    }

    @Test
    void testSearchAvailableCars() {
        Car car = new Car();
        car.setBrand("Mercedes");
        car.setModel("Maybach");
        car.setType("Standard");
        car.setTransmissionType("Automatic");
        car.setStatus("AVAILABLE");

        when(carRepository.findByCarTypeAndTransmissionTypeAndStatus("Standard", "Automatic", "AVAILABLE"))
                .thenReturn(Collections.singletonList(car));

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

        when(carRepository.findByStatusIn(List.of("LOANED", "RESERVED")))
                .thenReturn(Collections.singletonList(car));

        List<Car> rentedCars = carService.getAllRentedCars();
        assertEquals(1, rentedCars.size());
        assertEquals("Toyota", rentedCars.get(0).getBrand());
    }

    @Test
    void testDeleteCar() {
        Car car = new Car();
        car.setBarcode("999999");

        when(carRepository.findByBarCode("999999")).thenReturn(Optional.of(car));
        when(reservationRepository.findByCar_BarcodeAndStatusIn("999999", List.of("ACTIVE", "RESERVED", "LOANED")))
                .thenReturn(Collections.emptyList());

        String result = carService.deleteCar("999999");
        assertEquals("Car deleted successfully", result);
    }

    @Test
    void testDeleteCarWithActiveReservations() {
        Car car = new Car();
        car.setBarcode("999999");

        when(carRepository.findByBarCode("999999")).thenReturn(Optional.of(car));
        when(reservationRepository.findByCar_BarcodeAndStatusIn("999999", List.of("ACTIVE", "RESERVED", "LOANED")))
                .thenReturn(Collections.singletonList(new Reservation()));

        assertThrows(IllegalStateException.class, () -> carService.deleteCar("999999"));
    }

    @Test
    void testDeleteCarNotFound() {
        when(carRepository.findByBarCode("999999")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> carService.deleteCar("999999"));
    }
}