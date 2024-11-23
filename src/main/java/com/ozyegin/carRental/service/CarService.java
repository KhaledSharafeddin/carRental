package com.ozyegin.carRental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ozyegin.carRental.model.Car;
import com.ozyegin.carRental.repository.CarRepository;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // Search Available Cars
    public List<Car> searchAvailableCars(String carType, String transmissionType) {
        return carRepository.findByCarTypeAndTransmissionTypeAndStatus(carType, transmissionType, "AVAILABLE");
    }
    // Get All Rented Cars
    public List<Car> getAllRentedCars() {
        List<String> rentedStatuses = List.of("LOANED", "RESERVED");
        return carRepository.findByStatusIn(rentedStatuses);
    }
}
