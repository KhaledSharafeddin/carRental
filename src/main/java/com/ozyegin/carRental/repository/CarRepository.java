package com.ozyegin.carRental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozyegin.carRental.model.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {

    // Method to find a car by barcode and status
    Optional<Car> findByBarcodeAndStatus(String barcode, String status);

    // Method to find a car by barcode
    Optional<Car> findByBarCode(String barcode);

    // Method to find cars by type, transmission, and status
    List<Car> findByCarTypeAndTransmissionTypeAndStatus(String carType, String transmissionType, String status);

    // Find cars with status LOANED or RESERVED
    List<Car> findByStatusIn(List<String> statuses);

    
}
