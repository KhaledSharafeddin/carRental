package com.ozyegin.carRental.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ozyegin.carRental.model.Car;
public interface CarRepository extends JpaRepository<Car, Integer> {
    Optional<Car> findByBarcodeAndStatus(String barcode, String status);
    Optional<Car> findByBarcode(String barcode);
    List<Car> findByCarTypeAndTransmissionTypeAndStatus(String carType, String transmissionType, String status);
    List<Car> findByStatusIn(List<String> statuses);
}
