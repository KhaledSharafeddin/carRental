package com.ozyegin.carRental.service;
import com.ozyegin.carRental.model.Car;
import com.ozyegin.carRental.model.Equipment;
import com.ozyegin.carRental.model.Location;
import com.ozyegin.carRental.model.Member;
import com.ozyegin.carRental.model.Reservation;
import com.ozyegin.carRental.repository.CarRepository;
import com.ozyegin.carRental.repository.EquipmentRepository;
import com.ozyegin.carRental.repository.LocationRepository;
import com.ozyegin.carRental.repository.MemberRepository;
import com.ozyegin.carRental.repository.ReservationRepository;
import com.ozyegin.carRental.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReservationService {

    private final CarRepository carRepository;
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;
    private final EquipmentRepository equipmentRepository;
    private final ServiceRepository serviceRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(CarRepository carRepository, MemberRepository memberRepository,
            LocationRepository locationRepository,
            EquipmentRepository equipmentRepository,
            ServiceRepository serviceRepository,
            ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.memberRepository = memberRepository;
        this.locationRepository = locationRepository;
        this.equipmentRepository = equipmentRepository;
        this.serviceRepository = serviceRepository;
        this.reservationRepository = reservationRepository;
    }

public Reservation makeReservation(
        String carBarcode,
        Integer dayCount,
        Integer memberId,
        String pickupLocationCode,
        String dropoffLocationCode,
        List<Integer> equipmentIds,
        List<Integer> serviceIds,
        Date reservationDate,
        Date pickUpDate,
        Date dropOffDate) {
    Car car = carRepository.findByBarcodeAndStatus(carBarcode, "AVAILABLE")
            .orElseThrow(() -> new IllegalStateException("Car not available"));

    Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

    Location pickupLocation = locationRepository.findByCode(pickupLocationCode)
            .orElseThrow(() -> new IllegalArgumentException("Invalid pickup location"));

    Location dropoffLocation = locationRepository.findByCode(dropoffLocationCode)
            .orElseThrow(() -> new IllegalArgumentException("Invalid dropoff location"));

    List<Equipment> equipment = equipmentIds != null ? equipmentRepository.findAllById(equipmentIds) : List.of();
    List<com.ozyegin.carRental.model.Service> services = serviceIds != null ? serviceRepository.findAllById(serviceIds) : List.of();

    double equipmentCost = 0;
    for (Equipment eq : equipment) {
        equipmentCost += eq.getPrice();
    }

    double serviceCost = 0;
    for (com.ozyegin.carRental.model.Service srv : services) {
        serviceCost += srv.getPrice();
    }

    double totalAmount = (dayCount * car.getDailyPrice()) + equipmentCost + serviceCost;

    Reservation reservation = new Reservation();
    reservation.setReservationNumber(generateReservationNumber());
    reservation.setCreation(reservationDate);
    reservation.setPickupDate(pickUpDate);
    reservation.setDropOffDate(dropOffDate);
    reservation.setPickupLocation(pickupLocation);
    reservation.setDropOffLocation(dropoffLocation);
    reservation.setStatus("ACTIVE");
    reservation.setCar(car);
    reservation.setMember(member);
    reservation.setEquipment(equipment);
    reservation.setServices(services);

    car.setStatus("LOANED");

    reservationRepository.save(reservation);
    carRepository.save(car);

    return reservation;
}

    // ADD SERVICE TO RESERVATION
    @Transactional
    public boolean addServiceToReservation(String reservationNumber, Integer serviceId) {
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        com.ozyegin.carRental.model.Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));

        if (reservation.getServices().contains(service)) {
            return false;
        }
        reservation.getServices().add(service);

        reservationRepository.save(reservation);

        return true;
    }

    // ADD EQUIPMENT TO RESERVATION
    @Transactional
    public boolean addEquipmentToReservation(String reservationNumber, Integer equipmentId) {
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found"));

        if (reservation.getEquipment().contains(equipment)) {
            return false;
        }
        reservation.getEquipment().add(equipment);

        reservationRepository.save(reservation);

        return true;
    }

    // RETURN CAR
    public String returnCar(String reservationNumber, int mileage) {
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation number"));

        Car car = reservation.getCar();
        car.setMileage(car.getMileage() + mileage);
        car.setStatus("AVAILABLE");

        reservation.setStatus("COMPLETED");

        carRepository.save(car);
        reservationRepository.save(reservation);

        return "Car returned successfully";
    }

    // CANCEL RESERVATION
    public String cancelReservation(String reservationNumber) {
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation number"));

        Car car = reservation.getCar();
        car.setStatus("AVAILABLE");

        reservation.setStatus("CANCELLED");

        carRepository.save(car);
        reservationRepository.save(reservation);

        return "Reservation cancelled successfully";
    }

    // DELETE RESERVATION
    public String deleteReservation(String reservationNumber) {
        // 1. Find the reservation by its number
        Reservation reservation = reservationRepository.findByReservationNumber(reservationNumber)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if (!"CANCELLED".equals(reservation.getStatus())) {
            throw new IllegalStateException("Reservation cannot be deleted unless its status is 'CANCELLED'");
        }

        reservationRepository.delete(reservation);

        return "Reservation deleted successfully";
    }

    private String generateReservationNumber() {
        return String.format("%08d", (int) (Math.random() * 100000000));
    }
}