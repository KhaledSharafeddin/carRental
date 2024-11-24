package com.ozyegin.carRental.service;

import java.util.List;

import com.ozyegin.carRental.model.Car;
import com.ozyegin.carRental.repository.CarRepository;
import com.ozyegin.carRental.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;


    public CarService(CarRepository carRepository, ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
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

    // Delete a Car
    public String deleteCar(String carBarcode) {
        // 1. Check if the car exists
        Car car = carRepository.findByBarcode(carBarcode)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        // 2. Check for active reservations
        List<String> activeStatuses = List.of("ACTIVE", "RESERVED", "LOANED");
        boolean hasActiveReservations = !reservationRepository.findByCar_BarcodeAndStatusIn(carBarcode, activeStatuses).isEmpty();
        if (hasActiveReservations) {
            throw new IllegalStateException("Car cannot be deleted as it has active reservations");
        }

        // 3. Delete the car
        carRepository.delete(car);

        return "Car deleted successfully";
    }
}
