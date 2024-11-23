package com.ozyegin.carRental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ozyegin.carRental.model.Car;
import java.util.List;

public class CarRepository {
    public interface CarRepository extends JpaRepository<Car, Long> {
        List<Car> findByCarTypeAndTransmissionTypeAndStatus(String carType, String transmissionType, String status);
    }
}
