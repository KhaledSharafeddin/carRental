package com.ozyegin.carRental.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ozyegin.carRental.model.Car;
import com.ozyegin.carRental.repository.CarRepository;
import com.ozyegin.carRental.repository.ReservationRepository;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    public CarService(CarRepository carRepository, ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Car> searchAvailableCars(String carType, String transmissionType) {
        return carRepository.findByCarTypeAndTransmissionTypeAndStatus(carType, transmissionType, "AVAILABLE");
    }

    public List<Car> getAllRentedCars() {
        List<String> rentedStatuses = List.of("LOANED", "RESERVED");
        return carRepository.findByStatusIn(rentedStatuses);
    }

    public String deleteCar(String carBarcode) {
        Car car = carRepository.findByBarcode(carBarcode)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        List<String> activeStatuses = List.of("ACTIVE", "RESERVED", "LOANED");
        boolean hasActiveReservations = !reservationRepository.findByCar_BarcodeAndStatusIn(carBarcode, activeStatuses).isEmpty();
        if (hasActiveReservations) {
            throw new IllegalStateException("Car cannot be deleted as it has active reservations");
        }
        carRepository.delete(car);
        return "Car deleted successfully";
    }
}