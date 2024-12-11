package com.ozyegin.carRental.service;

import com.ozyegin.carRental.dto.CarInputDTO;
import com.ozyegin.carRental.dto.CarOutputDTO;
import com.ozyegin.carRental.model.Car;
import com.ozyegin.carRental.repository.CarRepository;
import com.ozyegin.carRental.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public CarService(CarRepository carRepository, ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
    }

    public CarOutputDTO addCar(CarInputDTO carInputDTO) {
        Car car = new Car();
        car.setBarcode(carInputDTO.getBarcode());
        car.setStatus(carInputDTO.getStatus());
        car.setDailyPrice(carInputDTO.getDailyPrice());
        car.setMileage(carInputDTO.getMileage());
        car.setCarType(carInputDTO.getCarType());
        car.setTransmissionType(carInputDTO.getTransmissionType());
        car = carRepository.save(car);
        return mapToOutputDTO(car);
    }

    public CarOutputDTO updateCar(int id, CarInputDTO carInputDTO) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Car not found"));
        car.setBarcode(carInputDTO.getBarcode());
        car.setStatus(carInputDTO.getStatus());
        car.setDailyPrice(carInputDTO.getDailyPrice());
        car.setMileage(carInputDTO.getMileage());
        car.setCarType(carInputDTO.getCarType());
        car.setTransmissionType(carInputDTO.getTransmissionType());
        car = carRepository.save(car);
        return mapToOutputDTO(car);
    }

    public CarOutputDTO getCarById(int id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Car not found"));
        return mapToOutputDTO(car);
    }

    public List<CarOutputDTO> getAllCars() {
        return carRepository.findAll().stream().map(this::mapToOutputDTO).collect(Collectors.toList());
    }
public List<CarOutputDTO> searchAvailableCars(String carType, String transmissionType) {
    List<Car> cars = carRepository.findByCarTypeAndTransmissionTypeAndStatus(carType, transmissionType, "AVAILABLE");
    return cars.stream().map(this::mapToOutputDTO).collect(Collectors.toList());
}

    public void deleteCar(int id) {
        carRepository.deleteById(id);
    }

    private CarOutputDTO mapToOutputDTO(Car car) {
        CarOutputDTO carOutputDTO = new CarOutputDTO();
        carOutputDTO.setId(car.getId());
        carOutputDTO.setBarcode(car.getBarcode());
        carOutputDTO.setStatus(car.getStatus());
        carOutputDTO.setDailyPrice(car.getDailyPrice());
        carOutputDTO.setMileage(car.getMileage());
        carOutputDTO.setCarType(car.getCarType());
        carOutputDTO.setTransmissionType(car.getTransmissionType());
        return carOutputDTO;
    }
}