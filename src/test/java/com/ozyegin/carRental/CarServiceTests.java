package com.ozyegin.carRental;


import com.ozyegin.carRental.model.Car;
import com.ozyegin.carRental.repository.CarRepository;
import com.ozyegin.carRental.service.CarService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class CarServiceTests {
    private CarRepository carRepository;
    private CarService carService;

    @BeforeEach
    void setUp() {
        carRepository = Mockito.mock(CarRepository.class);
        carService = new CarService(carRepository);
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

    }
}
