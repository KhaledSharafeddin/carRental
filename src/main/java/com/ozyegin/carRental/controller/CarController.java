package com.ozyegin.carRental.controller;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ozyegin.carRental.model.Car;
import com.ozyegin.carRental.service.CarService;
@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;
    public CarController(CarService carService) {
        this.carService = carService;
    }
    @GetMapping("/available")
    public ResponseEntity<List<Car>> searchAvailableCars(
        @RequestParam String carType,
        @RequestParam String transmissionType
    ) {
        List<Car> availableCars = carService.searchAvailableCars(carType, transmissionType);

        if(availableCars.isEmpty()) { 
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).build();
    }
    @GetMapping("/rented")
    public ResponseEntity<List<Car>> getAllRentedCars() {
        List<Car> rentedCars = carService.getAllRentedCars();
        if (rentedCars.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(rentedCars);
    }
    @DeleteMapping("/{carBarcode}")
    public ResponseEntity<String> deleteCar(@PathVariable String carBarcode) {
        try {
            String message = carService.deleteCar(carBarcode);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting the car");
        }
    }
}
